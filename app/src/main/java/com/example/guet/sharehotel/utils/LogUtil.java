package com.example.guet.sharehotel.utils;

import android.util.Log;

/**
 * Created by feng on 2016/8/8.
 * 用来控制Log的输出，当app上线时，由于众多的类文件中，都可能使用了Log，那么在这里只需要把LEVEL变量设置为6即可，这样就不会输出Log信息了。
 */
public class LogUtil {

    public static final int VERBOSE = 1;

    public static final int DEBUG = 2;

    public static final int INFO = 3;

    public static final int WARN = 4;

    public static final int ERROR = 5;

    public static final int NOTHING = 6;

    public static final int LEVEL = ERROR;        //当前Log输出等级


    public static void v(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            Log.e(tag, msg);
        }
    }


    public static void d(String tag, String msg) {
        if (LEVEL <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LEVEL <= INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LEVEL <= WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LEVEL <= ERROR) {
            Log.e(tag, msg);
        }
    }


}
