package jp.ac.chiba_fjb.x16g019_b.myapplication;


import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jp.ac.chiba_fjb.x16g_b.test.R;
import com.example.jp.ac.chiba_fjb.x16g_b.test.VoiceAllspaceDailogFlagment;
import com.example.jp.ac.chiba_fjb.x16g_b.test.VoiceCustomDialogFlagment;
import com.example.jp.ac.chiba_fjb.x16g_b.test.VoiceNextDialogFlagment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class RokuonFragment extends Fragment {

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
    private Button s_button ;
    private Button e_button ;

    private long startTime;

    public RokuonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rokuon, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mTextView = (TextView) view.findViewById(R.id.timer);
        mEditText = (EditText) view.findViewById(R.id.text);
        delay = 0;
        period = 10;
        mTextView.setText(dataFormat.format(0));


        //startボタン処理
        s_button = view.findViewById(R.id.start_Button);

        s_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                                        format(count * period));


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
                String name = Filename + ".wav";
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
        });

        //stopボタン処理
        e_button = view.findViewById(R.id.stop_button);

       e_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


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
       });

   }

    // ダイアログで入力した値をtextViewに入れる - ダイアログから呼び出される
   public void setTextView(String value) {
       if(value.equals("")){

        }
        TextView textView = (TextView)getActivity().findViewById(R.id.text);
       textView.setText(value);
    }


    }

