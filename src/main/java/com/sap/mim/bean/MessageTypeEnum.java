package com.sap.mim.bean;

public enum MessageTypeEnum {

    C2S(1,"C2S"),
    S2C(2,"S2C"),
    ACK(3,"ACK");

    private Integer type;

    private String  description;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    MessageTypeEnum(Integer type, String description){
        this.type        = type;
        this.description = description;
    }

    public static MessageTypeEnum getMessageTypeById(Integer type){
        for (MessageTypeEnum messageType : values()){
            if (messageType.type.equals(type)){
                return  messageType;
            }
        }
        return null;
    }
}
