package com.peanut.pojo.vo;


import com.peanut.pojo.PrivateLetter;

import java.util.Date;

/**
 * 我的私信
 */
public class PrivateLetterEntity extends PrivateLetter{
    private String fromName;

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    @Override
    public String toString() {
        return super.toString() +
                "fromName='" + fromName + '\'' +
                '}';
    }
}
