package com.sap.mim.bean;

/**
 * 描述:S2C（消息推送，c2c, g2c, ack）
 */
public enum S2CMessageTypeEnum {

    S_2_C_LOGIN_RESULT(1, "S_2_C_LOGIN_RESULT"),
    S_2_C_SEARCH_FRIEND(2, "S_2_C_SEARCH_FRIEND")
    ;
    private Integer s2cMessageType;

    private String  description;

    S2CMessageTypeEnum(Integer s2cMessageType, String description) {
        this.s2cMessageType = s2cMessageType;
        this.description = description;
    }

    public static S2CMessageTypeEnum getS2CMessageTypeByType(Integer s2cMessageType){
        for (S2CMessageTypeEnum s2CMessageTypeEnum : values()){
            if (s2CMessageTypeEnum.s2cMessageType.equals(s2cMessageType)){
                return s2CMessageTypeEnum;
            }
        }
        return null;
    }

    public Integer getS2cMessageType() {
        return s2cMessageType;
    }

    public void setS2cMessageType(Integer s2cMessageType) {
        this.s2cMessageType = s2cMessageType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
