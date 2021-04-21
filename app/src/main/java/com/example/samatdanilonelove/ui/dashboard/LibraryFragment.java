package com.example.samatdanilonelove.ui.dashboard;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.samatdanilonelove.Kniga_about;
import com.example.samatdanilonelove.PageFragmentforSearch;
import com.example.samatdanilonelove.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LibraryFragment extends Fragment {

    private LibraryViewModel libraryViewModel;
    private LinearLayout lin_for_genres;
    private LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private LayoutInflater infl;
    private FirebaseDatabase db;
    private DatabaseReference books;
    private ImageButton poisk, back_to_genres, poiskk;
    private int chet = 2;
    private RelativeLayout rel_back;
    private Animation anim_follow, anim_back_head, anim_back_body;
    private Toolbar head;
    private ScrollView body;


    static final int PAGE_COUNT = 2;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private TextView aut, kn;
    private EditText edit_poisk;
    private int state = 1;
    private Drawable text_line, text_line_1;
    private LinearLayout lin, lin1;
    private LottieAnimationView lottie, lottie_first;
    private RelativeLayout rel_anim, rel_anim_first;
    private int j = 0;
    private String[] all_genres = {"Учебники","Материалы для саморазвития","Материалы по английскому языку","Татарская литература","Классическая литература","Древняя литература"
            };
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_library, container, false);

        db = FirebaseDatabase.getInstance();
        books = db.getReference("Books");

        anim_follow = AnimationUtils.loadAnimation(getContext(),R.anim.go_follow);
        anim_back_head = AnimationUtils.loadAnimation(getContext(),R.anim.go_back);
        anim_back_body = AnimationUtils.loadAnimation(getContext(),R.anim.go_back);
        poisk = root.findViewById(R.id.search_but);
        poiskk = root.findViewById(R.id.search_butt);
        rel_back = root.findViewById(R.id.poisk_rel);
        head = root.findViewById(R.id.head);
        body = root.findViewById(R.id.allkarusel);
        back_to_genres = root.findViewById(R.id.back_to_genres);
        rel_anim = root.findViewById(R.id.rel_anim);
        lottie = root.findViewById(R.id.lottie);
        rel_anim_first = root.findViewById(R.id.rel_anim_first);
        lottie_first = root.findViewById(R.id.lottie_first);


        edit_poisk = root.findViewById(R.id.query_edit_text);
        text_line = getResources().getDrawable(R.drawable.text_line);
        text_line_1 = getResources().getDrawable(R.drawable.text_line_1);
        aut = root.findViewById(R.id.po_autoram);
        kn = root.findViewById(R.id.po_knigam);
        pager = (ViewPager) root.findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getFragmentManager());
        pager.setAdapter(pagerAdapter);
        aut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aut.setBackground(text_line);
                kn.setBackground(text_line_1);
                pager.setCurrentItem(1);
            }
        });
        kn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kn.setBackground(text_line);
                aut.setBackground(text_line_1);
                pager.setCurrentItem(0);
            }
        });
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d("1", String.valueOf(position));
                if(position == 0){

                    kn.setBackground(text_line);
                    aut.setBackground(text_line_1);
                    state = 1;

                }
                else
                if(position == 1){

                    aut.setBackground(text_line);
                    kn.setBackground(text_line_1);
                    state = 2;


                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        poisk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    rel_back.setVisibility(View.VISIBLE);
                    rel_back.startAnimation(anim_follow);
                    head.startAnimation(anim_back_head);
                    body.startAnimation(anim_back_body);

                    anim_back_body.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                                body.setVisibility(View.GONE);
                                head.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }
        });
        poiskk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setVisibility(View.GONE);
                rel_anim.setVisibility(View.VISIBLE);
                rel_anim.startAnimation(anim_follow);

                books.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot genre: dataSnapshot.getChildren()){
                            for(final DataSnapshot book: genre.getChildren()){
                                new CountDownTimer(2000, 1000) {

                                    public void onTick(long millisUntilFinished) {
                                    }

                                    public void onFinish() {
                                        rel_anim.startAnimation(anim_back_body);
                                        pager.setVisibility(View.VISIBLE);
                                        pager.startAnimation(anim_follow);
                                        anim_back_body.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {
                                                rel_anim.setVisibility(View.GONE);

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {

                                            }
                                        });
                                    }
                                }.start();

                                if(state == 1) {
                                    lin = pager.findViewById(R.id.search__po_kn);
                                    if(lin.getChildCount()!=0) {
                                        //lin.removeAllViews();
                                    }
                                    if (book.child("name").getValue(String.class).contains(edit_poisk.getText().toString())) {
                                        Log.d("FUUUUCK", book.child("name").getValue(String.class));
                                        View kitap_view = infl.inflate(R.layout.default_kitap_in_search, null, true);
                                        layoutParams.setMargins(20, 50, 20, 0);
                                        kitap_view.setLayoutParams(layoutParams);
                                        TextView kitap_name = kitap_view.findViewById(R.id.name);
                                        TextView kitap_autor = kitap_view.findViewById(R.id.autor);
                                        TextView kitap_genre = kitap_view.findViewById(R.id.genre);
                                        ImageView kitap_img = kitap_view.findViewById(R.id.KitapImage);
                                        final String name = book.child("name").getValue(String.class);
                                        final String autor = book.child("autor").getValue(String.class);
                                        final String Genre = genre.getKey();
                                        final String about = book.child("about").getValue(String.class);
                                        final String img = book.child("img").getValue(String.class);
                                        final Long pop = book.child("pop").getValue(Long.class);
                                        kitap_name.setText(name);
                                        kitap_autor.setText(autor);
                                        kitap_genre.setText(Genre);

                                        try {
                                            Glide.with(getContext()).load(book.child("img").getValue(String.class)).into(kitap_img);
                                        } catch (Exception e) {

                                        }
                                        kitap_view.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {bigClick(v, name, autor,Genre,about,img, j, Integer.parseInt(String.valueOf(pop)));
                                            }
                                        });

                                        lin.addView(kitap_view);
                                    }

                                }else
                                if(state == 2){
                                    lin1 = pager.findViewById(R.id.search__po_aut);
                                    if (book.child("autor").getValue(String.class).contains(edit_poisk.getText().toString())) {
                                        Log.d("FUUUUCK", book.child("autor").getValue(String.class));
                                        View kitap_view = infl.inflate(R.layout.default_kitap_in_search, null, true);
                                        layoutParams.setMargins(20, 50, 20, 0);
                                        kitap_view.setLayoutParams(layoutParams);
                                        TextView kitap_name = kitap_view.findViewById(R.id.name);
                                        TextView kitap_autor = kitap_view.findViewById(R.id.autor);
                                        TextView kitap_genre = kitap_view.findViewById(R.id.genre);
                                        ImageView kitap_img = kitap_view.findViewById(R.id.KitapImage);
                                        final String name = book.child("name").getValue(String.class);
                                        final String autor = book.child("autor").getValue(String.class);
                                        final String Genre = genre.getKey();
                                        final String about = book.child("about").getValue(String.class);
                                        final String img = book.child("img").getValue(String.class);
                                        final Long pop = book.child("pop").getValue(Long.class);
                                        kitap_name.setText(name);
                                        kitap_autor.setText(autor);
                                        kitap_genre.setText(Genre);

                                        try {
                                            Glide.with(getContext()).load(book.child("img").getValue(String.class)).into(kitap_img);
                                        } catch (Exception e) {

                                        }
                                        kitap_view.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {bigClick(v, name, autor,Genre,about,img, j, Integer.parseInt(String.valueOf(pop)));
                                            }
                                        });

                                        lin1.addView(kitap_view);
                                    }

                                }
                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        back_to_genres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                body.setVisibility(View.VISIBLE);
                head.setVisibility(View.VISIBLE);
                body.startAnimation(anim_follow);
                head.startAnimation(anim_follow);
                rel_back.startAnimation(anim_back_body);

                anim_back_body.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rel_back.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        lin_for_genres = root.findViewById(R.id.lin_for_genres);
        infl = getLayoutInflater();

        edit_poisk.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                pager.setVisibility(View.GONE);
                rel_anim.setVisibility(View.VISIBLE);
                rel_anim.startAnimation(anim_follow);

                books.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot genre: dataSnapshot.getChildren()){
                            for(final DataSnapshot book: genre.getChildren()){
                                new CountDownTimer(2000, 1000) {

                                    public void onTick(long millisUntilFinished) {
                                    }

                                    public void onFinish() {
                                        rel_anim.startAnimation(anim_back_body);
                                        pager.setVisibility(View.VISIBLE);
                                        pager.startAnimation(anim_follow);
                                        anim_back_body.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {
                                                rel_anim.setVisibility(View.GONE);

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {

                                            }
                                        });
                                    }
                                }.start();

                                if(state == 1) {
                                    lin = pager.findViewById(R.id.search__po_kn);
                                    if(lin.getChildCount()!=0) {
                                        //lin.removeAllViews();
                                    }
                                    if (book.child("name").getValue(String.class).contains(edit_poisk.getText().toString())) {
                                        Log.d("FUUUUCK", book.child("name").getValue(String.class));
                                        View kitap_view = infl.inflate(R.layout.default_kitap_in_search, null, true);
                                        layoutParams.setMargins(20, 50, 20, 0);
                                        kitap_view.setLayoutParams(layoutParams);
                                        TextView kitap_name = kitap_view.findViewById(R.id.name);
                                        TextView kitap_autor = kitap_view.findViewById(R.id.autor);
                                        TextView kitap_genre = kitap_view.findViewById(R.id.genre);
                                        ImageView kitap_img = kitap_view.findViewById(R.id.KitapImage);
                                        final String name = book.child("name").getValue(String.class);
                                        final String autor = book.child("autor").getValue(String.class);
                                        final String Genre = genre.getKey();
                                        final String about = book.child("about").getValue(String.class);
                                        final String img = book.child("img").getValue(String.class);
                                        final Long pop = book.child("pop").getValue(Long.class);
                                        kitap_name.setText(name);
                                        kitap_autor.setText(autor);
                                        kitap_genre.setText(Genre);

                                        try {
                                            Glide.with(getContext()).load(book.child("img").getValue(String.class)).into(kitap_img);
                                        } catch (Exception e) {

                                        }
                                        kitap_view.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {bigClick(v, name, autor,Genre,about,img, j, Integer.parseInt(String.valueOf(pop)));
                                            }
                                        });

                                        lin.addView(kitap_view);
                                    }

                                }else
                                if(state == 2){
                                    lin1 = pager.findViewById(R.id.search__po_aut);
                                    if (book.child("autor").getValue(String.class).contains(edit_poisk.getText().toString())) {
                                        Log.d("FUUUUCK", book.child("autor").getValue(String.class));
                                        View kitap_view = infl.inflate(R.layout.default_kitap_in_search, null, true);
                                        layoutParams.setMargins(20, 50, 20, 0);
                                        kitap_view.setLayoutParams(layoutParams);
                                        TextView kitap_name = kitap_view.findViewById(R.id.name);
                                        TextView kitap_autor = kitap_view.findViewById(R.id.autor);
                                        TextView kitap_genre = kitap_view.findViewById(R.id.genre);
                                        ImageView kitap_img = kitap_view.findViewById(R.id.KitapImage);
                                        final String name = book.child("name").getValue(String.class);
                                        final String autor = book.child("autor").getValue(String.class);
                                        final String Genre = genre.getKey();
                                        final String about = book.child("about").getValue(String.class);
                                        final String img = book.child("img").getValue(String.class);
                                        final Long pop = book.child("pop").getValue(Long.class);
                                        kitap_name.setText(name);
                                        kitap_autor.setText(autor);
                                        kitap_genre.setText(Genre);

                                        try {
                                            Glide.with(getContext()).load(book.child("img").getValue(String.class)).into(kitap_img);
                                        } catch (Exception e) {

                                        }
                                        kitap_view.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {bigClick(v, name, autor,Genre,about,img, j, Integer.parseInt(String.valueOf(pop)));
                                            }
                                        });

                                        lin1.addView(kitap_view);
                                    }

                                }
                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                return false;
            }
        });





        for(int i = 0; i < all_genres.length; i++) {
            String nameGenre = all_genres[i];
            layoutParams.setMargins(20, 40, 20, 0);
            //layoutParams.gravity = Gravity.CENTER;
            LinearLayout view = (LinearLayout) infl.inflate(R.layout.default_genre,null,true);
            view.setLayoutParams(layoutParams);
            final LinearLayout kon = view.findViewById(R.id.lin_for_knigas);
            //view.setLayoutParams(layoutParams);
            TextView genre = view.findViewById(R.id.genre);
            genre.setText(nameGenre);
            Query query = books.child(all_genres[i]).orderByChild("pop");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    j = 0;
                    for(final DataSnapshot book:dataSnapshot.getChildren()) {
                        body.setVisibility(View.VISIBLE);
                        body.startAnimation(anim_follow);
                        rel_anim_first.startAnimation(anim_back_body);
                        anim_back_body.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                rel_anim_first.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        View kitap_view = infl.inflate(R.layout.customkitapforcarusel, null, true);
                        TextView kitap_name = kitap_view.findViewById(R.id.name);
                        TextView kitap_autor = kitap_view.findViewById(R.id.autor);
                        ImageView kitap_img = kitap_view.findViewById(R.id.KitapImage);
                        final String name = book.child("name").getValue(String.class);
                        final String autor = book.child("autor").getValue(String.class);
                        final String Genre = dataSnapshot.getKey();
                        final String about = book.child("about").getValue(String.class);
                        final String img = book.child("img").getValue(String.class);
                        final Long pop = book.child("pop").getValue(Long.class);
                        kitap_name.setText(name);
                        kitap_autor.setText(autor);
                        try {
                            Glide.with(getContext()).load(book.child("img").getValue(String.class)).into(kitap_img);
                        } catch (Exception e) {

                        }
                        kitap_view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {bigClick(v, name, autor,Genre,about,img, Integer.parseInt(book.getKey()), Integer.parseInt(String.valueOf(pop))-1);
                                    Log.d("where","1");
                            }
                        });
                        kon.addView(kitap_view);
                    }


                    j++;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            lin_for_genres.addView(view);


        }




























        return root;
    }

    private void bigClick(View v, String name, String autor, String genre, String about, String img, int j, int pop){

        books.child(genre).child(String.valueOf(j)).child("pop").setValue(pop);


        Intent intent = new Intent(getContext(), Kniga_about.class);
        intent.putExtra("name", name);
        intent.putExtra("autor", autor);
        intent.putExtra("genre", genre);
        intent.putExtra("about", about);
        intent.putExtra("img", img);
        intent.putExtra("pop", pop);
        startActivityForResult(intent, 1);
        getActivity().overridePendingTransition(R.anim.left_anim, R.anim.alpha_to_zero);

    }

    private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragmentforSearch.newInstance(position);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

    }


}