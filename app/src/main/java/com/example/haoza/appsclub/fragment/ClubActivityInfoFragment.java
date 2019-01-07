package com.example.haoza.appsclub.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.customObject.ClubActivity;
import com.example.haoza.appsclub.customObject.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;


public class ClubActivityInfoFragment extends Fragment implements View.OnClickListener {
    private User user=BmobUser.getCurrentUser(User.class);
    private Bundle bundle;
    private TextView c_activity_info_tv;
    private Button c_activity_info_btn_ok;
    private Button c_activity_info_btn_quit;
    private ClubActivity clubActivity;

    public static ClubActivityInfoFragment getInstance(ClubActivity clubActivity){
        ClubActivityInfoFragment clubActivityInfoFragment=new ClubActivityInfoFragment();
        Bundle bundle=new Bundle();
        bundle.putString("activityInfo",clubActivity.getActivityInfo());
        bundle.putSerializable("clubActivity",clubActivity);
        clubActivityInfoFragment.setArguments(bundle);
        return clubActivityInfoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.c_activity_info_layout,null);
        init(view);
        c_activity_info_tv.setText(bundle.getString("activityInfo"));
        clubActivity = (ClubActivity) bundle.getSerializable("clubActivity");

        // 查询参加的所有用户，因此查询的是用户表
        BmobQuery<User> query = new BmobQuery<User>();
        ClubActivity activity = new ClubActivity();
        activity.setObjectId(clubActivity.getObjectId());
//likes是Post表中的字段，用来存储所有喜欢该帖子的用户
        query.addWhereRelatedTo("activityUser", new BmobPointer(activity));
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if(e==null){
                    Log.i("bmob","查询个数："+object.size());
                    for (User userS:object){
                        if (userS==user) {
                            c_activity_info_btn_ok.setVisibility(View.GONE);
                            c_activity_info_btn_quit.setVisibility(View.VISIBLE);
                        } else {
                            c_activity_info_btn_ok.setVisibility(View.VISIBLE);
                            c_activity_info_btn_quit.setVisibility(View.GONE);
                        }
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }

        });

        return view;
    }

    private void init(View view) {
        c_activity_info_tv = view.findViewById(R.id.c_activity_info_tv);
        c_activity_info_btn_ok = view.findViewById(R.id.c_activity_info_btn_ok);
        c_activity_info_btn_quit = view.findViewById(R.id.c_activity_info_btn_quit);
        c_activity_info_btn_ok.setOnClickListener(this);
        c_activity_info_btn_quit.setOnClickListener(this);
        bundle = getArguments();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.c_activity_info_btn_ok:
                c_activity_info_btn_ok.setVisibility(View.GONE);
                c_activity_info_btn_quit.setVisibility(View.VISIBLE);
                //参加活动
                ClubActivity activity_add = new ClubActivity();
                activity_add.setObjectId(clubActivity.getObjectId());
//将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
                BmobRelation relation_add = new BmobRelation();
//将当前用户添加到多对多关联中
                relation_add.add(user);
//多对多关联指向`post`的`likes`字段
                activity_add.setActivityUser(relation_add);
                activity_add.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Log.i("bmob","多对多关联添加成功");
                        }else{
                            Log.i("bmob","失败："+e.getMessage());
                        }
                    }

                });

                break;
            case R.id.c_activity_info_btn_quit:
                c_activity_info_btn_quit.setVisibility(View.GONE);
                c_activity_info_btn_ok.setVisibility(View.VISIBLE);
                //退出活动
                ClubActivity activity_remove = new ClubActivity();
                activity_remove.setObjectId(clubActivity.getObjectId());
                BmobRelation relation_remove = new BmobRelation();
                relation_remove.remove(user);
                activity_remove.setActivityUser(relation_remove);
                activity_remove.update(new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Log.i("bmob","关联关系删除成功");
                        }else{
                            Log.i("bmob","失败："+e.getMessage());
                        }
                    }

                });

                break;
        }
    }
}
