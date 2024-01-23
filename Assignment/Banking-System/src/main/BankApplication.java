package main;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import dao.*;
import entity.*;
import exception.InsufficientFundException;
import exception.InvalidAccountException;
import exception.OverDraftLimitExceededException;

public class BankApplication {

    public static void main(String[] args) {
        IBankServiceProvider bankServiceProvider = new BankServiceProviderImpl();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("<------ Banking System Menu ------>");
            System.out.println("1. Create New Account");
            System.out.println("2. Deposit into Account");
            System.out.println("3. Withdraw Amount");
            System.out.println("4. Get Balance of the Account");
            System.out.println("5. Transfer to others");
            System.out.println("6. Get Account Details");
            System.out.println("7. List all the Accounts");
            System.out.println("8. Check Transactions Between Date");
            System.out.println("9. Exit Banking System ");

            System.out.print("Please Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createAccountMenu(bankServiceProvider, scanner);
                    break;
                case 2:
                    depositMenu(bankServiceProvider, scanner);
                    break;
                case 3:
                    withdrawMenu(bankServiceProvider, scanner);
                    break;
                case 4:
                    getBalanceMenu(bankServiceProvider, scanner);
                    break;
                case 5:
                    transferMenu(bankServiceProvider, scanner);
                    break;
                case 6:
                    getAccountDetailsMenu(bankServiceProvider, scanner);
                    break;
                case 7:
                    listAccounts(bankServiceProvider);
                    break;
                case 8:
                    getTransactionsBetweenDate(bankServiceProvider, scanner);
                    break;
                case 9:
                    System.out.println("Exiting the Banking System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

        } while (choice != 9);

        scanner.close();
    }

    private static void getTransactionsBetweenDate(IBankServiceProvider bankServiceProvider, Scanner scanner) {
        BankServiceProviderImpl bankService = (BankServiceProviderImpl) bankServiceProvider;
        System.out.println("Enter your account number:");
        long account_id = scanner.nextLong();

        System.out.println("Enter start date (YYYY-MM-DD):");
        String startDateStr = scanner.next();

        if (isValidDateFormat(startDateStr)) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
            return;
        }

        System.out.println("Enter end date (YYYY-MM-DD):");
        String endDateStr = scanner.next();

        if (isValidDateFormat(endDateStr)) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD format.");
            return;
        }

        List<Transaction> transactions = bankService.getTransactions(account_id, startDateStr, endDateStr);

