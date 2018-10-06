package com.example.pokestar.vaccineremind.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by PokeStar on 2018/10/5.
 */

public class VaccineNews extends BmobObject {

    String title;
    String Url;
    String ImageUrl;

    public VaccineNews() {
    }

    public VaccineNews(String title, String url, String imageUrl) {
        this.title = title;
        Url = url;
        ImageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
