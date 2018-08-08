package com.peanut.pojo.vo;

import com.peanut.pojo.Post;

public class PostSimpleEntity extends Post{
    private String userName;
    private String picUrl;
    private String shortDesc;

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    @Override
    public String toString() {
        return "PostSimpleEntity{" +
                "userName='" + userName + '\'' +
                ", picUrl='" + picUrl + '\'' +
                '}'+super.toString();
    }
}
