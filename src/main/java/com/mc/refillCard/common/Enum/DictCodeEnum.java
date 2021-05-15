package com.mc.refillCard.common.Enum;

/**
 *  数据字典code
 *
 * @author
 */
public enum DictCodeEnum {
    BAIDUAK(1,"baiduak"),
    AREA_IP(2,"areaIp"),
    RANKING(3,"ranking"),
    PATTERN(4,"pattern"),
    PLATFORM(5,"platform"),
    NATIONWIDE(6,"nationwide"),
    REGION(7,"region"),
    DNF(8,"dnf"),
    LOL(9,"LOL");

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
