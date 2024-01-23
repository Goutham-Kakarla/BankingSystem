package dao;
import entity.*;
import exception.*;

import java.util.*;
public class BankServiceProviderImpl extends CustomerServiceProviderImpl implements IBankServiceProvider {

	private Map<Long, Account> accountList;
    BankRepositoryImpl bankdb;
    public BankServiceProviderImpl() {

        bankdb= new BankRepositoryImpl() ;
        accountList=listAccounts();
    }

	@Override
    public void createAccount(Customer customer_id, long account_id, String account_type, double balance) {
        Account account;
        switch (account_type) {
            case "Savings" -> account = new SavingsAccount(4.5, customer_id); 
            case "Current" -> account = new CurrentAccount(0.0, customer_id);
            case "ZeroBalance" -> account = new ZeroBalanceAccount(customer_id);
            case null, default -> {
                System.out.println("Invalid Account Type");
                return;
            }
        }
		account.setAccount_id(account_id);
        account.setBalance(balance);

        accountList.put(account_id,account);
        bankdb.createAccount(customer_id, account_id, account_type, (float) balance);
	}

    @Override
    public Map<Long, Account> listAccounts() {
    	accountList=castToMap(bankdb.listAccounts());

    	if(accountList.isEmpty())
    	{
    		throw new NullPointerException("No Accounts created");
    	}
        return accountList;
    }

    private Map<Long, Account> castToMap(List<Account> listAccounts) {
		// TODO Auto-generated method stub
    	Map<Long,Account> hm;
    	hm=new HashMap<>();
        for (Account listAccount : listAccounts) {
            hm.put(listAccount.getAccount_id(), listAccount);

        }
		return hm;
	}

    public Account findAccountObject(long account_id)
    {
    	if(accountList.get(account_id)!=null)
    	{
    		return accountList.get(account_id);
    	}
    	return null;
    }

    @Override
    public double getBalance(long account_id) {
    	Account acc=findAccountObject(account_id);
    	if(acc==null)
    	{
    		throw new InvalidAccountException("No account Found");
    	}
    	return bankdb.getBalance(account_id);
    }

    public double deposit(long account_id, double amount) {
    	Account acc=findAccountObject(account_id);
    	if(acc==null)
    	{
    		System.out.println("Receiver Account Invalid");
    		throw new InvalidAccountException("Receiver Account Invalid");
    	}
    	acc.setBalance(acc.getBalance()+amount);

        Transaction transaction = new Transaction(account_id, "Deposit by self", "Deposit", amount);
		transaction.setTransaction_type("Deposit");
		transaction.initializeTransactionDate();
        bankdb.deposit(account_id, (float) amount);
		bankdb.addTransaction(transaction);
		accountList = listAccounts();
		return bankdb.getBalance(account_id);

    }

	@Override
	public double withdraw(long account_id, double amount) {
		Account account = findAccountObject(account_id);
		if (account != null) {
			try {
				if (account.getBalance() >= amount) {
					double newBalance = account.getBalance() - amount;
					account.setBalance(newBalance);
					bankdb.withdraw(account_id, (float) amount);
					accountList.put(account_id, account);
					System.out.println("Transaction successful. New balance: Rs." + newBalance);
					return newBalance;
				} else {
					throw new InsufficientFundException("Insufficient Funds in account");
				}
			} catch (InvalidAccountException e) {
				throw new InvalidAccountException("Account Invalid");
			} catch (OverDraftLimitExceededException e) {
				throw new OverDraftLimitExceededException("Overdraft Limit Exceeded");
			}
		} else {
			throw new InvalidAccountException("Account Not Found");
		}
	}

    @Override
    public void transfer(long fromAccount_id, long toAccount_id, double amount) {

    	if(!accountList.containsKey(fromAccount_id))
    	{
    		System.out.println("Sender Account Invalid");
    		throw new InvalidAccountException("Sender Account Invalid");
    	}
    	if(!accountList.containsKey(toAccount_id))
    	{
    		System.out.println("Receiver Account Invalid");
    		throw new InvalidAccountException("Receiver Account Invalid");
    	}
    	try {
			withdraw(fromAccount_id,amount);
		} catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
			throw new InvalidAccountException("Sender Account Invalid");
		}
    	catch (InsufficientFundException e) {
			// TODO Auto-generated catch block
			throw new InsufficientFundException("Insufficient Funds in sender account");
		}
    	catch (OverDraftLimitExceededException e) {
			// TODO Auto-generated catch block
			throw new OverDraftLimitExceededException("Overdraft Limit Exceeded");
		}
    	try
    	{
        	deposit(toAccount_id,amount);
    	}
    	catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
    		double newAmount=deposit(fromAccount_id,amount);
    		System.out.println("Deposited back to Sender account, new balance Rs. "+newAmount);
			throw new InvalidAccountException("Receiver Account Invalid");
    	}


        System.out.println("Transferred Rs." + amount + " from account " + fromAccount_id + " to account " + toAccount_id);
    }
    public String getAccountDetails(long account_id) {
    	Account account = findAccountObject(account_id);
    	if(account==null)
    	{
    		throw new InvalidAccountException("Invalid Account Number");
    	}
    	String customerDetails=" Customer first_name: "+account.getCustomer().getFirst_name()+" Customer last_name: "+account.getCustomer().getLast_name()+" Customer ID: "+account.getCustomer().getCustomer_id()+" Customer email: "+account.getCustomer().getEmail()+" Customer Phonenumber: "+account.getCustomer().getPhone_number()+" Customer address: "+account.getCustomer().getAddress();
    	String result=" Account Type: "+account.getAccount_type()+" Account Balance: "+account.getBalance();
        return "Account details for account number " + account_id+result+customerDetails;
    }
    @Override
	public List<Transaction> getTransactions(long account_id, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return bankdb.getTransactions(account_id, startDate, endDate);
	}
}
