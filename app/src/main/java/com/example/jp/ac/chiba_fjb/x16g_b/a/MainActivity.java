package com.example.jp.ac.chiba_fjb.x16g_b.a;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.jp.ac.chiba_fjb.x16g_b.test.R;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

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
}