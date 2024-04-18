import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoneyTransfer {
    public MoneyTransfer(String username) {
        String fromAccount = AccountNumber(username);

        if (isValidAccount(fromAccount)) {
            String toAccount = JOptionPane.showInputDialog("Enter the recipient's account number:");
            
            if (isValidAccount(toAccount)) {
                // Perform the money transfer
                if (fromAccount.equals(toAccount)) {
                    JOptionPane.showMessageDialog(null, "Cannot transfer money to your own account.", "Money Transfer Error", JOptionPane.ERROR_MESSAGE);
                } else {
                String amountStr = JOptionPane.showInputDialog("Enter the amount to transfer:");
                String enteredMPIN = JOptionPane.showInputDialog("Enter your 6-digit M-PIN:");
                
                double currentBalance = getCurrentBalance(fromAccount);
                try {
                    double amount = Double.parseDouble(amountStr);
                    
                    if (isSufficientBalance(currentBalance, amount)) {
                        if (enteredMPIN != null && enteredMPIN.matches("\\d{6}")) {
                            if (isMPINValid(username, enteredMPIN)) {
                        boolean success = transferMoney(fromAccount, toAccount, amount, currentBalance);

                        if (success) {
                            JOptionPane.showMessageDialog(null, "Money transfer successful.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Money transfer failed.", "Money Transfer Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } 
                    
                else {
                    JOptionPane.showMessageDialog(null, "Invalid M-PIN. Money transfer canceled.", "Money Transfer Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid M-PIN format. Money transfer canceled.", "Money Transfer Error", JOptionPane.ERROR_MESSAGE);
            }
        }
                    else {
                        JOptionPane.showMessageDialog(null, "Insufficient balance.", "Money Transfer Error", JOptionPane.ERROR_MESSAGE);
                    }
                
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Invalid amount format.", "Money Transfer Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
             else {
                JOptionPane.showMessageDialog(null, "Invalid recipient's account number.", "Money Transfer Error", JOptionPane.ERROR_MESSAGE);
            }
        
            
        } else {
            JOptionPane.showMessageDialog(null, "Invalid account number.", "Money Transfer Error", JOptionPane.ERROR_MESSAGE);
        }
    
    }

    

    public String AccountNumber(String username) {
        Connection dbConnection = null;
        String accountNumber = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/bank";
            String dbUsername = "root";
            String dbPassword = "Sriram@1234";
            dbConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String query = "SELECT account_number FROM data WHERE username = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                accountNumber = resultSet.getString("account_number");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       
        return accountNumber;

    }
    private boolean isValidAccount(String accountNumber) {
        Connection dbConnection = null;
        boolean isValid = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/bank";
            String dbUsername = "root";
            String dbPassword = "Sriram@1234";
            dbConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String query = "SELECT account_number FROM bankaccountnumber WHERE account_number = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            isValid = resultSet.next();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isValid;
    }
    private double getCurrentBalance(String accountNumber) {
        Connection dbConnection = null;
        double currentBalance = 0.0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/bank";
            String dbUsername = "root";
            String dbPassword = "Sriram@1234";
            dbConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String query = "SELECT accountbalance FROM bankaccountnumber WHERE account_number = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                currentBalance = resultSet.getDouble("accountbalance");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return currentBalance;
    }
    private boolean isSufficientBalance(double currentBalance, double transferAmount) {
        
        return currentBalance >= transferAmount;
    }
    private boolean isMPINValid(String username, String enteredMPIN) {
        Connection dbConnection = null;
        boolean isMPINCorrect = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/bank";
            String dbUsername = "root";
            String dbPassword = "Sriram@1234";
            dbConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String query = "SELECT m_pin FROM data WHERE username = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedMPIN = resultSet.getString("m_pin");
                if (enteredMPIN.equals(storedMPIN)) {
                    isMPINCorrect = true;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isMPINCorrect;
    }

    private boolean transferMoney(String fromAccount, String toAccount, double amount,double currentBalance) {
        Connection dbConnection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/bank";
            String dbUsername = "root";
            String dbPassword = "Sriram@1234";
            dbConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            // Deduct from sender's account
            String debitQuery = "UPDATE bankaccountnumber SET accountbalance = accountbalance - ? WHERE account_number = ?";
            PreparedStatement debitStatement = dbConnection.prepareStatement(debitQuery);
            debitStatement.setDouble(1, amount);
            debitStatement.setString(2, fromAccount);
            int debitRows = debitStatement.executeUpdate();

            // Credit to recipient's account
            String creditQuery = "UPDATE bankaccountnumber SET accountbalance = accountbalance + ? WHERE account_number = ?";
            PreparedStatement creditStatement = dbConnection.prepareStatement(creditQuery);
            creditStatement.setDouble(1, amount);
            creditStatement.setString(2, toAccount);
            int creditRows = creditStatement.executeUpdate();
            String debitStatus, creditStatus;
            if (debitRows == 1) {
                debitStatus = "Success";
            } else {
                debitStatus = "Failed";
            }
    
            double senderNewBalance = currentBalance - amount;
            double recipientNewBalance = getCurrentBalance(toAccount) + amount;
            // Insert transaction record into transactiondetails table
            String transactionQuery = "INSERT INTO debitdetails (account_number, debited_into, amount,  date_time, status,balance) VALUES (?, ?, ?, CURTIME(), ?,?)";
                PreparedStatement transactionStatement = dbConnection.prepareStatement(transactionQuery);
                transactionStatement.setString(1, fromAccount);
                transactionStatement.setString(2, toAccount);
                transactionStatement.setDouble(3, amount);
                transactionStatement.setString(4, debitStatus);
                
                transactionStatement.setDouble(5, senderNewBalance);
                int transactionRows = transactionStatement.executeUpdate();
                
            String creditTransactionQuery = "INSERT INTO creditdetails (account_number, credited_from, amount, date_time,balance) VALUES (?, ?, ?, CURTIME(),?)";
             PreparedStatement creditTransactionStatement = dbConnection.prepareStatement(creditTransactionQuery);
             creditTransactionStatement.setString(1, toAccount);
             creditTransactionStatement.setString(2, fromAccount);
             creditTransactionStatement.setDouble(3, amount);
              
              creditTransactionStatement.setDouble(4, recipientNewBalance);
             int creditTransactionRows = creditTransactionStatement.executeUpdate();
                

             return (debitRows == 1)&& (transactionRows == 1) && (creditTransactionRows == 1);
            
           
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (dbConnection != null) {
                    dbConnection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new MoneyTransfer("");
    }
}
