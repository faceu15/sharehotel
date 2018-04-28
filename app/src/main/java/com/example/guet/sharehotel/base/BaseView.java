package com.example.guet.sharehotel.base;

/**
 * @auth ${user}
 * time 2018/4/25 8:43
 */
public interface BaseView {

    /**
     * @param msg 显示对话框
     */
    void showLoading(String msg);

    /**
     * 隐藏对话框
     */
    void hideLoading();

    /**
     * 显示错误信息
     */
    void showError(String msg);

}
