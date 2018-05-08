package com.example.guet.sharehotel.model;

import android.util.Log;

import com.example.guet.sharehotel.application.MyApplication;
import com.example.guet.sharehotel.bean.User;
import com.example.guet.sharehotel.listener.CallBack;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @auth ${user}
 * time 2018/4/25 9:33
 */
public class UserModel implements IUserModel {


    private String account;
    private String password;

    /**
     * @param ac 用户名
     * @param pw 密码
     * @param callBack 结果回调
     */
    @Override
    public void login(String ac,String pw, final CallBack callBack) {
        account=ac;
        password=pw;
        //获取密码进行比对
        BmobQuery<User> query = new BmobQuery<>();
        //查询Bmob中username字段叫account的数据
        query.addWhereEqualTo("account", account);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(1);
        //执行查询方法
        query.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if (e == null) {
                    if (list.size() == 0) { //没有创建账号
                        Log.i("LoginActivity", "账号不存在");
                        callBack.onFailure("账号不存在！");
                    } else {
                        if (list.get(0).getAccount().equals(account)) {
                            Log.i("LoginActivity", "账号" + account);
                            if (list.get(0).getPassword().equals(password)) {
                                Log.i("LoginActivity", "密码正确");
                                MyApplication.getInstance().setUser(list.get(0));
                                callBack.onSuccess();
                            } else {
                                Log.i("LoginActivity", "密码错误");
                                callBack.onFailure("密码错误！");
                            }
                        }

                    }
                } else {
                    Log.i("LoginActivity", e.toString());
                }
            }
        });

    }

    @Override
    public void register(String account, String password, CallBack callBack) {

    }
}
