package dao;

import entity.Transaction;

import java.util.List;
public interface ICustomerServiceProvider {

    // Implementation of interface methods
    double getBalance(long account_id);

    double getAmount(long accountNumber);

    double deposit(long account_id, double amount);

    double withdraw(long account_id, double amount);

    void transfer(long fromAccount_id, long toAccountNumber, double amount);

    String getAccountDetails(long account_id);

	List<Transaction> getTransactions(long account_id, String startDate, String endDate);

}
