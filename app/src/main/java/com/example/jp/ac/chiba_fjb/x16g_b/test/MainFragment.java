package com.example.jp.ac.chiba_fjb.x16g_b.test;


import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */





public class MainFragment extends Fragment implements View.OnClickListener {





    public MainFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        changeFragment(HomeFragment.class);
//インスタンスの取得
        Button home = view.findViewById(R.id.home);
        Button title =  view.findViewById(R.id.title);
        Button Top =  view.findViewById(R.id.Top);

        //イベントの設定
        home.setOnClickListener(this);
       Top.setOnClickListener(this);
        title.setOnClickListener(this);


//        view.findViewById(R.id.home).setOnClickListener(
//                new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(v.getId() == R.id.title)
//                    changeFragment(HomeFragment.class);
//
//                else if(v.getId() == R.id.home)
//                    changeFragment(KarendaFragment.class);
//
//                else if(v.getId() == R.id.title)
//                    changeFragment(titleFragment.class);
//                else if(v.getId() == R.id.top)
//                    changeFragment(TopFragment.class);
//            }
//        }
//        );







    }

    public void changeFragment(Class c) {
        changeFragment(R.id.fragment_sub, c, null);
    }

    public void changeFragment(int id, Class c) {
        changeFragment(id, c, null);
    }

    public void changeFragment(int id, Class c, Bundle budle) {
        try {
            Fragment f = (Fragment) c.newInstance();
            if (budle != null)
                f.setArguments(budle);
            else
                f.setArguments(new Bundle());

            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.replace(id, f);
            ft.addToBackStack(null);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {



        if(v.getId() == R.id.home)
                    changeFragment(HomeFragment.class);

        else if(v.getId() == R.id.settei)
                    changeFragment(KarendaFragment.class);

        else if(v.getId() == R.id.title)
                    changeFragment(titleFragment.class);
        else if(v.getId() == R.id.Top)
                    changeFragment(TopFragment.class);


    }


}
