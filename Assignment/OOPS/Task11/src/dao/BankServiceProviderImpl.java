package dao;
import entity.*;
import exception.*;

import java.util.*;
public class BankServiceProviderImpl extends CustomerServiceProviderImpl implements IBankServiceProvider {
    private Account[] accountList;
    private String branchName;
    private String branchAddress;

    public BankServiceProviderImpl(String branchName, String branchAddress) {
        this.branchName = branchName;
        this.branchAddress = branchAddress;
        this.accountList=new Account[0];
//        this.accountList = new Account[]{new SavingsAccount(4.5,new Customer(1,"avi","singh","avi@gmail.com",9898989898L,"armapur")),new CurrentAccount(-10000,new Customer(2,"ani","singh","ani@gmail.com",9898989899L,"panki")),new ZeroBalanceAccount(new Customer(3,"avinash","singh","avinash@gmail.com",9898989897L,"rawatpur"))}; // Initialize an empty array of accounts

    }

    @Override
    public Account createAccount(Customer customer, long accNo, String accType, double balance) {
        // Overriding createAccount to update accountList
        // to check account number set manual or automatic
        Account account=null;
        if ("Savings".equals(accType)) {
            account = new SavingsAccount(4.5, customer); // Assume 4.5% interest rate for now
        } else if ("Current".equals(accType)) {
            account = new CurrentAccount(0.0, customer); // Assume 0 overdraft limit for now
        } else if ("ZeroBalance".equals(accType)){
            account = new ZeroBalanceAccount(customer);
        }
        else
        {
            System.out.println("Invalid Account Type");
            return null;
        }

        // Set account number and balance
        account.setAccountNumber(accNo);
        account.setAccountBalance(balance);

        // Update accountList
        accountList = Arrays.copyOf(accountList, accountList.length + 1);
        accountList[accountList.length - 1] = account;

        // Return the created account
        return account;
    }

    @Override
    public Account[] listAccounts() {
        // List all accounts in the bank
        return accountList;
    }

    @Override
    public void calculateInterest() {
        // Calculate interest for all accounts in the bank
        for (Account account : accountList) {
            if (account instanceof SavingsAccount) {
                double interestRate = ((SavingsAccount) account).getInterestRate();
                double interest = (account.getAccountBalance()/100) * interestRate;
                account.setAccountBalance(account.getAccountBalance() + interest);
                System.out.println("Interest calculated for Savings Account " + account.getAccountNumber() +
                        ": Rs." + interest);
            }
        }

    }
    public Account findAccountObject(long accountNumber)
    {
        for(int i=0;i<accountList.length;i++)
        {
            if(accountList[i].getAccountNumber()==accountNumber)
            {
                return accountList[i];
            }
        }
        return null;
    }
    public void setAccountObject(Account acc)
    {
        for(int i=0;i<accountList.length;i++)
        {
            if(accountList[i].getAccountNumber()==acc.getAccountNumber())
            {
                accountList[i]=acc;
            }
        }
    }

    @Override
    public double getAccountBalance(long accountNumber) {
        Account acc=findAccountObject(accountNumber);
        if(acc==null)
        {
            System.out.println("No account exist with account number "+accountNumber);
        }
        return acc.getAccountBalance();
    }

    public double deposit(long accountNumber, double amount) {
        // Deposit the specified amount into the account
        // Implement logic to update the account balance in storage or database
        // For demonstration purposes, return a dummy balance
        Account acc=findAccountObject(accountNumber);
        if(acc==null)
        {
            System.out.println("Receiver Account Invalid");
            throw new InvalidAccountException("Receiver Account Invalid");
        }
        acc.setAccountBalance(acc.getAccountBalance()+amount);
        setAccountObject(acc);
        return acc.getAccountBalance();

    }

