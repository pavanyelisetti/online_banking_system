import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ATMApplicationForm {
    private JFrame frame;
    private JTextField accountNumberField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dateOfBirthField;
    private JRadioButton maleRadioButton;
    private JRadioButton femaleRadioButton;
    private JRadioButton othersRadioButton;
    private JTextField mobileNumberField; // New field for mobile number
    private JTextField occupationField;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField religionField; // New field for religion
    private JTextField casteField; // New field for caste

    public ATMApplicationForm(String username) {
        frame = new JFrame("ATM Card Application Form");
        frame.setSize(400, 450); // Increased the form height
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());

        Font timesNewRoman = new Font("Times New Roman", Font.PLAIN, 16); // Font size

        // Set a sky blue background color
        formPanel.setBackground(new Color(135, 206, 235)); // Sky Blue background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        addLabelAndField(formPanel, "Account Number:", accountNumberField = new JTextField(20), gbc, timesNewRoman);
        addLabelAndField(formPanel, "First Name:", firstNameField = new JTextField(20), gbc, timesNewRoman);
        addLabelAndField(formPanel, "Last Name:", lastNameField = new JTextField(20), gbc, timesNewRoman);
        addLabelAndField(formPanel, "Date of Birth:", dateOfBirthField = new JTextField(20), gbc, timesNewRoman);
        addLabelAndField(formPanel, "Gender:", createGenderPanel(), gbc, timesNewRoman);
        addLabelAndField(formPanel, "Mobile Number:", mobileNumberField = new JTextField(20), gbc, timesNewRoman);
        addLabelAndField(formPanel, "Occupation:", occupationField = new JTextField(20), gbc, timesNewRoman);
        addLabelAndField(formPanel, "Email:", emailField = new JTextField(20), gbc, timesNewRoman);
        addLabelAndField(formPanel, "Address:", addressField = new JTextField(20), gbc, timesNewRoman);
        addLabelAndField(formPanel, "Religion:", religionField = new JTextField(20), gbc, timesNewRoman);
        addLabelAndField(formPanel, "Caste:", casteField = new JTextField(20), gbc, timesNewRoman);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitApplication(username);
            }
        });
        submitButton.setFont(timesNewRoman);
        formPanel.add(submitButton, gbc);

        frame.add(formPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JPanel createGenderPanel() {
        JPanel genderPanel = new JPanel();
        genderPanel.setLayout(new FlowLayout());

        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        othersRadioButton = new JRadioButton("Others");

        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        genderGroup.add(othersRadioButton);

        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        genderPanel.add(othersRadioButton);

        return genderPanel;
    }

    private void addLabelAndField(JPanel panel, String labelText, JComponent field, GridBagConstraints gbc, Font font) {
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        panel.add(label, gbc);
        gbc.gridx++;
        panel.add(field, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
    }

    private void submitApplication(String username) {
        String accountNumber = accountNumberField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String dateOfBirth = dateOfBirthField.getText();
        String gender = maleRadioButton.isSelected() ? "Male" : (femaleRadioButton.isSelected() ? "Female" : "Others");
        String mobileNumber = mobileNumberField.getText();
        String occupation = occupationField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String religion = religionField.getText();
        String caste = casteField.getText();

        if (!accountNumber.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty() && !dateOfBirth.isEmpty()
                && !mobileNumber.isEmpty() && !occupation.isEmpty() && !email.isEmpty() && !address.isEmpty()
                && !religion.isEmpty() && !caste.isEmpty()) {
            if (storeApplication(username, accountNumber, firstName, lastName, dateOfBirth, gender, mobileNumber, occupation, email, address, religion, caste)) {
                JOptionPane.showMessageDialog(frame, "ATM card application submitted successfully!");
                frame.dispose();
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to submit application. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please fill in all the required fields.", "Incomplete Form", JOptionPane.WARNING_MESSAGE);
        }
    }

    private boolean storeApplication(String username, String accountNumber, String firstName, String lastName, String dateOfBirth, String gender, String mobileNumber, String occupation, String email, String address, String religion, String caste) {
        Connection dbConnection = null;
        boolean success = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/bank";
            String dbUsername = "root";
            String dbPassword = "Sriram@1234";
            dbConnection = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword);

            String query = "INSERT INTO atm (username, account_number, first_name, last_name, date_of_birth, gender, mobile_number, occupation, email, address, religion, caste) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, accountNumber);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setString(5, dateOfBirth);
            preparedStatement.setString(6, gender);
            preparedStatement.setString(7, mobileNumber);
            preparedStatement.setString(8, occupation);
            preparedStatement.setString(9, email);
            preparedStatement.setString(10, address);
            preparedStatement.setString(11, religion);
            preparedStatement.setString(12, caste);

            int rowsAffected = preparedStatement.executeUpdate();
            success = rowsAffected > 0;
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

        return success;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATMApplicationForm(""));
    }
}
