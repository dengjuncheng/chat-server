package com.peanut.chat.pojo.vo;

import com.peanut.pojo.User;

import java.util.Date;

public class FriendVo {
    private String stuId;
    private String nickName;
    private String headPic;
    private String sex;
    private String declaration;
    private Date birthday;
    private String personalIntroduction;
    private Date lastestLogin;
    private Boolean isOnline;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPersonalIntroduction() {
        return personalIntroduction;
    }

    public void setPersonalIntroduction(String personalIntroduction) {
        this.personalIntroduction = personalIntroduction;
    }

    public Date getLastestLogin() {
        return lastestLogin;
    }

    public void setLastestLogin(Date lastestLogin) {
        this.lastestLogin = lastestLogin;
    }

    public Boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Boolean online) {
        isOnline = online;
    }

    public void cover(User u) {
        if(u == null){
            return;
        }
        this.birthday = u.getBirthday();
        this.headPic = u.getHeadPic();
        this.nickName = u.getNickname();
        this.stuId = u.getStuId();
        this.sex = u.getSex();
        this.declaration = u.getDeclaration();
        this.personalIntroduction = u.getPersonalIntroduction();
        this.lastestLogin = u.getLastestLogin();
    }
}
