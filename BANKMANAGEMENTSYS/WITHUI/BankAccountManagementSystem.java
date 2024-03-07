import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.List;
class BankAccount implements Serializable {
 private static final long serialVersionUID = 1L;
 private String accountName;
 private double balance;
 private String securityNumber;
 private String phoneNumber;
 private String address;
 private ArrayList<String> transactionHistory;
 public BankAccount(String accountName, String securityNumber, String phoneNumber, 
String address,double balance) {
 this.accountName = accountName;
 this.balance = balance;
 this.securityNumber = securityNumber;
 this.phoneNumber = phoneNumber;
 this.address = address;
 this.transactionHistory = new ArrayList<>();}
 
public ArrayList<String> getTransactionHistory() {
 return transactionHistory;
 }
 public void addTransaction(String transaction) {
 if (transactionHistory == null) {
 transactionHistory = new ArrayList<>();
 }
 transactionHistory.add(transaction);
 }
 public void setTransactionHistory(ArrayList<String> transactionHistory) {
 this.transactionHistory = transactionHistory;
 }
 public String getAccountName() {
 return accountName;
 }
 public double getBalance() {
 return balance;
 }
 public String getSecurityNumber() {
 return securityNumber;
 }
 public String getPhoneNumber() {
 return phoneNumber;
 }
 public String getAddress() {
 return address;
 }
 public void deposit(double amount) {
 balance += amount;
 transactionHistory.add("Deposit: +" + amount);
 }
 public void setPhoneNumber(String phoneNumber) {
 this.phoneNumber = phoneNumber;
 }
 public void setAddress(String address) {
 this.address = address;
 }
 public void withdraw(double amount) {
 if (balance >= amount) {
 balance -= amount;
 transactionHistory.add("Withdrawal: -" + amount);
 } else {
 System.out.println("Insufficient funds!");
 }
 }
}
class BankAccountGUI extends Frame implements ActionListener {
 private BankAccount currentAccount;
 private List<BankAccount> accountList;
private ArrayList<String> transactionHistory;
 private TextField accountNameField, amountField, recipientField, securityNumberField, 
phoneNumberField, addressField;
 private TextArea outputArea;
 private TextField depositField;
 private TextField withdrawField,balanceField;
 public BankAccountGUI() {
 accountList = loadAccountList();
 currentAccount = null;
 Label accountNameLabel = new Label("Account Name:");
 Label amountLabel = new Label("Amount:");
 Label recipientLabel = new Label("Recipient:");
 Label securityNumberLabel = new Label("Security Number:");
 Label phoneNumberLabel = new Label("Phone Number:");
 Label addressLabel = new Label("Address:");
 accountNameField = new TextField(20);
 amountField = new TextField(20);
 recipientField = new TextField(20);
 securityNumberField = new TextField(20);
 phoneNumberField = new TextField(20);
 addressField = new TextField(20);
 Button createAccountButton = new Button("Create Account");
 Button depositButton = new Button("Deposit");
 Button withdrawButton = new Button("Withdraw");
 Button viewHistoryButton = new Button("View Transaction History");
 Button exitButton = new Button("Exit");
 Button resetButton = new Button("Reset");
 Button saveButton = new Button("Save Details");
 Button loadButton = new Button("Load Details");
 Button editButton = new Button("Edit");
 outputArea = new TextArea(20, 50); 
setLayout(new GridBagLayout());
GridBagConstraints gbc = new GridBagConstraints();
gbc.insets = new Insets(5, 5, 5, 5);
gbc.gridx = 0;
gbc.gridy = 0;
add(accountNameLabel, gbc);
gbc.gridx = 1;
gbc.gridy = 0;
add(accountNameField, gbc);
gbc.gridx = 3;
gbc.gridy = 0;
add(createAccountButton, gbc);
gbc.gridx = 0;
gbc.gridy = 1;
add(amountLabel, gbc);
gbc.gridx = 1;
gbc.gridy = 1;
add(amountField, gbc);
gbc.gridx = 2;
gbc.gridy = 1;
add(depositButton, gbc);
gbc.gridx = 3;
gbc.gridy = 1;
add(withdrawButton, gbc);
gbc.gridx = 0;
gbc.gridy = 2;
add(recipientLabel, gbc);
gbc.gridx = 1;
gbc.gridy = 2;
add(recipientField, gbc);
gbc.gridx = 3;
gbc.gridy = 2;
add(viewHistoryButton, gbc);
gbc.gridx = 4;
gbc.gridy = 2;
add(exitButton, gbc);
gbc.gridx = 0;
gbc.gridy = 3;
add(securityNumberLabel, gbc);
gbc.gridx = 1;
gbc.gridy = 3;
add(securityNumberField, gbc);
gbc.gridx = 0;
gbc.gridy = 4;
add(phoneNumberLabel, gbc);
gbc.gridx = 1;
gbc.gridy = 4;
add(phoneNumberField, gbc);
gbc.gridx = 0;
gbc.gridy = 5;
add(addressLabel, gbc);
gbc.gridx = 1;
gbc.gridy = 5;
add(addressField, gbc);
gbc.gridx = 0;
gbc.gridy = 6;
gbc.gridwidth = 5;
gbc.fill = GridBagConstraints.BOTH;
add(outputArea, gbc);
gbc.gridx = 4;
gbc.gridy = 3;
add(resetButton, gbc);
gbc.gridx = 4;
gbc.gridy = 4;
add(saveButton, gbc);
gbc.gridx = 4;
gbc.gridy = 5;
add(loadButton, gbc);
gbc.gridx = 3;
gbc.gridy = 5;
add(editButton, gbc); 
 createAccountButton.addActionListener(this);
 depositButton.addActionListener(this);
 withdrawButton.addActionListener(this);
 viewHistoryButton.addActionListener(this);
 exitButton.addActionListener(this);
 resetButton.addActionListener(this);
 saveButton.addActionListener(this);
 loadButton.addActionListener(this);
 editButton.addActionListener(this);
 setTitle("Bank Account Management System");
 setSize(600, 400);
 setVisible(true);
 }
 @Override
 public void actionPerformed(ActionEvent e) {
 String command = e.getActionCommand();
 switch (command) {
 case "Create Account":
 createAccount();
 break;
 case "Deposit":
 deposit();
 break;
 case "Withdraw":
 withdraw();
 break;
 case "View Transaction History":
 viewTransactionHistory();
 break;
 case "Reset":
 resetScreen();
 break;
 case "Save Details":
 saveDetails(getFilePath(currentAccount), currentAccount);
 break;
 case "Load Details":
 loadDetails();
 break;
 case "Edit":
 editDetails();
 break;
 case "Exit":
 System.exit(0);
 break;
 }
 }
private void loadDetails() {
 String accountName = accountNameField.getText();
 String filePath = "C:\\Users\\chris\\prog\\" + accountName + ".txt";
 try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
 String line;
 String loadedAccountName = null;
 String loadedSecurityNumber = null;
 String loadedPhoneNumber = null;
 String loadedAddress = null;
 double loadedBalance = 0.0;
 ArrayList<String> loadedTransactionHistory = new ArrayList<>();
while ((line = reader.readLine()) != null) {
 if (line.startsWith("Account Name:")) {
 loadedAccountName = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
 } else if (line.startsWith("Security Number:")) {
 loadedSecurityNumber = line.substring(line.indexOf("\"") + 1, 
line.lastIndexOf("\""));
 } else if (line.startsWith("Phone Number:")) {
 loadedPhoneNumber = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
 } else if (line.startsWith("Address:")) {
 loadedAddress = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
 } else if (line.startsWith("Balance:")) {
 String balanceStr = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
 loadedBalance = Double.parseDouble(balanceStr);
 } else if (line.startsWith("Transaction History:")) {
 // Read transaction history until the end of the file
 while ((line = reader.readLine()) != null) {
 if (line.isEmpty()) {
 break;
 }
 loadedTransactionHistory.add(line.substring(line.indexOf("\"") + 1, 
line.lastIndexOf("\"")));
 }
 }
 }
 if (loadedAccountName != null && loadedSecurityNumber != null && 
loadedPhoneNumber != null && loadedAddress != null) {
 currentAccount = new BankAccount(loadedAccountName, loadedSecurityNumber, 
loadedPhoneNumber, loadedAddress, loadedBalance);
 currentAccount.setTransactionHistory(loadedTransactionHistory);
 outputArea.setText("Details loaded successfully!\nAccount Name: " + 
currentAccount.getAccountName() +
 "\nSecurity Number: " + currentAccount.getSecurityNumber() +
 "\nPhone Number: " + currentAccount.getPhoneNumber() +
 "\nAddress: " + currentAccount.getAddress() +
 "\nBalance: Rs." + currentAccount.getBalance()+"\n");
 displayTransactionHistory();
 } else {
 outputArea.setText("Error loading details from file. Invalid file format.");
 }
 } catch (IOException | NumberFormatException ex) {
 ex.printStackTrace();
 outputArea.setText("Error loading details from file.");
 }
}
private void displayTransactionHistory() {
 // Display transaction history...
 ArrayList<String> history = currentAccount.getTransactionHistory();
 StringBuilder transactionHistoryText = new StringBuilder("Transaction History:\n");
 for (String transaction : history) {
 transactionHistoryText.append(transaction).append("\n");
 }
 outputArea.append("\n"+transactionHistoryText.toString());
}
private void editDetails() {
 if (currentAccount != null) {
 String accountName = accountNameField.getText();
 String filePath = "C:\\Users\\chris\\prog\\" + accountName + ".txt"; 
 try (FileWriter fileWriter = new FileWriter(filePath, true);
 PrintWriter writer = new PrintWriter(new BufferedWriter(fileWriter))) {
 String newPhoneNumber = phoneNumberField.getText();
 if (!newPhoneNumber.isEmpty() && isValidPhoneNumber(newPhoneNumber)) {
 currentAccount.setPhoneNumber(newPhoneNumber);
 writer.println("Phone Number: \"" + newPhoneNumber + "\"");
 } else {
 outputArea.setText("Please provide a valid 10-digit phone number.");
 return; // Exit the method if phone number is invalid
 }
 String newAddress = addressField.getText();
 if (!newAddress.isEmpty()) {
 currentAccount.setAddress(newAddress);
 writer.println("Address: \"" + newAddress + "\"");
 }
 String depositAmountStr = amountField.getText();
 if (!depositAmountStr.isEmpty()) {
 double depositAmount = Double.parseDouble(depositAmountStr);
 currentAccount.deposit(depositAmount);
 writer.println("Balance: \"" + currentAccount.getBalance() + "\"");}
 writer.println("Transaction History:");
 for (String transaction : currentAccount.getTransactionHistory()) {
 writer.println("\"" + transaction + "\"");
 }
 outputArea.setText("Details updated and saved successfully!");
 } catch (IOException | NumberFormatException ex) {
 ex.printStackTrace();
 outputArea.setText("Error updating or saving details.");
 }
 } else {
 outputArea.setText("Please create or select an account first.");
 }
}
private void saveDetails(String filePath, BankAccount account) {
 try (PrintWriter writer = new PrintWriter(filePath)) {
 writer.println("Account Name: \"" + account.getAccountName() + "\"");
 writer.println("Balance: \"" + account.getBalance() + "\"");
 writer.println("Security Number: \"" + account.getSecurityNumber() + "\"");
 writer.println("Phone Number: \"" + account.getPhoneNumber() + "\"");
 writer.println("Address: \"" + account.getAddress() + "\"");
 writer.println("Transaction History:");
 
 // Save transaction history to file
 for (String transaction : account.getTransactionHistory()) {
 writer.println("\"" + transaction + "\"");
 }
 outputArea.setText("Details updated and saved successfully!");
 } catch (IOException ex) {
 ex.printStackTrace();
 outputArea.setText("Error saving details to file.");
 }
}
private BankAccount getAccountByName(String accountName) {
 
 return currentAccount.getAccountName().equals(accountName) ? currentAccount : null;
}
 private List<BankAccount> loadAccountList() {
 // Load the account list from a file (deserialize)
 try (ObjectInputStream ois = new ObjectInputStream(new 
FileInputStream("accountList.ser"))) {
 return (List<BankAccount>) ois.readObject();
 } catch (FileNotFoundException e) {
 // If the file is not found, return a new empty list
 return new ArrayList<>();
 } catch (IOException | ClassNotFoundException e) {
 e.printStackTrace();
 return new ArrayList<>();
 }
 }
 private void saveAccountList() {
 // Save the account list to a file (serialize)
 try (ObjectOutputStream oos = new ObjectOutputStream(new 
FileOutputStream("accountList.ser"))) {
 oos.writeObject(accountList);
 } catch (IOException e) {
 e.printStackTrace();
 }
 }
 private boolean accountExists(String accountName) {
 for (BankAccount account : accountList) {
 if (account.getAccountName().equals(accountName)) {
 return true;
 }
 }
 return false;
 }
 private void createAccount() {
 // Your existing code...
 String accountName = accountNameField.getText();
 String securityNumber = securityNumberField.getText();
 String phoneNumber = phoneNumberField.getText();
 String address = addressField.getText(); 
 if (accountName.length() >= 5) {
 if (!securityNumber.isEmpty() && isValidPhoneNumber(phoneNumber) && 
!address.isEmpty()) {
 double initialBalance = 1000;
 currentAccount = new BankAccount(accountName, securityNumber, phoneNumber, 
address, initialBalance);
 accountList.add(currentAccount); // Add the new account to the list
 saveAccountList(); // Save the updated list to the file
 outputArea.setText("Account created successfully!\nAccount Name: " + 
currentAccount.getAccountName() +
 "\nSecurity Number: " + currentAccount.getSecurityNumber() +
 "\nPhone Number: " + currentAccount.getPhoneNumber() +
 "\nAddress: " + currentAccount.getAddress() +
 "\nBalance: Rs." + currentAccount.getBalance());
 } else {
 outputArea.setText("Please provide valid information...");
 }
 } else {
 outputArea.setText("Account name must have a minimum length of 5 characters.");
 }
 }
