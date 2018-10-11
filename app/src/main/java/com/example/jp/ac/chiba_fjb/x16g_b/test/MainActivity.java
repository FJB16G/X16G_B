package com.example.jp.ac.chiba_fjb.x16g_b.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 1;
    private EditText inpText;
    private String preInp = "";
    private Button inpBtn, canBtn, clsBtn;
    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inpText = (EditText)findViewById(R.id.result_id);
        inpBtn = (Button)findViewById(R.id.speech_id);
        inpBtn.setOnClickListener(this);
        canBtn = (Button)findViewById(R.id.cancel_id);
        canBtn.setOnClickListener(this);
        clsBtn = (Button)findViewById(R.id.clear_id);
        clsBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        if(view == inpBtn){
            try{
                // 音声認識の準備
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                // Intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "音声を入力してください。");
                // インテント発行
                startActivityForResult(intent, REQUEST_CODE);
            }catch (ActivityNotFoundException e){
                Toast.makeText(MainActivity.this, "Not found Activity", Toast.LENGTH_LONG).show();
            }
        }else if(view == canBtn) {
            if(preInp != null){
                inpText.setText(preInp);// 前の入力文字列を表示
                inpText.setSelection(preInp.length());// カーソルを移動
            }else if(view == clsBtn) {
                inpText.setText("");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String input = "";
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            preInp = inpText.getText().toString();
            // 認識結果を取得
            ArrayList<String> candidates = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            Log.v("Speech", "Candidate Num = " + candidates.size());
            if(candidates.size() > 0) {
                input = preInp + candidates.get(0);// 認識結果(1位候補)
            }
            if(input != null) {
                inpText.setText(input);// 入力文字列を表示
                inpText.setSelection(input.length());// カーソルを移動
            }

        }
    }

}


