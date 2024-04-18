import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Bank{
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
        //The SwingUtilities.invokeLater method is a utility method in Swing that allows you to schedule a task to be executed on the EDT. 
        //This is important because Swing is not thread-safe, and making Swing calls on threads other than the EDT can lead to unexpected behavior and
        // graphical glitches.
    }

    private static Connection dbConnection = null;

    public static void createAndShowGUI() {

        JFrame frame = new JFrame("Online Banking System - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 350);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.cyan);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 7);

        JLabel usernameLabel = new JLabel("Username:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(usernameLabel, constraints);

        JTextField usernameField = new JTextField(20);
        constraints.gridx = 1;
        panel.add(usernameField, constraints);

        JLabel passwordLabel = new JLabel("Password:");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(passwordLabel, constraints);

        JPasswordField passwordField = new JPasswordField(20);
        constraints.gridx = 1;
        panel.add(passwordField, constraints);

        JLabel forgotPasswordLabel = new JLabel("Forgot Password?");
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(forgotPasswordLabel, constraints);
        forgotPasswordLabel.setForeground(Color.BLUE); // Change label color to indicate it's clickable
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand

        JButton loginButton = new JButton("Login");
        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(loginButton, constraints);

        JButton createAccountButton = new JButton("Create Account");
        constraints.gridx = 1;
        constraints.gridy = 9;
        panel.add(createAccountButton, constraints);

        // Create the new account window
        JFrame newAccountFrame = new JFrame("Create New Account");
        JPanel newAccountPanel = new JPanel(new GridBagLayout());
        newAccountPanel.setBackground(Color.PINK);

        JLabel newAccountNumberLabel = new JLabel("Account Number");
        constraints.gridx = 0;
        constraints.gridy = 0;
        newAccountPanel.add(newAccountNumberLabel, constraints);

        JTextField newAccountNumberField = new JTextField(20);
        constraints.gridx = 1;
        newAccountPanel.add(newAccountNumberField, constraints);

        JLabel newnameLabel = new JLabel("Name");
        constraints.gridx = 0;
        constraints.gridy = 1;
        newAccountPanel.add(newnameLabel, constraints);

        JTextField newnameField = new JTextField(20);
        constraints.gridx = 1;
        newAccountPanel.add(newnameField, constraints);

        JLabel newmobileLabel = new JLabel("Mobile Number");
        constraints.gridx = 0;
        constraints.gridy = 2;
        newAccountPanel.add(newmobileLabel, constraints);

        JTextField newmobileField = new JTextField(20);
        constraints.gridx = 1;
        newAccountPanel.add(newmobileField, constraints);

        JLabel newaddressLabel = new JLabel("Address");
        constraints.gridx = 0;
        constraints.gridy = 3;
        newAccountPanel.add(newaddressLabel, constraints);

        JTextField newaddressField = new JTextField(20);
        constraints.gridx = 1;
        newAccountPanel.add(newaddressField, constraints);


        JButton verifyAccountButton = new JButton("Verify");
        constraints.gridx = 1;
        constraints.gridy = 5;
        newAccountPanel.add(verifyAccountButton, constraints);

        JLabel newUsernameLabel = new JLabel("New Username");
        constraints.gridx = 0;
        constraints.gridy = 6;
        newAccountPanel.add(newUsernameLabel, constraints);

        JTextField newUsernameField = new JTextField(20);
        constraints.gridx = 1;
        newAccountPanel.add(newUsernameField, constraints);

        JLabel newPasswordLabel = new JLabel("New Password");
        constraints.gridx = 0;
        constraints.gridy = 7;
        newAccountPanel.add(newPasswordLabel, constraints);

        JPasswordField newPasswordField = new JPasswordField(20);
        constraints.gridx = 1;
        newAccountPanel.add(newPasswordField, constraints);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        constraints.gridx = 0;
        constraints.gridy = 8;
        newAccountPanel.add(confirmPasswordLabel, constraints);

        JPasswordField confirmPasswordField = new JPasswordField(20);
        constraints.gridx = 1;
        newAccountPanel.add(confirmPasswordField, constraints);

        JButton createNewAccountButton = new JButton("Create");
        constraints.gridx = 1;
        constraints.gridy = 9;
        newAccountPanel.add(createNewAccountButton, constraints);
        newUsernameField.setEnabled(false);
        newPasswordField.setEnabled(false);
        confirmPasswordField.setEnabled(false);
        createNewAccountButton.setEnabled(false);

        // Set the font for login components to match Userdetails
        Font loginFont = new Font("serif", Font.BOLD, 15);

        // Set the font for the components in Bank.java
        usernameLabel.setFont(loginFont);
        passwordLabel.setFont(loginFont);
        forgotPasswordLabel.setFont(loginFont);
        usernameField.setFont(loginFont);
        passwordField.setFont(loginFont);
        loginButton.setFont(loginFont);
        createAccountButton.setFont(loginFont);

        // Set the font for the components in the new account window
        newAccountNumberLabel.setFont(loginFont);
        newnameLabel.setFont(loginFont);
        newmobileLabel.setFont(loginFont);
        newaddressLabel.setFont(loginFont);
        verifyAccountButton.setFont(loginFont);
        newUsernameLabel.setFont(loginFont);
        newPasswordLabel.setFont(loginFont);
        confirmPasswordLabel.setFont(loginFont);
        createNewAccountButton.setFont(loginFont);
        
            
        forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ForgotPassword();
            }
        });

        verifyAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountNumber = newAccountNumberField.getText();
                if(!newverifyAccountNumber(accountNumber))
                {
                if (verifyAccountNumber(accountNumber)) {
                    newUsernameField.setEnabled(true);
                    newPasswordField.setEnabled(true);
                    confirmPasswordField.setEnabled(true);
                    createNewAccountButton.setEnabled(true);

                } else {
                    JOptionPane.showMessageDialog(newAccountFrame, "Account number not found. Please check your account number.");
                }
            }
            else{
                JOptionPane.showMessageDialog(newAccountFrame, "Account already Created please Login to your account.");
                newAccountNumberField.setText("");
                    newnameField.setText("");
                    newmobileField.setText("");
                    newaddressField.setText("");
            }
            }
        });

        newAccountFrame.add(newAccountPanel);
        newAccountFrame.setSize(500, 500);
        newAccountFrame.setLocationRelativeTo(frame);
        

            loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Implement login logic here
                // You should check the username and password against your user database.
                // For this example, let's assume the username is "user" and the password is "password".
                if (authenticateUser(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    frame.setVisible(false);
                    new Dashboard(username);
                } else {
                    JOptionPane.showMessageDialog(frame, "Login failed. Please check your credentials.");
                }
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
           
            public void actionPerformed(ActionEvent e) {
                newAccountFrame.setVisible(true);

            }
        });
        createNewAccountButton.addActionListener(new ActionListener() {
            @Override
           
            public void actionPerformed(ActionEvent e) {
                
                // Implement account creation logic here
                
                String accountNumber = newAccountNumberField.getText();
                String name = newnameField.getText();
                String mobileNumber = newmobileField.getText();
                String address = newaddressField.getText();
                String newUsername = newUsernameField.getText();
                String newPassword = new String(newPasswordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                if (newUsername.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(newAccountFrame, "Please fill in all fields.");
                } else if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(newAccountFrame, "Password and Confirm Password do not match.");
                } else {
                    // Save the new account information in your database.
                    // For this example, we'll just show a message.
                    String favoritePersonName = JOptionPane.showInputDialog(newAccountFrame, "Enter your favorite person's name:");
                    String favoriteColor = JOptionPane.showInputDialog(newAccountFrame, "Enter your favorite color:");
                    if(favoritePersonName.isEmpty() || favoriteColor.isEmpty())
                    {
                        JOptionPane.showMessageDialog(newAccountFrame, "Account not created please try agein");
                    }
                    else
                    {
                    if (insertData(accountNumber, name, mobileNumber, address, newUsername, newPassword, favoritePersonName, favoriteColor)) {
                        JOptionPane.showMessageDialog(newAccountFrame, "Account created successfully!");
                    } else {
                        JOptionPane.showMessageDialog(newAccountFrame, "Failed to create the account. Please try again.");
                    }
                }

                    // Clear the input fields
                    newAccountNumberField.setText("");
                    newnameField.setText("");
                    newmobileField.setText("");
                    newaddressField.setText("");
                    newUsernameField.setText("");
                    newPasswordField.setText("");
                    confirmPasswordField.setText("");
                }
            }
        });
        frame.add(panel);
        frame.setVisible(true);
    }
    
 
    private static boolean verifyAccountNumber(String accountNumber) {
        if (dbConnection == null) {
            try {
                // Load the JDBC driver (you need to have the MySQL JDBC driver JAR in your classpath)
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish the database connection
                String jdbcUrl = "jdbc:mysql://localhost:3306/bank";
                String dbUsername = "root";
                String dbPassword = "Sriram@1234";
                dbConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Connection to the database failed. Check your database settings.");
                return false;
            }
        }

        try {
            // Check if the account number exists in the 'bankaccountnumber' table
           

            String query = "SELECT * FROM bankaccountnumber WHERE account_number = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();


            // If a record is found, the account number is verified
            return resultSet.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error while verifying account number: " + e.getMessage());
            return false;
        }
    }
     private static boolean newverifyAccountNumber(String accountNumber) {
        if (dbConnection == null) {
            try {
                // Load the JDBC driver (you need to have the MySQL JDBC driver JAR in your classpath)
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish the database connection
                String jdbcUrl = "jdbc:mysql://localhost:3306/bank";
                String dbUsername = "root";
                String dbPassword = "Sriram@1234";
                dbConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Connection to the database failed. Check your database settings.");
                return false;
            }
        }

        try {
            // Check if the account number exists in the 'bankaccountnumber' table
            String query1 = "SELECT * FROM data  WHERE account_number = ?";
            PreparedStatement preparedStatement1 = dbConnection.prepareStatement(query1);
            preparedStatement1.setString(1, accountNumber);
            ResultSet resultSet1 = preparedStatement1.executeQuery();

            // If a record is found, the account number is verified
            return resultSet1.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error while verifying account number: " + e.getMessage());
            return false;
        }
    }

    private static boolean authenticateUser(String username, String password) {
        // Implement your user authentication logic here
        // You should check the username and password against your user database table.
        // For this example, let's assume you have a 'users' table with 'username' and 'password' columns.
        // Replace this query with your actual table and columns.
        if (dbConnection == null) {
              try {
                // Load the JDBC driver (you need to have the MySQL JDBC driver JAR in your classpath)
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish the database connection
                String jdbcUrl = "jdbc:mysql://localhost:3306/bank";
                String dbUsername = "root";
                String dbPassword = "Sriram@1234";
                dbConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Connection to the database failed. Check your database settings.");
                return false;
            }
            
        }

        try {
            String query = "SELECT * FROM data WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            // If a record is found, the login is successful
            return resultSet.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error while authenticating: " + e.getMessage());
            return false;
        }
    }
    private static boolean insertData(String accountNumber, String name, String mobileNumber, String address, String username, String password, String favoritePersonName, String favoriteColor) {
        if (dbConnection == null) {
            JOptionPane.showMessageDialog(null, "No database connection. Please check your settings.");
            return false;
        }
    
        try {
            String query = "INSERT INTO data (account_number, name, mobilenumber, address, username, password, new_info1, new_info2) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, accountNumber);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, mobileNumber);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, username);
            preparedStatement.setString(6, password);
            preparedStatement.setString(7, favoritePersonName);
            preparedStatement.setString(8, favoriteColor);
    
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error while inserting data: " + e.getMessage());
            return false;
        }
    }
}
