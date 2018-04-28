package com.example.guet.sharehotel.view;

import com.example.guet.sharehotel.base.BaseView;

/**
 * @auth ${user}
 * time 2018/4/25 8:47
 */
public interface ILoginView extends BaseView {

    /**
     * @return从UI获取输入的用户名
     */
    String getAccount();

    /**
     * @return 获取用户密码
     */
    String getPassWord();

    /**
     * @param result 显示结果
     */
    void showResult(String result);

}
