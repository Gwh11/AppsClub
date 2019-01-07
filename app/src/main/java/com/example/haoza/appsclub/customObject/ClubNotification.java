package com.example.haoza.appsclub.customObject;

import cn.bmob.v3.BmobObject;
/**
 * 社团通知
 */
public class ClubNotification extends BmobObject {
    /**
     * 通知详情
     */
    private String notificationInfo;

    public String getNotificationInfo() {
        return notificationInfo;
    }

    public void setNotificationInfo(String notificationInfo) {
        this.notificationInfo = notificationInfo;
    }
}
