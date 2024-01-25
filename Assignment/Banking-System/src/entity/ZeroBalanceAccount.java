package entity;

public class ZeroBalanceAccount extends Account {
    public ZeroBalanceAccount(Customer customer) {
        super("Zero Balance", 0.0F, customer);
    }

    @Override
    public void printAccountInfo() {
        super.printAccountInfo();
    }
}
