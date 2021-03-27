package com.mc.refillCard.common.Enum;

/**
 * @author
 */
public enum TransactionStateEnum {
    AWAIT(1,"待推送"),
    SUCCEED(2,"推送成功"),
    FAIL(3,"3推送失败");

    private Integer code;
    private String name;

    /**
     * 构造方法
     */
    TransactionStateEnum(Integer code, String name) {
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
        for (TransactionStateEnum typeEnum : TransactionStateEnum.values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum.getName();
            }
        }
        return null;
    }
}
