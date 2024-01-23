import java.util.Scanner;

class Bank {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankAccount account = null;

        System.out.println("Welcome to the Bank!");

        // Display menu to create account
        System.out.println("Choose account type to create:");
        System.out.println("1. SavingsAccount");
        System.out.println("2. CurrentAccount");
        int accountTypeChoice = scanner.nextInt();

        switch (accountTypeChoice) {
            case 1:
                // Creating SavingsAccount
                account = createSavingsAccount();
                break;
            case 2:
                // Creating CurrentAccount
                account = createCurrentAccount();
                break;
            default:
                System.out.println("Invalid choice. Exiting...");
                System.exit(0);
        }

        // Display submenu for operations
        int choice;
        do {
            System.out.println("\nChoose operation:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Calculate Interest (for SavingsAccount)");
            System.out.println("4. Exit");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter deposit amount:");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    break;
                case 2:
                    System.out.println("Enter withdrawal amount:");
                    double withdrawAmount = scanner.nextDouble();
                    account.withdraw(withdrawAmount);
                    break;
                case 3:
                    if (account instanceof SavingsAccount) {
                        ((SavingsAccount) account).calculateInterest();
                    } else {
                        System.out.println("Invalid choice for CurrentAccount.");
                    }
                    break;
                case 4:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 4);

        scanner.close();
    }

    private static SavingsAccount createSavingsAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter account number:");
        String accountNumber = scanner.nextLine();
        System.out.println("Enter customer name:");
        String customerName = scanner.nextLine();
        System.out.println("Enter initial balance:");
        double balance = scanner.nextDouble();
        System.out.println("Enter interest rate:");
        double interestRate = scanner.nextDouble();

        return new SavingsAccount(accountNumber, customerName, balance, interestRate);
    }

    private static CurrentAccount createCurrentAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter account number:");
        String accountNumber = scanner.nextLine();
        System.out.println("Enter customer name:");
        String customerName = scanner.nextLine();
        System.out.println("Enter initial balance:");
        double balance = scanner.nextDouble();

        return new CurrentAccount(accountNumber, customerName, balance);
    }
}
