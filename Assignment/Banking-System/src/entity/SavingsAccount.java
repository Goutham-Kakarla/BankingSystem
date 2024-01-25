package entity;

public class SavingsAccount extends Account {

    public SavingsAccount(double interestRate, Customer customer) {
        super("Savings", 500.0, customer);
        this.interestRate = interestRate;
    }

    private final double interestRate;
    @Override
    public void printAccountInfo() {
        super.printAccountInfo();
        System.out.println("Interest Rate: " + interestRate + "%");
    }
}

