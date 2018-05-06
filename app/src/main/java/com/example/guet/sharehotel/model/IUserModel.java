package com.example.guet.sharehotel.model;

import com.example.guet.sharehotel.listener.CallBack;

/**
 * @auth ${user}
 * time 2018/4/25 9:34
 */
public interface IUserModel {

    void login(String account,String password,CallBack callBack);

    void register(String account,String password,CallBack callBack);


}
