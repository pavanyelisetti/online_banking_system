import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankAccountBalance {
    public BankAccountBalance(String username) {

        String balance = getAccountBalance(username);
        if (balance != null) {
            UIManager.put("OptionPane.messageFont", new Font("Times New Roman", Font.BOLD, 25));
            JOptionPane.showMessageDialog(null, balance, "Bank Account Balance", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Account not found.", "Bank Account Balance", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getAccountBalance(String username) {
        Connection dbConnection = null;
        String accountNumber = getAccountNumber(username);
        String balance = null;

        if (accountNumber != null) {
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
                    balance = "Account Number: " + accountNumber + "\n" +
                            "Account Balance: " + resultSet.getDouble("accountbalance");
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
        }

        return balance;
    }

    public String getAccountNumber(String username) {
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

    public static void main(String[] args) {
        // Example usage:
        new BankAccountBalance("");
    }
}
