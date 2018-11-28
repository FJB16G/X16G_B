package jp.ac.chiba_fjb.x16g019_b.myapplication;


import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.example.jp.ac.chiba_fjb.x16g_b.test.R;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


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
    boolean taskrunning = false;
    private ImageButton s_button;
    public SeekBar m_seek;


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


            s_button = view.findViewById(R.id.start);
            time = (TextView) view.findViewById(R.id.musicTime);
            m_seek = view.findViewById(R.id.musicseek);


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


        }

        private boolean audioSetup() {
            boolean fileCheck = false;

            // インタンスを生成
            mediaPlayer = new MediaPlayer();
            //バンドルからパスを取得
            Bundle args = getArguments();
            args.getString("path");

            //音楽ファイル名, あるいはパス
            String filePath = args.getString("path");;

            // assetsから mp3 ファイルを読み込み
            try (AssetFileDescriptor afdescripter = getActivity().getAssets().openFd(filePath);) {
                // MediaPlayerに読み込んだ音楽ファイルを指定
                mediaPlayer.setDataSource(afdescripter.getFileDescriptor(),
                        afdescripter.getStartOffset(),
                        afdescripter.getLength());
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

