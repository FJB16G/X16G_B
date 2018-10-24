package jp.ac.chiba_fjb.oikawa.googledrivesample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import jp.ac.chiba_fjb.oikawa.googledrivesample.Libs.AppFinger;
import jp.ac.chiba_fjb.oikawa.googledrivesample.Libs.GoogleDrive;
import jp.ac.chiba_fjb.oikawa.googledrivesample.Libs.Permission;

//https://console.cloud.google.com/
//にアクセスして、GoogleDriveの有効化とフィンガーコードの登録を行う

public class MainActivity extends AppCompatActivity {
	Permission mPermission;
	GoogleDrive mDrive;

		@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDrive = new GoogleDrive(this);

		//キー登録用SHA1の出力(いらなければ消す)
		Log.d("フィンガーコード", AppFinger.getSha1(this));
		//Android6.0以降のパーミッション処理
		mPermission = new Permission();
		mPermission.setOnResultListener(new Permission.ResultListener() {
			@Override
			public void onResult() {
				changeFragment(MainFragment.class);
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
	public GoogleDrive getDrive(){return mDrive;}
	public void changeFragment(Class c){
		changeFragment(c,null);
	}
	public void changeFragment(Class c,Bundle budle){
		try {
			Fragment f = (Fragment) c.newInstance();
			if(budle != null)
				f.setArguments(budle);
			else
				f.setArguments(new Bundle());

			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.fragment,f);
			ft.addToBackStack(null);
			ft.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
