package com.example.haoza.appsclub.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.haoza.appsclub.BaseActivity;
import com.example.haoza.appsclub.R;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class ForgetPaswordActivity extends BaseActivity implements View.OnClickListener {
    private StringBuffer mTvInfo = new StringBuffer("MTVINFO:");

    private EditText et_user_password_forget;
    private EditText et_user_password_again_forget;
    private EditText et_user_tel_forget;
    private EditText et_user_VCode_forget;
    private Button btn_sendVCode_forget;
    private Button btn_ok_forget;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ForgetPaswordActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pasword);
        initView();
    }

    private void initView() {
        et_user_password_forget = (EditText) findViewById(R.id.et_user_password_forget);
        et_user_password_again_forget = (EditText) findViewById(R.id.et_user_password_again_forget);
        et_user_tel_forget = (EditText) findViewById(R.id.et_user_tel_forget);
        et_user_VCode_forget = (EditText) findViewById(R.id.et_user_VCode_forget);
        btn_sendVCode_forget = (Button) findViewById(R.id.btn_sendVCode_forget);
        btn_ok_forget = (Button) findViewById(R.id.btn_ok_forget);

        btn_sendVCode_forget.setOnClickListener(this);
        btn_ok_forget.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sendVCode_forget:
                String phone_forget = et_user_tel_forget.getText().toString().trim();
                if (TextUtils.isEmpty(phone_forget)) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                /**
                 * TODO template 如果是自定义短信模板，此处替换为你在控制台设置的自定义短信模板名称；如果没有对应的自定义短信模板，则使用默认短信模板，模板名称为空字符串""。
                 */
                BmobSMS.requestSMSCode(phone_forget, "appsVCode", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer smsId, BmobException e) {
                        if (e == null) {
                            Toast.makeText(ForgetPaswordActivity.this, "发送验证码成功", Toast.LENGTH_SHORT).show();
                            mTvInfo.append("发送验证码成功，短信ID：" + smsId + "\n");
                        } else {
                            Toast.makeText(ForgetPaswordActivity.this, "发送验证码失败", Toast.LENGTH_SHORT).show();
                            mTvInfo.append("发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                        }
                    }
                });
                break;
            case R.id.btn_ok_forget:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        final String newPassword = et_user_password_forget.getText().toString().trim();
        if (TextUtils.isEmpty(newPassword)) {
            Toast.makeText(this, "新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        final String newPassword_again = et_user_password_again_forget.getText().toString().trim();
        if (TextUtils.isEmpty(newPassword_again)) {
            Toast.makeText(this, "请再次输入密码", Toast.LENGTH_SHORT).show();
            return;
        }else {
            if(!newPassword_again.equals(newPassword)){
                Toast.makeText(this, "两次输入密码不一样", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        final String phone_forget = et_user_tel_forget.getText().toString().trim();
        if (TextUtils.isEmpty(phone_forget)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        String vCode_forget = et_user_VCode_forget.getText().toString().trim();
        if (TextUtils.isEmpty(vCode_forget)) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something

        BmobUser.resetPasswordBySMSCode(vCode_forget, newPassword_again, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Toast.makeText(ForgetPaswordActivity.this, "重置成功", Toast.LENGTH_SHORT).show();
                    mTvInfo.append("重置成功");
                    SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(ForgetPaswordActivity.this);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putBoolean("isFirst",true);
                    editor.putString("phone",phone_forget);
                    editor.putString("password",newPassword_again);
                    LoginActivity.actionStart(ForgetPaswordActivity.this);
                    finish();
                } else {
                    Toast.makeText(ForgetPaswordActivity.this, "重置失败", Toast.LENGTH_SHORT).show();
                    mTvInfo.append("重置失败：" + e.getErrorCode() + "-" + e.getMessage());
                }
            }
        });
    }
}
