package com.example.samatdanilonelove;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Enterance extends Activity {
    private TextView tv;
    private Animation anim;
    private Button reg;
    private int ch = 1;
    private ConstraintLayout root;


    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference users;
    private String http;
    private RequestQueue mRequestQueue;
    double temp = 0,windSpeed = 0;


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
        setContentView(R.layout.entrance);
        //new AsyncRequest().execute("123", "/ajax", "foo=bar");

        http = "https://api.thecatapi.com/v1/breeds";
        anim = AnimationUtils.loadAnimation(this,R.anim.text_coming);
        tv = (TextView) findViewById(R.id.hello);
        reg = (Button) findViewById(R.id.reg);
        root = findViewById(R.id.root_element);
        mSettings = this.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Log.d("1", "NAME: "+mSettings.getString(APP_PREFERENCES_NAME, "")+ "   REGORNO: " + mSettings.getString(APP_PREFERENCES_REG, "2")+"   EMAIL: "+mSettings.getString(APP_PREFERENCES_EMAIL, "")+"   FORM: "+mSettings.getString(APP_PREFERENCES_FORM, "")+"   PASSWORD: "+mSettings.getString(APP_PREFERENCES_PASSWORD, "")+"   HOWMANYBOOKS: "+mSettings.getString(APP_PREFERENCES_HOWMANY, "") );
            if(mSettings.getString(APP_PREFERENCES_REG, "0").equals("1")){
                reg.setVisibility(View.INVISIBLE);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Enterance.this, MainActivity.class);
                        startActivity(intent);
                    }
                }, 2500);
            }
            else{
                reg.setVisibility(View.VISIBLE);
                reg.startAnimation(anim);
            }




        tv.setText("Добрый вечер, " + mSettings.getString(APP_PREFERENCES_NAME, "") + "!");
        tv.startAnimation(anim);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view1 = findViewById(R.id.trans);
                Intent intent = new Intent(Enterance.this, Registration.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(Enterance.this, view1, "trans");
                startActivity(intent, options.toBundle());
            }
        });

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");


        mRequestQueue = Volley.newRequestQueue(this);
        //getWeather(http);

    }
















    private String[] names, passwords;

    private void getWeather(String url) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, //GET - API-запрос для получение данных
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("");
                    names = new String[jsonArray.length()];
                    passwords = new String[jsonArray.length()];
                    for(int i = 0; i < jsonArray.length(); i++){
                        names[i] = jsonArray.getJSONObject(i).getString("Name");
                        passwords[i] = jsonArray.getJSONObject(i).getString("Pass");
                        Log.d(String.valueOf(i),names[i] + " " + passwords[i]);
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { // в случае возникновеня ошибки
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request); // добавляем запрос в очередь
    }
    private void setWether(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("Name", //editText.getText().toString;
                    "Jonathan");
            postData.put("Pass", //editText.getText().toString;
                    "Software Engineer");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
















    class AsyncRequest extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... arg) {
           //String http = "https://api.thecatapi.com/v1/images/search";
            String http = "http://irek.studio/login_form.php?login='or 1=1 #&password=0";

            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) new URL(http).openConnection();
                connection.setRequestMethod("POST");
                connection.setUseCaches(false);
                connection.setConnectTimeout(1000);
                connection.setReadTimeout(1000);
                connection.connect();
                StringBuilder sb = new StringBuilder();
                if(HttpURLConnection.HTTP_OK == connection.getResponseCode()){
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while((line = bufferedReader.readLine())!= null){
                        sb.append(line);
                        sb.append("\n");
                    }
                    System.out.println(sb.toString());


                }else{
                    System.out.println("failed"+ connection.getResponseCode() + "." + connection.getResponseMessage());
                }

            }catch (Throwable cause){
                cause.printStackTrace();
            }finally {
                if(connection != null){
                    connection.disconnect();
                }
            }
            return http;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }



}
