package dao;

import entity.*;

public interface IBankServiceProvider {

    Account createAccount(Customer customer, long accNo, String accType, double balance);

    Account[] listAccounts();

    void calculateInterest();
}
