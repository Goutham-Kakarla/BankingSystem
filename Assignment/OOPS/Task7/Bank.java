public class Bank {
    public static void main(String[] args) {

        Account savingsAccount = new Account("R98765", "Savings", 8000.0);

        System.out.println("Initial Account Information:");

        System.out.println("\nPerforming Deposit:");
        savingsAccount.deposit(1000.0);

        System.out.println("\nPerforming Withdrawal:");
        savingsAccount.withdraw(2000.0);

        System.out.println("\nPerforming Interest Calculation:");
        savingsAccount.calculateInterest();

        System.out.println("\nFinal Account Information:");
        savingsAccount.printAccountInfo();
    }
}