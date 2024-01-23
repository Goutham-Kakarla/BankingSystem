customer_accounts = {
    '123456': 1500.50,
    '789012': 2500.75,
    '345678': 100.00,
}

while True:
    account_number = input("Enter your account number: ")

    if account_number in customer_accounts:
        print(f"Your account balance is: ${customer_accounts[account_number]}")
        break 
    else:
        print("Invalid account number. Please try again.")
