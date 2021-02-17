package com.example.samatdanilonelove;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.samatdanilonelove.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Set1 extends Activity {
    private EditText email, name, form, password;
    private RadioButton radioButton1,radioButton2,radioButton3;
    private String first_password, first_name, first_form;
    private int[] fuckit = new int[3];
    private int already = 0;
    private Button change;
    private Animation anim,anim1;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference users;

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_REG = "2";
    public static final String APP_PREFERENCES_NAME = "Читатель";
    public static final String APP_PREFERENCES_EMAIL = "@gmail.com";
    public static final String APP_PREFERENCES_FORM = "form";
    public static final String APP_PREFERENCES_PASSWORD = "pass";
    public static final String APP_PREFERENCES_HOWMANY = "0";
    public static final String APP_PREFS_NAME = SyncStateContract.Constants.class.getPackage().getName();
    public static final String APP_CACHE_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/Android/data/" + APP_PREFS_NAME + "/cache/";

    public SharedPreferences mSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_info_settings);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        form = findViewById(R.id.form);
        password = findViewById(R.id.password);
        radioButton1 = findViewById(R.id.radio_for_name);
        radioButton2 = findViewById(R.id.radio_for_form);
        radioButton3 = findViewById(R.id.radio_for_password);
        change = findViewById(R.id.change);

        fuckit[0] = 1;
        fuckit[1] = 1;
        fuckit[2] = 1;

        anim = AnimationUtils.loadAnimation(this,R.anim.text_coming);
        anim1 =  AnimationUtils.loadAnimation(this,R.anim.go_back);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Log.d("1", "NAME: "+mSettings.getString(APP_PREFERENCES_NAME, "")+ "   REGORNO: " + mSettings.getString(APP_PREFERENCES_REG, "2")+"   EMAIL: "+mSettings.getString(APP_PREFERENCES_EMAIL, "")+"   FORM: "+mSettings.getString(APP_PREFERENCES_FORM, "")+"   PASSWORD: "+mSettings.getString(APP_PREFERENCES_PASSWORD, "") );

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");
        email.setText(mSettings.getString(APP_PREFERENCES_EMAIL,""));
        email.setFocusable(false);
        email.setLongClickable(false);
        name.setText(mSettings.getString(APP_PREFERENCES_NAME,""));
        form.setText(mSettings.getString(APP_PREFERENCES_FORM,""));
        password.setText(mSettings.getString(APP_PREFERENCES_PASSWORD,""));

        first_name = mSettings.getString(APP_PREFERENCES_NAME,"");
        first_form = mSettings.getString(APP_PREFERENCES_FORM,"");
        first_password = mSettings.getString(APP_PREFERENCES_PASSWORD,"");

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                fuckit[0] = 1;
                if(s.toString().equals(first_name)){
                    radioButton1.setChecked(false);
                }else
                if(!s.toString().equals(first_name)){
                    fuckit[0] = 0;
                    radioButton1.setChecked(true);
                }
            }
        });

        form.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fuckit[1] = 1;
                if(!form.getText().toString().equals(first_form)){
                    radioButton2.setChecked(true);
                    fuckit[1] = 0;
                }
                if(form.getText().toString().equals(first_form)){
                    radioButton2.setChecked(false);
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fuckit[2] = 1;
                if(!password.getText().toString().equals(first_password)){
                    fuckit[2] = 0;
                    radioButton3.setChecked(true);
                }
                if(password.getText().toString().equals(first_password)){
                    radioButton3.setChecked(false);
                }
            }
        });

        radioButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && already == 0){
                    change.setVisibility(View.VISIBLE);
                    change.startAnimation(anim);
                    already = 1;
                }
                if(!radioButton1.isChecked() && !radioButton2.isChecked() && !radioButton3.isChecked()){
                    already = 0;
                    change.startAnimation(anim1);
                    anim1.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            change.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
                fuckit[0] = 0;
            }
        });
        radioButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && already == 0){
                    change.setVisibility(View.VISIBLE);
                    change.startAnimation(anim);
                    already = 1;
                }
                if(!radioButton1.isChecked() && !radioButton2.isChecked() && !radioButton3.isChecked()){
                    already = 0;
                    change.startAnimation(anim1);
                    anim1.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            change.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
                fuckit[1] = 0;
            }
        });

        radioButton3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && already == 0){
                    change.setVisibility(View.VISIBLE);
                    change.startAnimation(anim);
                    already = 1;
                }
                if(!radioButton1.isChecked() && !radioButton2.isChecked() && !radioButton3.isChecked()){
                    already = 0;
                    change.startAnimation(anim1);
                    anim1.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            change.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
                fuckit[2] = 0;
            }
        });
        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radioButton1.isChecked() && fuckit[0] == 0){
                    openQuitDialog(1);
                }
            }
        });
        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radioButton2.isChecked() && fuckit[1] == 0){
                    openQuitDialog(2);

                }
            }
        });
        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radioButton3.isChecked() && fuckit[2] == 0){
                    openQuitDialog(3);

                }

            }
        });




        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                users = FirebaseDatabase.getInstance().getReference("Users").child(auth.getUid());
                users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = new User();
                        user.setEmail(email.getText().toString());
                        user.setForm(form.getText().toString());
                        user.setName(name.getText().toString());
                        user.setPass(password.getText().toString());
                        users.setValue(user);

                        SharedPreferences.Editor editor = mSettings.edit();
                        editor.putString(APP_PREFERENCES_REG, "1");
                        editor.putString(APP_PREFERENCES_NAME, name.getText().toString());
                        editor.putString(APP_PREFERENCES_EMAIL,email.getText().toString());
                        editor.putString(APP_PREFERENCES_FORM,form.getText().toString());
                        editor.putString(APP_PREFERENCES_PASSWORD,password.getText().toString());
                        editor.apply();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




                final FirebaseUser user = auth.getCurrentUser();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.




                AuthCredential credential = EmailAuthProvider
                        .getCredential("user@example.com", "password1234");

// Prompt the user to re-provide their sign-in credentials
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(password.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("1", "Password updated");
                                            } else {
                                                Log.d("1", "Error password not updated");
                                            }
                                        }
                                    });
                                } else {
                                    Log.d("1", "Error auth failed");
                                }
                            }
                        });

            }
        });

    }

    private void openQuitDialog(final int type) {
        final AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                Set1.this);
        quitDialog.setTitle("Выход");
        quitDialog.setMessage("Вы действительно хотите удалить изменения?");
        quitDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(type == 1) {
                    radioButton1.setChecked(false);
                    name.setText(first_name);
                }
                if(type == 2) {
                    radioButton2.setChecked(false);
                    form.setText(first_form);
                }
                if(type == 3) {
                    radioButton3.setChecked(false);
                    password.setText(first_password);
                }
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        quitDialog.show();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |                                   //скрываем нижнюю панель с кнопками
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |                    //скрываем нижнюю панель с кнопками
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |                         //скрываем нижнюю панель с кнопками
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |                           //скрываем нижнюю панель с кнопками
                        View.SYSTEM_UI_FLAG_FULLSCREEN |                                //скрываем нижнюю панель с кнопками
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        //скрываем нижнюю панель с кнопками
    }
}
