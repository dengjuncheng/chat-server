package com.peanut.pojo;

public class VoiceRequest {
    private String userId;
    private String friendId;

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

    public VoiceRequest rotate(){
        VoiceRequest v = new VoiceRequest();
        v.setFriendId(this.userId);
        v.setUserId(this.friendId);
        return v;
    }
}
