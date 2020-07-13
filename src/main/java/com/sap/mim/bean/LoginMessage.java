package com.sap.mim.bean;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class LoginMessage extends MessageModel {

    private static final long serialVersionUID = 3178351358356989351L;

    private C2SMessageTypeEnum c2SMessageTypeEnum;

    private Account         account;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeInt(c2SMessageTypeEnum.getC2sMessageType());
        out.writeObject(account);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        c2SMessageTypeEnum = C2SMessageTypeEnum.getC2SMessageTypeById(in.readInt());
        account = (Account) in.readObject();
    }

    public LoginMessage() {
    }

    public C2SMessageTypeEnum getC2SMessageTypeEnum() {
        return c2SMessageTypeEnum;
    }

    public void setC2SMessageTypeEnum(C2SMessageTypeEnum c2SMessageTypeEnum) {
        this.c2SMessageTypeEnum = c2SMessageTypeEnum;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
