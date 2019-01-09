package com.example.haoza.appsclub.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haoza.appsclub.BaseActivity;
import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.adapter.InfoAdapter;
import com.example.haoza.appsclub.customObject.Department;
import com.example.haoza.appsclub.customObject.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    private EditText et_user_name;
    private EditText et_user_major;
    private EditText et_user_class;
    private EditText et_user_password;
    private EditText et_user_password_again;
    private EditText et_user_tel;
    private EditText et_user_VCode;
    private Button btn_sendVCode;
    private Button btn_zhuce;
    private Spinner sp_user_department;
    /*private Spinner spi_gender;*/
    /*private int anInt=0;*/
    private List<Department> departmentList=new ArrayList<>();
    List<String> departNameList=new ArrayList<>();
    private int department_position;
    private TextView tv_sp_departName;
    private ArrayAdapter<String> arrayAdapter;

    public static void actonStart(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    private StringBuffer mTvInfo = new StringBuffer("MTVINFO:");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DepartmentQuery();
        initView();
    }

    private void initView() {
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_user_major = (EditText) findViewById(R.id.et_user_major);
        et_user_class = (EditText) findViewById(R.id.et_user_class);
        tv_sp_departName = findViewById(R.id.tv_sp_departName);
        sp_user_department = findViewById(R.id.sp_user_department_00000);
        arrayAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_list_item_1, departNameList);
        sp_user_department.setAdapter(arrayAdapter);
        sp_user_department.setOnItemSelectedListener(this);

        et_user_password = (EditText) findViewById(R.id.et_user_password);
        et_user_password_again = (EditText) findViewById(R.id.et_user_password_again);
        et_user_tel = (EditText) findViewById(R.id.et_user_tel);
        et_user_VCode = (EditText) findViewById(R.id.et_user_VCode);
        btn_sendVCode = (Button) findViewById(R.id.btn_sendVCode);
        btn_zhuce = (Button) findViewById(R.id.btn_zhuce);

        btn_sendVCode.setOnClickListener(this);
        btn_zhuce.setOnClickListener(this);
    }

    /**
     * 查询
     * Department
     */
    private void DepartmentQuery() {
        BmobQuery<Department> departmentBmobQuery = new BmobQuery<Department>();
        departmentBmobQuery.findObjects(new FindListener<Department>() {
            @Override
            public void done(List<Department> object, BmobException e) {
                if (e == null) {
                    departmentList = object;
                    for(Department department:departmentList){
                        departNameList.add(department.getDepartName());
                    }
                    arrayAdapter.notifyDataSetChanged();
//                    Snackbar.make(view, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                    Toast.makeText(RegisterActivity.this, "查询成功："+ object.size(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("BMOB", e.toString());
//                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sendVCode:
                String tel = et_user_tel.getText().toString().trim();
                if (TextUtils.isEmpty(tel)) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                /**
                 * TODO template 如果是自定义短信模板，此处替换为你在控制台设置的自定义短信模板名称；如果没有对应的自定义短信模板，则使用默认短信模板，默认模板名称为空字符串""。
                 * <p>
                 * TODO 应用名称以及自定义短信内容，请使用不会被和谐的文字，防止发送短信验证码失败。
                 */
                BmobSMS.requestSMSCode(tel, "appsVCode", new QueryListener<Integer>() {
                    @Override
                    public void done(Integer smsId, BmobException e) {
                        if (e == null) {
                            Toast.makeText(RegisterActivity.this, "发送验证码成功", Toast.LENGTH_SHORT).show();
                            mTvInfo.append("发送验证码成功，短信ID：" + smsId + "\n");
                        } else {
                            Toast.makeText(RegisterActivity.this, "发送验证码失败", Toast.LENGTH_SHORT).show();
                            mTvInfo.append("发送验证码失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                        }
                    }
                });
                break;
            case R.id.btn_zhuce:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String name = et_user_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "请输入真实姓名（这很重要）", Toast.LENGTH_SHORT).show();
            return;
        }

        String major = et_user_major.getText().toString().trim();
        if (TextUtils.isEmpty(major)) {
            Toast.makeText(this, "请输入专业", Toast.LENGTH_SHORT).show();
            return;
        }

        String user_class = et_user_class.getText().toString().trim();
        if (TextUtils.isEmpty(user_class)) {
            Toast.makeText(this, "班级", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = et_user_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String again = et_user_password_again.getText().toString().trim();
        if (TextUtils.isEmpty(again)) {
            Toast.makeText(this, "请再次输入密码", Toast.LENGTH_SHORT).show();
            return;
        }else {
            if(!again.equals(password)){
                Toast.makeText(this, "两次输入密码不一样", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        String tel = et_user_tel.getText().toString().trim();
        if (TextUtils.isEmpty(tel)) {
            Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        String VCode = et_user_VCode.getText().toString().trim();
        if (TextUtils.isEmpty(VCode)) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("isFirst",true);
        editor.putString("phone",tel);
        editor.putString("password",again);
        signOrLogin(name,major,user_class,departmentList.get(department_position),again,tel,VCode);

    }

    /**
     * 一键注册或登录的同时保存其他字段的数据
     * @param name
     * @param major
     * @param userClass
     * @param password
     * @param phone
     * @param code
     *
     */
    private void signOrLogin(String name,String major,String userClass,Department department,String password,String phone,String code) {
        User user = new User();
        //设置手机号码（必填）
        user.setMobilePhoneNumber(phone);
        //设置用户名，如果没有传用户名，则默认为手机号码
        user.setUsername(name);
        //设置用户密码
        user.setPassword(password);
        /*//设置性别
        user.setGender(gender);*/
        //设置专业
        user.setMajor(major);
        //设置部门
        user.setDepartmentId(department);
        //设置职位
        user.setPost("成员");
        //设置班级
        user.setUserClass(userClass);
        user.signOrLogin(code, new SaveListener<User>() {

            @Override
            public void done(User user,BmobException e) {
                if (e == null) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    mTvInfo.append("短信注册或登录成功：" + user.getUsername());
                    MainActivity.actionStart(RegisterActivity.this);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    mTvInfo.append("短信注册或登录失败：" + e.getErrorCode() + "-" + e.getMessage() + "\n");
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        department_position = position;
        Log.d("departname", "onItemSelected: "+departmentList.get(position).getDepartmentName());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}


