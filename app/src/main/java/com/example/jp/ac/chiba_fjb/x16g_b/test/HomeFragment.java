package com.example.jp.ac.chiba_fjb.x16g_b.test;


import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {


    private Timer mTimer;
    private Handler mHandler;
    private static SimpleDateFormat mSimpleDataFormat = new SimpleDateFormat("yyyy年　MM月dd日　HH:mm:ss");
    public HomeFragment() {

//
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        view.findViewById(R.id.home).setOnClickListener(this);
//        getView().findViewById(R.id.button4);

         mHandler = new Handler();
        Button rokuon =  view.findViewById(R.id.rokuon);
        Button saisei =  view.findViewById(R.id.saisei);
        saisei.setOnClickListener(this);
        rokuon.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mTimer = new Timer();

        // 一秒ごとに定期的に実行します。
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                        Calendar calendar = Calendar.getInstance();
                        String nowDate = mSimpleDataFormat.format(calendar.getTime());
                        // 時刻表示をするTextView
                        ((TextView) getView().findViewById(R.id.clock)).setText(nowDate);
                    }
                });}
        },0,1000);
    }

    @Override
    public void onPause() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.home) {
            //getView().findViewById(R.id.button3);
            ((MainActivity) getActivity()).changeFragment(HomeFragment.class);
        }else if(v.getId() == R.id.rokuon) {
            //((MainActivity) getActivity()).changeFragment(TopFragment.class);
            MainFragment f = (MainFragment) getParentFragment();
            f.changeFragment(titleFragment.class);
        }else if(v.getId() == R.id.saisei) {
           // ((MainActivity) getActivity()).changeFragment(titleFragment.class);
            MainFragment f = (MainFragment) getParentFragment();
            f.changeFragment(titleFragment.class);
        }

    }

}
