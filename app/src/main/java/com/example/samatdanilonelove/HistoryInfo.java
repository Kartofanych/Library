package com.example.samatdanilonelove;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HistoryInfo extends Activity {

    private String name, autor, time;
    private TextView Name, Autor, Time;
    private ImageView zeroTwo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_on_click);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            name = extras.getString("name");
            autor = extras.getString("autor");
            time = extras.getString("time");
            // and get whatever type user account id is
        }
        zeroTwo = findViewById(R.id.kitap_image);
        Name = findViewById(R.id.name);
        Autor = findViewById(R.id.autor);
        Time = findViewById(R.id.time);

        zeroTwo.setVisibility(View.VISIBLE);
        Name.setText(name);
        Autor.setText(autor);
        Time.setText(time);


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
