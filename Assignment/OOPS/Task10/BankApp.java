import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Customer {
    private static int nextCustomerId = 1001;

    private int customerId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String address;

    public Customer() {
        this.customerId = generateCustomerId();
    }

    public Customer(String firstName, String lastName, String emailAddress, String phoneNumber, String address) {
        this.customerId = generateCustomerId();
        this.firstName = firstName;
        this.lastName = lastName;
        setEmailAddress(emailAddress);
        setPhoneNumber(phoneNumber);
        this.address = address;
    }

    private int generateCustomerId() {
        return nextCustomerId++;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        // Validate email address (a basic check for simplicity)
        if (emailAddress.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            this.emailAddress = emailAddress;
        } else {
            System.out.println("Invalid email address format.");
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        // Validate phone number (basic check for 10 digits)
        if (phoneNumber.matches("\\d{10}")) {
            this.phoneNumber = phoneNumber;
        } else {
            System.out.println("Invalid phone number format.");
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void printInfo() {
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Email Address: " + emailAddress);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Address: " + address);
    }
}

class Account {
    private static long nextAccountNumber = 1001;

    private long accountNumber;
    private String accountType;
    private float accountBalance;
    private Customer customer;

    public Account(Customer customer, String accountType, float balance) {
        this.accountNumber = generateAccountNumber();
        this.customer = customer;
        this.accountType = accountType;
        this.accountBalance = balance;
    }

    private long generateAccountNumber() {
        return nextAccountNumber++;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public float getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(float accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void printInfo() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Type: " + accountType);
        System.out.println("Balance: " + accountBalance);
        customer.printInfo();
    }
}

class Bank {
    private Map<Long, Account> accounts = new HashMap<>();

    public void createAccount(Customer customer, String accountType, float balance) {
        Account account = new Account(customer, accountType, balance);
        accounts.put(account.getAccountNumber(), account);
        System.out.println("Account created successfully. Account Number: " + account.getAccountNumber());
    }

    public float getAccountBalance(long accountNumber) {
        if (accounts.containsKey(accountNumber)) {
            return accounts.get(accountNumber).getAccountBalance();
        } else {
            System.out.println("Account not found.");
            return -1;
        }
    }

    public float deposit(long accountNumber, float amount) {
        if (accounts.containsKey(accountNumber)) {
            Account account = accounts.get(accountNumber);
            account.setAccountBalance(account.getAccountBalance() + amount);
            return account.getAccountBalance();
        } else {
            System.out.println("Account not found.");
            return -1;
        }
    }

    public float withdraw(long accountNumber, float amount) {
        if (accounts.containsKey(accountNumber)) {
            Account account = accounts.get(accountNumber);
            if (amount <= account.getAccountBalance()) {
                account.setAccountBalance(account.getAccountBalance() - amount);
                return account.getAccountBalance();
            } else {
                System.out.println("Insufficient balance for withdrawal.");
                return -1;
            }
        } else {
            System.out.println("Account not found.");
            return -1;
        }
    }

    public void transfer(long fromAccountNumber, long toAccountNumber, float amount) {
        float fromBalance = withdraw(fromAccountNumber, amount);
        if (fromBalance != -1) {
            deposit(toAccountNumber, amount);
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Transfer failed.");
        }
    }

    public void getAccountDetails(long accountNumber) {
        if (accounts.containsKey(accountNumber)) {
            Account account = accounts.get(accountNumber);
            account.printInfo();
        } else {
            System.out.println("Account not found.");
        }
    }
}

public class BankApp {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(
                    "Enter a command ('create_account', 'get_balance', 'deposit', 'withdraw', 'transfer', 'getAccountDetails', 'exit'): ");
            String command = scanner.next();

            if (command.equalsIgnoreCase("create_account")) {
                Customer customer = createCustomer(scanner);
                System.out.println("Select account type ('Savings' or 'Current'): ");
                String accountType = scanner.next();
                System.out.println("Enter initial balance: ");
                float balance = scanner.nextFloat();
                bank.createAccount(customer, accountType, balance);
            } else if (command.equalsIgnoreCase("get_balance")) {
                System.out.println("Enter account number: ");
                long accountNumber = scanner.nextLong();
                float balance = bank.getAccountBalance(accountNumber);
                if (balance != -1) {
                    System.out.println("Account balance: " + balance);
                }
            } else if (command.equalsIgnoreCase("deposit")) {
                System.out.println("Enter account number: ");
                long accountNumber = scanner.nextLong();
                System.out.println("Enter deposit amount: ");
                float amount = scanner.nextFloat();
                float newBalance = bank.deposit(accountNumber, amount);
                if (newBalance != -1) {
                    System.out.println("Deposit successful. New balance: " + newBalance);
                }
            } else if (command.equalsIgnoreCase("withdraw")) {
                System.out.println("Enter account number: ");
                long accountNumber = scanner.nextLong();
                System.out.println("Enter withdrawal amount: ");
                float amount = scanner.nextFloat();
                float newBalance = bank.withdraw(accountNumber, amount);
                if (newBalance != -1) {
                    System.out.println("Withdrawal successful. New balance: " + newBalance);
                }
            } else if (command.equalsIgnoreCase("transfer")) {
                System.out.println("Enter source account number: ");
                long fromAccountNumber = scanner.nextLong();
                System.out.println("Enter target account number: ");
                long toAccountNumber = scanner.nextLong();
                System.out.println("Enter transfer amount: ");
                float amount = scanner.nextFloat();
                bank.transfer(fromAccountNumber, toAccountNumber, amount);
            } else if (command.equalsIgnoreCase("getAccountDetails")) {
                System.out.println("Enter account number: ");
                long accountNumber = scanner.nextLong();
                bank.getAccountDetails(accountNumber);
            } else if (command.equalsIgnoreCase("exit")) {
                System.out.println("Exiting BankApp. Thank you!");
                break;
            } else {
                System.out.println("Invalid command. Please try again.");
            }
        }

        scanner.close();
    }

    private static Customer createCustomer(Scanner scanner) {
        System.out.println("Enter customer details:");
        System.out.print("First Name: ");
        String firstName = scanner.next();
        System.out.print("Last Name: ");
        String lastName = scanner.next();
        System.out.print("Email Address: ");
        String emailAddress = scanner.next();
        System.out.print("Phone Number: ");
        String phoneNumber = scanner.next();
        System.out.print("Address: ");
        String address = scanner.next();

        return new Customer(firstName, lastName, emailAddress, phoneNumber, address);
    }
}