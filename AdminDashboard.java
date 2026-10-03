package com.Bank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame implements ActionListener {

    
    JLabel welcomeLabel;

    JButton customerButton;
    JButton transactionButton;
    JButton logoutButton;

    public AdminDashboard() {

        setTitle("Bank Management System - Admin Dashboard");
        setSize(600, 450);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        welcomeLabel = new JLabel("Welcome Admin");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setBounds(220, 90, 200, 35);
        add(welcomeLabel);

        
        customerButton = new JButton("Customer Management");
        customerButton.setBounds(170, 150, 250, 45);
        customerButton.addActionListener(this);
        add(customerButton);

        // Transaction Management
        transactionButton = new JButton("Transaction Management");
        transactionButton.setBounds(170, 215, 250, 45);
        transactionButton.addActionListener(this);
        add(transactionButton);

        
        logoutButton = new JButton("Logout");
        logoutButton.setBounds(220, 290, 150, 40);
        logoutButton.addActionListener(this);
        add(logoutButton);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

      
        if (e.getSource() == customerButton) {

            new CustomerManagement();

            dispose();
        }

       
        if (e.getSource() == transactionButton) {

            new TransactionManagement();

            dispose();
        }

     
        if (e.getSource() == logoutButton) {

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Do you want to logout?",
                    "Logout",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {

                new Login();

                dispose();
            }
        }
    }

    public static void main(String[] args) {

        new AdminDashboard();
    }
}
