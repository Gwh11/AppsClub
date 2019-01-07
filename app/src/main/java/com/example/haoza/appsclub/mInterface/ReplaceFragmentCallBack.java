package com.example.haoza.appsclub.mInterface;

import android.view.View;

import com.example.haoza.appsclub.customObject.ClubActivity;
import com.example.haoza.appsclub.customObject.MyBmobArticle;

import cn.bmob.v3.BmobArticle;


public interface ReplaceFragmentCallBack {
    //首页详情内容fragment移动
    void replaceFragment(MyBmobArticle bmobArticle);
    //活动详情内容fragment移动
    void replaceActivityInfo(ClubActivity clubActivity);
}
