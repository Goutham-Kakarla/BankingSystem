abstract class BankAccount {
    // Attributes
    private String accountNumber;
    private String customerName;
    private double balance;

    // Constructors
    public BankAccount() {
        // Default constructor
    }

    public BankAccount(String accountNumber, String customerName, double balance) {
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.balance = balance;
    }

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    // Abstract Methods
    public abstract void deposit(double amount);

    public abstract void withdraw(double amount);

    public abstract void calculateInterest();
}

class SavingsAccount extends BankAccount {
    // Additional attribute
    private double interestRate;

    // Constructor
    public SavingsAccount(String accountNumber, String customerName, double balance, double interestRate) {
        super(accountNumber, customerName, balance);
        this.interestRate = interestRate;
    }

    // Override abstract method
    @Override
    public void deposit(double amount) {
        setBalance(getBalance() + amount);
        System.out.println("Deposit successful. New balance: " + getBalance());
    }

    // Override abstract method
    @Override
    public void withdraw(double amount) {
        double newBalance = getBalance() - amount;
        if (newBalance >= 0) {
            setBalance(newBalance);
            System.out.println("Withdrawal successful. New balance: " + getBalance());
        } else {
            System.out.println("Insufficient funds for withdrawal.");
        }
    }

    // Override abstract method
    @Override
    public void calculateInterest() {
        double interest = getBalance() * interestRate / 100;
        setBalance(getBalance() + interest);
        System.out.println("Interest calculated. New balance: " + getBalance());
    }

    // Additional methods specific to SavingsAccount can be added here
}

class CurrentAccount extends BankAccount {
    // Constant for overdraft limit
    private static final double OVERDRAFT_LIMIT = -1000;

    // Constructor
    public CurrentAccount(String accountNumber, String customerName, double balance) {
        super(accountNumber, customerName, balance);
    }

    // Override abstract method
    @Override
    public void deposit(double amount) {
        setBalance(getBalance() + amount);
        System.out.println("Deposit successful. New balance: " + getBalance());
    }

    // Override abstract method
    @Override
    public void withdraw(double amount) {
        double newBalance = getBalance() - amount;
        if (newBalance >= OVERDRAFT_LIMIT) {
            setBalance(newBalance);
            System.out.println("Withdrawal successful. New balance: " + getBalance());
        } else {
            System.out.println("Insufficient funds for withdrawal.");
        }
    }

    // Override abstract method
    @Override
    public void calculateInterest() {
        // Current account has no interest, so this method can be left empty
    }

}

public class Main {
    public static void main(String[] args) {
     
        SavingsAccount savingsAccount = new SavingsAccount("R8657", "Harry", 10000,4);
        savingsAccount.calculateInterest();

        CurrentAccount currentAccount = new CurrentAccount("M5182", "Nick", 5500);
        currentAccount.withdraw(500);
    }
}
