package com.example.jp.ac.chiba_fjb.x16g_b.test;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jp.ac.chiba_fjb.x16g_b.test.Libs.GoogleDrive;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Timer;
import java.util.TimerTask;




/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {


    private GoogleApiClient mGoogleApiClient;
    private int RC_SIGN_IN;
    private View new_google;
    boolean swt = false;
    GoogleDrive mDriveTest;


    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.Sign_in).setOnClickListener(this);
        view.findViewById(R.id.new_google).setOnClickListener(this);
        view.findViewById(R.id.Sign_out).setOnClickListener(this);
    }

    // Sign-In処理
    private boolean signIn() {

        //ランタイムパーミッションの許可が下りた後の処理
        final GoogleDrive drive =((Main)getActivity()).getDrive();

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
        mDriveTest = drive;
        return  swt;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Sign_in:
                boolean sw2=false;
                sw2=signIn();

                final Intent intent = new Intent(getActivity(),MainActivity.class);
                final Timer timer = new Timer();

               TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        if (mDriveTest.aname.equals("set")) {
                            startActivity(intent);
                            timer.cancel();
                        }
                    }
                };
                //タイマー開始処理
                timer.schedule(timerTask,0,500);
                break;

            case R.id.new_google:
                Uri uri = Uri.parse("https://accounts.google.com/signup/v2/webcreateaccount?continue=https%3A%2F%2Faccounts.google.com%2Fsignin%2Fchrome%2Fsync%2Ffinish%3Fcontinue%3Dhttps%253A%252F%252Fwww.google.co.jp%252F%26est%3DACQ6tZJU4wyjAi6JeqxRooOXhGNsbUpCufuLrSm6WTgRDfY4xickxe0IihvBm6Li_7F4MfT6soQye61WYaIm4Nck0SNk-h6ckgLb&flowName=GlifWebSignIn&flowEntry=SignUp");
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent2);
                break;

            case R.id.Sign_out:
                GoogleDrive drive =((Main)getActivity()).getDrive();
                drive.resetAccount();
                Toast toast2 = Toast.makeText(getActivity(), "サインアウトしました。", Toast.LENGTH_LONG);
                toast2.show();
                break;
        }
    }
}
