package com.example.jp.ac.chiba_fjb.x16g_b.test;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class saisei extends AppCompatActivity {
    private MediaPlayer mediaPlayer ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.saisei);

        ImageButton s_button = findViewById(R.id.start);

        s_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 音楽再生
                audioPlay();
            }
        });
    }
}
