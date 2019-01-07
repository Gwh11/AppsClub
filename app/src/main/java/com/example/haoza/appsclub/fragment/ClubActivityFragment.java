package com.example.haoza.appsclub.fragment;

import android.content.Context;
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
import com.example.haoza.appsclub.adapter.ActivityAdapter;
import com.example.haoza.appsclub.customObject.ClubActivity;
import com.example.haoza.appsclub.mInterface.ReplaceFragmentCallBack;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 活动详情
 */
public class ClubActivityFragment extends Fragment {

    private ReplaceFragmentCallBack replaceFragmentCallBack;
    private RecyclerView c_activity_rec_view;
    private List<ClubActivity> clubActivityList;
    private View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        replaceFragmentCallBack = (ReplaceFragmentCallBack) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.c_activity_rec_frag_layout,null);
        initClubActivity();
        init(view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        c_activity_rec_view.setLayoutManager(layoutManager);

        return view;
    }

    private void initClubActivity() {
        MQuery();
    }

    /**
     * 查询
     */
    private void MQuery() {

        BmobQuery<ClubActivity> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.findObjects(new FindListener<ClubActivity>() {
            @Override
            public void done(List<ClubActivity> object, BmobException e) {
                if (e == null) {
                    clubActivityList=object;
                    ActivityAdapter activityAdapter=new ActivityAdapter(clubActivityList,replaceFragmentCallBack);
                    c_activity_rec_view.setAdapter(activityAdapter);
                    Snackbar.make(view, "查询成功：" + object.size(), Snackbar.LENGTH_LONG).show();
                } else {
                    Log.e("BMOB", e.toString());
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void init(View view) {
        c_activity_rec_view = view.findViewById(R.id.c_activity_rec_view);
    }
}
