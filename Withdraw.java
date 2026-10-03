package com.Bank;

import javax.swing.*;

import com.myconnection.mybankCon;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Withdraw extends JFrame implements ActionListener {

    JLabel titleLabel, accountLabel, amountLabel, balanceLabel;
    JTextField amountField;

    JButton withdrawButton;
    JButton clearButton;
    JButton backButton;

    int accountNo;

    public Withdraw(int accountNo) {

        this.accountNo = accountNo;

        setTitle("Bank Management System - Withdraw");
        setSize(500, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

       
        titleLabel = new JLabel("WITHDRAW MONEY");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(130, 30, 250, 40);
        add(titleLabel);

        // Account Number
        accountLabel = new JLabel("Account Number:");
        accountLabel.setBounds(80, 100, 130, 30);
        add(accountLabel);

        JLabel accountValue = new JLabel(String.valueOf(accountNo));
        accountValue.setBounds(230, 100, 150, 30);
        add(accountValue);

        // Withdrawal Amount
        amountLabel = new JLabel("Withdraw Amount:");
        amountLabel.setBounds(80, 150, 130, 30);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(230, 150, 150, 30);
        add(amountField);

       
        withdrawButton = new JButton("Withdraw");
        withdrawButton.setBounds(60, 220, 120, 35);
        withdrawButton.addActionListener(this);
        add(withdrawButton);

        
        clearButton = new JButton("Clear");
        clearButton.setBounds(190, 220, 100, 35);
        clearButton.addActionListener(this);
        add(clearButton);

        backButton = new JButton("Back");
        backButton.setBounds(300, 220, 100, 35);
        backButton.addActionListener(this);
        add(backButton);

        balanceLabel = new JLabel("Current Balance: Loading...");
        balanceLabel.setBounds(130, 290, 250, 30);
        add(balanceLabel);

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

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                double balance = rs.getDouble("balance");

                balanceLabel.setText(
                        "Current Balance: ₹" + balance
                );
            }

            con.close();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + e.getMessage()
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

       
        if (e.getSource() == withdrawButton) {

            String amountText = amountField.getText();

            
            if (amountText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter withdrawal amount"
                );

                return;
            }

            try {

                double amount =
                        Double.parseDouble(amountText);

                // Check amount
                if (amount <= 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Amount must be greater than 0"
                    );

                    return;
                }
                    
                Connection con = mybankCon.myowncon();

             
                String selectQuery =
                        "SELECT balance FROM accounts WHERE account_no=?";

                PreparedStatement selectPst =
                        con.prepareStatement(selectQuery);

                selectPst.setInt(1, accountNo);

                ResultSet rs =
                        selectPst.executeQuery();

                if (rs.next()) {

                    double oldBalance =
                            rs.getDouble("balance");

                  
                    if (amount > oldBalance) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Insufficient Balance!\n" +
                                "Available Balance: ₹" +
                                oldBalance
                        );

                        con.close();

                        return;
                    }

                  
                    double newBalance =
                            oldBalance - amount;

                    String updateQuery =
                            "UPDATE accounts SET balance=? " +
                            "WHERE account_no=?";

                    PreparedStatement updatePst =
                            con.prepareStatement(updateQuery);

                    updatePst.setDouble(1, newBalance);
                    updatePst.setInt(2, accountNo);

                    updatePst.executeUpdate();

                    String transactionQuery =
                            "INSERT INTO transactions " +
                            "(account_no, transaction_type, amount, balance) " +
                            "VALUES (?, ?, ?, ?)";

                    PreparedStatement transactionPst =
                            con.prepareStatement(transactionQuery);

                    transactionPst.setInt(1, accountNo);
                    transactionPst.setString(2, "WITHDRAW");
                    transactionPst.setDouble(3, amount);
                    transactionPst.setDouble(4, newBalance);

                    transactionPst.executeUpdate();

                    JOptionPane.showMessageDialog(
                            this,
                            "Withdrawal Successful!\n" +
                            "Withdrawn Amount: ₹" +
                            amount +
                            "\nNew Balance: ₹" +
                            newBalance
                    );

                    amountField.setText("");

                    balanceLabel.setText(
                            "Current Balance: ₹" +
                            newBalance
                    );
                }

                con.close();

            } catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter a valid number"
                );

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(
                        this,
                        "Database Error: " +
                        ex.getMessage()
                );
            }
        }

        
        if (e.getSource() == clearButton) {

            amountField.setText("");
        }

        if (e.getSource() == backButton) {

            new CustomerDashboard(accountNo);

            dispose();
        }
    }

    public static void main(String[] args) {

        new Withdraw(1);
    }
}
