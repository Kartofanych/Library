package com.example.samatdanilonelove.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.samatdanilonelove.Kniga_about;
import com.example.samatdanilonelove.PageFragmentforSearch;
import com.example.samatdanilonelove.R;
import com.example.samatdanilonelove.models.History;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.zip.Inflater;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

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
    private LayoutInflater infl;
    public SharedPreferences mSettings;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private FirebaseFirestore dbf = FirebaseFirestore.getInstance();
    private CollectionReference booksf;
    private TextView not_at_all;
    private DatabaseReference history_person;
    private LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);;
    private LinearLayout all_fav;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        infl = getLayoutInflater();
        all_fav = root.findViewById(R.id.all_favourite);
        booksf = dbf.collection("books");
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        layoutParams.setMargins(20, 50, 20, 0);
        history_person = db.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("History");
        not_at_all = root.findViewById(R.id.pusto);
        if(mSettings.getString(APP_PREFERENCES_FAVOURITES,"123").equals("123")){
            not_at_all.setVisibility(View.VISIBLE);
        }

        booksf.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (QueryDocumentSnapshot i : queryDocumentSnapshots) {
                    if (i.get("about") != null && mSettings.getString(APP_PREFERENCES_FAVOURITES,"123").contains(i.getId())) {
                        View kitap_view = infl.inflate(R.layout.default_kitap_in_search, null, true);
                        kitap_view.setLayoutParams(layoutParams);
                        TextView kitap_name = kitap_view.findViewById(R.id.name);
                        TextView kitap_autor = kitap_view.findViewById(R.id.autor);
                        TextView genre = kitap_view.findViewById(R.id.genre);
                        ImageView kitap_img = kitap_view.findViewById(R.id.KitapImage);
                        final String name = i.get("name").toString();
                        final String autor = i.get("autor").toString();
                        final String Genre = i.get("genre").toString();
                        final String about = i.get("about").toString();
                        final String img = i.get("img").toString();
                        final Long pop = (Long) i.get("pop");
                        kitap_name.setText(name);
                        genre.setText(Genre);
                        kitap_autor.setText(autor);
                        final String b = i.getId();
                        try {
                            Glide.with(getContext()).load(img).into(kitap_img);
                        } catch (Exception e) {

                        }
                        kitap_view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bigClick(v, name, autor, Genre, about, img, Integer.parseInt(String.valueOf(pop))-1, b);
                                Log.d("where", "1");
                            }
                        });
                        all_fav.addView(kitap_view);
                    }
                }
            }
        });
        return root;
    }

    private void bigClick(View v, String name, String autor, String genre, String about, String img, int pop, String b){
        Log.d("1",b);
        Map<String, Object> data = new HashMap<>();

        data.put("pop", pop);
        data.put("name", name);
        data.put("autor", autor);
        data.put("about", about);
        data.put("img", img);
        data.put("genre", genre);

        booksf.document(b).set(data);

        History history = new History();
        history.setName(name);
        history.setGenre(genre);
        history.setImg(img);
        history.setAbout(about);
        history.setAutor(autor);
        history.setPop((long) pop);
        history_person.push().setValue(history);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_HOWMANY,"1");
        editor.apply();

        Intent intent = new Intent(getContext(), Kniga_about.class);
        intent.putExtra("name", name);
        intent.putExtra("autor", autor);
        intent.putExtra("genre", genre);
        intent.putExtra("about", about);
        intent.putExtra("img", img);
        intent.putExtra("pop", pop);
        intent.putExtra("id",b);
        startActivityForResult(intent, 1);
        getActivity().overridePendingTransition(R.anim.left_anim, R.anim.alpha_to_zero);

    }


}