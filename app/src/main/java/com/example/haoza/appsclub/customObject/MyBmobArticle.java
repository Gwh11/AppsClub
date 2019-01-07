package com.example.haoza.appsclub.customObject;

import cn.bmob.v3.BmobArticle;
import cn.bmob.v3.datatype.BmobFile;
/**
 * 图文信息表
 */
public class MyBmobArticle extends BmobArticle {
    /**
     * 标题图片
     */
    private BmobFile titleImage;
    /**
     * URL
     */
    private String mUrl;
    /**
     * 类别
     */
    private String mType;
    /**
     * 标题
     */
    private String mTitle;


    public BmobFile getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(BmobFile titleImage) {
        this.titleImage = titleImage;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
