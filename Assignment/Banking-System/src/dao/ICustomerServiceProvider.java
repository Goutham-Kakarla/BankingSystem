package dao;

import java.util.*;
import entity.*;
public interface ICustomerServiceProvider {

    double getBalance(long account_id);

    double deposit(long account_id, double amount);

    double withdraw(long account_id, double amount);

    void transfer(long fromAccount_id, long toAccount_id, double amount);

    String getAccountDetails(long account_id);

	List<Transaction> getTransactions(long account_id, String startDate, String endDate);

}
