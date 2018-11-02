package com.example.jp.ac.chiba_fjb.x16g_b.test;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Timer;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextMessage;
    private Handler mHandler;
    private Timer mTimer;
    // 時刻表示のフォーマット
    private static SimpleDateFormat mSimpleDataFormat = new SimpleDateFormat("yyyy年　MM月dd日　HH:mm:ss");


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeFragment(MainFragment.class);

//インスタンスの取得
        Button top= (Button)findViewById(R.id.button2);
        findViewById(R.id.button2).setOnClickListener(this);

        //イベントの設定
        top.setOnClickListener(this);




//
//        Button b4= (Button)findViewById(R.id.top);
//        b4.setOnClickListener(this);
//        findViewById(R.id.top).setOnClickListener(this);


//      mHandler = new Handler();
//        mTimer = new Timer();
//
//       // 一秒ごとに定期的に実行します。
//       mTimer.schedule(new TimerTask() {
//            @Override
//           public void run() {
//                mHandler.post(new Runnable() {
//                    public void run() {
//                        Calendar calendar = Calendar.getInstance();
//                       String nowDate = mSimpleDataFormat.format(calendar.getTime());
//                       // 時刻表示をするTextView
//                      ((TextView) findViewById(R.id.clock)).setText(nowDate);
//                  }
//               });}
//        },0,1000);
   }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 定期実行をcancelする
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
    public void onClick(View v) {

        if(v.getId() == R.id.home)
        changeFragment(HomeFragment.class);


        else if(v.getId() == R.id.button2)
            changeFragment(TopFragment.class);

////インテントの作成
//        Intent intent = new Intent(this, home.class);
////データをセット
//        EditText editText = (EditText)this.findViewById(R.id.title);
//        intent.putExtra("sendText",editText.getText().toString());
////遷移先の画面を起動
//        startActivity(intent);
    }
    public void changeFragment(Class c){
        changeFragment(R.id.fragment,c,null);
    }


    public void changeFragment(int id,Class c){
        changeFragment(id,c,null);
    }

    public void changeFragment(int id,Class c,Bundle budle){
        try {
            Fragment f = (Fragment) c.newInstance();
            if(budle != null)
                f.setArguments(budle);
            else
                f.setArguments(new Bundle());

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(id,f);
            ft.addToBackStack(null);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        changeFragment(KarendaFragment.class);
    }


}

