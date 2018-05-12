package com.example.guet.sharehotel.listener;

import com.example.guet.sharehotel.model.bean.Comment;

import java.util.List;

/**
 * @auth ${user}
 * time 2018/5/9 20:40
 */
public interface OnLoadCommentListener {

    void onSuccess(List<Comment> list);

    void onFailure(String msg);

}
