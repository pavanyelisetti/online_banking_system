import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MPinGenerator extends JFrame {
    private static Connection dbConnection = null;
    private String username;

    public MPinGenerator(String username) {
        this.username = username;
        setTitle("M-PIN Generator");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JButton createMPINButton = new JButton("Create M-PIN");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(createMPINButton, constraints);

        JButton resetMPINButton = new JButton("Reset M-PIN");
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(resetMPINButton, constraints);

        add(panel);
        setVisible(true);

        createMPINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initializeDBConnection();
                if (isMPINNull()) {
                    String newMPIN = requestNewMPIN();
                    if (newMPIN != null && newMPIN.matches("\\d{6}")) {
                        insertMPIN(newMPIN);
                        JOptionPane.showMessageDialog(null, "New M-PIN created: " + newMPIN);
                    } else if (newMPIN != null) {
                        JOptionPane.showMessageDialog(null, "M-PIN should be a 6-digit number.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "M-PIN already created.");
                }
            }
        });

        resetMPINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initializeDBConnection();
                if (isMPINNull()) {
                    JOptionPane.showMessageDialog(null, "M-PIN is not created. Please create an M-PIN first.");
                } else {
                    if (validateSecurityQuestions(username)) {
                        resetMPIN();
                    } else {
                        JOptionPane.showMessageDialog(null, "Security questions validation failed.");
                    }
                }
            }
        });
    }

    private void initializeDBConnection() {
        if (dbConnection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String jdbcUrl = "jdbc:mysql://localhost:3306/bank";
                String dbUsername = "root";
                String dbPassword = "Sriram@1234";
                dbConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);
            } catch (ClassNotFoundException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Connection to the database failed. Check your database settings.");
            }
        }
    }

    private boolean isMPINNull() {
        try {
            String query = "SELECT m_pin FROM data WHERE username = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String existingMPIN = resultSet.getString("m_pin");
                 if(existingMPIN == null)
                 {
                    return true;
                 }
                 return false;
            }
            else{
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error while checking M-PIN: " + ex.getMessage());
        }
        return false;
    }

    private String requestNewMPIN() {
        return JOptionPane.showInputDialog(this, "Enter your new 6-digit M-PIN:");
    }

    private void insertMPIN(String mPin) {
        try {
            String query = "UPDATE data SET m_pin = ? WHERE username = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, mPin);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error while inserting M-PIN: " + ex.getMessage());
        }
    }

    private void resetMPIN() {
        try {
            String newpin = JOptionPane.showInputDialog(this, "Enter new M-PIN :");
            String query = "UPDATE data SET m_pin = ? WHERE username = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, newpin);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "M-PIN reset successful.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error while resetting M-PIN: " + ex.getMessage());
        }
    }

    private boolean validateSecurityQuestions(String username) {
        String accountNumber = JOptionPane.showInputDialog(this, "Enter your account number:");
        String favoritePersonName = JOptionPane.showInputDialog(this, "Enter your favorite person's name:");
        String favoriteColor = JOptionPane.showInputDialog(this, "Enter your favorite color:");

        try {
            String query = "SELECT * FROM data WHERE username = ? AND account_number = ? AND new_info1 = ? AND new_info2 = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, accountNumber);
            preparedStatement.setString(3, favoritePersonName);
            preparedStatement.setString(4, favoriteColor);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error while validating security questions: " + ex.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MPinGenerator(""));
    }
}
