package com.peanut.chat.pojo.vo;

public class RequestUserInfo extends FriendVo{
    private Byte isRead;
    private Byte complete;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getIsRead() {
        return isRead;
    }

    public void setIsRead(Byte isRead) {
        this.isRead = isRead;
    }

    public Byte getComplete() {
        return complete;
    }

    public void setComplete(Byte complete) {
        this.complete = complete;
    }
}
