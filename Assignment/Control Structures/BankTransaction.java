import java.util.ArrayList;
import java.util.Scanner;

public class BankTransaction {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> transactionHistory = new ArrayList<>();

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Exit");

            System.out.print("Enter your choice (1, 2, or 3): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    handleTransaction("Deposit", transactionHistory, scanner);
                    break;
                case 2:
                    handleTransaction("Withdrawal", transactionHistory, scanner);
                    break;
                case 3:
                    displayTransactionHistory(transactionHistory);
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please choose 1, 2, or 3.");
            }
        }
    }

    private static void handleTransaction(String transactionType, ArrayList<String> transactionHistory, Scanner scanner) {
        System.out.print("Enter the amount for the " + transactionType + ": $");
        double amount = scanner.nextDouble();

        transactionHistory.add(transactionType + ": $" + amount);
        System.out.println(transactionType + " successful!");

        displayTransactionHistory(transactionHistory);
    }

    private static void displayTransactionHistory(ArrayList<String> transactionHistory) {
        System.out.println("\nTransaction History:");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}
