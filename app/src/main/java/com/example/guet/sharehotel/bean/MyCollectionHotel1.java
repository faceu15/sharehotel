package com.example.guet.sharehotel.bean;

/**
 * Created by feng on 2016/7/25.
 */
public class MyCollectionHotel1 {

    /**
     * 收藏ID
     */
    private String id;
    /**
     * 被哪个用户收藏的ID
     */
    private String user_id;
    /**
     * 酒店图片资源id
     */
    private int hotelImageResourceId;
    /**
     * 酒店评分
     */
    private int score;
    /**
     * 酒店名称
     */
    private String name;
    /**
     * 酒店地址
     */
    private String address;
    /**
     * 酒店最低价
     */
    private int minValue;
    /**
     * 有多少条评论
     */
    private int comment_num;


    public MyCollectionHotel1(String id, String user_id, int hotelImageResourceId, int score, String name, String address, int minValue, int comment_num) {
        this.id = id;
        this.user_id = user_id;
        this.hotelImageResourceId = hotelImageResourceId;
        this.score = score;
        this.name = name;
        this.address = address;
        this.minValue = minValue;
        this.comment_num = comment_num;
    }

    public int getHotelImageResourceId() {
        return hotelImageResourceId;
    }

    public void setHotelImageResourceId(int hotelImageResourceId) {
        this.hotelImageResourceId = hotelImageResourceId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
