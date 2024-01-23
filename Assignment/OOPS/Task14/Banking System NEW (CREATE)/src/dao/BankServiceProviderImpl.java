package dao;

import entity.*;
import exception.*;

import java.util.*;

public class BankServiceProviderImpl extends CustomerServiceProviderImpl implements IBankServiceProvider {
	private Map<Long, Account> accountList;
	private String branchName;
	private String branchAddress;
	private final BankRepositoryImpl bank;

	public BankServiceProviderImpl(String branchName, String branchAddress) {
		this.branchName = branchName;
		this.branchAddress = branchAddress;
		bank = new BankRepositoryImpl();
		accountList = listAccounts();
	}

	@Override
	public void createAccount( long account_id,Customer customer, String account_type, double balance) {
		Account account;

		switch (account_type) {
			case "Savings":
				account = new SavingsAccount(4.5, customer);
				break;
			case "Current":
				account = new CurrentAccount(0.0, customer);
				break;
			case "ZeroBalance":
				account = new ZeroBalanceAccount(customer);
				break;
			default:
				System.out.println("Invalid Account Type");
				return;
		}

		account.setAccount_id(account_id);
		account.setBalance(balance);
		//account.add(account);
		accountList.put(account_id, account);
		bank.createAccount( account_id,customer,account_type, (float) balance);
	}

	@Override
	public Map<Long, Account> listAccounts() {
		List<Account> accounts = bank.listAccounts();
		if (accounts.isEmpty()) {
			throw new NullPointerException("No Accounts created");
		}
		accountList = castToMap(accounts);
		return accountList;
	}

	private Map<Long, Account> castToMap(List<Account> listAccounts) {
		Map<Long, Account> accountMap = new HashMap<>();
		for (Account account : listAccounts) {
			accountMap.put(account.getAccount_id(), account);
		}
		return accountMap;
	}

	@Override
	public void calculateInterest() {
		for (Map.Entry<Long, Account> entry : accountList.entrySet()) {
			long account_id = entry.getKey();
			Account account = entry.getValue();
			if (account instanceof SavingsAccount) {
				double interestRate = ((SavingsAccount) account).getInterestRate();
				double interest = (account.getBalance() / 100) * interestRate;
				account.setBalance(account.getBalance() + interest);
				accountList.put(account_id, account);
				System.out.println("Interest calculated for Savings Account " + account.getAccount_id() +
						": Rs." + interest);
			}
		}
	}

	public Account findAccountObject(long account_id) {
		return accountList.get(account_id);
	}

	public void setAccountObject(Account account_id) {
		accountList.put(account_id.getAccount_id(), account_id);
	}

	public double getBalance(long accountNumber) {
		Account acc = findAccountObject(accountNumber);
		if (acc == null) {
			throw new InvalidAccountException("No account Found");
		}
		return bank.getBalance(accountNumber);
	}

	public double deposit(long account_id, double amount) {
		Account acc = findAccountObject(account_id);
		if (acc == null) {
			System.out.println("Receiver Account Invalid");
			throw new InvalidAccountException("Receiver Account Invalid");
		}
		acc.setBalance(acc.getBalance() + amount);

		String transaction_type = "Deposit";

		Transaction transaction = new Transaction(account_id, "Deposit by self", "Deposit", amount);

		transaction.initializeTransactionDate();
		Date currentDate = transaction.getTransaction_date();
		bank.deposit(account_id, (float) amount);
		bank.addTransaction(transaction);
		accountList = listAccounts();
		return bank.getBalance(account_id);
	}

	@Override
	public double withdraw(long account_id, double amount) {
		Account account = findAccountObject(account_id);
		if (account != null) {
			try {
				// Check for sufficient funds
				if (account.getBalance() >= amount) {
					double newBalance = account.getBalance() - amount;
					account.setBalance(newBalance);
					bank.withdraw(account_id, (float) amount);
					accountList.put(account_id, account);
					System.out.println("Withdrawal successful. New balance: Rs." + newBalance);
					return newBalance;
				} else {
					throw new InsufficientFundException("Insufficient Funds in account");
				}
			} catch (InvalidAccountException | OverDraftLimitExcededException e) {
				// Let these exceptions propagate naturally
				throw e;
			}
		} else {
			throw new InvalidAccountException("Account Not Found");
		}
	}

	@Override
	public void transfer(long fromAccount_id, long toAccount_id, double amount) {
		if (!accountList.containsKey(fromAccount_id)) {
			System.out.println("Sender Account Invalid");
			throw new InvalidAccountException("Sender Account Invalid");
		}
		if (!accountList.containsKey(toAccount_id)) {
			System.out.println("Receiver Account Invalid");
			throw new InvalidAccountException("Receiver Account Invalid");
		}
		try {
			double sendAmount = withdraw(fromAccount_id, amount);
		} catch (InvalidAccountException e) {
			throw new InvalidAccountException("Sender Account Invalid");
		} catch (InsufficientFundException e) {
			throw new InsufficientFundException("Insufficient Funds in sender account");
		} catch (OverDraftLimitExcededException e) {
			throw new OverDraftLimitExcededException("Overdraft Limit Exceeded");
		}
		double newAmount;
		try {
			newAmount = deposit(toAccount_id, amount);
		} catch (InvalidAccountException e) {
			newAmount = deposit(fromAccount_id, amount);
			System.out.println("Deposited back to Sender account, new balance Rs. " + newAmount);
			throw new InvalidAccountException("Receiver Account Invalid");
		}



		System.out.println("Transferred Rs." + amount + " from account " + fromAccount_id + " to account " + toAccount_id);
	}

	public String getAccountDetails(long account_id) {
		Account account = findAccountObject(account_id);
		if (account == null) {
			throw new InvalidAccountException("Invalid Account Number");
		}
		String customerDetails = " Customer first_name: " + account.getCustomer().getFirst_name() +
				" Customer last_name: " + account.getCustomer().getLast_name() +
				" Customer ID: " + account.getCustomer().getCustomer_id() +
				" Customer email: " + account.getCustomer().getEmail() +
				" Customer Phonenumber: " + account.getCustomer().getPhone_number() +
				" Customer address: " + account.getCustomer().getAddress();
		String result = " Account Type: " + account.getAccount_type() +
				" Account Balance: " + account.getBalance();
		return "Account details for account number " + account_id + result + customerDetails;
	}

	public List<Transaction> getTransactions(long accountNumber, String startDate, String endDate) {
		return bank.getTransactions(accountNumber, startDate, endDate);
	}

	// Additional methods...
}
