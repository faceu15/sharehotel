package com.example.guet.sharehotel.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guet.sharehotel.R;
import com.example.guet.sharehotel.model.bean.Comment;
import com.example.guet.sharehotel.model.bean.Hotel;
import com.example.guet.sharehotel.model.bean.Order;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class PublishCommentActivity extends AppCompatActivity {

    private static final String TAG = "PublishComment";
    private Order mOrder;
    private HashMap<String, Integer> commentHasMap = new HashMap<>();
    //返回
    @BindView(R.id.ll_publish_comment_back)
    LinearLayout mLinearLayout;
    //评论内容
    @BindView(R.id.et_comment)
    EditText comment_content;
    //环境评分
    @BindView(R.id.environment_comment_ll)
    LinearLayout mEnviromentLL;
    //房子评分
    @BindView(R.id.hotel_condition_comment_ll)
    LinearLayout mHotelConditionLL;
    //服务态度评分
    @BindView(R.id.service_comment_ll)
    LinearLayout mServiceLL;
    //提交按钮

    @BindView(R.id.tv_publish_comment)
    TextView mPublishTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_publish_comment);
        ButterKnife.bind(this);
        mOrder = (Order) getIntent().getExtras().getBundle("OrderBundle").getSerializable("Order");
    }

    @OnClick({R.id.tv_publish_comment, R.id.ll_publish_comment_back})
    public void click(View view) {
        if (view.getTag() != null) {
            String tag = (String) view.getTag();
            int index = Integer.parseInt(tag);
            LinearLayout layout = (LinearLayout) view.getParent();
            switch (layout.getId()) {
                case R.id.environment_comment_ll:
                    for (int i = 0; i < mEnviromentLL.getChildCount(); i++) {
                        ImageView imageView = (ImageView) mEnviromentLL.getChildAt(i);
                        if (i < index) {
                            commentHasMap.put("environment", index);
                            imageView.setImageResource(R.mipmap.comment_like);
                        } else {
                            imageView.setImageResource(R.mipmap.comment_no_like);
                        }
                    }
                    break;
                case R.id.hotel_condition_comment_ll:
                    for (int i = 0; i < mHotelConditionLL.getChildCount(); i++) {
                        ImageView imageView = (ImageView) mHotelConditionLL.getChildAt(i);
                        if (i < index) {
                            commentHasMap.put("condition", index);
                            imageView.setImageResource(R.mipmap.comment_like);
                        } else {
                            imageView.setImageResource(R.mipmap.comment_no_like);
                        }
                    }
                    break;
                case R.id.service_comment_ll:
                    for (int i = 0; i < mServiceLL.getChildCount(); i++) {
                        ImageView imageView = (ImageView) mServiceLL.getChildAt(i);
                        if (i < index) {
                            commentHasMap.put("service", index);
                            imageView.setImageResource(R.mipmap.comment_like);
                        } else {
                            imageView.setImageResource(R.mipmap.comment_no_like);
                        }
                    }

                    break;
                default:
                    break;
            }

        }
        switch (view.getId()) {
            case R.id.tv_publish_comment:
                String commentContent = comment_content.getText().toString().trim();
                if (commentContent.equals("")) {
                    Toast.makeText(this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (commentHasMap.get("condition") == null || commentHasMap.get("service") == null || commentHasMap.get("environment") == null) {
                        Toast.makeText(this, "请打评分", Toast.LENGTH_SHORT).show();
                    } else {
                        publishCommentAction(this, commentContent);
                    }
                }
                break;
            case R.id.ll_publish_comment_back:
                Intent intent = new Intent();
                intent.putExtra("Comment", "N");
                setResult(1, intent);
                finish();
                break;
            default:
                break;
        }


    }

    private void publishCommentAction(final Context context, String commentContent) {
        Comment comment = new Comment();
        comment.setOrder(mOrder);
        comment.setHotel(mOrder.getHotel());
        comment.setUser(mOrder.getUser());
        comment.setContent(commentContent);
        final Double grade = (commentHasMap.get("environment") + commentHasMap.get("condition") + commentHasMap.get("service")) / 3.0;
        comment.setGrade(grade);
        comment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    mOrder.setState(5);
                    mOrder.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Log.i(TAG, "评论成功");
                                Toast.makeText(context, "评论成功", Toast.LENGTH_SHORT).show();
                                Hotel hotel = new Hotel();
                                hotel.setObjectId(mOrder.getHotel().getObjectId());
                                hotel.increment("comment");
                                hotel.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null) {
                                            Intent intent = new Intent();
                                            intent.putExtra("Comment", "Y");
                                            setResult(1, intent);
                                            finish();
                                        } else {
                                            Log.i(TAG, "评论增加失败" + e.toString());
                                        }
                                    }
                                });

                            } else {
                                Log.i(TAG, "发表评论失败" + e.toString());
                            }
                        }
                    });

                } else {
                    Log.i(TAG, "发表评论失败" + e.toString());
                }
            }
        });


    }
}
