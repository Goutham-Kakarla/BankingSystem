package entity;

public class Account {
    private static long lastAccNo=161;
    private long account_id;
    public Account(long account_id){
        this.account_id= account_id;
    }
    private String account_type;
    private double balance;
    private Customer customer_id;

    // D
    public Account() {
        this.account_id = generateAccount_id();
    }

    // Overloaded constructor
    public Account(String account_type, double balance, Customer customer_id) {
        this.account_id = lastAccNo;
        this.account_type = account_type;
        this.balance = balance;
        this.customer_id = customer_id;
    }

    public Account(long account_id, String account_type, double balance, Customer customer_id) {
        this.account_id = account_id;
        this.account_type = account_type;
        this.balance = balance;
        this.customer_id = customer_id;
    }

    // Getter methods
    public long getAccount_id() {
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
    // Setter methods
    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }
    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void setCustomer(Customer customer) {
        this.customer_id = customer;
    }
    public static long getLastAccNo() {
        return lastAccNo;
    }

    public static void setLastAccNo(long lastAccNo) {
        Account.lastAccNo = lastAccNo;
    }

    public Customer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Customer customer_id) {
        this.customer_id = customer_id;
    }

    // Method to generate a unique account number
    public static int generateAccount_id() {
        return (int) ++lastAccNo;
    }//

    // Method to print account information
    public void printAccountInfo() {
        System.out.println("Account Number: " + account_id);
        System.out.println("Account Type: " + account_type);
        System.out.println("Account Balance: $" + balance);
        System.out.println("Customer Information:");
        customer_id.printInfo();
    }

    public void withdraw(double amount) {
        // TODO Auto-generated method stub

    }
}