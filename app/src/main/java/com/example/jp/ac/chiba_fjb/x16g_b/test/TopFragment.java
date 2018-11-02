package com.example.jp.ac.chiba_fjb.x16g_b.test;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment  extends Fragment implements View.OnClickListener{

    public TopFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top, container, false);
    }


        //view.findViewById(R.id.home).setOnClickListener(this);

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            TextView a = view.findViewById(R.id.Top);

            view.findViewById(R.id.Top);
        }



        //getView().findViewById();



    public void onClick(View v) {


        ((MainActivity)getActivity()).changeFragment(TopFragment.class);
    }

}