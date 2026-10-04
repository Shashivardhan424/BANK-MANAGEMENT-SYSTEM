package com.Bank;

import javax.swing.*;

import com.myconnection.mybankCon;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Deposit extends JFrame implements ActionListener {

    JLabel titleLabel, accountLabel, amountLabel, balanceLabel;
    JTextField amountField;
    JButton depositButton, clearButton, backButton;

    int accountNo;

    public Deposit(int accountNo) {

        this.accountNo = accountNo;

        setTitle("Bank Management System - Deposit");

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        titleLabel = new JLabel("DEPOSIT MONEY");

        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        titleLabel.setBounds(717, 180, 250, 40);

        add(titleLabel);


        accountLabel = new JLabel("Account Number:");

        accountLabel.setBounds(637, 250, 130, 30);

        add(accountLabel);


        JLabel accountValue = new JLabel(String.valueOf(accountNo));

        accountValue.setBounds(787, 250, 150, 30);

        add(accountValue);


        amountLabel = new JLabel("Deposit Amount:");

        amountLabel.setBounds(637, 300, 130, 30);

        add(amountLabel);


        amountField = new JTextField();

        amountField.setBounds(787, 300, 150, 30);

        add(amountField);


        depositButton = new JButton("Deposit");

        depositButton.setBounds(627, 370, 100, 35);

        depositButton.addActionListener(this);

        add(depositButton);


        clearButton = new JButton("Clear");

        clearButton.setBounds(737, 370, 100, 35);

        clearButton.addActionListener(this);

        add(clearButton);


        backButton = new JButton("Back");

        backButton.setBounds(847, 370, 100, 35);

        backButton.addActionListener(this);

        add(backButton);


        balanceLabel = new JLabel("Current Balance: Loading...");

        balanceLabel.setBounds(707, 430, 250, 30);

        add(balanceLabel);


        loadBalance();

        setVisible(true);
    }

   
    public void loadBalance() {

        try {
        	
        	  Connection con = mybankCon.myowncon();


            String query = "SELECT balance FROM accounts WHERE account_no=?";

            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, accountNo);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                double balance = rs.getDouble("balance");

                balanceLabel.setText("Current Balance: ₹" + balance);
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

        if (e.getSource() == depositButton) {

            String amountText = amountField.getText();

            if (amountText.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter deposit amount"
                );

                return;
            }

            try {

                double amount = Double.parseDouble(amountText);

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

                ResultSet rs = selectPst.executeQuery();

                if (rs.next()) {

                    double oldBalance = rs.getDouble("balance");

                    double newBalance = oldBalance + amount;

                    
                    String updateQuery =
                            "UPDATE accounts SET balance=? WHERE account_no=?";

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
                    transactionPst.setString(2, "DEPOSIT");
                    transactionPst.setDouble(3, amount);
                    transactionPst.setDouble(4, newBalance);

                    transactionPst.executeUpdate();

                    JOptionPane.showMessageDialog(
                            this,
                            "Deposit Successful!\n" +
                            "Deposited Amount: ₹" + amount +
                            "\nNew Balance: ₹" + newBalance
                    );

                    amountField.setText("");

                    balanceLabel.setText(
                            "Current Balance: ₹" + newBalance
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
                        "Database Error: " + ex.getMessage()
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

        new Deposit(1);
    }
}
