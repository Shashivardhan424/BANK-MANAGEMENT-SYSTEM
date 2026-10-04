package com.Bank;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class CustomerDashboard extends JFrame implements ActionListener {

    int accountNo;

    JLabel titleLabel;
    JLabel welcomeLabel;

    JButton accountButton;
    JButton depositButton;
    JButton withdrawButton;
    JButton balanceButton;
    JButton transactionButton;
    JButton logoutButton;

    public CustomerDashboard(int accountNo) {

        this.accountNo = accountNo;

        setTitle("Customer Dashboard");

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        titleLabel = new JLabel("BANK MANAGEMENT SYSTEM");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titleLabel.setBounds(667, 180, 450, 40);

        add(titleLabel);


        welcomeLabel = new JLabel(
                "Welcome Customer - Account No: " + accountNo
        );

        welcomeLabel.setBounds(692, 230, 300, 30);

        add(welcomeLabel);


        accountButton = new JButton("Account Details");

        accountButton.setBounds(607, 290, 190, 40);

        accountButton.addActionListener(this);

        add(accountButton);


        depositButton = new JButton("Deposit");

        depositButton.setBounds(817, 290, 190, 40);

        depositButton.addActionListener(this);

        add(depositButton);


        withdrawButton = new JButton("Withdraw");

        withdrawButton.setBounds(607, 350, 190, 40);

        withdrawButton.addActionListener(this);

        add(withdrawButton);


        balanceButton = new JButton("Balance");

        balanceButton.setBounds(817, 350, 190, 40);

        balanceButton.addActionListener(this);

        add(balanceButton);


        transactionButton = new JButton("Transaction History");

        transactionButton.setBounds(607, 410, 400, 40);

        transactionButton.addActionListener(e -> {

            new TransactionHistory(accountNo);

            dispose();

        });

        add(transactionButton);


        logoutButton = new JButton("Logout");

        logoutButton.setBounds(732, 490, 150, 40);

        logoutButton.addActionListener(this);

        add(logoutButton);


        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == accountButton) {

            new AccountDetails(accountNo);

        } else if (e.getSource() == depositButton) {

            new Deposit(accountNo);

        } else if (e.getSource() == withdrawButton) {

            new Withdraw(accountNo);

        } else if (e.getSource() == balanceButton) {

            new Balance(accountNo);

        } else if (e.getSource() == transactionButton) {

            new TransactionHistory(accountNo);

        } else if (e.getSource() == logoutButton) {

            new Login();
            dispose();
        }
    }
}
