package entity;

public class CurrentAccount extends Account {
    private final double overdraftLimit;
    public CurrentAccount(double overdraftLimit, Customer customer) {
        super("Current", 0.0F, customer);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void printAccountInfo() {
        super.printAccountInfo();
        System.out.println("Overdraft Limit: Rs." + overdraftLimit);
    }
    
}
