package com.daqianjietong.diandian.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.daqianjietong.diandian.model.UserInfoBean;


/**
 * Created by MuWenlei on 16/10/15.
 */
public class SpUtil {
    static SharedPreferences prefs;

    public static void init(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void saveUser(UserInfoBean userEntity) {
        SharedPreferences.Editor editor = prefs.edit();
        if (userEntity != null) {
            editor.putString("uid", userEntity.getUid());
            editor.putString("u_username", userEntity.getU_username());
            editor.putString("u_photo", userEntity.getU_photo());
            editor.putString("u_phone", userEntity.getU_phone());
            editor.putString("u_carnum", userEntity.getU_carnum());
            editor.putString("token", userEntity.getToken());
        } else {
            editor.putString("uid", "");
            editor.putString("u_username","");
            editor.putString("u_photo", "");
            editor.putString("u_phone", "");
            editor.putString("u_carnum","");
            editor.putString("token","");
        }
        editor.commit();
    }
    public static void saveUserName(String name) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("u_username", name);
        editor.commit();
    }

    public static void savePhoto(String photo) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("u_photo", photo);
        editor.commit();
    }

    public static void savePhone(String phone) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("u_phone", phone);
        editor.commit();
    }

    public static void saveCarnum(String carnum) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("u_carnum", carnum);
        editor.commit();
    }
    public static String getToken() {
        return prefs.getString("token", "");
    }
    public static String getUid() {
        return prefs.getString("uid", "");
    }
    public static String getUsername() {
        return prefs.getString("u_username", "");
    }
    public static String getPhoto() {
        return prefs.getString("u_photo", "");
    }
    public static String getPhone() {
        return prefs.getString("u_phone", "");
    }
    public static String getCarnum() {
        return prefs.getString("u_carnum", "");
    }

}
