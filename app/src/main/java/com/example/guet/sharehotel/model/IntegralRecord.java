package com.example.guet.sharehotel.model;

/**
 * Created by Administrator on 2017/11/25 0025.
 * 积分记录
 */

public class IntegralRecord {

    private String id;

    /**
     * 房子图片
     */
    private String home_image_url;
    /**
     * 房子地址
     */
    private String home_address;
    /**
     * 房子名称
     */
    private String home_name;
    /**
     * 积分消费或者获取日期
     */
    private String time;
    /**
     * 获取或者消费的积分数额
     */
    private String integral;
    /**
     * 1表示获得积分，0表示消费积分
     */
    private String state;

    public IntegralRecord(String id, String home_image_url, String home_address, String home_name, String time, String integral, String state) {
        this.id = id;
        this.home_image_url = home_image_url;
        this.home_address = home_address;
        this.home_name = home_name;
        this.time = time;
        this.integral = integral;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHome_image_url() {
        return home_image_url;
    }

    public void setHome_image_url(String home_image_url) {
        this.home_image_url = home_image_url;
    }

    public String getHome_address() {
        return home_address;
    }

    public void setHome_address(String home_address) {
        this.home_address = home_address;
    }

    public String getHome_name() {
        return home_name;
    }

    public void setHome_name(String home_name) {
        this.home_name = home_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
