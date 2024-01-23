package app;

import java.util.Scanner;
import dao.*;
import entity.*;
import exception.InsufficientFundException;
import exception.InvalidAccountException;
import exception.OverDraftLimitExcededException;
public class BankApp {

    public static void main(String[] args) {
        IBankServiceProvider bankServiceProvider = new BankServiceProviderImpl("Main Branch", "123 Main St");
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("===== Banking System Menu =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Get Balance");
            System.out.println("5. Transfer");
            System.out.println("6. Get Account Details");
            System.out.println("7. List Accounts");
            System.out.println("8. Exit");

            System.out.print("Enter your choice: ");
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
                    System.out.println("Exiting the Banking System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

        } while (choice != 8);

        scanner.close();
    }

    private static void createAccountMenu(IBankServiceProvider bankServiceProvider, Scanner scanner) {
        System.out.println("===== Create Account Menu =====");
        System.out.println("1. Savings Account");
        System.out.println("2. Current Account");
        System.out.println("3. ZeroBalance");
        System.out.print("Enter account type choice: ");
        int accountTypeChoice = scanner.nextInt();

        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();

        System.out.print("Enter first name: ");
        String firstName = scanner.next();

        System.out.print("Enter last name: ");
        String lastName = scanner.next();

        System.out.print("Enter email address: ");
        String email = scanner.next();

        System.out.print("Enter phone number: ");
        long phoneNumber = scanner.nextLong();

        System.out.print("Enter address: ");
        String address = scanner.next();

        Customer customer = new Customer(customerId, firstName, lastName, email, phoneNumber, address);

        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();

        switch (accountTypeChoice) {
            case 1:
                bankServiceProvider.createAccount(customer,Account.generateAccountNumber(), "Savings", initialBalance);
                break;
            case 2:
                bankServiceProvider.createAccount(customer, Account.generateAccountNumber(), "Current", initialBalance);
                break;
            case 3:
                bankServiceProvider.createAccount(customer, Account.generateAccountNumber(), "ZeroBalance", initialBalance);
                break;
            default:
                System.out.println("Invalid account type choice.");
        }
    }

    private static void depositMenu(IBankServiceProvider bankServiceProvider, Scanner scanner) {
        BankServiceProviderImpl bankService=(BankServiceProviderImpl) bankServiceProvider;
        System.out.print("Enter account number to deposit into: ");
        long accountNumber = scanner.nextLong();

        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();

        // Call bankServiceProvider.deposit() method
        double newBalance = bankService.deposit(accountNumber, amount);

        if (newBalance >= 0) {
            System.out.println("Deposit successful. New balance: Rs." + newBalance);
        } else {
            System.out.println("Deposit failed. Account not found.");
        }
    }

    private static void withdrawMenu(IBankServiceProvider bankServiceProvider, Scanner scanner) {
        // Implement the withdraw menu logic
        // Use bankServiceProvider.withdraw() method
        BankServiceProviderImpl bankService=(BankServiceProviderImpl) bankServiceProvider;
        System.out.print("Enter account number to withdraw from: ");
        long accountNumber = scanner.nextLong();

        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();

        // Call bankServiceProvider.withdraw() method
        double newBalance = bankService.withdraw(accountNumber, amount);

    }

    private static void getBalanceMenu(IBankServiceProvider bankServiceProvider, Scanner scanner) {
        // Implement the get balance menu logic
        // Use bankServiceProvider.getAccountBalance() method
        BankServiceProviderImpl bankService=(BankServiceProviderImpl) bankServiceProvider;
        System.out.print("Enter account number to get balance: ");
        long accountNumber = scanner.nextLong();

        // Call bankServiceProvider.getAccountBalance() method
        double balance = bankService.getAccountBalance(accountNumber);
        System.out.println("Current balance for account " + accountNumber + ": Rs." + balance);
    }

    private static void transferMenu(IBankServiceProvider bankServiceProvider, Scanner scanner) {
        BankServiceProviderImpl bankService=(BankServiceProviderImpl) bankServiceProvider;
        System.out.print("Enter from account number: ");
        long fromAccountNumber = scanner.nextLong();

        System.out.print("Enter to account number: ");
        long toAccountNumber = scanner.nextLong();

        System.out.print("Enter transfer amount: ");
        double amount = scanner.nextDouble();

        // Call bankServiceProvider.transfer() method
        try
        {
            bankService.transfer(fromAccountNumber, toAccountNumber, amount);
            System.out.println("Transfer successful");
        }
        catch(OverDraftLimitExcededException e)
        {
            System.out.println("Transfer Failed");
        }
        catch (InvalidAccountException e) {
            // TODO Auto-generated catch block
            System.out.println("Transfer Failed");
        }
        catch (InsufficientFundException e) {
            // TODO Auto-generated catch block
            System.out.println("Transfer Failed");    	}
    }

    private static void getAccountDetailsMenu(IBankServiceProvider bankServiceProvider, Scanner scanner) {
        BankServiceProviderImpl bankService=(BankServiceProviderImpl) bankServiceProvider;
        System.out.print("Enter from account number: ");
        long AccountNumber = scanner.nextLong();
        try
        {
            System.out.println(bankService.getAccountDetails(AccountNumber));
        }
        catch(InvalidAccountException e)
        {
            System.out.println("Invalid Account Number");
        }
    }

    private static void listAccounts(IBankServiceProvider bankServiceProvider) {
        System.out.println("===== List of Accounts =====");
        Account[] accounts = bankServiceProvider.listAccounts();
        for (Account account : accounts) {
            System.out.println("Account Number: " + account.getAccountNumber() +
                    ", Type: " + account.getAccountType() +
                    ", Balance: Rs." + account.getAccountBalance());
        }
    }
}
