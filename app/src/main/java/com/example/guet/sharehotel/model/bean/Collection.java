package com.example.guet.sharehotel.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * @auth ${user}
 * time 2018/5/25 10:24
 */
public class Collection extends BmobObject {
    private User user;
    private Hotel hotel;

    public Collection() {
    }

    public Collection(User user, Hotel hotel) {
        this.user = user;
        this.hotel = hotel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
