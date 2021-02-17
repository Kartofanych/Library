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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.samatdanilonelove.Enterance;
import com.example.samatdanilonelove.HistoryInfo;
import com.example.samatdanilonelove.R;
import com.example.samatdanilonelove.models.History;
import com.google.firebase.auth.FirebaseAuth;

public class LibraryFragment extends Fragment {

    private LibraryViewModel libraryViewModel;


    private View[] Raskaz = new View[8];
    private View[] Skazka = new View[8];
    private View[] Povesti = new View[8];
    private View[] Zarub = new View[8];
    private View[] Voen = new View[8];
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_library, container, false);

        Raskaz[0] = view.findViewById(R.id.first_rask);
        Raskaz[1] = view.findViewById(R.id.second_rask);
        Raskaz[2] = view.findViewById(R.id.third_rask);
        Raskaz[3] = view.findViewById(R.id.fourth_rask);
        Raskaz[4] = view.findViewById(R.id.fifth_rask);
        Raskaz[5] = view.findViewById(R.id.sixth_rask);
        Raskaz[6] = view.findViewById(R.id.seventh_rask);
        Raskaz[7] = view.findViewById(R.id.eightth_rask);


        Skazka[0] = view.findViewById(R.id.first_skaz);
        Skazka[1] = view.findViewById(R.id.second_skaz);
        Skazka[2] = view.findViewById(R.id.third_skaz);
        Skazka[3] = view.findViewById(R.id.fourth_skaz);
        Skazka[4] = view.findViewById(R.id.fifth_skaz);
        Skazka[5] = view.findViewById(R.id.sixth_skaz);
        Skazka[6] = view.findViewById(R.id.seventh_skaz);
        Skazka[7] = view.findViewById(R.id.eightth_skaz);


        Povesti[0] = view.findViewById(R.id.first_povesti);
        Povesti[1] = view.findViewById(R.id.second_povesti);
        Povesti[2] = view.findViewById(R.id.third_povesti);
        Povesti[3] = view.findViewById(R.id.fourth_povesti);
        Povesti[4] = view.findViewById(R.id.fifth_povesti);
        Povesti[5] = view.findViewById(R.id.sixth_povesti);
        Povesti[6] = view.findViewById(R.id.seventh_povesti);
        Povesti[7] = view.findViewById(R.id.eightth_povesti);


        Zarub[0] = view.findViewById(R.id.first_zarub);
        Zarub[1] = view.findViewById(R.id.second_zarub);
        Zarub[2] = view.findViewById(R.id.third_zarub);
        Zarub[3] = view.findViewById(R.id.fourth_zarub);
        Zarub[4] = view.findViewById(R.id.fifth_zarub);
        Zarub[5] = view.findViewById(R.id.sixth_zarub);
        Zarub[6] = view.findViewById(R.id.seventh_zarub);
        Zarub[7] = view.findViewById(R.id.eightth_zarub);

        Voen[0] = view.findViewById(R.id.first_voen);
        Voen[1] = view.findViewById(R.id.second_voen);
        Voen[2] = view.findViewById(R.id.third_voen);
        Voen[3] = view.findViewById(R.id.fourth_voen);
        Voen[4] = view.findViewById(R.id.fifth_voen);
        Voen[5] = view.findViewById(R.id.sixth_voen);
        Voen[6] = view.findViewById(R.id.seventh_voen);
        Voen[7] = view.findViewById(R.id.eightth_voen);

        for(int i = 0 ; i < 8; i ++){
            final int finalI = i;
            Raskaz[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Intent intent = new Intent(getContext(), KnigaAbout.class);
                    startActivity(intent);*/
                    Bundle bundle = null;
                    int f = finalI;
                    Log.d("1", String.valueOf(finalI));
                    ImageView view1 = Raskaz[f].findViewById(R.id.KitapImage);


                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        if (view1 != null) {
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view1, "trans1");
                            bundle = options.toBundle();
                        }
                    }

                    Intent intent = new Intent(getContext(), HistoryInfo.class);
                    if (bundle == null) {
                        getContext().startActivity(intent);
                    } else {
                        getContext().startActivity(intent, bundle);
                    }



                }
            });
            Skazka[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = null;

                    int f = finalI;
                    Log.d("1", String.valueOf(finalI));
                    ImageView view1 = Skazka[f].findViewById(R.id.KitapImage);
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        if (view1 != null) {
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view1, "trans1");
                            bundle = options.toBundle();
                        }
                    }

                    Intent intent = new Intent(getContext(), HistoryInfo.class);
                    if (bundle == null) {
                        getContext().startActivity(intent);
                    } else {
                        getContext().startActivity(intent, bundle);
                    }
                }
            });
            Povesti[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = null;

                    int f = finalI;
                    Log.d("1", String.valueOf(finalI));
                    ImageView view1 = Povesti[f].findViewById(R.id.KitapImage);
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        if (view1 != null) {
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view1, "trans1");
                            bundle = options.toBundle();
                        }
                    }

                    Intent intent = new Intent(getContext(), HistoryInfo.class);
                    if (bundle == null) {
                        getContext().startActivity(intent);
                    } else {
                        getContext().startActivity(intent, bundle);
                    }
                }
            });
            Zarub[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = null;

                    int f = finalI;
                    Log.d("1", String.valueOf(finalI));
                    ImageView view1 = Zarub[f].findViewById(R.id.KitapImage);
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        if (view1 != null) {
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view1, "trans1");
                            bundle = options.toBundle();
                        }
                    }

                    Intent intent = new Intent(getContext(), HistoryInfo.class);
                    if (bundle == null) {
                        getContext().startActivity(intent);
                    } else {
                        getContext().startActivity(intent, bundle);
                    }
                }
            });
            Voen[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = null;

                    int f = finalI;
                    Log.d("1", String.valueOf(finalI));
                    ImageView view1 = Voen[f].findViewById(R.id.KitapImage);
                    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                        if (view1 != null) {
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), view1, "trans1");
                            bundle = options.toBundle();
                        }
                    }

                    Intent intent = new Intent(getContext(), HistoryInfo.class);

                    if (bundle == null) {
                        getContext().startActivity(intent);
                    } else {
                        getContext().startActivity(intent, bundle);
                    }
                }
            });
        }


        return view;
    }
}