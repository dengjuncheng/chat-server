package com.peanut.chat.pojo.vo;

import com.peanut.pojo.User;

import java.util.Date;

public class UserVo {
    private String stuId;
    private String password;
    private String nickName;
    private String headPic;
    private String sex;
    private String declaration;
    private Date birthday;
    private String personalIntroduction;
    private Date lastestLogin;

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void coverUser(User user){
        stuId = user.getStuId();
        password = user.getPassword();
        nickName = user.getNickname();
        headPic = user.getHeadPic();
        sex = user.getSex();
        declaration = user.getDeclaration();
        birthday = user.getBirthday();
        personalIntroduction = user.getPersonalIntroduction();
        lastestLogin = user.getLastestLogin();
    }
}
