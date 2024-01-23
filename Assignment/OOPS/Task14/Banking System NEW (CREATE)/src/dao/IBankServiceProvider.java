package dao;

import entity.Account;
import entity.Customer;

import java.util.Map;

public interface IBankServiceProvider {

    void createAccount( long account_id,Customer customer, String account_type, double balance);

    Map<Long, Account> listAccounts();

    void calculateInterest();
}
