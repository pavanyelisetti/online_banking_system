import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MiniStatement {
    public MiniStatement(String username) {
        JFrame frame = new JFrame("Mini Statement");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        frame.add(panel);

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();

        // Set column names
        model.addColumn("Date/Time");
        model.addColumn("Amount (Debit)");
        model.addColumn("Amount (Credit)");
        model.addColumn("Balance");

        // Get account number for the username
        String accountNumber = getAccountNumberForUsername(username);

        if (accountNumber != null) {
            // Retrieve the last 10 transactions
            retrieveMiniStatement(accountNumber, model);
        } else {
            JOptionPane.showMessageDialog(frame, "Account number not found for the username.");
        }

        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MiniStatement(""));
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
    private static void retrieveMiniStatement(String accountNumber, DefaultTableModel model) {
        Connection dbConnection = null;

        try {
            // Establish a database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/bank";
            String dbUsername = "root";
            String dbPassword = "Sriram@1234";
            dbConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            // Retrieve the last 10 transactions (both debit and credit)
            String query = "SELECT date_time, amount AS debit_amount, NULL AS credit_amount, balance FROM debitdetails WHERE account_number = ? " +
                    "UNION ALL " +
                    "SELECT date_time, NULL AS debit_amount, amount AS credit_amount, balance FROM creditdetails WHERE account_number = ? " +
                    "ORDER BY date_time DESC LIMIT 10";

            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, accountNumber);
            preparedStatement.setString(2, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String dateTime = resultSet.getString("date_time");
                double debitAmount = resultSet.getDouble("debit_amount");
                double creditAmount = resultSet.getDouble("credit_amount");
                double balance = resultSet.getDouble("balance");

                model.addRow(new Object[]{dateTime, debitAmount, creditAmount, balance});
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
