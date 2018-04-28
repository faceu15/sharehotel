package com.example.guet.sharehotel.bean;

import cn.bmob.v3.BmobObject;

/**
 * @auth ${user}
 * time 2018/4/25 15:34
 */
public class User extends BmobObject{

    private String account;
    private String password;

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
}
