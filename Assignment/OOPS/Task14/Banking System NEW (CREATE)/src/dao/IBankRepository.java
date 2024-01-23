package dao;

import entity.Account;
import entity.Customer;
import entity.Transaction;

import java.util.List;
public interface IBankRepository {
    void createAccount(long account_id,Customer customer,  String account_type, double balance);



    List<Account> listAccounts();

    void calculateInterest();

    float getBalance(long accountNumber);

    float deposit(long accountNumber, float amount);

    float withdraw(long accountNumber, float amount);

    void transfer(long fromAccountNumber, long toAccountNumber, float amount);

    String getAccountDetails(long accountNumber);

	List<Transaction> getTransactions(long accountNumber, String fromDate, String toDate);
}

