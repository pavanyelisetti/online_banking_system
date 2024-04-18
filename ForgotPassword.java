import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotPassword {
    private String username;
    private String favoritePersonName;
    private String favoriteColor;

    public ForgotPassword() {
        JFrame frame = new JFrame("Forgot Password");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(450, 200);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));

        JLabel usernameLabel = new JLabel("Enter your username:");
        JTextField usernameField = new JTextField();
        JLabel nameLabel = new JLabel("Enter your favorite person's name:");
        JTextField nameField = new JTextField();
        JLabel colorLabel = new JLabel("Enter your favorite color:");
        JTextField colorField = new JTextField();
        JButton validateButton = new JButton("Validate");
        JButton updatePasswordButton = new JButton("Update Password");

        nameField.setEnabled(false);
        colorField.setEnabled(false);
        validateButton.setEnabled(false);
        updatePasswordButton.setEnabled(false);

        usernameField.addActionListener(e -> {
            username = usernameField.getText();
            if (verifyUsername(username)) {
                nameField.setEnabled(true);
                colorField.setEnabled(true);
                validateButton.setEnabled(true);
                usernameField.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username. Please try again.");
            }
        });

        validateButton.addActionListener(e -> {
            favoritePersonName = nameField.getText();
            favoriteColor = colorField.getText();
            if (validateAnswers(username, favoritePersonName, favoriteColor)) {
                // Display a message when validation is successful
                JOptionPane.showMessageDialog(frame, "Validation successful. You can now update your password.");
                updatePasswordButton.setEnabled(true);
                nameField.setEnabled(false);
                colorField.setEnabled(false);
                validateButton.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(frame, "Validation failed. Please check your answers.");
            }
        });

        updatePasswordButton.addActionListener(e -> {
            String newPassword = JOptionPane.showInputDialog(frame, "Enter new password:");
            String confirmPassword = JOptionPane.showInputDialog(frame, "Confirm new password:");

            if (newPassword != null && newPassword.equals(confirmPassword)) {
                if (updatePassword(username, newPassword)) {
                    JOptionPane.showMessageDialog(frame, "Password updated successfully.");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to update password. Please try again.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Password and Confirm Password do not match.");
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(colorLabel);
        panel.add(colorField);
        panel.add(validateButton);
        panel.add(updatePasswordButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    private boolean verifyUsername(String username) {
        // Implement the logic to verify the username from the database
        Connection dbConnection = null;
        boolean usernameExists = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/bank";
            String dbUsername = "root";
            String dbPassword = "Sriram@1234";
            dbConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String query = "SELECT username FROM data WHERE username = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            usernameExists = resultSet.next();
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

        return usernameExists;
    }

    private boolean validateAnswers(String username, String favoritePersonName, String favoriteColor) {
        // Implement the logic to validate answers from the database
        Connection dbConnection = null;
        boolean answersMatch = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/bank";
            String dbUsername = "root";
            String dbPassword = "Sriram@1234";
            dbConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String query = "SELECT username FROM data WHERE username = ? AND new_info1 = ? AND new_info2 = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, favoritePersonName);
            preparedStatement.setString(3, favoriteColor);
            ResultSet resultSet = preparedStatement.executeQuery();

            answersMatch = resultSet.next();
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

        return answersMatch;
    }

    private boolean updatePassword(String username, String newPassword) {
        // Implement the logic to update the password in the database
        Connection dbConnection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/bank";
            String dbUsername = "root";
            String dbPassword = "Sriram@1234";
            dbConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String query = "UPDATE data SET password = ? WHERE username = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, username);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
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

        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ForgotPassword());
    }
}
