package com.sap.mim.bean;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

public class SearchFriendResultMessage extends MessageModel {

    private static final long serialVersionUID = -4838287755627485505L;

    private S2CMessageTypeEnum s2CMessageTypeEnum;
    private List<Account>   accounts;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
        out.writeInt(s2CMessageTypeEnum.getS2cMessageType());
        out.writeObject(accounts);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
        s2CMessageTypeEnum = S2CMessageTypeEnum.getS2CMessageTypeByType(in.readInt());
        accounts = (List<Account>)in.readObject();
    }

    public SearchFriendResultMessage() {
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public S2CMessageTypeEnum getS2CMessageTypeEnum() {
        return s2CMessageTypeEnum;
    }

    public void setS2CMessageTypeEnum(S2CMessageTypeEnum s2CMessageTypeEnum) {
        this.s2CMessageTypeEnum = s2CMessageTypeEnum;
    }
}
