package com.example.guet.sharehotel.model;

import com.example.guet.sharehotel.listener.OnLoadCommentListener;

/**
 * @auth ${user}
 * time 2018/5/9 20:38
 */
public interface ICommentModel {

    void loadComment(String hotelId, OnLoadCommentListener onLoad);

}