private boolean isValidPhoneNumber(String phoneNumber) {
 return phoneNumber.matches("\\d{10}");
}
private BankAccount loadAccountDetails(String accountName) {
 String filePath = getFilePath(accountName);
 try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
 String line;
 String loadedAccountName = null;
 String loadedSecurityNumber = null;
 String loadedPhoneNumber = null;
 String loadedAddress = null;
 double loadedBalance = 0.0;
 while ((line = reader.readLine()) != null) {
 if (line.startsWith("Account Name:")) {
 loadedAccountName = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
 } else if (line.startsWith("Security Number:")) {
 loadedSecurityNumber = line.substring(line.indexOf("\"") + 1, 
line.lastIndexOf("\""));
 } else if (line.startsWith("Phone Number:")) {
 loadedPhoneNumber = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
 } else if (line.startsWith("Address:")) {
 loadedAddress = line.substring(line.indexOf("\"") + 1, line.lastIndexOf("\""));
 } else if (line.startsWith("Balance:")) {
 loadedBalance = Double.parseDouble(line.substring(line.indexOf("\"") + 1, 
line.lastIndexOf("\"")));
 }
 }
 if (loadedAccountName != null && loadedSecurityNumber != null && 
loadedPhoneNumber != null && loadedAddress != null) {
 return new BankAccount(loadedAccountName, loadedSecurityNumber, 
loadedPhoneNumber, loadedAddress, loadedBalance);
 } else {
 return null; // Invalid file format
 }
 } catch (IOException | NumberFormatException ex) {
 ex.printStackTrace();
 return null; // Error loading details from file
 }
}
// Helper method to get the file path for an account
private String getFilePath(String accountName) {
 return "C:\\Users\\chris\\prog\\" + accountName + ".txt";
}
private void resetScreen() {
 accountNameField.setText("");
 amountField.setText("");
 recipientField.setText("");
 securityNumberField.setText("");
 phoneNumberField.setText("");
 addressField.setText("");
 outputArea.setText("");
 
}
private String getFilePath(BankAccount account) {
 return "C:\\Users\\chris\\prog\\" + account.getAccountName() + ".txt";
}
private void deposit() {
 if (currentAccount != null) {
 double amount = Double.parseDouble(amountField.getText());
 if (amount >= 100) {
 currentAccount.deposit(amount);
 // Update details in the file
 saveDetails(getFilePath(currentAccount), currentAccount);
 outputArea.setText("Deposit successful!\nNew Balance: Rs." + 
currentAccount.getBalance());
 } else {
 outputArea.setText("Deposit amount must be at least Rs.100.");
 }
 } else {
 outputArea.setText("Please create or select an account first.");
 }
}
private void withdraw() {
 if (currentAccount != null) {
 double amount = Double.parseDouble(amountField.getText());
 if (amount >= 100) {
currentAccount.withdraw(amount);
saveDetails(getFilePath(currentAccount), currentAccount);
 outputArea.setText("Withdrawal successful!\nNew Balance: Rs." + 
currentAccount.getBalance());
 } else {
 outputArea.setText("Withdrawal amount must be at least Rs.100.");
 }
 } else {
 outputArea.setText("Please create or select an account first.");
 }
}
private void viewTransactionHistory() {
 if (currentAccount != null) {
 // Display transaction history...
 ArrayList<String> history = currentAccount.getTransactionHistory();
 StringBuilder transactionHistoryText = new StringBuilder("Transaction History:\n");
 for (String transaction : history) {
 transactionHistoryText.append(transaction).append("\n");
 }
 outputArea.setText(transactionHistoryText.toString());
 } else {
 outputArea.setText("Please create or select an account first.");
 }
}}public class BankAccountManagementSystem {
 public static void main(String[] args) {
 new BankAccountGUI();
 List<BankAccount> accountList = new ArrayList<>();
 }
}