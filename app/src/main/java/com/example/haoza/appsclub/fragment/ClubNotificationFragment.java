package com.example.haoza.appsclub.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.adapter.NotificationAdapter;
import com.example.haoza.appsclub.customObject.ClubNotification;
import com.example.haoza.appsclub.customObject.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 通知
 */
public class ClubNotificationFragment extends Fragment {
    private User user=BmobUser.getCurrentUser(User.class);
    private RecyclerView c_notification_rec_view;
    private List<ClubNotification> clubNotificationList;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.c_notification_rec_frag_layout, null);
        initNotification();
        initView(view);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        c_notification_rec_view.setLayoutManager(layoutManager);

        return view;
    }
    //初始化数据
    private void initNotification() {
    }
    /**
     * 查询
     * ClubNotification
     */
    private void ClubNotificationQuery() {

        BmobQuery<ClubNotification> clubNotificationBmobQuery = new BmobQuery<>();
        clubNotificationBmobQuery.addWhereGreaterThanOrEqualTo("createdAt",user.getCreatedAt());//当前User创建时间及以后
        clubNotificationBmobQuery.order("-createdAt");
        clubNotificationBmobQuery.findObjects(new FindListener<ClubNotification>() {
            @Override
            public void done(List<ClubNotification> object, BmobException e) {
                if (e == null) {
                    clubNotificationList=object;
                    NotificationAdapter notificationAdapter=new NotificationAdapter(clubNotificationList);
                    c_notification_rec_view.setAdapter(notificationAdapter);
                    Snackbar.make(view, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initView(View view) {
        c_notification_rec_view = (RecyclerView) view.findViewById(R.id.c_notification_rec_view);
    }
}
