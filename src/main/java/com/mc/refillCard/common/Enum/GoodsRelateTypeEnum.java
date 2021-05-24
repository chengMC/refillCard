package com.mc.refillCard.common.Enum;

/**
 * @author
 */
public enum GoodsRelateTypeEnum {
    QB(1,"QB"),
    MOMO(2,"陌陌"),
    TANTAN(3,"探探"),
    E_CARD(4,"京东E卡"),
    DNF(5,"DNF"),
    LOL(6,"LOL"),
    MINI_QB(7,"小额QB"),
    TAQU(8,"他趣");

    private Integer code;
    private String name;

    /**
     * 构造方法
     */
    GoodsRelateTypeEnum(Integer code, String name) {
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
        for (GoodsRelateTypeEnum typeEnum : GoodsRelateTypeEnum.values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum.getName();
            }
        }
        return null;
    }
}
