package com.example.guet.sharehotel.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * @auth ${user}
 * time 2018/5/24 15:32
 */
public class HotelPhoto extends BmobObject {
    private String url;
    private Hotel hotel;

    public HotelPhoto() {
    }

    public HotelPhoto(String url, Hotel hotel) {
        this.url = url;
        this.hotel = hotel;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
