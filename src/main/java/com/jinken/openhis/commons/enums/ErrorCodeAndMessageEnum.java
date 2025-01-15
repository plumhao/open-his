package com.jinken.openhis.commons.enums;

public enum ErrorCodeAndMessageEnum {

    //示例
    NO_LOGIN_ERROR(401,"未登录"),
    PHONE_PATTERN_ERROR(1001,"手机号码错误"),
    ROLE_HAS_USER_ERROR(1002,"角色还关联有用户，不能删除"),
    MENU_HAS_ROLE_ERROR(1003,"角色还关联有用户，不能删除"),
    MENU_HAS_CHILD_ERROR(1004,"此菜单还有子菜单，不能删除"),




    ;

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
