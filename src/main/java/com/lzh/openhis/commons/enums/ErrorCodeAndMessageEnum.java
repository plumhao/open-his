package com.lzh.openhis.commons.enums;

public enum ErrorCodeAndMessageEnum {

    //示例
    NO_LOGIN(1000, "未登录"),
    PHONE_ERROR(1001, "手机号码错误");

    private Integer code;

    private String message;

    ErrorCodeAndMessageEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
