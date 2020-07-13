package com.sap.mim.bean;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class SearchFriendMessage extends MessageModel {

    private static final long serialVersionUID = 6242907966383961315L;

    private C2SMessageTypeEnum c2SMessageTypeEnum;
    private int             accountNo;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeInt(c2SMessageTypeEnum.getC2sMessageType());
        out.writeInt(accountNo);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        c2SMessageTypeEnum = C2SMessageTypeEnum.getC2SMessageTypeById(in.readInt());
        accountNo = in.readInt();
    }

    public SearchFriendMessage() {
    }

    public int getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(int accountNo) {
        this.accountNo = accountNo;
    }

    public C2SMessageTypeEnum getC2SMessageTypeEnum() {
        return c2SMessageTypeEnum;
    }

    public void setC2SMessageTypeEnum(C2SMessageTypeEnum c2SMessageTypeEnum) {
        this.c2SMessageTypeEnum = c2SMessageTypeEnum;
    }
}
