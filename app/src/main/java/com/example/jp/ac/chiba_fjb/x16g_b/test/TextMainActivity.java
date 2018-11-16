package com.example.jp.ac.chiba_fjb.x16g_b.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class TextMainActivity extends AppCompatActivity {
    TextPermission mTextPermission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_activity_main);

        //Android6.0以降用パーミッション設定
        mTextPermission = new TextPermission();
        mTextPermission.setOnResultListener(new TextPermission.ResultListener() {
            @Override
            public void onResult() {
                //パーミッション設定完了後の初期化処理を入れる
                changeFragment(RecordFragment.class);

            }
        });
        mTextPermission.requestPermissions(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mTextPermission.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }

    public void changeFragment(Class c){
        changeFragment(c,null);
    }
    public void changeFragment(Class c, Bundle budle){
        try {
            Fragment f = (Fragment) c.newInstance();
            if(budle != null)
                f.setArguments(budle);
            else
                f.setArguments(new Bundle());

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment,f);
            ft.addToBackStack(null);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
