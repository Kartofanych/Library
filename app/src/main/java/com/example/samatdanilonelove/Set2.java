package com.example.samatdanilonelove;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;

import com.bumptech.glide.Glide;
import com.example.samatdanilonelove.models.History;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Set2 extends Activity {
    private FirebaseListAdapter<History> adapter;

    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference users;
    private ListView history;
    private int kol = 0;
    private TextView pusto;

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
        setContentView(R.layout.history_settings);

        pusto = findViewById(R.id.pusto);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Log.d("1", "NAME: "+mSettings.getString(APP_PREFERENCES_NAME, "")+ "   REGORNO: " + mSettings.getString(APP_PREFERENCES_REG, "")+"   EMAIL: "+mSettings.getString(APP_PREFERENCES_EMAIL, "")+"   FORM: "+mSettings.getString(APP_PREFERENCES_FORM, "")+"   PASSWORD: "+mSettings.getString(APP_PREFERENCES_PASSWORD, "")+"   HOWMANYBOOKS: "+mSettings.getString(APP_PREFERENCES_HOWMANY, "") );

        if(mSettings.getString(APP_PREFERENCES_HOWMANY,"0").equals("0")){

            pusto.setVisibility(View.VISIBLE);
        }
        else{
            pusto.setVisibility(View.GONE);

        }

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

                        GetHistory();
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


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    private ArrayList<String> ab, pop, image;
    int i;
    public void GetHistory() {
        history = (ListView) findViewById(R.id.list_history);

        kol = 0;
        ab = new ArrayList<String>();
        pop = new ArrayList<String>();
        image = new ArrayList<String>();
        i = 0;
        Query query = FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getUid()).child("History");
//The error said the constructor expected FirebaseListOptions - here you create them:
        FirebaseListOptions<History> options = new FirebaseListOptions.Builder<History>()
                .setQuery(query, History.class)
                .setLayout(R.layout.default_kitap_in_search)
                .build();
        adapter = new FirebaseListAdapter<History>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull final History model, int position) {

                TextView autor = (TextView) v.findViewById(R.id.autor);
                TextView name = (TextView) v.findViewById(R.id.name);
                TextView genre = (TextView) v.findViewById(R.id.genre);
                ImageView img = (ImageView) v.findViewById(R.id.KitapImage);
                try {
                    Glide
                            .with(Set2.this)
                            .load(model.getImg())
                            .into(img);
                } catch (Exception e) {

                }
                Log.d("1", String.valueOf(position));
                // Get references to the views of message.xml
                autor.setText(model.getAutor());
                name.setText(model.getName());
                genre.setText(model.getGenre());
                ab.add(i, model.getAbout());
                pop.add(i, model.getPop().toString());
                image.add(i, model.getImg());
                i++;
                kol++;
            }

        };

        history.setAdapter(adapter);
        history.smoothScrollToPosition(kol);
        SetHistoryChecker();

    }

    private void SetHistoryChecker(){
        history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView autor = (TextView) view.findViewById(R.id.autor);
                TextView name = (TextView) view.findViewById(R.id.name);
                TextView genre = (TextView) view.findViewById(R.id.genre);

                Intent intent = new Intent(Set2.this, Kniga_about.class);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("autor", autor.getText().toString());
                intent.putExtra("genre", genre.getText().toString());
                intent.putExtra("about", ab.get(history.getCount()-position-1));
                intent.putExtra("img", image.get(history.getCount()-position-1));
                intent.putExtra("pop", pop.get(history.getCount()-position-1));
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.left_anim, R.anim.alpha_to_zero);

            }
        });
    }
    
}
