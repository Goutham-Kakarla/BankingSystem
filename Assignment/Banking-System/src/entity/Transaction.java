
package entity;

import java.util.Date;

public class Transaction {
    private long account_id;
    private long transaction_id;
    private Date transaction_date;
    private String transaction_type;
    private double amount;

    public Transaction(long account_id, int transaction_id, String transaction_type, double amount, Date transaction_date) {
    	this.account_id = account_id;
        this.transaction_id = transaction_id;
        this.transaction_type = transaction_type;
        this.amount = amount;
        this.transaction_date = transaction_date;
    }

    public Transaction(long accountId, String depositBySelf, String deposit, double amount) {
    	 this.account_id = accountId;
    	    this.transaction_type = deposit;
    	    this.amount = amount;
    	    initializeTransactionDate(); 
    }

    public long getAccount_id() {
        return account_id;
    }

    public int getTransaction_id() {
        return (int) transaction_id;
    }

    public Date getTransaction_date() {
        return transaction_date;
    }

    public String getTransaction_type() {
        return transaction_type;
    }
    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public double getAmount() {
        return amount;
    }

    public void initializeTransactionDate() {
        if (transaction_date == null) {
            transaction_date = new Date();
        }
    }
}

