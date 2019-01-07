package com.example.haoza.appsclub.customObject;


import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * 社团活动表
 */
public class ClubActivity extends BmobObject {
    /**
     * 活动标题
     */
    private String activityTitle;
    /**
     * 活动时间
     */
    private BmobDate activityTime;
    /**
     * 活动地点
     */
    private String activitySite;
    /**
     * 活动详情
     */
    private String activityInfo;
    /**
     * 活动人员
     */
    private BmobRelation activityUser;

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public BmobRelation getActivityUser() {
        return activityUser;
    }

    public void setActivityUser(BmobRelation activityUser) {
        this.activityUser = activityUser;
    }

    public BmobDate getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(BmobDate activityTime) {
        this.activityTime = activityTime;
    }

    public String getActivitySite() {
        return activitySite;
    }

    public void setActivitySite(String activitySite) {
        this.activitySite = activitySite;
    }

    public String getActivityInfo() {
        return activityInfo;
    }

    public void setActivityInfo(String activityInfo) {
        this.activityInfo = activityInfo;
    }
}
