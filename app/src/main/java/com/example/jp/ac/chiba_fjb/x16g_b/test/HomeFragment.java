package com.example.jp.ac.chiba_fjb.x16g_b.test;


import android.os.Bundle;

import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private File[] files;
    private ArrayList<String> songList = new ArrayList<String>();


    private Timer mTimer;
    private Handler mHandler;
    private static SimpleDateFormat mSimpleDataFormat = new SimpleDateFormat("yyyy年　MM月dd日　HH:mm:ss");
    private ListView listView;
    ArrayAdapter<String> arrayList;
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

//        ListView listView = (ListView)view.findViewById(R.id.music_list);




         mHandler = new Handler();
        Button rokuon =  view.findViewById(R.id.rokuon);
        rokuon.setOnClickListener(this);
        //
        String sdPath = Environment.getExternalStorageDirectory().getPath();
        files = new File(sdPath).listFiles();
        ArrayList<String> arrayList = new ArrayList<>();
        if(files != null) {
            for (int i = 0; i < files.length; i++) {
                //if (files[i].isFile() && files[i].getName().endsWith(".mp3")) {
                songList.add(files[i].getName());
                //}
            }
        }


        ListView listView = (ListView)view.findViewById(R.id.music_list);

        // simple_list_item_1 は、 もともと用意されている定義済みのレイアウトファイルのID
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(getActivity(), R.layout.test, songList);

        listView.setAdapter(arrayAdapter);





        // セルを選択されたら詳細画面フラグメント呼び出す
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("selected",position);
                ((MainFragment)HomeFragment.this.getParentFragment()).changeFragment(titleFragment.class,bundle);


    }




});
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
        //}else if(v.getId() == R.id.saisei) {
           // ((MainActivity) getActivity()).changeFragment(titleFragment.class);
            //MainFragment f = (MainFragment) getParentFragment();
           // f.changeFragment(titleFragment.class);



        }


    }




//    public class voice_Search extends AppCompatActivity {
//        private File[] files;
//        private ArrayList<String> songList = new ArrayList<String>();
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//
//        String sdPath = Environment.getExternalStorageDirectory().getPath();
//        files = new File(sdPath).listFiles();
//        ArrayList<String> arrayList = new ArrayList<>();
//        if(files != null) {
//            for (int i = 0; i < files.length; i++) {
//                //if (files[i].isFile() && files[i].getName().endsWith(".mp3")) {
//                songList.add(files[i].getName());
//                //}
//            }
//        }
//
//        ListView listView = (ListView)findViewById(R.id.music_list);
//
//        // simple_list_item_1 は、 もともと用意されている定義済みのレイアウトファイルのID
//        ArrayAdapter<String> arrayAdapter =
//                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songList);
//
//        listView.setAdapter(arrayAdapter);
//    }

    private void setContentView(ListView listView) {
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}



