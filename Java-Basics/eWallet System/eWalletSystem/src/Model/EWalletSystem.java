package model;

import java.util.*;

public class EWalletSystem {

    private final String name = "eWalletSystem";

    List<Account> accounts = new ArrayList<Account>();
    private Map<String, List<History>> histories = new HashMap<>();


    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    public String getName() {
        return name;
    }

    public Map<String, List<History>> getHistories() {
        return histories;
    }
}
