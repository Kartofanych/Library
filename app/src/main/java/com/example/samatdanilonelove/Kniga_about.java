package com.example.samatdanilonelove;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Kniga_about extends Activity {

    private String name, autor, img_url, about, genre, id;
    private TextView Name, Autor, About;
    private ImageView zeroTwo;
    private ImageButton back_to_search;
    private ImageView in_izbr;
    private int a = 0;
    private FirebaseDatabase db;
    private DatabaseReference favourites;
    private FirebaseAuth auth;
    private SharedPreferences mSettings;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kniga_about);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Bundle extras = getIntent().getExtras();
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        in_izbr = findViewById(R.id.in_izbr);
        favourites = db.getReference("Users").child(auth.getCurrentUser().getUid());
        if (extras != null) {
            name = extras.getString("name");
            autor = extras.getString("autor");
            about = extras.getString("about");
            img_url = extras.getString("img");
            genre = extras.getString("genre");
            id = extras.getString("id");

        }

        if(mSettings.getString(APP_PREFERENCES_FAVOURITES,"123").contains(id)){
            in_izbr.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_favorite_24));
        }

        zeroTwo = findViewById(R.id.kitap_image);
        Name = findViewById(R.id.name);
        Autor = findViewById(R.id.autor);
        About = findViewById(R.id.about);
        back_to_search = findViewById(R.id.back_to_search);

        back_to_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        try {
            Glide.with(this).load(img_url).into(zeroTwo);
        } catch (Exception e) {

        }

        Name.setText(name);
        Autor.setText(autor);
        About.setText(about);

        in_izbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSettings.getString(APP_PREFERENCES_FAVOURITES,"123").contains(id)){
                    in_izbr.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                    SharedPreferences.Editor editor = mSettings.edit();
                    String s = mSettings.getString(APP_PREFERENCES_FAVOURITES,"123").replaceAll(id,"");
                    editor.putString(APP_PREFERENCES_FAVOURITES, s);
                    editor.apply();
                }else {
                    in_izbr.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_favorite_24));
                    String s = mSettings.getString(APP_PREFERENCES_FAVOURITES, "123") + id;
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putString(APP_PREFERENCES_FAVOURITES, s);
                    editor.apply();
                }
                Log.d("1",mSettings.getString(APP_PREFERENCES_FAVOURITES,"123"));
            }
        });










    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
