package com.example.guet.sharehotel.model.bean;

/**
 * Created by feng on 2017/8/2.
 * APP更新
 */

public class UpdateInfo {

    public int versionCode;   //版本号
    public String versionName;  //版本名称
    public String url;  //apk下载链接
    public String description;   //版本描述

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
