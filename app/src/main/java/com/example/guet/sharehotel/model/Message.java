package com.example.guet.sharehotel.model;

/**
 * Created by feng on 2017/11/13.
 * 消息
 */

public class Message {

    /**
     * 消息记录id
     */
    private String id;
    /**
     * 消息icon
     */
    private String image_url;
    /**
     * 消息主题
     */
    private String title;
    /**
     * 消息简短介绍
     */
    private String easy_content;
    /**
     * 消息详细内容
     */
    private String detail;
    /**
     * 消息时间
     */
    private String time;

    public Message(String id, String image_url, String title, String easy_content, String detail, String time) {
        this.id = id;
        this.image_url = image_url;
        this.title = title;
        this.easy_content = easy_content;
        this.detail = detail;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEasy_content() {
        return easy_content;
    }

    public void setEasy_content(String easy_content) {
        this.easy_content = easy_content;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
