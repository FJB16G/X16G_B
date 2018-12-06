package com.example.jp.ac.chiba_fjb.x16g_b.test;


import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TextFragment extends Fragment implements Text_SpeechText.OnSpeechListener, View.OnClickListener {

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


