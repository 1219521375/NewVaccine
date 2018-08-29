package com.example.pokestar.vaccineremind.bean;

import java.util.Date;

/**
 * Created by PokeStar on 2018/8/22.
 */

public class Vaccine {
    String vacName;
    Date vacTime;
    Boolean isUsed;

    public String getVacName() {
        return vacName;
    }

    public void setVacName(String vacName) {
        this.vacName = vacName;
    }

    public Date getVacTime() {
        return vacTime;
    }

    public void setVacTime(Date vacTime) {
        this.vacTime = vacTime;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public Vaccine(String vacName, Date vacTime, Boolean isUsed) {
        this.vacName = vacName;
        this.vacTime = vacTime;
        this.isUsed = isUsed;
    }
}
