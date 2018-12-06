package com.example.jp.ac.chiba_fjb.x16g_b.a;

import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jp.ac.chiba_fjb.x16g_b.test.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class VoiceMainActivity extends Activity{

    private MediaRecorder mRecorder;
    private TextView mTextView;
    private boolean isRecording = false;
    private EditText mEditText;
    private Button Dialogbutton;
    private Timer timer;
    private Handler handler = new Handler();
    private long delay, period;
    private int count;
    private SimpleDateFormat dataFormat =
            new SimpleDateFormat("mm:ss.SS", Locale.JAPAN);

    private long startTime;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_main);

        mTextView = (TextView)findViewById(R.id.timer);
        mEditText = (EditText)findViewById(R.id.text);
        delay = 0;
        period = 10;
        mTextView.setText(dataFormat.format(0));

        // ダイアログクラスをインスタンス化
        VoiceCustomDialogFlagment dialog = new VoiceCustomDialogFlagment();

        // 表示  getFagmentManager()は固定、sampleは識別タグ
        dialog.show(getFragmentManager(), "sample");

        Dialogbutton = findViewById(R.id.DialogBtn);
        Dialogbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ダイアログクラスをインスタンス化
                VoiceCustomDialogFlagment dialog = new VoiceCustomDialogFlagment();
                // 表示  getFagmentManager()は固定、sampleは識別タグ
                dialog.show(getFragmentManager(), "sample");
            }
        });

    }
    // ダイアログで入力した値をtextViewに入れる - ダイアログから呼び出される
    public void setTextView(String value) {
        if(value.equals("")){
           VoiceAllspaceDailogFlagment dialog = new VoiceAllspaceDailogFlagment();
            // 表示  getFagmentManager()は固定、sampleは識別タグ
            dialog.show(getFragmentManager(), "sample");
        }
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(value);
    }


    // Startボタンが押されたら呼び出される
    public void onStartButton(View view) {

        if (isRecording) return;
        mTextView.setText(dataFormat.format(0));
        String Filename = mEditText.getText().toString();
        if (null != timer) {

            timer.cancel();
            timer = null;

        }
        startTime = System.currentTimeMillis();
        // Timer インスタンスを生成
        timer = new Timer();

        // カウンター
        count = 0;
        mTextView.setText(dataFormat.format(0));

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // handlerdを使って処理をキューイングする
                handler.post(new Runnable() {
                    public void run() {
                        count++;
                        mTextView.setText(dataFormat.
                                format(count*period));


                    }
                });
            }
        }, delay, period);

        // SDカードのディレクトリ
        File dir = Environment.getExternalStorageDirectory();
        // アプリ名で
        File appDir = new File(dir, "Voice");
        // ディレクトリを作る
        if (!appDir.exists()) appDir.mkdir();
        // ファイル名
        String name =  Filename+".wav";
        // 出力ファイルのパス
        String path = new File(appDir, name).getAbsolutePath();

        mRecorder = new MediaRecorder();
        // 入力ソースにマイクを指定
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        // 出力フォーマットに3gpを指定
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
        // 音声エンコーダにAMRを指定
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
        // 出力ファイルのパスを指定
        mRecorder.setOutputFile(path);
        try {
            // 準備して
            mRecorder.prepare();
            // 録音スタート！
            mRecorder.start();
            isRecording = true;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Stopボタンが押されたら呼び出される
    public void onStopButton(View view) {
        // ダイアログクラスをインスタンス化
        VoiceNextDialogFlagment dialog = new VoiceNextDialogFlagment();

        // 表示  getFagmentManager()は固定、sampleは識別タグ
        dialog.show(getFragmentManager(), "sample");

        if (null != timer) {
            long endTime = System.currentTimeMillis();
            // カウント時間 = 経過時間 - 開始時間
            long diffTime = (endTime - startTime);
            mTextView.setText(dataFormat.format(diffTime));
            // Cancel
            timer.cancel();
            timer = null;
        }

        if (!isRecording) return;


        // 録音を停止して
        mRecorder.stop();
        // 解放
        mRecorder.release();
        isRecording = false;
    }
}