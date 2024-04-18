import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DebitDetails {
    String username;
    public DebitDetails(String username) {
        JFrame frame = new JFrame("Debit Details");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        frame.add(panel);

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();

        // Set column names
        model.addColumn("Account Number");
        model.addColumn("Debited Into");
        model.addColumn("Amount");
        model.addColumn("Date/Time");
        model.addColumn("Status");
        model.addColumn("Balance");

        // Get account number for the username (you can modify this part)
        
        this.username=username;
        String accountNumber = getAccountNumberForUsername(username);

        if (accountNumber != null) {
            // Retrieve debit details using the obtained account number
            retrieveDebitDetails(accountNumber, model);
        } else {
            JOptionPane.showMessageDialog(frame, "Account number not found for the username.");
        }
        
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DebitDetails(""));
    }

    private static String getAccountNumberForUsername(String username) {
        
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

    private static void retrieveDebitDetails(String accountNumber, DefaultTableModel model) {
        Connection dbConnection = null;

        try {
            // Establish a database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/bank";
            String dbUsername = "root";
            String dbPassword = "Sriram@1234";
            dbConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            // Retrieve debit details for the specified account number
            String query = "SELECT account_number, debited_into, amount, date_time, status,balance FROM debitdetails WHERE account_number = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String accountNum = resultSet.getString("account_number");
                String debitedFrom = resultSet.getString("debited_into");
                String amount = resultSet.getString("amount");
                String dateTime = resultSet.getString("date_time");
                String status = resultSet.getString("status");
                String balance = resultSet.getString("balance");
                model.addRow(new Object[]{accountNum, debitedFrom, amount, dateTime, status,balance});
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
}
