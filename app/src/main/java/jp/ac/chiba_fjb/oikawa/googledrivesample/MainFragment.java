package jp.ac.chiba_fjb.oikawa.googledrivesample;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;

import jp.ac.chiba_fjb.oikawa.googledrivesample.Libs.GoogleDrive;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {


    private GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN;
    private View button2;


    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button).setOnClickListener(this);
        view.findViewById(R.id.button2).setOnClickListener(this);
        view.findViewById(R.id.button5).setOnClickListener(this);
    }

    // Sign-In処理
    private void signIn() {
        //ランタイムパーミッションの許可が下りた後の処理
        final GoogleDrive drive =((MainActivity)getActivity()).getDrive();
        drive.setOnConnectedListener(new GoogleDrive.OnConnectListener() {
            @Override
            public void onConnected(boolean flag) {
                if (flag) {
                    String src = Environment.getExternalStorageDirectory().getPath()+"/test.txt";
                    drive.upload("/text.txt", src,"text/plain");
                }
            }
        });
        drive.connect();
    }

    /**
     *
     */

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                //signIn();

                // インテントの生成
                Intent intent = new Intent();
                intent.setClassName("jp.ac.chiba_fjb.oikawa.googledrivesample", "jp.ac.chiba_fjb.oikawa.googledrivesample.ConfigrationActivity");

               // SubActivity の起動
                 startActivity(intent);
                break;
            case R.id.button2:
                Uri uri = Uri.parse("https://accounts.google.com/signup/v2/webcreateaccount?continue=https%3A%2F%2Faccounts.google.com%2Fsignin%2Fchrome%2Fsync%2Ffinish%3Fcontinue%3Dhttps%253A%252F%252Fwww.google.co.jp%252F%26est%3DACQ6tZJU4wyjAi6JeqxRooOXhGNsbUpCufuLrSm6WTgRDfY4xickxe0IihvBm6Li_7F4MfT6soQye61WYaIm4Nck0SNk-h6ckgLb&flowName=GlifWebSignIn&flowEntry=SignUp");
                //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                //startActivity(intent);
                break;
            case R.id.button5:
                GoogleDrive drive =((MainActivity)getActivity()).getDrive();
                drive.resetAccount();
                Toast toast2 = Toast.makeText(getActivity(), "サインアウトしました。", Toast.LENGTH_LONG);
                toast2.show();
                break;
        }
    }
}
