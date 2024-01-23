package dao;

public interface ICustomerServiceProvider {

    double getAccountBalance(long accountNumber);

    double deposit(long accountNumber, double amount);

    double withdraw(long accountNumber, double amount);

    void transfer(long fromAccountNumber, long toAccountNumber, double amount);

    String getAccountDetails(long accountNumber);
}
