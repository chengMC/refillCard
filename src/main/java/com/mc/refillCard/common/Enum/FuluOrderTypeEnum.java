package com.mc.refillCard.common.Enum;

/**
 * @author
 */
public enum FuluOrderTypeEnum {
    SUCCESS("success","成功"),
    PROCESSING("processing","处理中"),
    FAILED("failed","失败"),
    UNTREATED("untreated","未处理");

    private String code;
    private String name;

    /**
     * 构造方法
     */
    FuluOrderTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getNameByCode(String code) {
        for (FuluOrderTypeEnum typeEnum : FuluOrderTypeEnum.values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum.getName();
            }
        }
        return null;
    }
}
