package com.example.samatdanilonelove;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;

import com.example.samatdanilonelove.models.History;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Set2 extends Activity {
    private FirebaseListAdapter<History> adapter;

    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference users;
    private String name;
    private Integer rank;
    private ListView history;
    private int kol = 0;
    private FloatingActionButton plus;
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

        plus = findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(APP_PREFERENCES_HOWMANY, "1");
                editor.apply();
                FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getUid()).child("History").push().setValue(new History("name","autor"));
            }
        });

                        displayChatMessages();
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
    public void displayChatMessages(){
        history = (ListView)findViewById(R.id.list_history);

        kol = 0;

            Query query = FirebaseDatabase.getInstance().getReference("Users").child(auth.getCurrentUser().getUid()).child("History");
//The error said the constructor expected FirebaseListOptions - here you create them:
            FirebaseListOptions<History> options = new FirebaseListOptions.Builder<History>()
                    .setQuery(query, History.class)
                    .setLayout(R.layout.history_object)
                    .build();
            adapter = new FirebaseListAdapter<History>(options) {
                @Override
                protected void populateView(@NonNull View v, @NonNull History model, int position) {

                    TextView autor = (TextView) v.findViewById(R.id.autor);
                    TextView name = (TextView) v.findViewById(R.id.name);
                    TextView time = (TextView) v.findViewById(R.id.time);
                    // Get references to the views of message.xml
                    autor.setText(model.getMessageText());
                    name.setText(model.getMessageUser());
                    // Format the date before showing it
                    time.setText(DateFormat.format("HH:mm",
                            model.getMessageTime()));
                    kol++;
                }
            };
            history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    TextView autor = (TextView) view.findViewById(R.id.autor);
                    TextView name = (TextView) view.findViewById(R.id.name);
                    TextView time = (TextView) view.findViewById(R.id.time);
                    ImageView img = view.findViewById(R.id.kitap_image);

                    Intent intent = new Intent(Set2.this, Kniga_about.class);
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("autor", autor.getText().toString());
                    intent.putExtra("time", time.getText().toString());
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(Set2.this, img, "trans1");
                    startActivity(intent, options.toBundle());

                }
            });
            history.smoothScrollToPosition(kol);
            history.setAdapter(adapter);
    }
    
}
