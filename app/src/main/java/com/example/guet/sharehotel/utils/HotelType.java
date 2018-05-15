package com.example.guet.sharehotel.utils;

/**
 * @auth ${user}
 * time 2018/5/14 10:57
 */
public class HotelType {

    public static Integer type(String s) {

        if (s.equals("酒店/旅馆")) {
            return 1;
        } else {
            return 2;
        }
    }

}
