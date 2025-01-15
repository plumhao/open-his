package com.jinken.openhis.commons.enums;

/**
 * 操作类型枚举
 */
public enum BusinessTypeEnum {
    OTHER,

    INSERT,

    UPDATE,

    DELETE;


    public static void main(String[] args) {
        System.out.println(OTHER.ordinal());
        System.out.println(INSERT.ordinal());
        System.out.println(UPDATE.ordinal());
        System.out.println(DELETE.ordinal());


    }


}
