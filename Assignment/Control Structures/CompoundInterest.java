import java.util.Scanner;

public class CompoundInterest{
    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of customers: ");
        int numberOfCustomers = scanner.nextInt();

        for (int i = 1; i <= numberOfCustomers; i++) {
            System.out.println("\nCustomer " + i + ":");
            
            System.out.print("Enter initial balance for customer " + i + ": $");
            double initialBalance = scanner.nextDouble();
            
            System.out.print("Enter annual interest rate for customer " + i + " (%): ");
            double annualInterestRate = scanner.nextDouble();
            
            System.out.print("Enter the number of years for customer " + i + ": ");
            int numberOfYears = scanner.nextInt();

            double futureBalance = initialBalance * Math.pow((1 + annualInterestRate / 100), numberOfYears);

            System.out.println("Future balance for customer " + i + ": $" + futureBalance);
        }

        scanner.close();
    }
}
