package com.example.guet.sharehotel.presenter;

import android.util.Log;

import com.example.guet.sharehotel.base.BasePresenter;
import com.example.guet.sharehotel.model.CallBack;
import com.example.guet.sharehotel.model.IUserModel;
import com.example.guet.sharehotel.model.UserModel;
import com.example.guet.sharehotel.view.ILoginView;

/**
 * @auth ${user}
 * time 2018/4/25 8:55
 */
public class LoginPresenter<T extends ILoginView> extends BasePresenter<ILoginView> implements ILoginPresenter{

    private ILoginView mILoginView;
    private IUserModel mUserModel=new UserModel();

    public LoginPresenter(T view){
        mILoginView=view;
    }

    @Override
    public void Login(String ac,String pw) {
        Log.i("LoginActivity","执行登录");
        mUserModel.login(ac,pw, new CallBack() {
            @Override
            public void onSuccess() {
                mILoginView.showResult("登录成功！");
            }

            @Override
            public void onFailure(String msg) {
                mILoginView.showError(msg);
            }
        });
    }
}
