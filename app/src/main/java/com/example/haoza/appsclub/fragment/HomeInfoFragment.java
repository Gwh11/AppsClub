package com.example.haoza.appsclub.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.haoza.appsclub.R;

public class HomeInfoFragment extends Fragment {
    public WebView web_view;
    private Bundle bundle;

    public static HomeInfoFragment getInstance(String url) {
        HomeInfoFragment homeInfoFragment = new HomeInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url",url);
        homeInfoFragment.setArguments(bundle);
        return homeInfoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_info_fragment, null);
        init(view);

        return view;
    }

    private void init(View view) {
        bundle = getArguments();
        web_view = (WebView) view.findViewById(R.id.web_view);
        web_view.getSettings().setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        web_view.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        web_view.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        web_view.setWebViewClient(new WebViewClient());
        web_view.loadUrl(bundle.getString("url"));
        web_view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && web_view.canGoBack()) {  //表示按返回键

                        web_view.goBack();   //后退

                        return true;    //已处理
                    }
                }
                return false;
            }
        });

    }
}
