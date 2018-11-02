package com.example.jp.ac.chiba_fjb.x16g_b.test;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * A simple {@link Fragment} subclass.
 */
public class titleFragment  extends Fragment implements View.OnClickListener{

    public titleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_title, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //view.findViewById(R.id.home).setOnClickListener(this);

        //getView().findViewById();

    }


    public void onClick(View v) {
        ((MainActivity)getActivity()).changeFragment(titleFragment.class);
    }
}