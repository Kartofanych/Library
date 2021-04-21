package com.example.samatdanilonelove;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Kniga_about extends Activity {

    private String name, autor, img_url, about, genre;
    private TextView Name, Autor, About;
    private ImageView zeroTwo;
    private ImageButton back_to_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kniga_about);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            autor = extras.getString("autor");
            about = extras.getString("about");
            img_url = extras.getString("img");
            genre = extras.getString("genre");
            // and get whatever type user account id is
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
