package com.mc.refillCard.common.Enum;

/**
 * @author
 */
public enum AlarmRecordStatusEnum {
    NO_DISPOSE(1,"未处理"),
    DISPOSE(2,"已处理");

    private Integer code;
    private String name;

    /**
     * 构造方法
     */
    AlarmRecordStatusEnum(Integer code, String name) {
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
        for (AlarmRecordStatusEnum typeEnum : AlarmRecordStatusEnum.values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum.getName();
            }
        }
        return null;
    }
}
