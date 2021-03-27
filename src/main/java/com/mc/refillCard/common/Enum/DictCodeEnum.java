package com.mc.refillCard.common.Enum;

/**
 *  数据字典code
 *
 * @author
 */
public enum DictCodeEnum {
    BAIDUAK(1,"baiduak"),
    SUPPLEMENT_MODE(3,"supplementMode");

    private Integer code;
    private String name;

    /**
     * 构造方法
     */
    DictCodeEnum(Integer code, String name) {
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
        for (DictCodeEnum typeEnum : DictCodeEnum.values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum.getName();
            }
        }
        return null;
    }
}
