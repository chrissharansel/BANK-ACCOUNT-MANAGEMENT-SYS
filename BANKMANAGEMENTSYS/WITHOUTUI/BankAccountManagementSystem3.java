import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;

    private List<String> transactionHistory = new ArrayList<>();

    public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            recordTransaction("Deposited $" + amount);
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recordTransaction("Withdrawn $" + amount);
        }
    }

    public void transfer(BankAccount recipient, double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            recordTransaction("Transferred $" + amount + " to " + recipient.getAccountHolder());
        }
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    private void recordTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber + ", Account Holder: " + accountHolder + ", Balance: $" + balance;
    }
}

class PersonalFinanceManager {
    private List<BankAccount> accounts = new ArrayList<>();

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    public BankAccount findAccount(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}

public class BankAccountManagementSystem3 {
    private static final double MIN_DEPOSIT_AMOUNT = 100;
    private static final double MAX_DEPOSIT_AMOUNT = 10000.0;
    private static final double MIN_WITHDRAWAL_AMOUNT = 100;
    private static final double MAX_WITHDRAWAL_AMOUNT = 5000.0;
    private static final int MIN_NAME_LENGTH = 3;
    private static final int MIN_ACCOUNT_NUMBER_LENGTH = 3;

    public static void main(String[] args) {
        PersonalFinanceManager pfm = new PersonalFinanceManager();
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("Bank Account Management System Menu:");
            System.out.println("1. View Accounts");
            System.out.println("2. Create Account");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Transfer");
            System.out.println("6. View Transaction History");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Accounts:");
                    List<BankAccount> accounts = pfm.getAccounts();
                    for (BankAccount account : accounts) {
                        System.out.println(account);
                    }
                    break;
                case 2:
                    createAccount(pfm, scanner);
                    break;
                case 3:
                    deposit(pfm, scanner);
                    break;
                case 4:
                    withdraw(pfm, scanner);
                    break;
                case 5:
                    onlineTransfer(pfm, scanner);
                    break;
                case 6:
                    System.out.println("Transaction History:");
                    for (BankAccount account : pfm.getAccounts()) {
                        System.out.println("Account: " + account.getAccountNumber());
                        List<String> transactions = account.getTransactionHistory();
                        for (String transaction : transactions) {
                            System.out.println(" - " + transaction);
                        }
                    }
                    break;
                case 7:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);
    }

    private static void createAccount(PersonalFinanceManager pfm, Scanner scanner) {
        System.out.print("Enter account number (minimum " + MIN_ACCOUNT_NUMBER_LENGTH + " characters): ");
        String accountNumber = scanner.next();
        while (accountNumber.length() < MIN_ACCOUNT_NUMBER_LENGTH) {
            System.out.println("Account number is too short. Please enter a valid account number.");
            System.out.print("Enter account number (minimum " + MIN_ACCOUNT_NUMBER_LENGTH + " characters): ");
            accountNumber = scanner.next();
        }

        System.out.print("Enter account holder name (minimum " + MIN_NAME_LENGTH + " characters): ");
        String accountHolder = scanner.next();
        while (accountHolder.length() < MIN_NAME_LENGTH) {
            System.out.println("Account holder name is too short. Please enter a valid name.");
            System.out.print("Enter account holder name (minimum " + MIN_NAME_LENGTH + " characters): ");
            accountHolder = scanner.next();
        }

        System.out.print("Enter initial deposit amount: ");
        double initialDeposit = scanner.nextDouble();

        BankAccount newAccount = new BankAccount(accountNumber, accountHolder, initialDeposit);
        pfm.addAccount(newAccount);

        System.out.println("Account created successfully!");
    }

    // ... (other methods)



    private static void deposit(PersonalFinanceManager pfm, Scanner scanner) {
        System.out.print("Enter account number for deposit: ");
        String depositAccountNumber = scanner.next();
        BankAccount depositAccount = pfm.findAccount(depositAccountNumber);
        if (depositAccount != null) {
            System.out.print("Enter deposit amount: ");
            double depositAmount = scanner.nextDouble();
            if (isValidAmount(depositAmount, MIN_DEPOSIT_AMOUNT, MAX_DEPOSIT_AMOUNT)) {
                depositAccount.deposit(depositAmount);
            } else {
                System.out.println("Invalid deposit amount. Please enter an amount between "
                        + MIN_DEPOSIT_AMOUNT + " and " + MAX_DEPOSIT_AMOUNT);
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void withdraw(PersonalFinanceManager pfm, Scanner scanner) {
        System.out.print("Enter account number for withdrawal: ");
        String withdrawAccountNumber = scanner.next();
        BankAccount withdrawAccount = pfm.findAccount(withdrawAccountNumber);
        if (withdrawAccount != null) {
            System.out.print("Enter withdrawal amount: ");
            double withdrawalAmount = scanner.nextDouble();
            if (isValidAmount(withdrawalAmount, MIN_WITHDRAWAL_AMOUNT, MAX_WITHDRAWAL_AMOUNT)) {
                withdrawAccount.withdraw(withdrawalAmount);
            } else {
                System.out.println("Invalid withdrawal amount. Please enter an amount between "
                        + MIN_WITHDRAWAL_AMOUNT + " and " + MAX_WITHDRAWAL_AMOUNT);
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void onlineTransfer(PersonalFinanceManager pfm, Scanner scanner) {
        System.out.print("Enter account number to transfer from: ");
        String sourceAccountNumber = scanner.next();
        BankAccount sourceAccount = pfm.findAccount(sourceAccountNumber);
        if (sourceAccount != null) {
            System.out.print("Enter account number to transfer to: ");
            String destinationAccountNumber = scanner.next();
            BankAccount destinationAccount = pfm.findAccount(destinationAccountNumber);
            if (destinationAccount != null) {
                System.out.print("Enter transfer amount: ");
                double transferAmount = scanner.nextDouble();
                if (isValidAmount(transferAmount, MIN_WITHDRAWAL_AMOUNT, MAX_WITHDRAWAL_AMOUNT)) {
                    sourceAccount.transfer(destinationAccount, transferAmount);
                } else {
                    System.out.println("Invalid transfer amount. Please enter an amount between "
                            + MIN_WITHDRAWAL_AMOUNT + " and " + MAX_WITHDRAWAL_AMOUNT);
                }
            } else {
                System.out.println("Destination account not found.");
            }
        } else {
            System.out.println("Source account not found.");
        }
    }

    private static boolean isValidAmount(double amount, double minAmount, double maxAmount) {
        return amount >= minAmount && amount <= maxAmount;
    }
}
