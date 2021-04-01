package com.example.samatdanilonelove.ui.dashboard;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.samatdanilonelove.Enterance;
import com.example.samatdanilonelove.HistoryInfo;
import com.example.samatdanilonelove.R;
import com.example.samatdanilonelove.models.History;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LibraryFragment extends Fragment {

    private LibraryViewModel libraryViewModel;
    private LinearLayout lin_for_genres;
    private LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private LayoutInflater infl;
    private FirebaseDatabase db;
    private DatabaseReference books;
    private String[] all_genres = {"Учебники","Материалы для саморазвития","Материалы по английскому языку","Татарская литература","Классическая литература","Древняя литература"
            };
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_library, container, false);

        db = FirebaseDatabase.getInstance();
        books = db.getReference("Books");

        lin_for_genres = root.findViewById(R.id.lin_for_genres);
        infl = getLayoutInflater();

        for(int i = 0; i < all_genres.length; i++) {
            String nameGenre = all_genres[i];
            layoutParams.setMargins(20, 20, 20, 0);
            //layoutParams.gravity = Gravity.CENTER;
            LinearLayout view = (LinearLayout) infl.inflate(R.layout.default_genre,null,true);
            view.setLayoutParams(layoutParams);
            final LinearLayout kon = view.findViewById(R.id.lin_for_knigas);
            //view.setLayoutParams(layoutParams);

            TextView genre = view.findViewById(R.id.genre);
            genre.setText(nameGenre);
            books.child(all_genres[i]).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot kniga:dataSnapshot.getChildren()) {

                        Log.d("2", kniga.toString());
                        View kitap_view = infl.inflate(R.layout.customkitapforcarusel, null, true);
                        kitap_view.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {//bigClick(v);
                                            }
                                        });
                        TextView kitap_name = kitap_view.findViewById(R.id.name);
                        TextView kitap_autor = kitap_view.findViewById(R.id.autor);
                        ImageView kitap_img = kitap_view.findViewById(R.id.KitapImage);
                        kitap_name.setText(kniga.child("name").getValue(String.class));
                        kitap_autor.setText(kniga.child("autor").getValue(String.class));
                        try {
                            Glide.with(getContext()).load(kniga.child("img").getValue(String.class)).into(kitap_img);
                        } catch (Exception e) {

                        }
                        kon.addView(kitap_view);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



            /*name.setText("");
            TextView autor = view.findViewById(R.id.autor);
            autor.setText("");

            ImageView imageView = view.findViewById(R.id.KitapImage);

            String urll = "";
            urll.trim().replaceAll(" ", "%20");

            try {
                Glide.with(getContext()).load("http://irek.studio/books/books_img/" + urll).into(imageView);
            }catch (Exception e){

            }

*/
            lin_for_genres.addView(view);


        }


        return root;
    }
}