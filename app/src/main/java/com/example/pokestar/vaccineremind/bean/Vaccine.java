package com.example.pokestar.vaccineremind.bean;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by PokeStar on 2018/8/22.
 */

public class Vaccine extends BmobObject{
    String vacName;
    String vacTime;
    int useTimes;


    public String getVacName() {
        return vacName;
    }

    public void setVacName(String vacName) {
        this.vacName = vacName;
    }

    public String getVacTime() {
        return vacTime;
    }

    public void setVacTime(String vacTime) {
        this.vacTime = vacTime;
    }

    public int getUseTimes() {
        return useTimes;
    }

    public void setUseTimes(int useTimes) {
        this.useTimes = useTimes;
    }

}
