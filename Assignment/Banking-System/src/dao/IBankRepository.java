package dao;

import java.util.List;
import entity.*;
public interface IBankRepository {
    void createAccount(Customer customer, long account_id, String account_type, float balance);

    List<Account> listAccounts();

    float getBalance(long account_id);

    void deposit(long account_id, float amount);

    void withdraw(long account_id, float amount);

	List<Transaction> getTransactions(long account_id, String fromDate, String toDate);
}

