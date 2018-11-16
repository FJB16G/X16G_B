package com.example.jp.ac.chiba_fjb.x16g_b.test;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class audioS extends AppCompatActivity {
    private MediaPlayer mediaPlayer ;
    double maxTime = 0;
    double nowTime = 0;
    Looper looper = Looper.getMainLooper();
    final Handler handler = new Handler(Looper.getMainLooper());
    private TextView time;
    boolean taskrunning = false;
    private ImageButton s_button;
    public SeekBar m_seek;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saisei);

        s_button = findViewById(R.id.start);
        time = (TextView) findViewById(R.id.musicTime);
        m_seek = findViewById(R.id.musicseek);



        //シークバーの管理
        m_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(m_seek.getProgress());
                showTime();
            }
        });

        //スタートボタン処理
        s_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!taskrunning){
                    // 音楽再生
                    taskrunning = true;
                    audioPlay();

                    m_seek.setMax(mediaPlayer.getDuration());
                    showTime();

                    //0.1秒毎にループ
                    int loopTime = 100;
                    //タイマー生成
                    final Timer timer = new Timer(false);
                    //秒数更新
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (taskrunning){
                                        showTime();
                                    }else{
                                        timer.cancel();
                                    }
                                }
                            });
                        }
                    },0,loopTime);
                }
            }
        });

        // 音楽停止ボタン
        ImageButton buttonStop = findViewById(R.id.stop);

        // リスナーをボタンに登録
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer != null) {
                    // 音楽停止
                    audioPause();
                    taskrunning=false;
                }
            }
        });



    }
    private boolean audioSetup(){
        boolean fileCheck = false;

        // インタンスを生成
        mediaPlayer = new MediaPlayer();

        //音楽ファイル名, あるいはパス
        String filePath = "sample.mp3";

        // assetsから mp3 ファイルを読み込み
        try(AssetFileDescriptor afdescripter = getAssets().openFd(filePath);)
        {
            // MediaPlayerに読み込んだ音楽ファイルを指定
            mediaPlayer.setDataSource(afdescripter.getFileDescriptor(),
                    afdescripter.getStartOffset(),
                    afdescripter.getLength());
            // 音量調整を端末のボタンに任せる
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            fileCheck = true;
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return fileCheck;
    }

    //再生メソッド
    private void audioPlay(){
        if(mediaPlayer == null){
            if(audioSetup()){
                Toast.makeText(getApplication(), "Rread audio file", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplication(), "Error: read audio file", Toast.LENGTH_SHORT).show();
                return;
            }
        }else{

            // 繰り返し再生する場合
            //mediaPlayer.stop();
            //mediaPlayer.reset();
            // リソースの解放
            // mediaPlayer.release();

        }

        //再生する
        mediaPlayer.start();


        //終了を検知するリスナー
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.d("debug","end of audio");
                audioStop();
            }
        });
    }

    private  void audioPause(){
        mediaPlayer.pause();
    }
    private void audioStop() {
        taskrunning=false;
        // 再生終了
        mediaPlayer.stop();
        // リセット
        mediaPlayer.reset();
        // リソースの解放
        mediaPlayer.release();

        mediaPlayer = null;
    }

    private void showTime(){
        maxTime =mediaPlayer.getDuration();
        maxTime = maxTime/1000;
        nowTime = mediaPlayer.getCurrentPosition();
        nowTime = nowTime/1000;
        time.setText((int)Math.floor(nowTime/60)+":"+(int)Math.floor(nowTime%60)+"/"+(int)Math.floor(maxTime/60)+":"+(int)Math.floor(maxTime%60));
        m_seek.setProgress(mediaPlayer.getCurrentPosition());
    }

    /**
     * A simple {@link Fragment} subclass.
     */
    public static class TextFragment extends Fragment implements Text_SpeechText.OnSpeechListener, View.OnClickListener {

        private EditText editText;
        private TextView textView;
        private File appDir;
        FragmentManager flagmentManager;
        TextSpaceDialogFlagment dialog;
        private EditText Title;
        private String filePath = ".txt";


        public TextFragment() {
            // Required empty public constructor
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            //return inflater.inflate(R.layout.fragment_text, container, false);

            // 第３引数のbooleanは"container"にreturnするViewを追加するかどうか
            //trueにすると最終的なlayoutに再度、同じView groupが表示されてしまうのでfalseでOKらしい
            View v = inflater.inflate(R.layout.fragment_text, container, false);

            // ボタンを取得して、ClickListenerをセット
            Button btn = (Button)v.findViewById(R.id.button_save);
            btn.setOnClickListener(this);
            return v;


        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            String path = getArguments().getString("path");
            Text_SpeechText.convert(getContext(),path,this);

            TextView textPath = getView().findViewById(R.id.path);
            textPath.setText(path);
            TextView textOutput = getView().findViewById(R.id.output);
            textOutput.setText("変換中");
            // SDカードのディレクトリ
            File dir = Environment.getExternalStorageDirectory();;
            // アプリ名で
            appDir = new File(dir, "Text3");
            // ディレクトリを作る
            if (!appDir.exists()) appDir.mkdir();


        }

        @Override
        public void OnSpeech(RecognizeResponse response) {
             editText = getView().findViewById(R.id.output);
            if(response == null)
                editText.setText("変換エラー");
            else{
                editText.setText("");
                List<SpeechRecognitionResult> results = response.getResultsList();
                for (SpeechRecognitionResult result : results) {
                    // There can be several alternative transcripts for a given chunk of speech. Just use the
                    // first (most likely) one here.
                    SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                    editText.append(alternative.getTranscript());
                }
            }

        }

        @Override
        public void onClick(View view) {
            Title = getActivity().findViewById(R.id.titletext);
            if(Title.getText().toString().equals("")){
                //ダイアログクラスをインスタンス化
                flagmentManager = getFragmentManager();
                // ダイアログクラスをインスタンス化
                dialog = new TextSpaceDialogFlagment();
                dialog.show(flagmentManager,"sample");
                return;
            }



            // 現在ストレージが書き込みできるかチェック
            textView = getActivity().findViewById(R.id.text_view);
            if (isExternalStorageWritable()) {
                String str = editText.getText().toString();

                try (FileOutputStream fileOutputStream = new FileOutputStream(appDir +"/"+Title.getText().toString()+filePath);
                     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
                     BufferedWriter bw = new BufferedWriter(new BufferedWriter(outputStreamWriter));
                ) {
                    bw.write(str);
                    bw.flush();
                    textView.setText("保存しました!");
                } catch (Exception e) {
                    textView.setText("エラーです");
                    e.printStackTrace();
                }
            }
        }

        // ダイアログで入力した値をtextViewに入れる - ダイアログから呼び出される
        public void setTextView(Bundle value) {
            String name =  value.getString("Title");
            EditText editText = getActivity().findViewById(R.id.titletext);
            editText.setText(name);
        }

        /* Checks if external storage is available for read and write */
        public boolean isExternalStorageWritable() {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                return true;
            }
            return false;
        }

        /* Checks if external storage is available to at least read */
        public boolean isExternalStorageReadable() {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state) ||
                    Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
                return true;
            }
            return false;
        }


    }
}
