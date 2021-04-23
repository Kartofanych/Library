package com.example.samatdanilonelove;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class PageFragmentForSettings extends Fragment {



    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.3F);
    int pageNumber;

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
    private View first, second, third;
    public static PageFragmentForSettings newInstance(int page) {
        PageFragmentForSettings pageFragment = new PageFragmentForSettings();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        if(pageNumber == 0) {
            view = inflater.inflate(R.layout.settings_profile, null);

            mSettings = getContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            Log.d("1", "NAME: "+mSettings.getString(APP_PREFERENCES_NAME, "")+ "   REGORNO: " + mSettings.getString(APP_PREFERENCES_REG, "")+"   EMAIL: "+mSettings.getString(APP_PREFERENCES_EMAIL, "")+"   FORM: "+mSettings.getString(APP_PREFERENCES_FORM, "")+"   PASSWORD: "+mSettings.getString(APP_PREFERENCES_PASSWORD, "")+"   HOWMANYBOOKS: "+mSettings.getString(APP_PREFERENCES_HOWMANY, "") );


            first = view.findViewById(R.id.first);
            second = view.findViewById(R.id.second);
            third = view.findViewById(R.id.third);
            TextView t1 = first.findViewById(R.id.group);
            TextView t2 = second.findViewById(R.id.group);
            t2.setText("История");
            ImageView im2 = second.findViewById(R.id.face_icon);
            im2.setImageResource(R.drawable.history_icon);
            TextView t3 = third.findViewById(R.id.group);
            ImageView im3 = third.findViewById(R.id.face_icon);
            ImageView im31 = third.findViewById(R.id.next);
            im3.setImageResource(R.drawable.exit_icon);
            im31.setVisibility(View.GONE);
            t3.setText("Выйти");
            first.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    touchEffect(v);
                    View view1 = v.findViewById(R.id.face_icon);
                    Intent intent = new Intent(getContext(), Set1.class);
// Pass data object in the bundle and populate details activity.
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(getActivity(), view1, "trans");
                    startActivity(intent, options.toBundle());




                }
            });
            second.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View view1 = v.findViewById(R.id.face_icon);
                    touchEffect(v);
                    Intent intent = new Intent(getContext(), Set2.class);
// Pass data object in the bundle and populate details activity.
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(getActivity(), view1, "trans");
                    startActivity(intent, options.toBundle());




                }
            });
            third.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    touchEffect(v);
                    openQuitDialog();
                }
            });

        }
        else{
            view = inflater.inflate(R.layout.about_us, null);

        }
        return view;
    }

    public static void touchEffect(View view){
        view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.setBackgroundColor(Color.parseColor("#faedf7"));
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.setBackgroundColor(android.R.color.transparent);
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }

    private void openQuitDialog() {
        final AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                getContext());
        quitDialog.setTitle("Выход");
        quitDialog.setMessage("Вы действительно хотите выйти из аккаунта?");
        quitDialog.setPositiveButton("Выход", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                FirebaseAuth.getInstance().signOut();
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(APP_PREFERENCES_REG, "0");
                editor.putString(APP_PREFERENCES_NAME, "Читатель");
                editor.putString(APP_PREFERENCES_EMAIL, "email");
                editor.putString(APP_PREFERENCES_FORM, "form");
                editor.putString(APP_PREFERENCES_HOWMANY, "0");
                editor.putString(APP_PREFERENCES_PASSWORD, "pass");
                editor.putString(APP_PREFERENCES_FAVOURITES,"123");
                editor.apply();
                startActivity(new Intent(getContext(), Enterance.class));
            }
        });

        quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        quitDialog.show();
    }
}
