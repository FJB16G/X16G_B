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
public class HomeFragment extends Fragment implements View.OnClickListener {


    public HomeFragment() {
        // Required empty public constructor
//        FileOutputStream outputStream;
//
//// ※以下、例外処理は省略
//
//// 内部ストレージにファイルを作成し、書き込む。
//        outputStream = openFileOutput("myfile.txt", Context.MODE_PRIVATE);
//        outputStream.write("test".getBytes());
//
//// ちなみにDDMSで確認したところ、確認時の環境下では
//// "/data/data/[パッケージ名]/files/myfile.txt"
//// に書き込まれた。
//
//        outputStream.close();


//        FileInputStream inputStream;
//        byte[] buffer = new byte[256];
//
//// ※以下、例外処理は省略
//
//        inputStream = openFileInput(FILE_NAME);
//        inputStream.read(buffer);
//
//        String data = new String(buffer, "UTF-8");
//
//        inputStream.close();
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

    }



    @Override
    public void onClick(View v) {
        //getView().findViewById(R.id.button3);
      ((MainActivity)getActivity()).changeFragment(HomeFragment.class);

    }
}
