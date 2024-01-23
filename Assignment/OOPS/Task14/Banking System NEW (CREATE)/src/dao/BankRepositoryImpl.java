package dao;

import entity.Account;
import entity.Customer;
import entity.Transaction;
import exception.InsufficientFundException;
import exception.InvalidAccountException;
import exception.OverDraftLimitExcededException;
import util.DBUtil;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankRepositoryImpl implements IBankRepository {

	Connection con=null;
	private String account_type;

	BankRepositoryImpl()
	{
		this.con=DBUtil.getDBConn();
	}



	@Override
	public void createAccount(long account_id, Customer customer, String account_type, double balance) {
		// TODO Auto-generated method stub
		try {
	        // Assuming you have a table named 'Customers' with columns 'first_name', 'last_name', 'email', 'phone_number', and 'address'
	        String sql = "INSERT INTO Customers (customer_id,first_name, last_name, email, phone_number, address,DOB) VALUES (?, ?, ?, ?, ?, ?, ?)";

	        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
	            // Set values for the placeholders in the SQL statement
	        	preparedStatement.setInt(1, customer.getCustomer_id());
	            preparedStatement.setString(2, customer.getFirst_name());
	            preparedStatement.setString(3, customer.getLast_name());
	            preparedStatement.setString(4, customer.getEmail());
	            preparedStatement.setLong(5, customer.getPhone_number());
	            preparedStatement.setString(6, customer.getAddress());
				//preparedStatement.setDate(7,customer.getDate());
				java.sql.Date sqlDate = new java.sql.Date(customer.getDate().getTime());

				preparedStatement.setDate(7, sqlDate);
				preparedStatement.executeUpdate();
				/*String utilDate = customer.getDate();
				try {
					if (utilDate != null) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date parsedDate = dateFormat.parse(utilDate);
						java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
						preparedStatement.setDate(7, new java.sql.Date(sqlDate));
					} else {
						// Log or print a message indicating that the date is null
						System.out.println("Customer date is null.");
						// Handle this situation based on your application requirements
						// You might want to throw an exception, log the issue, or set a default date
					}*/
				}
// Execute the SQL statement


        } catch (SQLException e) {
	        e.printStackTrace();
	    }
		try {
	        // Assuming you have a table named 'Accounts' with columns 'AccountNumber', 'AccountType', 'AccountBalance', and 'customer_id'
	        String sql = "INSERT INTO Accounts (account_id, account_type, balance, customer_id,DOB) VALUES (?, ?, ?, ?,?)";

	        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
	            preparedStatement.setLong(1, account_id);
				preparedStatement.setString(2, account_type);
	            preparedStatement.setFloat(3, (float) balance);
	            preparedStatement.setInt(4, customer.getCustomer_id());
				preparedStatement.setDate(5,utilDate());
	            // Execute the SQL statement
	            preparedStatement.executeUpdate();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	}

	private java.sql.Date utilDate() {
		return null;
	}


	@Override
	public List<Account> listAccounts() {
		// TODO Auto-generated method stub
		List<Account> accounts = new ArrayList<>();

        try {
            // Assuming you have a table named 'Accounts' with columns 'AccountNumber', 'AccountType', 'AccountBalance', and 'customer_id'
            String sql = "SELECT * FROM Accounts";

            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Extract data from the result set
                        long account_id = resultSet.getLong("account_id");
                        String account_type = resultSet.getString("account_type");
                        float balance = resultSet.getFloat("balance");

                        // Assuming you have a method to fetch customer details by ID
                        int customer_id = resultSet.getInt("customer_id");
                        Customer customer = getCustomerById(customer_id);
                        if(customer==null)
                        {
                        	throw new NullPointerException("No customer associated with account");
                        }
                        // Create an Account object and add it to the list
                        Account account = new Account(account_type, balance, customer);
                        account.setAccount_id(account_id);
                        accounts.add(account);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately, e.g., log them or throw a custom exception
        }

        return accounts;
	}

	@Override
	public void calculateInterest() {
		// TODO Auto-generated method stub
		try {
	        // Assuming you have a table named 'SavingsAccounts' with columns 'AccountNumber', 'InterestRate'
	        String sql = "SELECT * FROM Accounts WHERE account_type='savings'";
	        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                while (resultSet.next()) {
	                    // Extract data from the result set

	                    long account_id = resultSet.getLong("account_id");
	                    String account_type=resultSet.getString("account_type");
	                    double balance=resultSet.getDouble("balance");
	                    int customer_id = resultSet.getInt("customer_id");
	                    double interestRate = 4.5;
	                    double interest = (balance / 100) * interestRate;
	                    System.out.print("Interest is Rs. "+interest);
	                    }
	                }
	            }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle exceptions appropriately, e.g., log them or throw a custom exception
	    }
	}

	@Override
	public float getBalance(long account_id) {
		try {
	        // Assuming you have a table named 'Accounts' with columns 'AccountNumber', 'AccountBalance'
	        String sql = "SELECT balance FROM Accounts WHERE account_id = ?";

	        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
	            // Set the parameter for the SQL statement
	            preparedStatement.setLong(1, account_id);

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    // Extract the account balance from the result set
	                    float balance = resultSet.getFloat("balance");
	                    return balance;
	                } else {
	                    // Account not found
	                    System.out.println("Account not found with account number: " + account_id);
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle exceptions appropriately, e.g., log them or throw a custom exception
	    }

	    // Return a default value or throw an exception based on your application logic
	    return 0;
	}

	@Override
	public float deposit(long account_id, float amount) {
		// TODO Auto-generated method stub
		try {
	        // Assuming you have a table named 'Accounts' with columns 'account_id', 'AccountBalance'
	        String sql = "UPDATE Accounts SET balance = balance + ? WHERE account_id = ?";

	        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
	            preparedStatement.setFloat(1, amount);
	            preparedStatement.setLong(2, account_id);

	            // Execute the SQL statement
	            int rowsAffected = preparedStatement.executeUpdate();

	            if (rowsAffected > 0) {
	                // Deposit successful, return the new account balance
	                float newBalance = getBalance(account_id);
	                System.out.println("Database Updated Deposit successful. New balance: RS. " + newBalance);
	                return newBalance;
	            } else {
	                // Account not found
	                System.out.println("Account not found with account number: " + account_id);
	                throw new InvalidAccountException("Account not found");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();	    }
	    return 0;
	}

	@Override
	public float withdraw(long account_id, float amount) {
		// TODO Auto-generated method stub
		try {
	        // Assuming you have a table named 'Accounts' with columns 'account_id', 'AccountBalance', 'AccountType'
	        String sqlSelect = "SELECT balance, account_type FROM Accounts WHERE account_id = ?";
	        String sqlUpdate = "UPDATE Accounts SET balance = balance - ? WHERE account_id = ?";

	        try (PreparedStatement selectStatement = con.prepareStatement(sqlSelect);
	             PreparedStatement updateStatement = con.prepareStatement(sqlUpdate)) {

	            // Set the parameter for the SELECT statement
	            selectStatement.setLong(1, account_id);

	            try (ResultSet resultSet = selectStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    float currentBalance = resultSet.getFloat("balance");
	                    String account_type = resultSet.getString("account_type");

	                    // Check withdrawal constraints based on account type

	                    if ("Savings".equals(account_type)&& currentBalance - amount < 500.0) {
	                        System.out.println("Withdrawal failed. Minimum balance rule violated.");
	                        throw new InsufficientFundException("Withdrawal failed. Minimum balance rule violated");
	                    }

	                    if ("Current".equals(account_type) && currentBalance - amount < -10000.0) {
	                        System.out.println("Withdrawal failed. Overdraft limit exceeded.");
	                        throw new OverDraftLimitExcededException("Withdrawal failed. Overdraft limit exceeded.");
	                    }

	                    if ("ZeroBalance".equals(account_type) && currentBalance - amount < 0) {
	                        System.out.println("Withdrawal failed. Minimum balance rule violated.");
	                        throw new InsufficientFundException("Withdrawal failed. Minimum balance rule violated");
	                    }
	                    // Set values for the placeholders in the UPDATE statement
	                    updateStatement.setFloat(1, amount);
	                    updateStatement.setLong(2, account_id);

	                    // Execute the UPDATE statement
	                    int rowsAffected = updateStatement.executeUpdate();

	                    if (rowsAffected > 0) {
	                        // Withdrawal successful, return the new account balance
	                        float newBalance = getBalance(account_id);
	                        System.out.println("Withdrawal successful. New balance: Rs ." + newBalance);
	                        return newBalance;
	                    }
	                } else {
	                    // Account not found
	                    System.out.println("Account not found with account number: " + account_id);
	                    throw new InvalidAccountException("Account not found");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle exceptions appropriately, e.g., log them or throw a custom exception
	    }

	    // Return a default value or throw an exception based on your application logic
	    return 0;
	}

	@Override
	public void transfer(long fromAccount_id, long toAccount_id, float amount) {
		// TODO Auto-generated method stub

		try {
	        // Withdraw from sender's account
	        float senderBalance = withdraw(fromAccount_id, amount);

	        // If the withdrawal was successful, deposit into receiver's account
	        if (senderBalance != 0) {
	            deposit(toAccount_id, amount);
	            System.out.println("Transfer successful. Rs." + amount + " transferred from account " + fromAccount_id + " to account " + toAccount_id);
	        } else {
	            System.out.println("Transfer failed. Insufficient funds in sender's account.");
	        }
	    } catch (InvalidAccountException e) {
	    	System.out.println(e.getMessage());
	    } catch (InsufficientFundException e) {
	        System.out.println(e.getMessage());
	    } catch (OverDraftLimitExcededException e) {
	        System.out.println(e.getMessage());
	    }
	}

	@Override
	public String getAccountDetails(long account_id) {
		// TODO Auto-generated method stub
	    try {
	        String sql = "SELECT A.account_id, A.account_type, A.balance, C.customer_id, C.first_name, C.last_name, C.email, C.phone_number, C.address " +
	                     "FROM Accounts A JOIN Customers C ON A.customer_id = C.customer_id " +
	                     "WHERE A.account_id = ?";

	        try (PreparedStatement statement = con.prepareStatement(sql)) {
	            statement.setLong(1, account_id);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    // Extract information from the result set
	                     account_id = resultSet.getLong("account_id");
	                    String account_type = resultSet.getString("account_type");
	                    float balance = resultSet.getFloat("balance");
	                    long customer_id = resultSet.getLong("customer_id");
	                    String first_name = resultSet.getString("first_name");
	                    String last_name = resultSet.getString("last_name");
	                    String email = resultSet.getString("email");
	                    long phone_number = resultSet.getLong("phone_number");
	                    String address = resultSet.getString("address");

	                    // Construct the account details string
	                    StringBuilder detailsBuilder = new StringBuilder();
	                    detailsBuilder.append("Account Number: ").append(account_id).append("\n");
						detailsBuilder.append("Account Type: ").append(account_type).append("\n");
						detailsBuilder.append("Account Balance: ").append(balance).append("\n");
	                    detailsBuilder.append("Customer Details:\n");
						detailsBuilder.append("Customer ID: ").append(customer_id).append("\n");
						detailsBuilder.append("First Name: ").append(first_name).append("\n");
						detailsBuilder.append("Last Name: ").append(last_name).append("\n");
	                    detailsBuilder.append("email: ").append(email).append("\n");
						detailsBuilder.append("Phone Number: ").append(phone_number).append("\n");
	                    detailsBuilder.append("address: ").append(address);

	                    return detailsBuilder.toString();
	                } else {
	                    // Account not found
	                    return "Account not found with account number: " + account_id;
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle exceptions appropriately, e.g., log them or throw a custom exception
	        return "An error occurred while fetching account details.";
	    }
	}

	@Override
	public List<Transaction> getTransactions(long account_id, String fromDate, String toDate) {
		// TODO Auto-generated method stub
		List<Transaction> transactions = new ArrayList<>();

	    try {
	        // Convert String dates to Date objects
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date startDate = dateFormat.parse(fromDate);
	        Date endDate = dateFormat.parse(toDate);

	        // Implement logic to fetch transactions from the database between startDate and endDate
	        // Assuming you have a 'Transactions' table with columns 'AccountNumber', 'transaction_date', 'TransactionType', 'TransactionAmount', 'Description'
	        String sql = "SELECT * FROM Transactions WHERE account_id = ? AND transaction_date BETWEEN ? AND ?";
	        try (PreparedStatement statement = con.prepareStatement(sql)) {
	            statement.setLong(1, account_id);
	            statement.setTimestamp(2, new Timestamp(startDate.getTime()));
	            statement.setTimestamp(3, new Timestamp(endDate.getTime()));

	            try (ResultSet resultSet = statement.executeQuery()) {
	                while (resultSet.next()) {
						int transaction_id = resultSet.getInt("transaction_id");
	                    String transaction_type = resultSet.getString("transaction_type");
	                    double amount = resultSet.getDouble("amount");
	                    Date transaction_date = resultSet.getTimestamp("transaction_date");
	                    Account acc=getAccount(account_id);
	                    Transaction transaction = new Transaction(account_id, transaction_id,transaction_type, amount, transaction_date);
	                    transactions.add(transaction);
	                }
	            }
	        }
	    } catch (ParseException | SQLException e) {
	        e.printStackTrace();
	        // Handle exceptions appropriately, e.g., log them or throw a custom exception
	    }

	    return transactions;
	}

	private Customer getCustomerById(int customer_id) {
		try {
	        // Assuming you have a table named 'Customers' with columns 'customer_id', 'first_name', 'last_name', 'email', 'phone_number', and 'address'
	        String sql = "SELECT * FROM Customers WHERE customer_id = ?";

	        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
	            // Set the parameter for the SQL statement
	            preparedStatement.setInt(1, customer_id);

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    // Extract data from the result set
	                    String first_name = resultSet.getString("first_name");
	                    String last_name = resultSet.getString("last_name");
	                    String email = resultSet.getString("email");
	                    long phone_number = resultSet.getLong("phone_number");
	                    String address = resultSet.getString("address");
						java.sql.Date date = resultSet.getDate("DOB");
	                    // Create and return a Customer object with the retrieved details
	                    return new Customer(customer_id, first_name, last_name, email, phone_number, address,date);
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle exceptions appropriately, e.g., log them or throw a custom exception
	    }

	    return null;
    }

	public Account getAccount(long account_id) {
	    try {
	        // Assuming you have a table named 'Accounts' with columns 'AccountNumber', 'AccountType', 'AccountBalance', 'customer_id'
	        // and a table named 'Customers' with columns 'customer_id', 'first_name', 'last_name', 'email', 'phone_number', 'address'
	        String sql = "SELECT A.account_id, A.account_type, A.balance, C.customer_id, C.first_name, C.last_name, C.email, C.phone_number, C.address " +
	                     "FROM Accounts A JOIN Customers C ON A.customer_id = C.customer_id " +
	                     "WHERE A.account_id = ?";

	        try (PreparedStatement statement = con.prepareStatement(sql)) {
	            statement.setLong(1, account_id);

	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    // Extract information from the result set 
	                    long accountId = resultSet.getLong("account_id");
	                    String account_type = resultSet.getString("account_type");
	                    double balance = resultSet.getDouble("balance");

	                    int customer_id = resultSet.getInt("customer_id");
	                    String first_name = resultSet.getString("first_name");
	                    String last_name = resultSet.getString("last_name");
	                    String email = resultSet.getString("email");
	                    long phone_number = resultSet.getLong("phone_number");
	                    String address = resultSet.getString("address");
						java.sql.Date dateStr = resultSet.getDate("DOB");

	                    // Construct and return the account object using the parameterized constructor
	                    return new Account(accountId, account_type, balance, new Customer(customer_id, first_name, last_name, email, phone_number, address,dateStr));
	                } else {
	                    // Account not found
	                    return null;
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle exceptions appropriately, e.g., log them or throw a custom exception
	        System.out.println("An error occurred while fetching account details.");
	        return null;
	    }
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	void addTransaction(Transaction transaction)
	{
		if (transaction.getTransaction_date() == null) {
			//transaction.initializeTransactionDate();
		}
		try {
	        // Assuming you have a table named 'Transactions' with columns 'AccountNumber', 'Description', 'transaction_date', 'TransactionType', 'TransactionAmount'
	        String sql = "INSERT INTO Transactions (account_id, transaction_id, transaction_date, transaction_type, amount) VALUES (?, ?, ?, ?, ?)";

	        try (PreparedStatement statement = con.prepareStatement(sql)) {
	            statement.setLong(1, transaction.getAccount_id());
	            statement.setInt(2, transaction.getTransaction_id());
	            statement.setTimestamp(3, new Timestamp(transaction.getTransaction_date().getTime()));
	            statement.setString(4, transaction.getTransaction_type());
	            statement.setDouble(5, transaction.getAmount());

	            int rowsAffected = statement.executeUpdate();

	            if (rowsAffected > 0) {
	                System.out.println("Transaction added successfully.");
	            } else {
	                System.out.println("Failed to add transaction.");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        // Handle exceptions appropriately, e.g., log them or throw a custom exception
	        System.out.println("An error occurred while adding the transaction.");
	    }
	}

}
