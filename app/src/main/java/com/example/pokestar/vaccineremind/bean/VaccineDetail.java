package com.example.pokestar.vaccineremind.bean;

import java.io.StringReader;
import java.lang.ref.SoftReference;

import cn.bmob.v3.BmobObject;

public class VaccineDetail extends BmobObject {

    String Name;
    String Notice;
    String Reaction;

    public VaccineDetail(String name, String notice, String reaction) {
        Name = name;
        Notice = notice;
        Reaction = reaction;
    }

    public VaccineDetail() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNotice() {
        return Notice;
    }

    public void setNotice(String notice) {
        Notice = notice;
    }

    public String getReaction() {
        return Reaction;
    }

    public void setReaction(String reaction) {
        Reaction = reaction;
    }
}
