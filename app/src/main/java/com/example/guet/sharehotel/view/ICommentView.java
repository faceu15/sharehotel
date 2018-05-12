package com.example.guet.sharehotel.view;

import com.example.guet.sharehotel.base.BaseView;
import com.example.guet.sharehotel.model.bean.Comment;

import java.util.List;

/**
 * @auth ${user}
 * time 2018/5/9 20:26
 */
public interface ICommentView extends BaseView {

    void showResult(List<Comment> list);

}
