package com.example.samatdanilonelove;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.samatdanilonelove.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends Activity {
    private EditText email, name, form;
    private Button next;
    private View rel;
    private Animation anim,anim1,anim01,anim11,anim001,anim111;
    private String NAME, EMAIL, FORM,PASS1,PASS2;
    private TextView so;

    private int where = 0;


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
    public static final String APP_PREFERENCES_FAVOURITES = "123";
    public static final String APP_PREFS_NAME = SyncStateContract.Constants.class.getPackage().getName();
    public static final String APP_CACHE_PATH =
            Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/Android/data/" + APP_PREFS_NAME + "/cache/";

    public SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg1);


        rel = findViewById(R.id.for_anim);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        form = findViewById(R.id.form);
        next = findViewById(R.id.next);
        so = findViewById(R.id.so);
        anim = AnimationUtils.loadAnimation(this,R.anim.go_back);
        anim01 = AnimationUtils.loadAnimation(this,R.anim.go_back);
        anim001 = AnimationUtils.loadAnimation(this,R.anim.go_back);
        anim1 = AnimationUtils.loadAnimation(this,R.anim.go_follow);
        anim11 = AnimationUtils.loadAnimation(this,R.anim.go_follow);
        anim111 = AnimationUtils.loadAnimation(this,R.anim.go_follow);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Log.d("1", "NAME: "+mSettings.getString(APP_PREFERENCES_NAME, "")+ "   REGORNO: " + mSettings.getString(APP_PREFERENCES_REG, "2")+"   EMAIL: "+mSettings.getString(APP_PREFERENCES_EMAIL, "")+"   FORM: "+mSettings.getString(APP_PREFERENCES_FORM, "")+"   PASSWORD: "+mSettings.getString(APP_PREFERENCES_PASSWORD, "")+"   HOWMANYBOOKS: "+mSettings.getString(APP_PREFERENCES_HOWMANY, "") );

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((name.getText().toString().length() != 0 && email.getText().toString().length() != 0 && form.getText().toString().length() != 0) && where == 0) {
                    rel.startAnimation(anim);
                    anim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            FORM = form.getText().toString();
                            EMAIL = email.getText().toString();
                            NAME = name.getText().toString();
                            form.setText("");
                            email.setText("");
                            name.setText("");
                            form.setVisibility(View.GONE);
                            email.setHint("Пароль");
                            email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, 0, 0);
                            name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_lock_24, 0, 0, 0);
                            name.setHint("Повторите пароль");
                            so.setText("Еще чуть чуть...");
                            next.setText("Зарегистрироваться");
                            where++;
                            rel.startAnimation(anim1);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
                if(where == 1 && name.getText().toString().length() != 0 && !email.getText().toString().equals(name.getText().toString())){
                    Toast.makeText(Registration.this,"Пароли не совпадают!",Toast.LENGTH_LONG).show();
                }
                if(where == 1 && name.getText().toString().length() != 0 && email.getText().toString().equals(name.getText().toString())){
                    PASS1 = name.getText().toString();
                    PASS2 = PASS1;
                    rel.startAnimation(anim01);
                    auth.createUserWithEmailAndPassword(EMAIL, PASS1)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    User user = new User();
                                    user.setEmail(EMAIL);
                                    user.setPass(name.getText().toString());
                                    user.setName(NAME);
                                    user.setForm(FORM);
                                    rel.setVisibility(View.GONE);
                                    users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            SharedPreferences.Editor editor = mSettings.edit();
                                            editor.putString(APP_PREFERENCES_REG, "1");
                                            editor.putString(APP_PREFERENCES_NAME, NAME);
                                            editor.putString(APP_PREFERENCES_EMAIL,EMAIL);
                                            editor.putString(APP_PREFERENCES_FORM,FORM);
                                            editor.putString(APP_PREFERENCES_PASSWORD,PASS1);
                                            editor.putString(APP_PREFERENCES_HOWMANY, "0");
                                            editor.apply();
                                            Log.d("1", "NAME: "+mSettings.getString(APP_PREFERENCES_NAME, "")+ "   REGORNO: " + mSettings.getString(APP_PREFERENCES_REG, "2")+"   EMAIL: "+mSettings.getString(APP_PREFERENCES_EMAIL, "")+"   FORM: "+mSettings.getString(APP_PREFERENCES_FORM, "")+"   PASSWORD: "+mSettings.getString(APP_PREFERENCES_PASSWORD, "")+"   HOWMANYBOOKS: "+mSettings.getString(APP_PREFERENCES_HOWMANY, "") );

                                            startActivity(new Intent(Registration.this, MainActivity.class));

                                            finish();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull final Exception e) {
                            anim01.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    Toast.makeText(Registration.this, "Что то не так(((", Toast.LENGTH_LONG).show();
                                    where = 2;
                                    rel.startAnimation(anim11);
                                    form.setVisibility(View.VISIBLE);
                                    email.setText(EMAIL);
                                    name.setText(NAME);
                                    form.setText(FORM);
                                    email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mail_icon, 0, 0, 0);
                                    name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_account_circle_24, 0, 0, 0);
                                    so.setText("Где-то ошибка(((");
                                    next.setText("Исправил!");
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                    });
                }
                if(where == 2){
                    rel.startAnimation(anim001);
                            FORM = form.getText().toString();
                            EMAIL = email.getText().toString();
                            NAME = name.getText().toString();

                            anim001.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    rel.startAnimation(anim111);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            auth.createUserWithEmailAndPassword(EMAIL, PASS1)
                                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                        @Override
                                        public void onSuccess(AuthResult authResult) {
                                            User user = new User();
                                            user.setEmail(EMAIL);
                                            user.setPass(PASS1);
                                            user.setName(NAME);
                                            user.setForm(FORM);
                                            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                    .setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    SharedPreferences.Editor editor = mSettings.edit();
                                                    editor.putString(APP_PREFERENCES_REG, "1");
                                                    editor.putString(APP_PREFERENCES_NAME, NAME);
                                                    editor.putString(APP_PREFERENCES_EMAIL,EMAIL);
                                                    editor.putString(APP_PREFERENCES_FORM,FORM);
                                                    editor.putString(APP_PREFERENCES_PASSWORD,PASS1);
                                                    editor.putString(APP_PREFERENCES_HOWMANY, "0");
                                                    editor.apply();
                                                    startActivity(new Intent(Registration.this, MainActivity.class));
                                                    Log.d("1", "NAME: "+mSettings.getString(APP_PREFERENCES_NAME, "")+ "   REGORNO: " + mSettings.getString(APP_PREFERENCES_REG, "2")+"   EMAIL: "+mSettings.getString(APP_PREFERENCES_EMAIL, "")+"   FORM: "+mSettings.getString(APP_PREFERENCES_FORM, "")+"   PASSWORD: "+mSettings.getString(APP_PREFERENCES_PASSWORD, "")+"   HOWMANYBOOKS: "+mSettings.getString(APP_PREFERENCES_HOWMANY, "") );

                                                    finish();
                                                    Toast.makeText(Registration.this, "Регистрация успешна!", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Registration.this, "Опять что то не так(((", Toast.LENGTH_LONG).show();
                                            where = 2;
                                            form.setVisibility(View.VISIBLE);
                                            email.setText(EMAIL);
                                            email.setCompoundDrawablesWithIntrinsicBounds(R.drawable.mail_icon, 0, 0, 0);
                                            name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_account_circle_24, 0, 0, 0);
                                            name.setText(NAME);
                                            form.setText(FORM);

                                            so.setText("Где-то ошибка(((");
                                            next.setText("Исправил!");
                                }
                            });
                }

            }
        });
    }
}
