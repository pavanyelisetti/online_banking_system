import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Userdetails {
    private String username;
    private JTextField accountNumberTextField;
    private JTextField nameTextField;
    private JTextField mobileNumberTextField;
    private JTextField addressTextField;

    public Userdetails(String username) {
        this.username = username;

        JFrame userDetailsFrame = new JFrame("User Details");
        userDetailsFrame.setSize(400, 250);
        userDetailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        userDetailsFrame.setLayout(new GridLayout(5, 2, 5, 5));

        JLabel accountNumberLabel = new JLabel("Account Number:");
        JLabel nameLabel = new JLabel("Name:");
        JLabel mobileLabel = new JLabel("Mobile Number:");
        JLabel addressLabel = new JLabel("Address:");

        accountNumberTextField = new JTextField();
        nameTextField = new JTextField();
        mobileNumberTextField = new JTextField();
        addressTextField = new JTextField();

        accountNumberTextField.setEditable(false);
        nameTextField.setEditable(false);
        mobileNumberTextField.setEditable(false);
        addressTextField.setEditable(false);

        userDetailsFrame.add(accountNumberLabel);
        userDetailsFrame.add(accountNumberTextField);
        userDetailsFrame.add(nameLabel);
        userDetailsFrame.add(nameTextField);
        userDetailsFrame.add(mobileLabel);
        userDetailsFrame.add(mobileNumberTextField);
        userDetailsFrame.add(addressLabel);
        userDetailsFrame.add(addressTextField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        JButton editButton = new JButton("Edit");
        JButton saveButton = new JButton("Save");
        buttonPanel.add(Box.createHorizontalGlue()); // Add glue to center-align buttons
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalGlue()); // Add glue to center-align buttons

        userDetailsFrame.add(buttonPanel);

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableTextFields(true);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableTextFields(false);
                saveUserDetails();
            }
        });

        displayUserDetailsFromDatabase(username);

        userDetailsFrame.setLocationRelativeTo(null);
        userDetailsFrame.setVisible(true);
    }

    private void displayUserDetailsFromDatabase(String username) {
        // Implement the logic to display user details from the database
        Connection dbConnection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/bank";
            String dbUsername = "root";
            String dbPassword = "Sriram@1234";
            dbConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String query = "SELECT account_number, name, mobilenumber, address FROM data WHERE username = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                accountNumberTextField.setText(resultSet.getString("account_number"));
                nameTextField.setText(resultSet.getString("name"));
                mobileNumberTextField.setText(resultSet.getString("mobilenumber"));
                addressTextField.setText(resultSet.getString("address"));
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

    private void enableTextFields(boolean enable) {
        mobileNumberTextField.setEditable(enable);
        addressTextField.setEditable(enable);
    }

    private void saveUserDetails() {
        // Implement the logic to save user details to the database
        Connection dbConnection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/bank";
            String dbUsername = "root";
            String dbPassword = "Sriram@1234";
            dbConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String query = "UPDATE data SET mobilenumber = ?, address = ? WHERE username = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, mobileNumberTextField.getText());
            preparedStatement.setString(2, addressTextField.getText());
            preparedStatement.setString(3, username);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "User details saved successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to save user details.");
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Userdetails(""));
    }
}
