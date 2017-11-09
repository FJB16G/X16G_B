package jp.ac.chiba_fjb.oikawa.googledrivesample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import jp.ac.chiba_fjb.oikawa.googledrivesample.Libs.AppFinger;
import jp.ac.chiba_fjb.oikawa.googledrivesample.Libs.GoogleDrive;
import jp.ac.chiba_fjb.oikawa.googledrivesample.Libs.Permission;

//hhttps://console.cloud.google.com/
//にアクセスして、GoogleDriveの有効化とフィンガーコードの登録を行う

public class MainActivity extends AppCompatActivity {
	Permission mPermission;
	GoogleDrive mDrive;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//キー登録用SHA1の出力(いらなければ消す)
		Log.d("フィンガーコード", AppFinger.getSha1(this));
		//Android6.0以降のパーミッション処理
		mPermission = new Permission();
		mPermission.setOnResultListener(new Permission.ResultListener() {
			@Override
			public void onResult() {
				//ランタイムパーミッションの許可が下りた後の処理
				mDrive = new GoogleDrive(MainActivity.this);
				mDrive.setOnConnectedListener(new GoogleDrive.OnConnectListener() {
					@Override
					public void onConnected(boolean flag) {
						if (flag) {
							//mDrive.createFolder(mDrive.getRootId(), "ふぉっふぉっふぉ");
							String src = Environment.getExternalStorageDirectory().getPath()+"/test.txt";
							mDrive.upload("/text.txt", src,"text/plain");
						}
					}
				});
				mDrive.connect();
			}
		});
		mPermission.requestPermissions(this);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//必要に応じてアカウントや権限ダイアログの表示
		mDrive.onActivityResult(requestCode,resultCode,data);

	}
}
