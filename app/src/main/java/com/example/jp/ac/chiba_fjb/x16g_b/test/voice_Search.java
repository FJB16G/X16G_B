package com.example.jp.ac.chiba_fjb.x16g_b.test;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

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
    }
}