        if (transactions != null && !transactions.isEmpty()) {
            System.out.println("Transactions between " + startDateStr + " and " + endDateStr + ":");
            for (Transaction transaction : transactions) {
                System.out.println("Transaction Type: " + transaction.getTransaction_type());
                System.out.println("Transaction Amount: " + transaction.getAmount());
                System.out.println("Transaction ID" + transaction.getTransaction_id());
                System.out.println("Date and Time: " + transaction.getTransaction_date());
                System.out.println("-------------------------------------");
            }
        } else {
            System.out.println("No transactions found between " + startDateStr + " and " + endDateStr);
        }
    }

    private static void createAccountMenu(IBankServiceProvider bankServiceProvider, Scanner scanner) {
        System.out.println("<----- Create Account Menu ----->");
        System.out.println("Select the type of account that you wish to create");
        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");
        System.out.println("3. ZeroBalance");

        System.out.print("Enter account type choice: ");
        int accountTypeChoice = scanner.nextInt();

        System.out.print("Enter customer ID: ");
        int customer_id = scanner.nextInt();

        System.out.print("Enter first name: ");
        String first_name = scanner.next();

        System.out.print("Enter last name: ");
        String last_name = scanner.next();

        System.out.print("Enter email address: ");
        String email = scanner.next();

        System.out.print("Enter phone number: ");
        long phone_number = scanner.nextLong();

        System.out.print("Enter address: ");
        String address = scanner.next();

        System.out.print("Enter Date of Birth (yyyy-MM-dd): ");
        String DOB = scanner.next();

        Customer customer = new Customer(customer_id, first_name, last_name, email, phone_number, address, DOB);

        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();

        switch (accountTypeChoice) {
            case 1:
                bankServiceProvider.createAccount(customer, Account.generateAccount_id(), "Savings", initialBalance);
                break;
            case 2:
                bankServiceProvider.createAccount(customer, Account.generateAccount_id(), "Current", initialBalance);
                break;
            case 3:
                bankServiceProvider.createAccount(customer, Account.generateAccount_id(), "ZeroBalance", initialBalance);
                break;
            default:
                System.out.println("Invalid account type choice.");
        }
    }


    private static void depositMenu(IBankServiceProvider bankServiceProvider, Scanner scanner) {
        BankServiceProviderImpl bankService = (BankServiceProviderImpl) bankServiceProvider;
        System.out.print("Enter account number to deposit into: ");
        long account_id = scanner.nextLong();

        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();

        try {
            bankService.deposit(account_id, amount);
        } catch (InvalidAccountException e) {
            System.out.println("Invalid Account Number");
        }
    }

    private static void withdrawMenu(IBankServiceProvider bankServiceProvider, Scanner scanner) throws InsufficientFundException {
        BankServiceProviderImpl bankService = (BankServiceProviderImpl) bankServiceProvider;
        System.out.print("Enter account number to withdraw from: ");
        long account_id = scanner.nextLong();

        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();

        try {
            bankService.withdraw(account_id, amount);
        } catch (OverDraftLimitExceededException e) {
            System.out.println("Withdraw Failed");
        } catch (InvalidAccountException e) {
            System.out.println("Invalid Account withdraw Failed");
        }
    }

    private static void getBalanceMenu(IBankServiceProvider bankServiceProvider, Scanner scanner) {
        BankServiceProviderImpl bankService = (BankServiceProviderImpl) bankServiceProvider;
        System.out.print("Enter account number to get balance: ");
        long account_id = scanner.nextLong();

        try {
            double newBalance = bankService.getBalance(account_id);
            System.out.println("Current balance for account " + account_id + ": Rs." + newBalance);
        } catch (InvalidAccountException e

        ) {
            System.out.println("No account exist with account number " + account_id);
        }
    }

    private static void transferMenu(IBankServiceProvider bankServiceProvider, Scanner scanner) throws InsufficientFundException, InvalidAccountException {
        BankServiceProviderImpl bankService = (BankServiceProviderImpl) bankServiceProvider;
        System.out.print("Enter from account number: ");
        long fromAccount_id = scanner.nextLong();

        System.out.print("Enter to account number: ");
        long toAccount_id = scanner.nextLong();

        System.out.print("Enter transfer amount: ");
        double amount = scanner.nextDouble();

        try {
            bankService.transfer(fromAccount_id, toAccount_id, amount);
            System.out.println("Transfer successful");
        } catch (OverDraftLimitExceededException e) {
            System.out.println("Transfer Failed");
        }
    }

    private static void getAccountDetailsMenu(IBankServiceProvider bankServiceProvider, Scanner scanner) {
        BankServiceProviderImpl bankService = (BankServiceProviderImpl) bankServiceProvider;
        System.out.print("Enter from account number: ");
        long account_id = scanner.nextLong();
        try {
            System.out.println(bankService.getAccountDetails(account_id));
        } catch (InvalidAccountException e) {
            System.out.println("Invalid Account Number");
        }
    }

    private static void listAccounts(IBankServiceProvider bankServiceProvider) {
        System.out.println("===== List of Accounts =====");
        try {
            Map<Long, Account> accountList = bankServiceProvider.listAccounts();
            for (Map.Entry<Long, Account> entry : accountList.entrySet()) {
                Account account = entry.getValue();
                System.out.println("Account Number: " + account.getAccount_id() +
                        ", Type: " + account.getAccount_type() +
                        ", Balance: Rs." + account.getBalance());
            }

        } catch (NullPointerException e) {
            System.out.println("NullPointerException caught: " + e.getMessage());
        }
    }

    private static boolean isValidDateFormat(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false);
            dateFormat.parse(dateStr);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }
}
