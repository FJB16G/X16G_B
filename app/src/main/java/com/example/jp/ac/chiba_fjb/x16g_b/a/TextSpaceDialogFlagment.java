package com.example.jp.ac.chiba_fjb.x16g_b.a;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

public class TextSpaceDialogFlagment extends DialogFragment {
    private EditText editText;

    // ダイアログが生成された時に呼ばれるメソッド ※必須
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // ダイアログ生成  AlertDialogのBuilderクラスを指定してインスタンス化します
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity()).setCancelable(false);
        // タイトル設定
        dialogBuilder.setTitle("タイトルが空白です");
        // 表示する文章設定
        dialogBuilder.setMessage("タイトルを入力してください");

        // 入力フィールド作成/
       /* final EditText editText = new EditText(getActivity());
        editText.setText("");
        dialogBuilder.setView(editText);*/
        // OKボタン作成
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
               /* // editTextから値を取得
                String returnValue = editText.getText().toString();
                // ダイアログクラスをインスタンス化
                TextFragment textFragment = new TextFragment();
                // ダイアログに値を渡す
                Bundle bundle = new Bundle();
                // キーと値の順で渡す
                bundle.putString("Title", editText.getText().toString());
                // 値をセット
                textFragment.setTextView(bundle);*/





            }

        });

        /*// NGボタン作成
        dialogBuilder.setNegativeButton("NG", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 何もしないで閉じる
            }
        });*/

        // dialogBulderを返す
        return dialogBuilder.create();

    }


}