package com.example.jp.ac.chiba_fjb.x16g_b.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfigrationActivity extends AppCompatActivity implements View.OnClickListener {
    final String[] items = {"MP3", "WAV"};
    final String[] items1 = {"大", "標準"};
    TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configration);

        ((Button)findViewById(R.id.button_SaveFormat)).setOnClickListener(this);
        ((Button)findViewById(R.id.button_font)).setOnClickListener(this);
        ((Button)findViewById(R.id.button_top)).setOnClickListener(this);
        ((Button)findViewById(R.id.button_GoogleDrive)).setOnClickListener(this);

       text = (TextView) findViewById(R.id.OUTPUT);
    }


    @Override
    public void onClick(final View view) {

        switch (view.getId()){
            case R.id.button_SaveFormat:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("保存形式")
                        .setItems(items, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // item_which pressed
                                if (which == 0){
                                    // itemsのMP3クリック時の処理
                                }else{
                                    // itemsのWAVクリック時の処理
                                }

                            }
                        }).show();

                break;
            case R.id.button_font:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("フォントサイズ")
                        .setItems(items1, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // item_which pressed
                                if (which == 0){
                                    // itemsの大クリック時の処理
                                    text.setTextSize(24.0f);

                                }else{
                                    // itemsの標準クリック時の処理
                                    text.setTextSize(13.8f);

                                }
                            }
                        }).show();
                break;

            //トップ遷移
            case R.id.button_top:
                finish();
                break;


             //GoogleDrive共有
            case R.id.button_GoogleDrive:
                Uri uri = Uri.parse("https://drive.google.com/drive/u/1/my-drive");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;

        }


    }
}
