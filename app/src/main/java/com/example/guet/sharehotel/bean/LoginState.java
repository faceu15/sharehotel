package com.example.guet.sharehotel.bean;

/**
 * @auth ${user}
 * time 2018/4/27 15:04
 */
public class LoginState {

    public static Boolean isLogin;
    //保存登录账号
    public static String account;

    private static User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
