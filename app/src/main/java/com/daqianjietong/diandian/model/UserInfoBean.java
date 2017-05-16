package com.daqianjietong.diandian.model;

/**
 * Created by Administrator on 2017/3/13 0013.
 */

public class UserInfoBean {
    private String uid;
    private String u_username;
    private String u_photo;
    private String u_phone;
    private String u_carnum;
    private String token;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getU_username() {
        return u_username;
    }

    public void setU_username(String u_username) {
        this.u_username = u_username;
    }

    public String getU_photo() {
        return u_photo;
    }

    public void setU_photo(String u_photo) {
        this.u_photo = u_photo;
    }

    public String getU_phone() {
        return u_phone;
    }

    public void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }

    public String getU_carnum() {
        return u_carnum;
    }

    public void setU_carnum(String u_carnum) {
        this.u_carnum = u_carnum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
