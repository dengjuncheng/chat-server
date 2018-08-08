package com.peanut.pojo.vo;

public class CheckResult {
    private Integer code;
    private String reason;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public CheckResult(Integer code, String reason) {
        this.code = code;
        this.reason = reason;
    }
}
