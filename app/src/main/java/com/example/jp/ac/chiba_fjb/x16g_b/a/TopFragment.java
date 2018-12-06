package com.example.jp.ac.chiba_fjb.x16g_b.a;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jp.ac.chiba_fjb.x16g_b.a.Libs.AppFinger;
import com.example.jp.ac.chiba_fjb.x16g_b.a.Libs.GoogleDrive;
import com.example.jp.ac.chiba_fjb.x16g_b.a.Libs.Permission;
import com.example.jp.ac.chiba_fjb.x16g_b.test.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {

    Permission mPermission;
    GoogleDrive mDrive;

    public TopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDrive = new GoogleDrive(getActivity());

        //キー登録用SHA1の出力(いらなければ消す)
        Log.d("フィンガーコード", AppFinger.getSha1(getActivity()));
        //Android6.0以降のパーミッション処理
        mPermission = new Permission();
        mPermission.setOnResultListener(new Permission.ResultListener() {
            @Override
            public void onResult() {
                changeFragment(LoginFragment.class);
            }
        });
        mPermission.requestPermissions(getActivity());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //必要に応じてアカウントや権限ダイアログの表示
        mDrive.onActivityResult(requestCode,resultCode,data);

    }
    public GoogleDrive getDrive(){return mDrive;}
    public void changeFragment(Class c){
        changeFragment(c,null);
    }
    public void changeFragment(Class c, Bundle budle){
        try {
            android.support.v4.app.Fragment f = (android.support.v4.app.Fragment) c.newInstance();
            if(budle != null)
                f.setArguments(budle);
            else
                f.setArguments(new Bundle());

//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.fragment,f);
//            ft.addToBackStack(null);
//            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
