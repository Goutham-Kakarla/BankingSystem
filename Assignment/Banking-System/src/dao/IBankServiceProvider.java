package dao;

import java.util.*;

import entity.*;

public interface IBankServiceProvider {

    void createAccount(Customer customer, long account_id, String account_type, double balance);

    Map<Long, Account> listAccounts();

}
