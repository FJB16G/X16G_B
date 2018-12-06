package com.example.jp.ac.chiba_fjb.x16g_b.a;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

public class VoiceAllspaceDailogFlagment extends DialogFragment {

    // ダイアログが生成された時に呼ばれるメソッド ※必須
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // ダイアログ生成  AlertDialogのBuilderクラスを指定してインスタンス化します
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity()).setCancelable(false);
        // タイトル設定
        dialogBuilder.setTitle("録音データ名設定");
        // 表示する文章設定
        dialogBuilder.setMessage("データ名が空白です。再度入力してください");

        // 入力フィールド作成
        final EditText editText = new EditText(getActivity());
        editText.setText("新規録音");
        dialogBuilder.setView(editText);
        // OKボタン作成
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // editTextの内容を元画面に反映する
                // editTextから値を取得
                String returnValue = editText.getText().toString();
                // MainActivityのインスタンスを取得
                VoiceMainActivity voiceMainActivity = (VoiceMainActivity) getActivity();
                voiceMainActivity.setTextView(returnValue);


            }
        });

        // NGボタン作成
        dialogBuilder.setNegativeButton("NG", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // editTextの内容を元画面に反映する
                // editTextから値を取得
                String returnValue = editText.getText().toString();
                // MainActivityのインスタンスを取得
                VoiceMainActivity voiceMainActivity = (VoiceMainActivity) getActivity();
                voiceMainActivity.setTextView(returnValue);
            }
        });

        // dialogBulderを返す
        return dialogBuilder.create();
    }

}