    @Override
    public double withdraw(long accountNumber, double amount) {
        Account account = findAccountObject(accountNumber);
        if (account != null) {
            // Check if the withdrawal violates the minimum balance rule
            if (account instanceof SavingsAccount) {
                double minimumBalance = 500.0; // Example minimum balance for savings account
                if (account.getAccountBalance() - amount < minimumBalance) {
                    System.out.println("Withdrawal failed. Minimum balance rule violated.");
                    throw new InsufficientFundException("Insufficient Funds");
                }
            }
            if (account instanceof CurrentAccount) {
                CurrentAccount acc=(CurrentAccount) account;
                double overdraftLimit = acc.getOverdraftLimit(); // Example minimum balance for savings account
                //System.out.println(account.getAccountBalance());
                //System.out.println(overdraftLimit);
                if (account.getAccountBalance() - amount < overdraftLimit) {
                    System.out.println("Withdrawal failed. Overdraft Limit Exceeded.");
                    throw new OverDraftLimitExcededException("Overdraft Limit Exceeded"); // Special value indicating failure due to minimum balance rule
                }
            }

            if (account instanceof ZeroBalanceAccount) {
                ZeroBalanceAccount acc=(ZeroBalanceAccount) account;
                double minbal = 0; // Example minimum balance for savings account
                if (account.getAccountBalance() - amount < minbal) {
                    System.out.println("Withdrawal failed. Insufficient Funds");
                    throw new InsufficientFundException("Insufficient Funds");// Special value indicating failure due to minimum balance rule
                }
            }
            account.setAccountBalance(account.getAccountBalance() - amount);
            setAccountObject(account);
            System.out.println("Withdrawal successful. New balance: Rs." + account.getAccountBalance());
            return account.getAccountBalance();

        } else {
            throw new InvalidAccountException("Account Not Found");
        }
    }
    @Override
    public void transfer(long fromAccountNumber, long toAccountNumber, double amount) {
//    	Account senderAccount = findAccountObject(fromAccountNumber);
//    	Account receiverAccount = findAccountObject(toAccountNumber);
        try {
            double sendAmount=withdraw(fromAccountNumber,amount);
        } catch (InvalidAccountException e) {
            // TODO Auto-generated catch block
            throw new InvalidAccountException("Sender Account Invalid");
        }
        catch (InsufficientFundException e) {
            // TODO Auto-generated catch block
            throw new InsufficientFundException("Insufficient Funds in sender account");
        }
        catch (OverDraftLimitExcededException e) {
            // TODO Auto-generated catch block
            throw new OverDraftLimitExcededException("Overdraft Limit Exceeded");
        }
        try
        {
            double newAmount=deposit(toAccountNumber,amount);
        }
        catch (InvalidAccountException e) {
            // TODO Auto-generated catch block
            double newAmount=deposit(fromAccountNumber,amount);
            System.out.println("Deposited back to Sender account, new balance Rs. "+newAmount);
            throw new InvalidAccountException("Receiver Account Invalid");
        }


        System.out.println("Transferred Rs." + amount + " from account " + fromAccountNumber + " to account " + toAccountNumber);
    }
    public String getAccountDetails(long accountNumber) {
        Account account = findAccountObject(accountNumber);
        if(account==null)
        {
            throw new InvalidAccountException("Invalid Account Number");
        }
        String customerdetails=" Customer Firstname: "+account.getCustomer().getFirstName()+" Customer lastname: "+account.getCustomer().getLastName()+" Customer ID: "+account.getCustomer().getCustomerId()+" Customer email: "+account.getCustomer().getEmail()+" Customer Phonenumber: "+account.getCustomer().getPhoneNumber()+" Customer address: "+account.getCustomer().getAddress();
        String result=" Account Type: "+account.getAccountType()+" Account Balance: "+account.getAccountBalance();
        return "Account details for account number " + accountNumber+result+customerdetails;
    }
    // Additional methods...

    // Existing methods from BankServiceProviderImpl...

    // Additional methods...
}
