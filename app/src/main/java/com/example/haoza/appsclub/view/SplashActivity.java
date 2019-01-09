package com.example.haoza.appsclub.view;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.haoza.appsclub.BaseActivity;
import com.example.haoza.appsclub.R;

import cn.bmob.v3.Bmob;

/**
 * 闪屏页
 *
 * @date 2018-12-30
 */
public class SplashActivity extends BaseActivity {

    private static final String APPID = "50d6ec06947463dc4bbfc0ee0ed7ed36";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化 Bmob SDK
        Bmob.initialize(this,"50d6ec06947463dc4bbfc0ee0ed7ed36");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
        setContentView(R.layout.activity_splash);

        mHandler.sendEmptyMessageDelayed(GO_LOGIN, 3000);
    }

    private static final int GO_HOME = 100;
    private static final int GO_LOGIN = 200;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    break;
                case GO_LOGIN:
                    LoginActivity.actionStart(SplashActivity.this);
                    finish();
                    break;
            }
        }
    };


}
