package com.Bank;

import javax.swing.*;

import com.myconnection.mybankCon;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Balance extends JFrame implements ActionListener {

    JLabel titleLabel;
    JLabel accountLabel;
    JLabel balanceLabel;

    JButton refreshButton;
    JButton backButton;

    int accountNo;

    public Balance(int accountNo) {

        this.accountNo = accountNo;

        setTitle("Bank Management System - Balance");
        setSize(500, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        titleLabel = new JLabel("ACCOUNT BALANCE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(130, 40, 250, 40);
        add(titleLabel);

        accountLabel = new JLabel("Account Number:");
        accountLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        accountLabel.setBounds(80, 120, 140, 30);
        add(accountLabel);

        JLabel accountValue =
                new JLabel(String.valueOf(accountNo));

        accountValue.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        accountValue.setBounds(240, 120, 150, 30);
        add(accountValue);

       
        JLabel balanceText =
                new JLabel("Current Balance:");

        balanceText.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        balanceText.setBounds(80, 170, 140, 30);
        add(balanceText);

        balanceLabel =
                new JLabel("Loading...");

        balanceLabel.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        balanceLabel.setBounds(240, 170, 180, 30);
        add(balanceLabel);

     
        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(100, 250, 120, 35);
        refreshButton.addActionListener(this);
        add(refreshButton);

        
        backButton = new JButton("Back");
        backButton.setBounds(260, 250, 120, 35);
        backButton.addActionListener(this);
        add(backButton);

       
        loadBalance();

        setVisible(true);
    }

    
    public void loadBalance() {

        try {
          
        	  Connection con = mybankCon.myowncon();

            String query =
                    "SELECT balance FROM accounts WHERE account_no=?";

            PreparedStatement pst =
                    con.prepareStatement(query);

            pst.setInt(1, accountNo);

            ResultSet rs =
                    pst.executeQuery();

            if (rs.next()) {

                double balance =
                        rs.getDouble("balance");

                balanceLabel.setText(
                        "₹ " + balance
                );

            } else {

                balanceLabel.setText(
                        "Account Not Found"
                );
            }

            con.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " +
                    e.getMessage()
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

       
        if (e.getSource() == refreshButton) {

            loadBalance();

            JOptionPane.showMessageDialog(
                    this,
                    "Balance Updated"
            );
        }

      
        if (e.getSource() == backButton) {

            new CustomerDashboard(accountNo);

            dispose();
        }
    }

    public static void main(String[] args) {

        new Balance(1);
    }
}
