package com.mc.refillCard.common.Enum;

/**
 * 运营商
 * @author
 */
public enum NSotrTypeEnum {
    YIDONG(1,"移动"),
    LIANTONG(2,"联通"),
    DIANXIN(3,"电信"),
    XUSHANG(4,"虚商");

    private Integer code;
    private String name;

    /**
     * 构造方法
     */
    NSotrTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(String code) {
        for (NSotrTypeEnum typeEnum : NSotrTypeEnum.values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum.getName();
            }
        }
        return null;
    }
}
