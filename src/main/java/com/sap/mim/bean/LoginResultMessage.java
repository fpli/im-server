package com.sap.mim.bean;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class LoginResultMessage extends MessageModel {

    private static final long serialVersionUID = 5876866548881894849L;

    private S2CMessageTypeEnum s2CMessageTypeEnum;

    private Integer         code;

    private Account         account;

    private String          message;

    public LoginResultMessage() {
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeInt(s2CMessageTypeEnum.getS2cMessageType());
        out.writeInt(code);
        out.writeObject(account);
        out.writeUTF(message);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        s2CMessageTypeEnum = S2CMessageTypeEnum.getS2CMessageTypeByType(in.readInt());
        code = in.readInt();
        account = (Account) in.readObject();
        message = in.readUTF();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public S2CMessageTypeEnum getS2CMessageTypeEnum() {
        return s2CMessageTypeEnum;
    }

    public void setS2CMessageTypeEnum(S2CMessageTypeEnum s2CMessageTypeEnum) {
        this.s2CMessageTypeEnum = s2CMessageTypeEnum;
    }
}
