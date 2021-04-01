package com.example.samatdanilonelove;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

public class PageFragmentforSearch extends Fragment {

    static final String TAG = "myLogs";

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String SAVE_PAGE_NUMBER = "save_page_number";

    int pageNumber;

    private LinearLayout search;
    public static PageFragmentforSearch newInstance(int page) {
        PageFragmentforSearch pageFragmentforSearch = new PageFragmentforSearch();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragmentforSearch.setArguments(arguments);
        return pageFragmentforSearch;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

        int savedPageNumber = -1;
        if (savedInstanceState != null) {
            savedPageNumber = savedInstanceState.getInt(SAVE_PAGE_NUMBER);
        }
        Log.d(TAG, "savedPageNumber = " + savedPageNumber);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        if(pageNumber == 0) {
            view = inflater.inflate(R.layout.fragment_po_knigam, null);

            search = view.findViewById(R.id.search_aut);

        }
        if(pageNumber == 1) {
            view = inflater.inflate(R.layout.fragment_po_autoram, null);
        }

        return view;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVE_PAGE_NUMBER, pageNumber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + pageNumber);
    }
}