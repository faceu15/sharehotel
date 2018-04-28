package com.example.guet.sharehotel.model;

/**
 * @auth ${user}
 * time 2018/4/25 9:34
 */
public interface IUserModel {

    void login(String account,String password,CallBack callBack);

    void register(String account,String password,CallBack callBack);


}
