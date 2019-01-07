package com.example.haoza.appsclub.customObject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
/**
 * 用户表
 */
public class User extends BmobUser {
    /**
     * 性别
     */
    private String gender;
    /**
     * 头像
     */
    private BmobFile avatar;
    /**
     * 专业
     */
    private String major;
    /**
     * 班级
     */
    private String userClass;
    /**
     * 部门Id
     */
    private Department departmentId;
    /**
     * 职位
     */
    private String post;
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public BmobFile getAvatar() {
        return avatar;
    }

    public void setAvatar(BmobFile avatar) {
        this.avatar = avatar;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getUserClass() {
        return userClass;
    }

    public void setUserClass(String userClass) {
        this.userClass = userClass;
    }

    public Department getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Department departmentId) {
        this.departmentId = departmentId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
