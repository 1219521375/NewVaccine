package com.example.pokestar.vaccineremind.bean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by PokeStar on 2018/9/3.
 *
 */

public class Baby extends BmobObject {

    String name;
    BmobDate birth;
    String sex;
    String userId;
    BmobFile image;
    String vacListId;
    List<Vaccine> mVaccineList = new ArrayList<Vaccine>();
    int year;
    int month;
    int day;
    String nextTime;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getVacListId() {
        return vacListId;
    }

    public void setVacListId(String vacListId) {
        this.vacListId = vacListId;
    }

    public List<Vaccine> getVaccineList() {
        return mVaccineList;
    }

    public void setVaccineList(List<Vaccine> vaccineList) {
        mVaccineList = vaccineList;
    }


    public BmobDate getBirth() {
        return birth;
    }

    public void setBirth(BmobDate birth) {
        this.birth = birth;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    //通过userId寻找baby
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

}
