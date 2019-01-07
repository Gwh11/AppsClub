package com.example.haoza.appsclub.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haoza.appsclub.R;


public class ClubActivityInfoFragment extends Fragment {

    private Bundle bundle;
    private TextView c_activity_info_tv;

    public static ClubActivityInfoFragment getInstance(String activityInfo){
        ClubActivityInfoFragment clubActivityInfoFragment=new ClubActivityInfoFragment();
        Bundle bundle=new Bundle();
        bundle.putString("activityInfo",activityInfo);
        clubActivityInfoFragment.setArguments(bundle);
        return clubActivityInfoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.c_activity_info_layout,null);
        init(view);
        c_activity_info_tv.setText(bundle.getString("activityInfo"));
        return view;
    }

    private void init(View view) {
        c_activity_info_tv = view.findViewById(R.id.c_activity_info_tv);
        bundle = getArguments();
    }
}
