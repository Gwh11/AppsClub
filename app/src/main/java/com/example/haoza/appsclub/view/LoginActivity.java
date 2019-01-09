package com.example.haoza.appsclub.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haoza.appsclub.BaseActivity;
import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.customObject.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * 登陆页面
 *
 * @date 2018-12-30
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private StringBuffer mTvInfo = new StringBuffer("MTVINFO:");
    private EditText et_user_id;
    private EditText et_user_password;
    private Button btn_login;
    private TextView tv_forgetPassword;
    private TextView tv_register;
    private SharedPreferences sharedPreferences;
    private Boolean isfirst;
    private SharedPreferences.Editor editor;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
        setContentView(R.layout.activity_login);
        sharedPreferences=getSharedPreferences("userinfo",MODE_PRIVATE);
        initView();
        isFirst();
    }

    //判断是否为第一次启动
    private void isFirst(){
        isfirst = sharedPreferences.getBoolean("isFirst",false);
        if(isfirst){
            et_user_id.setText(sharedPreferences.getString("phone",null));
            et_user_password.setText(sharedPreferences.getString("password",null));
            submit();
        }
        return;
    }

    private void initView() {

        et_user_id = (EditText) findViewById(R.id.et_user_id);
        et_user_password = (EditText) findViewById(R.id.et_user_password);

        btn_login = (Button) findViewById(R.id.btn_login);
        tv_forgetPassword = (TextView) findViewById(R.id.tv_forgetPassword);
        tv_register = (TextView) findViewById(R.id.tv_register);

        btn_login.setOnClickListener(this);
        tv_forgetPassword.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                btn_login.setEnabled(false);
                submit();
                break;
            case R.id.tv_forgetPassword:
                ForgetPaswordActivity.actionStart(LoginActivity.this);
                break;
            case R.id.tv_register:
                RegisterActivity.actonStart(LoginActivity.this);
                break;
        }
    }

    private void submit() {
        // validate
        String id = et_user_id.getText().toString().trim();
        if (TextUtils.isEmpty(id)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = et_user_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        loginByPhone(id,password);

    }

    /**
     * 手机号码密码登录
     */
    private void loginByPhone(String phone,String password){
        //TODO 此处替换为你的手机号码和密码
        BmobUser.loginByAccount(phone, password, new LogInListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if(user!=null){
                    if (e == null) {
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        mTvInfo.append("短信登录成功：" + user.getObjectId() + "-" + user.getUsername());
                        MainActivity.actionStart(LoginActivity.this);
                        editor=sharedPreferences.edit();
                        editor.putBoolean("isFirst",true);
                        editor.putString("phone",et_user_id.getText().toString().trim());
                        editor.putString("password",et_user_password.getText().toString().trim());
                        editor.commit();
                        finish();
                    } else {
                        btn_login.setEnabled(true);
                        Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                        mTvInfo.append("短信登录失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                    }
                }
            }
        });
    }
}
