package com.example.jp.ac.chiba_fjb.x16g_b.test;


import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class KarendaFragment extends Fragment implements View.OnClickListener {


    public KarendaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_karenda, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //view.findViewById(R.id.home).setOnClickListener(this);
        Button home = view.findViewById(R.id.home);
        Button title =  view.findViewById(R.id.title);

        //イベントの設定
        home.setOnClickListener(this);

        title.setOnClickListener(this);

        //getView().findViewById();

    }

    @Override
    public void onClick(View v) {
        ((MainActivity)getActivity()).changeFragment(KarendaFragment.class);
    }
}
