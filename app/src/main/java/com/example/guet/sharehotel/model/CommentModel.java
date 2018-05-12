package com.example.guet.sharehotel.model;

import android.util.Log;

import com.example.guet.sharehotel.listener.OnLoadCommentListener;
import com.example.guet.sharehotel.model.bean.Comment;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @auth ${user}
 * time 2018/5/9 20:38
 */
public class CommentModel implements ICommentModel {

    @Override
    public void loadComment(String hotelId, final OnLoadCommentListener onLoad) {
        BmobQuery<Comment> query = new BmobQuery<>("Comment");
        query.setLimit(30);
        query.addWhereEqualTo("hotel", hotelId);
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> list, BmobException e) {
                if (e == null) {
                    onLoad.onSuccess(list);
                } else {
                    Log.i("CommentActivity", "加载评论失败");
                    onLoad.onFailure("加载评论失败");
                }
            }
        });
    }
}
