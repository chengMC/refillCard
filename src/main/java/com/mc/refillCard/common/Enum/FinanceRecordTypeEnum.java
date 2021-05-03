package com.mc.refillCard.common.Enum;


/**
 * 交易记录类型
 * @author
 */
public enum FinanceRecordTypeEnum {

    ADD(1,"加款"),
    SUBTRACT(2,"扣款");

    private Integer code;
    private String name;

    /**
     * 构造方法
     */
    FinanceRecordTypeEnum(Integer code, String name) {
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
        for (FinanceRecordTypeEnum typeEnum : FinanceRecordTypeEnum.values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum.getName();
            }
        }
        return null;
    }
}
