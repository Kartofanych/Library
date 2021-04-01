package com.example.samatdanilonelove.ui.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.samatdanilonelove.PageFragmentforSearch;
import com.example.samatdanilonelove.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    static final int PAGE_COUNT = 2;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private TextView aut, kn;
    private EditText poisk;
    private int state = 1;
    private Drawable text_line, text_line_1;
    private ImageButton search_but;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        poisk = view.findViewById(R.id.query_edit_text);
        text_line = getResources().getDrawable(R.drawable.text_line);
        text_line_1 = getResources().getDrawable(R.drawable.text_line_1);
        aut = view.findViewById(R.id.po_autoram);
        kn = view.findViewById(R.id.po_knigam);
        pager = (ViewPager) view.findViewById(R.id.pager);
        search_but = view.findViewById(R.id.search_but);
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
        return view;
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