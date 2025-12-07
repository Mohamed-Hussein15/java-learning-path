package Model;

import java.util.ArrayList;
import java.util.List;

public class EWalletSystem {

    private final String name = "eWalletSystem";

    List<Account> accounts = new ArrayList<Account>();

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    public String getName() {
        return name;
    }
}
