package com.example.guet.sharehotel.model.bean;

import cn.bmob.v3.BmobObject;

/**
 * @auth ${user}
 * time 2018/4/25 15:34
 */
public class User extends BmobObject{

    private String account;
    private String password;
    private String name;
    private String sexy;

    public User(){
        //this.setTableName("_User");
    }

    public String getAccount() {
        return account;
    }

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSexy() {
        return sexy;
    }

    public void setSexy(String sexy) {
        this.sexy = sexy;
    }
}
