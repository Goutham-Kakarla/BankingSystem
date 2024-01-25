package dao;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.List;

import entity.Account;
import entity.Customer;
import entity.Transaction;
import util.DBUtil;
import exception.*;

public  class BankRepositoryImpl implements IBankRepository {

	Connection con;
	BankRepositoryImpl()
	{
		this.con=DBUtil.getDBConn();
	}

	@Override
	public void createAccount(Customer customer, long account_id, String account_type, float balance) {
		try {
			// Inside createAccount method in BankRepositoryImpl
			String sql = "INSERT INTO Customers (customer_id, first_name, last_name, email, phone_number, address, DOB) VALUES (?, ?, ?, ?, ?, ?, ?)";

			try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
			    preparedStatement.setInt(1, customer.getCustomer_id());
			    preparedStatement.setString(2, customer.getFirst_name());
			    preparedStatement.setString(3, customer.getLast_name());
			    preparedStatement.setString(4, customer.getEmail());
			    preparedStatement.setLong(5, customer.getPhone_number());
			    preparedStatement.setString(6, customer.getAddress());
			    preparedStatement.setString(7, customer.getDOB()); // Add this line to set DOB

			    preparedStatement.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {

			String sql = "INSERT INTO Accounts (account_id, account_type, balance, customer_id) VALUES (?, ?, ?, ?)";

			try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
				preparedStatement.setLong(1, account_id);
				preparedStatement.setString(2, account_type);
				preparedStatement.setFloat(3, balance);
				preparedStatement.setInt(4, customer.getCustomer_id());

				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Account> listAccounts() {
		// TODO Auto-generated method stub
		List<Account> accounts = new ArrayList<>();

        try {

            String sql = "SELECT * FROM Accounts";

            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {

						long account_id = resultSet.getLong("account_id");
                        String account_type = resultSet.getString("account_type");
                        float balance = resultSet.getFloat("balance");
						int customer_id = resultSet.getInt("customer_id");
                        Customer customer = getCustomerById(customer_id);
                        if(customer==null)
                        {
                        	throw new NullPointerException("No customer associated with account");
                        }
                        Account account = new Account(account_type, balance, customer);
						account.setAccount_id(account_id);
						accounts.add(account);
					}
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
	}



	@Override
	public float getBalance(long account_id) {
		try {

	        String sql = "SELECT balance FROM Accounts WHERE account_id = ?";

	        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {

	            preparedStatement.setLong(1, account_id);

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {

                        return resultSet.getFloat("balance");
	                } else {

	                    System.out.println("Account not found with account number: " + account_id);
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();

	    }

	    return 0;
	}

	@Override
	public void deposit(long account_id, float amount) {
		try {

	        String sql = "UPDATE Accounts SET balance = balance + ? WHERE account_id = ?";

	        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
	            preparedStatement.setFloat(1, amount);
	            preparedStatement.setLong(2, account_id);

	            int rowsAffected = preparedStatement.executeUpdate();

	            if (rowsAffected > 0) {
	                float newBalance = getBalance(account_id);
	                System.out.println("Database Updated Deposit successful."+newBalance);
				} else {

	                System.out.println("Account not found with account number: " + account_id);
	                throw new InvalidAccountException("Account not found");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();	    }
	}

	@Override
	public void withdraw(long account_id, float amount) {
		// TODO Auto-generated method stub
		try {

	        String sqlSelect = "SELECT balance, account_type FROM Accounts WHERE account_id = ?";
	        String sqlUpdate = "UPDATE Accounts SET balance = balance - ? WHERE account_id = ?";

	        try (PreparedStatement selectStatement = con.prepareStatement(sqlSelect);
	             PreparedStatement updateStatement = con.prepareStatement(sqlUpdate)) {

	            selectStatement.setLong(1, account_id);

	            try (ResultSet resultSet = selectStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    float currentBalance = resultSet.getFloat("balance");
	                    String account_type = resultSet.getString("account_type");

	                    if ("Savings".equals(account_type) && currentBalance - amount < 500.0) {
	                        System.out.println("Withdrawal failed. Minimum balance rule violated.");
	                        throw new InsufficientFundException("Withdrawal failed. Minimum balance rule violated");
	                    }

	                    if ("Current".equals(account_type) && currentBalance - amount < -10000.0) {
	                        System.out.println("Withdrawal failed. Overdraft limit exceeded.");
	                        throw new OverDraftLimitExceededException("Withdrawal failed. Overdraft limit exceeded.");
	                    }

	                    if ("ZeroBalance".equals(account_type) && currentBalance - amount < 0) {
	                        System.out.println("Withdrawal failed. Minimum balance rule violated.");
	                        throw new InsufficientFundException("Withdrawal failed. Minimum balance rule violated");
	                    }

	                    updateStatement.setFloat(1, amount);
	                    updateStatement.setLong(2, account_id);


	                    int rowsAffected = updateStatement.executeUpdate();

	                    if (rowsAffected > 0) {

	                        float newBalance = getBalance(account_id);
							System.out.println("Transaction Succsessful :"+ newBalance);
						}
	                } else {

	                    System.out.println("Account not found with account number: " + account_id);
	                    throw new InvalidAccountException("Account not found");
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public List<Transaction> getTransactionsBetweenDate(long account_id, String fromDate, String toDate) {
		List<Transaction> transactions = new ArrayList<>();

		try {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = dateFormat.parse(fromDate);
			Date endDate = dateFormat.parse(toDate);
			String sql = "SELECT * FROM Transactions WHERE account_id = ? AND transaction_date BETWEEN ? AND ?";

		//	String sql = "SELECT * FROM Transactions WHERE account_id = ? AND DATE(transaction_date) BETWEEN ? AND ?";
			try (PreparedStatement statement = con.prepareStatement(sql)) {
				statement.setLong(1, account_id);
				statement.setTimestamp(2, new Timestamp(startDate.getTime()));
				statement.setTimestamp(3, new Timestamp(endDate.getTime()));
				System.out.println("Before executing query");
				try (ResultSet resultSet = statement.executeQuery()) {
					System.out.println("SQL Query: " + sql);
					System.out.println("Start Date: " + startDate);
					System.out.println("End Date: " + endDate);
					while (resultSet.next()) {
						int transaction_id = resultSet.getInt("transaction_id");
						String transaction_type = resultSet.getString("transaction_type");
						double amount = resultSet.getDouble("amount");
						Date transaction_date = resultSet.getTimestamp("transaction_date");
						Transaction transaction = new Transaction(account_id, transaction_id,transaction_type, amount, transaction_date);
						transactions.add(transaction);
						System.out.println("After executing query");

					}
				}
			}
		} catch (ParseException | SQLException e) {
			e.printStackTrace();

		}

		return transactions;
	}
	private Customer getCustomerById(int customer_id) {
		try {

			String sql = "SELECT * FROM Customers WHERE customer_id = ?";

			try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {

				preparedStatement.setInt(1, customer_id);

				try (ResultSet resultSet = preparedStatement.executeQuery()) {
					if (resultSet.next()) {

						String first_name = resultSet.getString("first_name");
						String last_name = resultSet.getString("last_name");
						String email = resultSet.getString("email");
						long phone_number = resultSet.getLong("phone_number");
						String address = resultSet.getString("address");
						String DOB = resultSet.getString("DOB");
						return new Customer(customer_id, first_name, last_name, email, phone_number, address, DOB);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	void addTransaction(Transaction transaction) {
		try {
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
			System.out.println("An error occurred while adding the transaction.");
		}

	}

	public void createAccount(Customer customer_id, String account_type, double balance) {
		// TODO Auto-generated method stub
		
	}

	
}


