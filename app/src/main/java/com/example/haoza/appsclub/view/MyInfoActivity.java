package com.example.haoza.appsclub.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.customObject.Department;
import com.example.haoza.appsclub.customObject.User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class MyInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private User user;
    private ImageView img_my_back;
    private TextView my_name;
    private TextView my_major;
    private TextView my_userClass;
    private TextView my_department;
    private TextView my_post;
    private TextView my_phone;
    private String departName;
    private Department user_department;

    /**
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MyInfoActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        user =BmobUser.getCurrentUser(User.class);
        query(user.getDepartmentId().getObjectId());

    }

    private void initView() {
        img_my_back = (ImageView) findViewById(R.id.img_my_back);
        img_my_back.setOnClickListener(this);
        my_name = (TextView) findViewById(R.id.my_name);
        my_major = (TextView) findViewById(R.id.my_major);
        my_userClass = (TextView) findViewById(R.id.my_userClass);
        my_department = (TextView) findViewById(R.id.my_department);
        my_post = (TextView) findViewById(R.id.my_post);
        my_phone = (TextView) findViewById(R.id.my_phone);

        my_name.setText(user.getUsername());
        my_major.setText(user.getMajor());
        my_userClass.setText(user.getUserClass());
        /*if(user.getDepartmentId()!=null){
            String departName=user.getDepartmentId().getDepartName();
            String departmentName=user.getDepartmentId().getDepartmentName();
            my_department.setText(user.getDepartmentId().getDepartName());
        }else {
            my_department.setText("暂未选择部门");
        }*/
//        my_department.setText(user.getDepartmentId().getDepartName());
        my_department.setText(user_department.getDepartName());

        my_post.setText(user.getPost());
        my_phone.setText(user.getMobilePhoneNumber());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_my_back:
                finish();
                break;
        }
    }

    /**
     * 查询一个对象
     */
    private void query(String objectId) {
        BmobQuery<Department> bmobQuery = new BmobQuery<Department>();
        bmobQuery.getObject(objectId, new QueryListener<Department>() {
            @Override
            public void done(Department department, BmobException e) {
                if (e == null) {
                    user_department = department;
                    initView();
//                    Snackbar.make(mBtnQuery, "查询成功：" + category.getName(), Snackbar.LENGTH_LONG).show();
                    Toast.makeText(MyInfoActivity.this, "查询成功：" + department.getDepartmentName(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("BMOB", e.toString());
//                    Snackbar.make(mBtnQuery, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    Toast.makeText(MyInfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
