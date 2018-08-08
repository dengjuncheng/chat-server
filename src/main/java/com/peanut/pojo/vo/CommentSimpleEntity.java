package com.peanut.pojo.vo;

import com.peanut.pojo.Comment;

import java.util.Date;

public class CommentSimpleEntity extends Comment{
    private String nickName;
    private String headPic;
    private CommentSimpleEntity replyComment;

    public String getNickName() {
        return nickName;
    }

    public CommentSimpleEntity getReplyComment() {
        return replyComment;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setReplyComment(CommentSimpleEntity replyComment) {
        this.replyComment = replyComment;
    }

    @Override
    public String toString() {
        return "CommentSimpleEntity{" +
                "nickName='" + nickName + '\'' +
                ", headPic='" + headPic + '\'' +
                ", replyComment=" + replyComment +
                "} " + super.toString();
    }
}
