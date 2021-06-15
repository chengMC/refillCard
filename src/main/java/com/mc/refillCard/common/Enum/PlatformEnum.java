package com.mc.refillCard.common.Enum;

/**
 *  供应商平台字典
 *
 * @author
 */
public enum PlatformEnum {
    FULU(1,"fulu"),
    SHUSHAN(2,"shushan"),
    JINGLAN(3,"jinglan"),
    MINIDIAN(4,"minidian"),
    XINGZOU(5,"xingzou");

    private Integer code;
    private String name;

    /**
     * 构造方法
     */
    PlatformEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(Integer code) {
        for (PlatformEnum typeEnum : PlatformEnum.values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum.getName();
            }
        }
        return null;
    }
}
