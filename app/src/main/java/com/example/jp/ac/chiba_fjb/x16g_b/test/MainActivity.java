package com.example.jp.ac.chiba_fjb.x16g_b.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.voicetext);
        // 表示内容の切替え
        final ImageView button = (ImageView) findViewById(R.id.soundButton);
        button.setOnClickListener(new View.OnClickListener() {
            int start = 1;
            @Override
            public void onClick(View view) {
                if(start == 1){
                    button.setImageResource(R.drawable.pause);
                    start = 0;
                }else {
                    button.setImageResource(R.drawable.start);
                    start = 1;
                }
            }
        });
    }



}


