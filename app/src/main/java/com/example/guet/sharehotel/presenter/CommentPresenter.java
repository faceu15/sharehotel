package com.example.guet.sharehotel.presenter;

import com.example.guet.sharehotel.base.BasePresenter;
import com.example.guet.sharehotel.listener.OnLoadCommentListener;
import com.example.guet.sharehotel.model.CommentModel;
import com.example.guet.sharehotel.model.bean.Comment;
import com.example.guet.sharehotel.view.ICommentView;

import java.util.List;

/**
 * @auth ${user}
 * time 2018/5/9 20:29
 */
public class CommentPresenter<T extends ICommentView> extends BasePresenter<ICommentView> implements ICommentPresenter {

    private ICommentView mICommentView;
    private CommentModel mCommentModel;

    public CommentPresenter(T view) {
        mICommentView = view;
        mCommentModel = new CommentModel();
    }

    @Override
    public void fetchComment(String hotelId) {
        mCommentModel.loadComment(hotelId, new OnLoadCommentListener() {
            @Override
            public void onSuccess(List<Comment> list) {
                mICommentView.showResult(list);
            }

            @Override
            public void onFailure(String msg) {
                mICommentView.showError(msg);
            }
        });

    }
}
