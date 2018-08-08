package com.peanut.chat.pojo.vo;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties
public class MsgVo {
    private String userId;
    private String friendId;
    private String fileName;
    private String uuid;
    private String msg;
    private Boolean direction = false;
    private Integer msgType;
    private Integer sendTime;

    public MsgVo() {
    }

    public MsgVo(String userId, String friendId, String fileName, String uuid, String msg, Boolean direction, Integer msgType, Integer sendTime) {
        this.userId = userId;
        this.friendId = friendId;
        this.fileName = fileName;
        this.uuid = uuid;
        this.msg = msg;
        this.direction = direction;
        this.msgType = msgType;
        this.sendTime = sendTime;
    }

    public Integer getSendTime() {
        return sendTime;
    }

    public void setSendTime(Integer sendTime) {
        this.sendTime = sendTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getDirection() {
        return direction;
    }

    public void setDirection(Boolean direction) {
        this.direction = direction;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    @Override
    public String toString() {
        return "MsgVo{" +
                "userId='" + userId + '\'' +
                ", friendId='" + friendId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", uuid='" + uuid + '\'' +
                ", msg='" + msg + '\'' +
                ", direction=" + direction +
                ", msgType=" + msgType +
                '}';
    }

    public MsgVo rotate(){
        MsgVo m = new MsgVo();
        m.setUserId(this.friendId);
        m.setFriendId(this.userId);
        m.setFileName(this.fileName);
        m.setMsgType(this.msgType);
        m.setUuid(this.uuid);
        m.setMsg(this.msg);
        m.setDirection(!this.direction);
        return m;
    }
}
