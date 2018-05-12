package com.example.guet.sharehotel.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.adapter.CommonAdapter;
import com.example.guet.sharehotel.adapter.ViewHolder;
import com.example.guet.sharehotel.base.BaseActivity;
import com.example.guet.sharehotel.model.bean.Comment;
import com.example.guet.sharehotel.presenter.CommentPresenter;
import com.example.guet.sharehotel.view.ICommentView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentActivity extends BaseActivity<ICommentView, CommentPresenter<ICommentView>> implements ICommentView {


    @BindView(R.id.toolbar_comment)
    Toolbar mCommentToolbar;
    @BindView(R.id.lv_comment)
    ListView mCommentListView;

    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);


        mCommentToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        showLoading("加载中...");
        mPresenter.fetchComment(getIntent().getExtras().getString("HotelId"));


    }

    @Override
    protected CommentPresenter<ICommentView> createPresenter() {
        return new CommentPresenter<>((ICommentView) this);
    }


    @Override
    public void showResult(List<Comment> list) {
        mCommentListView.setAdapter(new CommonAdapter<Comment>(this, list, R.layout.comment_listview_item) {
            @Override
            public void convert(ViewHolder viewHolder, Comment item) {
                viewHolder.setImageResource(R.id.iv_comment_user_icon, R.mipmap.login_head);
                viewHolder.setText(R.id.tv_comment_username, item.getUser().getAccount());
                viewHolder.setText(R.id.tv_comment_date, item.getCreatedAt());
                viewHolder.setText(R.id.tv_commet_content, item.getContent());
            }
        });
        hideLoading();
    }

    @Override
    public void showLoading(String msg) {
        mProgressDialog = new ProgressDialog(CommentActivity.this, 1);//后面的参数是风格，1比较好看
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        mProgressDialog.hide();
    }

    @Override
    public void showError(String msg) {
        hideLoading();
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgressDialog.dismiss();
    }

}
