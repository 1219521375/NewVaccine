package com.example.pokestar.vaccineremind.bean;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;

/**
 * Created by PokeStar on 2018/9/2.
 * 用户类
 */

public class User extends BmobUser{

    String babyId;

    public String getBabyId() {
        return babyId;
    }

    public void setBabyId(String babyId) {
        this.babyId = babyId;
    }
}
