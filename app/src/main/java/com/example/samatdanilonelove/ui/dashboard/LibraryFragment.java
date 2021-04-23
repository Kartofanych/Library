package com.example.samatdanilonelove.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.Gravity;
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
import com.example.samatdanilonelove.models.History;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LibraryFragment extends Fragment {

    private LibraryViewModel libraryViewModel;
    private LinearLayout lin_for_genres;
    private LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    private LinearLayout kon1,kon2,kon3,kon4, kon5, kon6;
    private LayoutInflater infl;
    private FirebaseDatabase db;
    private FirebaseFirestore dbf = FirebaseFirestore.getInstance();
    private CollectionReference booksf;
    private DatabaseReference books, history_person;
    private ImageButton poisk, back_to_genres, poiskk;
    private int chet = 2;
    private RelativeLayout rel_back;
    private Animation anim_follow, anim_back_head, anim_back_body;
    private Toolbar head;
    private ScrollView body;

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
    private LinearLayout layo1, layo2, layo3, layo4, layo5, layo6;
    static final int PAGE_COUNT = 2;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private TextView aut, kn;
    private EditText edit_poisk;
    private int state = 1;
    private Drawable text_line, text_line_1;
    private LinearLayout lin, lin1;
    private LottieAnimationView lottie, lottie_first;
    private RelativeLayout rel_anim, rel_anim_first, rel_between;
    private int j = 0;
    private int i = 0;
    private int g = 0;
    private ArrayList<History> all_in_mass;
    private String[] all_genres = {"Учебники","Материалы для саморазвития","Материалы по английскому языку","Татарская литература","Классическая литература","Древняя литература"
            };
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_library, container, false);

        db = FirebaseDatabase.getInstance();
        books = db.getReference("Books");

        history_person = db.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("History");


        booksf = dbf.collection("books");


        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        anim_follow = AnimationUtils.loadAnimation(getContext(), R.anim.go_follow);
        anim_back_head = AnimationUtils.loadAnimation(getContext(), R.anim.go_back);
        anim_back_body = AnimationUtils.loadAnimation(getContext(), R.anim.go_back);
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
        rel_between = root.findViewById(R.id.between);
        lin_for_genres = root.findViewById(R.id.lin_for_genres);

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


        infl = getLayoutInflater();
        CreateCarusel();

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d("1", String.valueOf(position));
                if (position == 0) {

                    kn.setBackground(text_line);
                    aut.setBackground(text_line_1);
                    state = 1;

                } else if (position == 1) {

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
                lin = pager.findViewById(R.id.search__po_kn);
                lin1 = pager.findViewById(R.id.search__po_aut);
                lin.removeAllViewsInLayout();
                lin1.removeAllViewsInLayout();

                booksf.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
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
                        for(QueryDocumentSnapshot i:queryDocumentSnapshots){
                            if (state == 1&& i.get("name")!=null) {
                                if (i.get("name").toString().contains(edit_poisk.getText().toString())) {
                                    View kitap_view = infl.inflate(R.layout.default_kitap_in_search, null, true);
                                    layoutParams.setMargins(20, 50, 20, 0);
                                    kitap_view.setLayoutParams(layoutParams);
                                    TextView kitap_name = kitap_view.findViewById(R.id.name);
                                    TextView kitap_autor = kitap_view.findViewById(R.id.autor);
                                    TextView kitap_genre = kitap_view.findViewById(R.id.genre);
                                    ImageView kitap_img = kitap_view.findViewById(R.id.KitapImage);
                                    final String name = i.get("name").toString();
                                    final String autor = i.get("autor").toString();
                                    final String Genre = i.get("genre").toString();
                                    String about1 = "";
                                    if(i.get("about")!= null) {
                                        about1 = i.get("about").toString();
                                    }
                                    final String about = about1;
                                    final String img = i.get("img").toString();
                                    final Long pop = (Long) i.get("pop");
                                    final String b = i.getId();
                                    kitap_name.setText(name);
                                    kitap_autor.setText(autor);
                                    kitap_genre.setText(Genre);

                                    try {
                                        Glide.with(getContext()).load(img).into(kitap_img);
                                    } catch (Exception e) {

                                    }
                                    kitap_view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            bigClick(v, name, autor, Genre, about, img, j, Integer.parseInt(String.valueOf(pop)), b);
                                        }
                                    });

                                    lin.addView(kitap_view);
                                }

                            } else if (state == 2&& i.get("name")!=null) {
                                if (i.get("autor").toString().contains(edit_poisk.getText().toString())) {
                                    View kitap_view = infl.inflate(R.layout.default_kitap_in_search, null, true);
                                    layoutParams.setMargins(20, 50, 20, 0);
                                    kitap_view.setLayoutParams(layoutParams);
                                    TextView kitap_name = kitap_view.findViewById(R.id.name);
                                    TextView kitap_autor = kitap_view.findViewById(R.id.autor);
                                    TextView kitap_genre = kitap_view.findViewById(R.id.genre);
                                    ImageView kitap_img = kitap_view.findViewById(R.id.KitapImage);
                                    final String name = i.get("name").toString();
                                    final String autor = i.get("autor").toString();
                                    final String Genre = i.get("genre").toString();
                                    String about1 = "";
                                    if(i.get("about")!= null) {
                                        about1 = i.get("about").toString();
                                    }
                                    final String about = about1;
                                    final String img = i.get("img").toString();
                                    final Long pop = (Long) i.get("pop");
                                    final String b = i.getId();
                                    kitap_name.setText(name);
                                    kitap_autor.setText(autor);
                                    kitap_genre.setText(Genre);

                                    try {
                                        Glide.with(getContext()).load(img).into(kitap_img);
                                    } catch (Exception e) {

                                    }
                                    kitap_view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            bigClick(v, name, autor, Genre, about, img, j, Integer.parseInt(String.valueOf(pop)), b);
                                        }
                                    });

                                    lin1.addView(kitap_view);
                                }
                            }
                        }
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
                rel_between.setVisibility(View.GONE);
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

        edit_poisk.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                pager.setVisibility(View.GONE);
                rel_anim.setVisibility(View.VISIBLE);
                rel_anim.startAnimation(anim_follow);
                lin = pager.findViewById(R.id.search__po_kn);
                lin1 = pager.findViewById(R.id.search__po_aut);
                lin.removeAllViewsInLayout();
                lin1.removeAllViewsInLayout();

                booksf.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
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
                        for(QueryDocumentSnapshot i:queryDocumentSnapshots){
                            if (state == 1&& i.get("name")!=null) {
                                if (i.get("name").toString().contains(edit_poisk.getText().toString())) {
                                    View kitap_view = infl.inflate(R.layout.default_kitap_in_search, null, true);
                                    layoutParams.setMargins(20, 50, 20, 0);
                                    kitap_view.setLayoutParams(layoutParams);
                                    TextView kitap_name = kitap_view.findViewById(R.id.name);
                                    TextView kitap_autor = kitap_view.findViewById(R.id.autor);
                                    TextView kitap_genre = kitap_view.findViewById(R.id.genre);
                                    ImageView kitap_img = kitap_view.findViewById(R.id.KitapImage);
                                    final String name = i.get("name").toString();
                                    final String autor = i.get("autor").toString();
                                    final String Genre = i.get("genre").toString();
                                    String about1 = "";
                                    if(i.get("about")!= null) {
                                       about1 = i.get("about").toString();
                                    }
                                    final String about = about1;
                                    final String img = i.get("img").toString();
                                    final Long pop = (Long) i.get("pop");
                                    final String b = i.getId();
                                    kitap_name.setText(name);
                                    kitap_autor.setText(autor);
                                    kitap_genre.setText(Genre);

                                    try {
                                        Glide.with(getContext()).load(img).into(kitap_img);
                                    } catch (Exception e) {

                                    }
                                    kitap_view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            bigClick(v, name, autor, Genre, about, img, j, Integer.parseInt(String.valueOf(pop)), b);
                                        }
                                    });

                                    lin.addView(kitap_view);
                                }

                            } else if (state == 2&& i.get("name")!=null) {
                                if (i.get("autor").toString().contains(edit_poisk.getText().toString())) {
                                    View kitap_view = infl.inflate(R.layout.default_kitap_in_search, null, true);
                                    layoutParams.setMargins(20, 50, 20, 0);
                                    kitap_view.setLayoutParams(layoutParams);
                                    TextView kitap_name = kitap_view.findViewById(R.id.name);
                                    TextView kitap_autor = kitap_view.findViewById(R.id.autor);
                                    TextView kitap_genre = kitap_view.findViewById(R.id.genre);
                                    ImageView kitap_img = kitap_view.findViewById(R.id.KitapImage);
                                    final String name = i.get("name").toString();
                                    final String autor = i.get("autor").toString();
                                    final String Genre = i.get("genre").toString();
                                    String about1 = "";
                                    if(i.get("about")!= null) {
                                        about1 = i.get("about").toString();
                                    }
                                    final String about = about1;
                                    final String img = i.get("img").toString();
                                    final Long pop = (Long) i.get("pop");
                                    final String b = i.getId();
                                    kitap_name.setText(name);
                                    kitap_autor.setText(autor);
                                    kitap_genre.setText(Genre);

                                    try {
                                        Glide.with(getContext()).load(img).into(kitap_img);
                                    } catch (Exception e) {

                                    }
                                    kitap_view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            bigClick(v, name, autor, Genre, about, img, j, Integer.parseInt(String.valueOf(pop)), b);
                                        }
                                    });

                                    lin1.addView(kitap_view);
                                }
                            }
                        }
                    }
                });
                return false;
            }
        });



        booksf.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
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
                for (QueryDocumentSnapshot i : queryDocumentSnapshots) {
                    if (i.get("about") != null) {
                        View kitap_view = infl.inflate(R.layout.customkitapforcarusel, null, true);
                        TextView kitap_name = kitap_view.findViewById(R.id.name);
                        TextView kitap_autor = kitap_view.findViewById(R.id.autor);
                        ImageView kitap_img = kitap_view.findViewById(R.id.KitapImage);
                        final String name = i.get("name").toString();
                        final String autor = i.get("autor").toString();
                        final String Genre = i.get("genre").toString();
                        final String about = i.get("about").toString();
                        final String img = i.get("img").toString();
                        final Long pop = (Long) i.get("pop");
                        kitap_name.setText(name);
                        kitap_autor.setText(autor);
                        final String b = i.getId();
                        try {
                            Glide.with(getContext()).load(img).into(kitap_img);
                        } catch (Exception e) {

                        }
                        kitap_view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bigClick(v, name, autor, Genre, about, img, j, Integer.parseInt(String.valueOf(pop))-1, b);
                                Log.d("where", "1");
                            }
                        });
                        Object genre = Objects.requireNonNull(i.get("genre"));
                        if ("Учебники".equals(genre)) {
                            kon1.addView(kitap_view);
                        } else if ("Материалы для саморазвития".equals(genre)) {
                            kon2.addView(kitap_view);
                        } else if ("Материалы по английскому языку".equals(genre)) {
                            kon3.addView(kitap_view);
                        } else if ("Татарская литература".equals(genre)) {
                            kon4.addView(kitap_view);
                        } else if ("Классическая литература".equals(genre)) {
                            kon5.addView(kitap_view);
                        } else if ("Древняя литература".equals(genre)) {
                            kon6.addView(kitap_view);
                        }
                    }
                }
            }
        });


        /*j = 0;
        for (int i = 0; i < all_genres.length; i++) {
            layoutParams.setMargins(20, 40, 20, 0);
            layoutParams.gravity = Gravity.CENTER;
            LinearLayout view = (LinearLayout) infl.inflate(R.layout.default_genre, null, true);
            view.setLayoutParams(layoutParams);
            final LinearLayout kon = view.findViewById(R.id.lin_for_knigas);
            view.setLayoutParams(layoutParams);
            final TextView genre = view.findViewById(R.id.genre);
            genre.setText(all_genres[i]);




            View kitap_view = infl.inflate(R.layout.customkitapforcarusel, null, true);
            TextView kitap_name = kitap_view.findViewById(R.id.name);
            TextView kitap_autor = kitap_view.findViewById(R.id.autor);
            ImageView kitap_img = kitap_view.findViewById(R.id.KitapImage);
            final String name = books.getString("name");
            final String autor = books.getString("autor");
            final String Genre = books.getString("genre");
            final String about = books.getString("about");
            final String img = books.getString("img");
            final Long pop = books.getLong("pop");
            kitap_name.setText(name);
            kitap_autor.setText(autor);
                        /*Map<String, Object> bookf = new HashMap<>();
                        bookf.put("name", name);
                        bookf.put("autor", autor);
                        bookf.put("about", about);
                        bookf.put("img", img);
                        bookf.put("genre", Genre);
                        bookf.put("pop", pop);
                        booksf.add(bookf);
            try {
                Glide.with(getContext()).load(books.getString(img)).into(kitap_img);
            } catch (Exception e) {

            }
            kitap_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bigClick(v, name, autor, Genre, about, img, j, Integer.parseInt(String.valueOf(pop)) - 1);
                    Log.d("where", "1");
                }
            });
            kon.addView(kitap_view);
            lin_for_genres.addView(view);
        }*/

                /*get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            return;
                        } else {
                            // Convert the whole Query Snapshot to a list
                            // of objects directly! No need to fetch each
                            // document.
                            List<History> history = documentSnapshots.toObjects(History.class);

                            // Add all to your list
                            all_in_mass.addAll(history);
                            Log.d("1", all_in_mass.get(i).getName().toString());
                            i++;
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });*/


        /*g = 61;
        for(int i = 0; i < all_genres.length; i++) {
            Log.d("1","1");
            String nameGenre = all_genres[i];
            layoutParams.setMargins(20, 40, 20, 0);
            layoutParams.gravity = Gravity.CENTER;
            LinearLayout view = (LinearLayout) infl.inflate(R.layout.default_genre,null,true);
            view.setLayoutParams(layoutParams);
            final LinearLayout kon = view.findViewById(R.id.lin_for_knigas);
            view.setLayoutParams(layoutParams);
            final TextView genre = view.findViewById(R.id.genre);
            genre.setText(nameGenre);
            Query query = books.child(all_genres[i]).orderByChild("pop");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    j = 0;
                    Log.d("2","2");
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
                        if(g > 0) {
                            Map<String, Object> bookf = new HashMap<>();
                            bookf.put("name", name);
                            bookf.put("autor", autor);
                            bookf.put("about", about);
                            bookf.put("img", img);
                            bookf.put("genre", Genre);
                            bookf.put("pop", pop);
                            booksf.add(bookf);
                            g--;
                            Log.d(String.valueOf(g), name);
                        }
                        try {
                            Glide.with(getContext()).load(book.child("img").getValue(String.class)).into(kitap_img);
                        } catch (Exception e) {

                        }
                        kitap_view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {//bigClick(v, name, autor,Genre,about,img, Integer.parseInt(book.getKey()), Integer.parseInt(String.valueOf(pop))-1);
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


        }*/

            return root;
        }


    private void CreateCarusel() {
        layoutParams.setMargins(25, 40, 25, 0);
        layoutParams.gravity = Gravity.CENTER;

        layo1 = (LinearLayout) infl.inflate(R.layout.default_genre, null, true);
        layo1.setLayoutParams(layoutParams);
        layo1.setTag("Учебники");
        kon1 = layo1.findViewById(R.id.lin_for_knigas);
        layo1.setLayoutParams(layoutParams);
        final TextView genre1 = layo1.findViewById(R.id.genre);
        genre1.setText("Учебники");

        layo2 = (LinearLayout) infl.inflate(R.layout.default_genre, null, true);
        layo2.setLayoutParams(layoutParams);
        layo2.setTag("Материалы для саморазвития");
        kon2 = layo2.findViewById(R.id.lin_for_knigas);
        layo2.setLayoutParams(layoutParams);
        final TextView genre2 = layo2.findViewById(R.id.genre);
        genre2.setText("Материалы для саморазвития");

        layo3 = (LinearLayout) infl.inflate(R.layout.default_genre, null, true);
        layo3.setLayoutParams(layoutParams);
        layo3.setTag("Материалы по английскому языку");
         kon3 = layo3.findViewById(R.id.lin_for_knigas);
        layo3.setLayoutParams(layoutParams);
        final TextView genre3 = layo3.findViewById(R.id.genre);
        genre3.setText("Материалы по английскому языку");

        layo4 = (LinearLayout) infl.inflate(R.layout.default_genre, null, true);
        layo4.setLayoutParams(layoutParams);
        layo4.setTag("Татарская литература");
         kon4 = layo4.findViewById(R.id.lin_for_knigas);
        layo4.setLayoutParams(layoutParams);
        final TextView genre4 = layo4.findViewById(R.id.genre);
        genre4.setText("Татарская литература");

        layo5 = (LinearLayout) infl.inflate(R.layout.default_genre, null, true);
        layo5.setLayoutParams(layoutParams);
        layo5.setTag("Классическая литература");
        kon5 = layo5.findViewById(R.id.lin_for_knigas);
        layo5.setLayoutParams(layoutParams);
        final TextView genre5 = layo5.findViewById(R.id.genre);
        genre5.setText("Классическая литература");


        layo6 = (LinearLayout) infl.inflate(R.layout.default_genre, null, true);
        layo6.setLayoutParams(layoutParams);
        layo6.setTag("Древняя литература");
        kon6 = layo6.findViewById(R.id.lin_for_knigas);
        layo6.setLayoutParams(layoutParams);
        final TextView genre6 = layo6.findViewById(R.id.genre);
        genre6.setText("Древняя литература");

        lin_for_genres.addView(layo1);
        lin_for_genres.addView(layo2);
        lin_for_genres.addView(layo3);
        lin_for_genres.addView(layo4);
        lin_for_genres.addView(layo5);
        lin_for_genres.addView(layo6);


    }

    @Override
    public void onResume() {
        super.onResume();

        if(getView() == null){
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK && edit_poisk.getVisibility() == View.VISIBLE){
                    rel_between.setVisibility(View.GONE);
                    return true;
                }
                return false;
            }
        });
    }

    private void bigClick(View v, String name, String autor, String genre, String about, String img, int j, int pop, String b){
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