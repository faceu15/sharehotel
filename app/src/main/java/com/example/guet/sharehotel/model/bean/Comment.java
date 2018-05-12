package com.example.guet.sharehotel.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * @auth ${user}
 * time 2018/5/9 14:32
 */
public class Comment extends BmobObject {

    private Order order;
    private String content;
    private Double grade;
    private Hotel hotel;
    private User user;


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
