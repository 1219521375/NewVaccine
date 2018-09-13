package com.example.pokestar.vaccineremind.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by PokeStar on 2018/9/2.
 * 保存用户登录状态
 */

public class Configure {
    public static String USERID = ""; // 本地保存
    //未登录设置为空字符串,登录即为当前id
    public static String BABYID = ""; // 本地保存

    public static String getBABYID(Context context) {
        SharedPreferences sp = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        BABYID = sp.getString("babyId", null);
        if(BABYID == null){
            return "";
        }else {
            return BABYID;
        }

    }

    public static void setBABYID(Context context,String BABYID) {
        Configure.BABYID = BABYID;
        SharedPreferences sp = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        sp.edit()
                .putString("babyId", BABYID)
                .apply();
    }

    public static String getUSERID(Context context) {

        SharedPreferences sp = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        USERID = sp.getString("username", null);
        if(USERID == null){
            return "";
        }else {
            return USERID;
        }

    }

    public static void setUSERID(Context context, String USERID) {
        Configure.USERID = USERID;

        SharedPreferences sp = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        sp.edit()
                .putString("username", USERID)
                .apply();
    }
}
