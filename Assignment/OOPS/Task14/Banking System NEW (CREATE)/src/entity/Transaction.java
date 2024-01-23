
package entity;

import java.util.Date;

public class Transaction {
    private static long transactionCounter = 1;
    private long account_id;
    private long transaction_id;

    private Date transaction_date;
    private String transaction_type;
    private double amount;

    // Constructor

    public Transaction(long account_id, String transaction_type, double amount, Date transaction_date) {

        this.account_id = account_id;
        this.transaction_type = transaction_type;
        this.amount = amount;
        this.transaction_date = new Date(); // Current date and time

    }


    public Transaction(long account_id, int transaction_Id, String transaction_type, double amount, Date transactionDate) {
    }

    public Transaction(long accountId, String depositBySelf, String deposit, double amount) {
    }


    public static long getTransactionCounter() {
        return transactionCounter;
    }

    public static void setTransactionCounter(long transactionCounter) {
        Transaction.transactionCounter = transactionCounter;
    }

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public int getTransaction_id() {
        return (int) transaction_id;
    }

    public void setTransaction_id(long transaction_id) {
        this.transaction_id = transaction_id;
    }

    public Date getTransaction_date() {
        return transaction_date;
    }

    public void setTransaction_date(Date transaction_date) {
        this.transaction_date = transaction_date;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public Transaction(long account_id, String description, Date transaction_date, String transaction_type, double amount, Object transactionType) {
        this.account_id = account_id;

        this.transaction_date = transaction_date;
        this.transaction_type = transaction_type;
        this.amount = amount;

    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void initializeTransactionDate() {
        if (transaction_date == null) {
            transaction_date = new Date(); // Initialize transaction_date with the current date/time
        }
    }


    }

