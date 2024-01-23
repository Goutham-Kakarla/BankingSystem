import java.util.Scanner;

class Account {
    private int accountNumber;
    private double balance;

    public Account(int accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal of " + amount + " successful. Remaining balance: " + balance);
        } else {
            System.out.println("Insufficient balance for withdrawal.");
        }
    }

    public double getBalance() {
        return balance;
    }

    public void displayBalance() {
        System.out.println("Account Number: " + accountNumber + ", Balance: " + balance);
    }
}

class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(int accountNumber, double balance, double interestRate) {
        super(accountNumber, balance);
        this.interestRate = interestRate;
    }

    public void calculateInterest() {
        double interest = getBalance() * (interestRate / 100);
        deposit(interest);
        System.out.println("Interest calculated and added: " + interest);
    }
}

class CurrentAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 1000;
    private double overdraftLimit;

    public CurrentAccount(int accountNumber, double balance, double overdraftLimit) {
        super(accountNumber, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) {
        double availableBalance = getBalance() + overdraftLimit;
        if (amount <= availableBalance) {
            super.withdraw(amount);
        } else {
            System.out.println("Withdrawal limit exceeded.");
        }
    }
}

class Bank {
    public Account createAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select account type:");
        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");
        int choice = scanner.nextInt();

        System.out.print("Enter account number: ");
        int accountNumber = scanner.nextInt();

        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();

        if (choice == 1) {
            System.out.print("Enter interest rate for savings account: ");
            double interestRate = scanner.nextDouble();
            return new SavingsAccount(accountNumber, initialBalance, interestRate);
        } else if (choice == 2) {
            System.out.print("Enter overdraft limit for current account: ");
            double overdraftLimit = scanner.nextDouble();
            return new CurrentAccount(accountNumber, initialBalance, overdraftLimit);
        } else {
            System.out.println("Invalid choice. Returning a default Savings Account.");
            return new SavingsAccount(accountNumber, initialBalance, 2.5);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        Account account = bank.createAccount();
        account.displayBalance();

        account.deposit(1000);
        account.displayBalance();

        account.withdraw(500);
        account.displayBalance();

        if (account instanceof SavingsAccount) {
            ((SavingsAccount) account).calculateInterest();
            account.displayBalance();
        }
    }
}

