package jp.ac.chiba_fjb.x16g019_b.myapplication;


import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
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
import android.support.v4.app.Fragment;

import com.example.jp.ac.chiba_fjb.x16g_b.test.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SaiseiFragment extends Fragment {
    private MediaPlayer mediaPlayer;
    double maxTime = 0;
    double nowTime = 0;
    Looper looper = Looper.getMainLooper();
    final Handler handler = new Handler(Looper.getMainLooper());
    private TextView time;
    boolean one;
    boolean taskrunning = false;
    private ImageButton s_button;
    public SeekBar m_seek;
    private EditText e_text;
    private Button h_button;

    public SaiseiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saisei, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            one = false;

            s_button = view.findViewById(R.id.start);
            h_button = view.findViewById(R.id.save);
            time = (TextView) view.findViewById(R.id.musicTime);
            m_seek = view.findViewById(R.id.musicseek);
            audioPlay();
            m_seek.setMax(mediaPlayer.getDuration());
            showTime();

            Bundle args2 = getArguments();
            String text_name = args2.getString("path");
            text_name=text_name.replace(".wav",".txt");
            final String text_name2 =Environment.getExternalStorageDirectory().getPath()+"/Text/"+text_name;
            e_text = view.findViewById(R.id.voice_text);

        try {
            FileInputStream fis = new FileInputStream(text_name2);
            InputStreamReader isr = new InputStreamReader(fis,"UTF-8");
            BufferedReader reader = new BufferedReader(isr);
            String str = "";
            String c;
            while((c = reader.readLine())!=null) {
                str +=c;
            }
            e_text.setText(str);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


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
                    if (!taskrunning) {
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
                                        if (taskrunning) {
                                            showTime();
                                        } else {
                                            timer.cancel();
                                        }
                                    }
                                });
                            }
                        }, 0, loopTime);
                    }
                }
            });

            // 音楽停止ボタン
            ImageButton buttonStop = view.findViewById(R.id.stop);

            // リスナーをボタンに登録
            buttonStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaPlayer != null) {
                        // 音楽停止
                        audioPause();
                        taskrunning = false;
                    }
                }
            });

            //保存ボタン

        h_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    FileOutputStream outStream = new FileOutputStream(text_name2);
                    OutputStreamWriter writer = new OutputStreamWriter(outStream);
                    writer.write(e_text.getText().toString());
                    writer.flush();
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        }

        private boolean audioSetup() {
            boolean fileCheck = false;

            // インタンスを生成
            mediaPlayer = new MediaPlayer();
            //バンドルからパスを取得
            Bundle args = getArguments();
            args.getString("path");

            //音楽ファイル名, あるいはパス
            String filePath = Environment.getExternalStorageDirectory().getPath()+"/Voice/"+args.getString("path");;

            // assetsから mp3 ファイルを読み込み
            try{
                // MediaPlayerに読み込んだ音楽ファイルを指定
                mediaPlayer.setDataSource(filePath);
                // 音量調整を端末のボタンに任せる
                getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepare();
                fileCheck = true;
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            return fileCheck;
        }

        //再生メソッド
        private void audioPlay() {
            if (mediaPlayer == null) {
                if (audioSetup()) {
                    Toast.makeText(getActivity().getApplication(), "Rread audio file", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplication(), "Error: read audio file", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {

                // 繰り返し再生する場合
                //mediaPlayer.stop();
                //mediaPlayer.reset();
                // リソースの解放
                // mediaPlayer.release();

            }
            if (one){
                //再生する
                mediaPlayer.start();


                //終了を検知するリスナー
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Log.d("debug", "end of audio");
                        audioStop();
                    }
                });
            }else{
                one = true;
            }

        }

        private void audioPause() {
            mediaPlayer.pause();
        }

        private void audioStop() {
            taskrunning = false;

            // 再生終了
            mediaPlayer.stop();
            // リセット
            mediaPlayer.reset();
            // リソースの解放
            mediaPlayer.release();

            mediaPlayer = null;
        }

        private void showTime() {
            maxTime = mediaPlayer.getDuration();
            maxTime = maxTime / 1000;
            nowTime = mediaPlayer.getCurrentPosition();
            nowTime = nowTime / 1000;
            time.setText((int) Math.floor(nowTime / 60) + ":" + (int) Math.floor(nowTime % 60) + "/" + (int) Math.floor(maxTime / 60) + ":" + (int) Math.floor(maxTime % 60));
            m_seek.setProgress(mediaPlayer.getCurrentPosition());
        }


    }

