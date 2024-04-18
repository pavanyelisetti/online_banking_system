import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Dashboard extends JFrame implements ActionListener {
    JFrame f;
    JLabel l1;
    JMenuBar menuBar;
    public String username;
    JMenu user, accountbalance, debitdetails, creditdetails, applyforATM, ministatement, Transfer,MPin, logout;
    JMenuItem viewdetailsItem,viewBalanceItem, debitItem, creditItem, applyForATMItem, miniStatementItem, transferFundsItem,MpinItem, logoutItem;
    
    public Dashboard(String username) {
        this.username=username;
        f = new JFrame("Dashboard");
        f.setBounds(0, 0, 1300, 1000);
        f.setLayout(null);

        
        // Create a menu bar
        menuBar = new JMenuBar();
        f.setJMenuBar(menuBar);

        // Create menus
        Font menuFont = new Font("TimesNewRoman", Font.BOLD, 16);

        user = new JMenu("User");
        user.setFont(menuFont);
        accountbalance = new JMenu("Account Balance");
        accountbalance.setFont(menuFont);
        debitdetails = new JMenu("Debit Details");
        debitdetails.setFont(menuFont);
        creditdetails = new JMenu("Credit Details");
        creditdetails.setFont(menuFont);
        applyforATM = new JMenu("Apply for ATM");
        applyforATM.setFont(menuFont);
        ministatement = new JMenu("Mini Statement");
       ministatement.setFont(menuFont);
        Transfer = new JMenu("Transfer");
        Transfer.setFont(menuFont);
        MPin = new JMenu("MPin");
        MPin.setFont(menuFont);
        logout = new JMenu("Logout");
        logout.setFont(menuFont);

        // Create menu itemsM
       
        viewdetailsItem = new JMenuItem("View User Details");
        viewdetailsItem.setFont(menuFont);
        viewBalanceItem = new JMenuItem("View Balance");
        viewBalanceItem.setFont(menuFont);
        debitItem = new JMenuItem("Debit Details");
        debitItem.setFont(menuFont);
        creditItem = new JMenuItem("Credit Details");
        creditItem.setFont(menuFont);
        applyForATMItem = new JMenuItem("Apply for ATM");
        applyForATMItem.setFont(menuFont);
        miniStatementItem = new JMenuItem("Mini Statement");
        miniStatementItem.setFont(menuFont);
        transferFundsItem = new JMenuItem("Transfer Funds");
        transferFundsItem.setFont(menuFont);
        MpinItem = new JMenuItem("Create/Reset MPIN");
        MpinItem.setFont(menuFont);
        logoutItem = new JMenuItem("Logout");
        logoutItem.setFont(menuFont);

        

        user.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        accountbalance.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        debitdetails.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        creditdetails.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        applyforATM.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        ministatement.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        Transfer.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        MPin.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        logout.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // Add menu items to menus
        user.add(viewdetailsItem);
        accountbalance.add(viewBalanceItem);
        debitdetails.add(debitItem);
        creditdetails.add(creditItem);
        applyforATM.add(applyForATMItem);
        ministatement.add(miniStatementItem);
        Transfer.add(transferFundsItem);
        MPin.add(MpinItem);
        logout.add(logoutItem);

        // Add menus to the menu bar
        menuBar.add(user);
        menuBar.add(accountbalance);
        menuBar.add(debitdetails);
        menuBar.add(creditdetails);
        menuBar.add(applyforATM);
        menuBar.add(ministatement);
        menuBar.add(Transfer);
        menuBar.add(MPin);
        menuBar.add(logout);

        // Add action listeners to menu items
        viewdetailsItem.addActionListener(this);
        viewBalanceItem.addActionListener(this);
        debitItem.addActionListener(this);
        creditItem.addActionListener(this);
        applyForATMItem.addActionListener(this);
        miniStatementItem.addActionListener(this);
        transferFundsItem.addActionListener(this);
        MpinItem.addActionListener(this);
        logoutItem.addActionListener(this);

        l1 = new JLabel();
        // Load image from resources directory
        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("image1.jpg"));
        Image i1 = img.getImage().getScaledInstance(1300, 1000, Image.SCALE_SMOOTH);
        ImageIcon img2 = new ImageIcon(i1);

        l1.setIcon(img2);
        l1.setBounds(0, 0, 1300, 1000);
        f.add(l1);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null); // Center the window
        f.setVisible(true);
        

    }
    public Userdetails userdetails;  
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==viewdetailsItem)
        { 
        Userdetails u = new Userdetails(username); 
        }
        else if (e.getSource() == viewBalanceItem) {
        BankAccountBalance b = new BankAccountBalance(username);
            // Perform the action for viewing the balance
            // You can open a new window or dialog for this functionality
        } else if (e.getSource() == debitItem) {
            // Perform the action for viewing debit details
            DebitDetails d = new DebitDetails(username);
            // You can open a new window or dialog for this functionality
        } else if (e.getSource() == creditItem) {
            // Perform the action for viewing credit details
            // You can open a new window or dialog for this functionality
            CreditDetails c = new CreditDetails(username);
        } else if (e.getSource() == applyForATMItem) {
            // Perform the action for applying for an ATM
            // You can open a new window or dialog for this functionality
            ATMApplicationForm atm = new ATMApplicationForm(username);
        } else if (e.getSource() == miniStatementItem) {
            // Perform the action for viewing a mini statement
            // You can open a new window or dialog for this functionality
            MiniStatement M = new MiniStatement(username);
        } else if (e.getSource() == transferFundsItem) {
            MoneyTransfer m = new MoneyTransfer(username);
            // Perform the action for transferring funds
            // You can open a new window or dialog for this functionality
        }
        else if(e.getSource()== MpinItem)
        {
            MPinGenerator m1= new MPinGenerator(username);
        } 
        else if (e.getSource() == logoutItem) {
            // Perform the action for logging out
            // You can implement the logout logic
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) 
            {
                f.dispose();
                // Bank b = new Bank();
                // b.createAndShowGUI();
            }
        
    }
    }
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Dashboard("");
            }
        });
    }
}
