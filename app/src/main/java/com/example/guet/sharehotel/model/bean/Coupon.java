package com.example.guet.sharehotel.model.bean;

/**
 * Created by Administrator on 2017/11/24 0024.
 * 优惠券
 */

public class Coupon {

    /**
     * 优惠券id
     */
    private String id;
    /**
     * 优惠券有效期
     */
    private String time_validate;
    /**
     * 优惠券状态，可用1，已过期0
     */
    private String state;
    /**
     * 优惠券金额
     */
    private String price;

    public Coupon(String id, String time_validate, String state, String price) {
        this.id = id;
        this.time_validate = time_validate;
        this.state = state;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime_validate() {
        return time_validate;
    }

    public void setTime_validate(String time_validate) {
        this.time_validate = time_validate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
