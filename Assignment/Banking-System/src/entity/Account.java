package entity;

public class Account {
    private long account_id;
    private String account_type;
    private double balance;
    private Customer customer_id;

    public Account(String account_type, double balance, Customer customer_id) {
        this.account_type = account_type;
        this.balance = balance;
        this.customer_id = customer_id;
    }

    public  long getAccount_id() {
        return account_id;
    }

    public String getAccount_type() {
        return account_type;
    }

    public double getBalance() {
        return balance;
    }

    public Customer getCustomer() {
        return customer_id;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public void printAccountInfo() {
        System.out.println("Account Number: " + account_id);
        System.out.println("Account Type: " + account_type);
        System.out.println("Account Balance: $" + balance);
        System.out.println("Customer Information:" + getCustomer());
        customer_id.printInfo();
    }

	public static long generateAccount_id() {
		// TODO Auto-generated method stub
		return 0;
	}
}
