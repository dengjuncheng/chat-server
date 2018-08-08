package com.peanut.pojo;

public class UserRel {
    private String stuId;
    private String otherId;
    private Integer isDelete;

    public UserRel(String stuId, String otherId, Integer isDelete) {
        this.stuId = stuId;
        this.otherId = otherId;
        this.isDelete = isDelete;
    }

    public UserRel() {
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getOtherId() {
        return otherId;
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public void rotate(){
        String temp = this.stuId;
        this.stuId = this.otherId;
        this.otherId = temp;
    }
}
