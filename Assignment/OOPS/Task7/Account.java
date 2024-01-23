public class Account {

    private String accountNumber;
    private String accountType;
    private double accountBalance;

    public Account() {

    }

    public Account(String accountNumber, String accountType, double accountBalance) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void printAccountInfo() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Type: " + accountType);
        System.out.println("Account Balance: $" + accountBalance);
    }

    public void deposit(double amount) {
        accountBalance += amount;
        System.out.println("Deposit of $" + amount + " successful. Updated balance: $" + accountBalance);
    }

    public void withdraw(double amount) {
        if (amount <= accountBalance) {
            accountBalance -= amount;
            System.out.println("Withdrawal of $" + amount + " successful. Updated balance: $" + accountBalance);
        } else {
            System.out.println("Insufficient balance. Withdrawal failed.");
        }
    }

    public void calculateInterest() {
        if ("Savings".equalsIgnoreCase(accountType)) {
            double interestRate = 0.045;
            double interestAmount = accountBalance * interestRate;
            accountBalance += interestAmount;
            System.out.println("Interest calculated and added. Updated balance: $" + accountBalance);
        } else {
            System.out.println("Interest calculation is only applicable to Savings accounts.");
        }
    }
}