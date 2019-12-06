package com.sap.mim.DataBase;

import com.sap.mim.bean.Account;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 */
public class AccountManager {

    private static final Map<String, Account> accounts = new HashMap<>();

    static {
        Account account1 = new Account();
        account1.setId(0);
        account1.setAccount("administrator");
        account1.setPassword("Initial");
        account1.setUserName("administrator");
        List<Account> friendList1 = new LinkedList();
        account1.setFriendList(friendList1);
        accounts.put(account1.getAccount(), account1);

        Account account = new Account();
        List<Account> friendList = new LinkedList();
        account.setFriendList(friendList);
        account.setId(1);
        account.setAccount("tony");
        account.setPassword("Initial");
        account.setUserName("tony");
        accounts.put(account.getAccount(), account);
        friendList1.add(account);
        friendList.add(account1);
    }

    public static Account login(Account account) {
        if (account == null || account.getAccount() == null || account.getPassword() == null) {
            return null;
        }
        Account account1 = accounts.get(account.getAccount());
        if (account1.getPassword().equals(account.getPassword())) {
            account = account1;
        } else {
            account = null;
        }
        return account;
    }
}
