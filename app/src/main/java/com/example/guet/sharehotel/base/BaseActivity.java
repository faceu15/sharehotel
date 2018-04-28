package com.example.guet.sharehotel.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @auth ${user}
 * time 2018/4/24 10:15
 */
public abstract class BaseActivity<V,T extends BasePresenter<V>> extends AppCompatActivity{

    public T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter=createPresenter();//创建presenter
        mPresenter.attackView((V)this);
    }

    //子类实现创建presenter
    protected abstract T createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detackView();
    }
}
