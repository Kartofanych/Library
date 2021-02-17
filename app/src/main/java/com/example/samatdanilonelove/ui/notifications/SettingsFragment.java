package com.example.samatdanilonelove.ui.notifications;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.samatdanilonelove.PageFragmentForSettings;
import com.example.samatdanilonelove.R;
import com.example.samatdanilonelove.Set1;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private View tch1, tch2;
    private TextView profile, about;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        pager = (ViewPager) view.findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getFragmentManager());
        pager.setAdapter(pagerAdapter);



        profile = view.findViewById(R.id.prof);
        about = view.findViewById(R.id.about);
        tch1 = view.findViewById(R.id.tch1);
        tch2 = view.findViewById(R.id.tch2);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tch1.setVisibility(View.VISIBLE);
                tch2.setVisibility(View.GONE);
                pager.setCurrentItem(0);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tch1.setVisibility(View.GONE);
                tch2.setVisibility(View.VISIBLE);
                pager.setCurrentItem(1);
            }
        });


        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if(position == 0){

                    tch1.setVisibility(View.VISIBLE);
                    tch2.setVisibility(View.GONE);
                }
                else
                if(position == 1){

                    tch1.setVisibility(View.GONE);
                    tch2.setVisibility(View.VISIBLE);
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

        return view;
    }
    private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PageFragmentForSettings.newInstance(position);
        }

        @Override
        public int getCount() {
            return 2;
        }

    }
}