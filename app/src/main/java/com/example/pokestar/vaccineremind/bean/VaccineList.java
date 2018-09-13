package com.example.pokestar.vaccineremind.bean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

/**
 * Created by PokeStar on 2018/9/7.
 * 历史已经打过的疫苗
 */

public class VaccineList extends BmobObject {

    List<Vaccine> mVaccineList = new ArrayList<Vaccine>();

    String babyId;

    public String getBabyId() {
        return babyId;
    }

    public void setBabyId(String babyId) {
        this.babyId = babyId;
    }

    public List<Vaccine> getVaccineList() {
        return mVaccineList;
    }

    public void setVaccineList(List<Vaccine> vaccineList) {
        mVaccineList = vaccineList;
    }
}
