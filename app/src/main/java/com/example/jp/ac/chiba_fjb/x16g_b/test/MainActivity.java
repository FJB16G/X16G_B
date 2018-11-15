package com.example.jp.ac.chiba_fjb.x16g_b.test;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import android.widget.TextView;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;




public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextMessage;




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };





        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_main);



            changeFragment(MainFragment.class);




        }











    public void onClick(View v) {

        if(v.getId() == R.id.home)

        changeFragment(HomeFragment.class);




    }
    public void changeFragment(Class c){
        changeFragment(R.id.fragment,c,null);
    }


    public void changeFragment(int id,Class c){
        changeFragment(id,c,null);
    }

    public void changeFragment(int id,Class c,Bundle budle){
        try {
            Fragment f = (Fragment) c.newInstance();
            if(budle != null)
                f.setArguments(budle);
            else
                f.setArguments(new Bundle());

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(id,f);
            ft.addToBackStack(null);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        changeFragment(KarendaFragment.class);
    }

//
       public class voice_Search extends AppCompatActivity {
        private File[] files;
        private ArrayList<String> songList = new ArrayList<String>();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);


    String sdPath = Environment.getExternalStorageDirectory().getPath();
    files = new File(sdPath).listFiles();

            if(files != null) {
        for (int i = 0; i < files.length; i++) {
            //if (files[i].isFile() && files[i].getName().endsWith(".mp3")) {
            songList.add(files[i].getName());
            //}
        }
    }

    ListView listView = new ListView(this);
    setContentView(listView);

    // simple_list_item_1 は、 もともと用意されている定義済みのレイアウトファイルのID
    ArrayAdapter<String> arrayAdapter =
            new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, songList);

            listView.setAdapter(arrayAdapter);
}}



    }







