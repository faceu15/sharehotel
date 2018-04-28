package com.example.guet.sharehotel.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by feng on 2016/7/24.
 * 用于存储用户的账户，密码等信息。
 */
public class AccountSQLiteOpenHelper extends SQLiteOpenHelper {

    Context myContext;       //   可能用于Toast的信息提示输出!!!

    /**创建账户表，在本地数据库
     * account_id       ID(自动递增)
     * account         用户手机号码即账号
     * password        用户登录密码
     * is_last_login   标记是否是最后一次登录这个软件的账户，用于自动登录 默认值0
     * */
    public static final String CREATE_ACCOUNT = "create table Account ("
            + "account_id integer primary key autoincrement , "
            + "account varchar(11) not null , "
            + "password varchar(17) not null ,"
            + "is_last_login tinyint(1) default 0 ) ";

    public AccountSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_ACCOUNT);      //创建账户表
        //Toast.makeText(myContext, "成功创建音乐数据库", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {  //数据库更新时候会调用!!!

        switch(oldVersion)
        {
            case 1:
                db.execSQL("alter table Account add column lyric text");       //升级数据库，添加一个字段
            case 2:
        }
    }

}