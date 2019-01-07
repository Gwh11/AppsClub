package com.example.haoza.appsclub.customObject;

import cn.bmob.v3.BmobObject;
/**
 * 部门表
 */
public class Department extends BmobObject {
    /**
     * 部门名字
     */
    private String departmentName;
    /**
     * 部门介绍
     */
    private String departmentInfo;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentInfo() {
        return departmentInfo;
    }

    public void setDepartmentInfo(String departmentInfo) {
        this.departmentInfo = departmentInfo;
    }
}
