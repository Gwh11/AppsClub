package com.example.haoza.appsclub.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.haoza.appsclub.BaseActivity;
import com.example.haoza.appsclub.R;
import com.example.haoza.appsclub.customObject.ClubActivity;
import com.example.haoza.appsclub.customObject.MyBmobArticle;
import com.example.haoza.appsclub.customObject.User;
import com.example.haoza.appsclub.fragment.ClubActivityFragment;
import com.example.haoza.appsclub.fragment.ClubActivityInfoFragment;
import com.example.haoza.appsclub.fragment.ClubInfoFragment;
import com.example.haoza.appsclub.fragment.ClubMemberFragment;
import com.example.haoza.appsclub.fragment.ClubNotificationFragment;
import com.example.haoza.appsclub.fragment.HomeFragmentPTR;
import com.example.haoza.appsclub.fragment.HomeFragmentRec;
import com.example.haoza.appsclub.fragment.HomeInfoFragment;
import com.example.haoza.appsclub.mInterface.ReplaceFragmentCallBack;

import cn.bmob.v3.BmobUser;

/**
 * 应用主界面
 */
public class MainActivity extends BaseActivity implements ReplaceFragmentCallBack,View.OnClickListener,NavigationView.OnNavigationItemSelectedListener {
    private FrameLayout content_framelayout;
    private User user;
    private NavigationView nav_view;

    /**
     *
     * @param context
     */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private Toolbar toolbar;
    private DrawerLayout drawer_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
//        replaceFragment(new HomeFragmentRec());
        replaceFragment(new HomeFragmentPTR());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    private void initView() {
        user =BmobUser.getCurrentUser(User.class);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        content_framelayout = (FrameLayout) findViewById(R.id.content_framelayout);

        nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.content_framelayout,fragment);
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //菜单按钮
            case android.R.id.home:
                drawer_layout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            //社团信息
            case R.id.nav_clubInfo:
                replaceFragment(new ClubInfoFragment());
                drawer_layout.closeDrawers();
                break;
            //社团花名册
            case R.id.nav_clubMember:
                replaceFragment(new ClubMemberFragment());
                drawer_layout.closeDrawers();
                break;
            //社团活动
            case R.id.nav_clubActivity:
                replaceFragment(new ClubActivityFragment());
                drawer_layout.closeDrawers();
                break;
            //社团通知
            case R.id.nav_clubNotification:
                replaceFragment(new ClubNotificationFragment());
                drawer_layout.closeDrawers();
                break;
            //首页
            case R.id.nav_home:
//                replaceFragment(new HomeFragmentRec());
                replaceFragment(new HomeFragmentPTR());
                drawer_layout.closeDrawers();
                break;
            //我的
            case R.id.nav_my:
                MyInfoActivity.actionStart(MainActivity.this);
                drawer_layout.closeDrawers();
                break;
        }
        return true;
    }

    @Override
    public void replaceFragment(MyBmobArticle bmobArticle) {
        HomeInfoFragment homeInfoFragment =HomeInfoFragment.getInstance(bmobArticle.getmUrl());
        replaceFragment(homeInfoFragment);
    }

    @Override
    public void replaceActivityInfo(ClubActivity clubActivity) {
        ClubActivityInfoFragment clubActivityInfoFragment=ClubActivityInfoFragment.getInstance(clubActivity.getActivityInfo());
        replaceFragment(clubActivityInfoFragment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nav_header_view:
                MyInfoActivity.actionStart(MainActivity.this);
                break;
        }
    }

}
