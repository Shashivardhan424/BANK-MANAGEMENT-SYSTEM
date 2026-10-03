package com.Bank;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.myconnection.mybankCon;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class TransactionHistory extends JFrame implements ActionListener {

    JLabel titleLabel;
    JButton refreshButton;
    JButton backButton;

    JTable transactionTable;
    JScrollPane scrollPane;

    DefaultTableModel model;

    int accountNo;

    public TransactionHistory(int accountNo) {

        this.accountNo = accountNo;

        setTitle("Bank Management System - Transaction History");
        setSize(800, 500);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        titleLabel = new JLabel("TRANSACTION HISTORY");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(270, 20, 300, 40);
        add(titleLabel);

        // Table
        String[] columns = {
            "Transaction ID",
            "Account No",
            "Type",
            "Amount",
            "Balance",
            "Date & Time"
        };

        model = new DefaultTableModel(columns, 0);

        transactionTable = new JTable(model);

        scrollPane = new JScrollPane(transactionTable);
        scrollPane.setBounds(30, 80, 730, 280);

        add(scrollPane);

       
        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(230, 390, 120, 35);
        refreshButton.addActionListener(this);
        add(refreshButton);

        backButton = new JButton("Back");
        backButton.setBounds(390, 390, 120, 35);
        backButton.addActionListener(this);
        add(backButton);

        loadTransactions();

        setVisible(true);
    }

    
    public void loadTransactions() {

        try {

         
            model.setRowCount(0);
           
            Connection con = mybankCon.myowncon();

            String query =
                    "SELECT transaction_id, account_no, " +
                    "transaction_type, amount, balance, " +
                    "transaction_date " +
                    "FROM transactions " +
                    "WHERE account_no=? " +
                    "ORDER BY transaction_id DESC";

            PreparedStatement pst =
                    con.prepareStatement(query);

            pst.setInt(1, accountNo);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                int transactionId =
                        rs.getInt("transaction_id");

                int accountNumber =
                        rs.getInt("account_no");

                String type =
                        rs.getString("transaction_type");

                double amount =
                        rs.getDouble("amount");

                double balance =
                        rs.getDouble("balance");

                Timestamp date =
                        rs.getTimestamp("transaction_date");

                model.addRow(new Object[] {
                    transactionId,
                    accountNumber,
                    type,
                    amount,
                    balance,
                    date
                });
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

            loadTransactions();

            JOptionPane.showMessageDialog(
                    this,
                    "Transaction History Updated"
            );
        }

     
        if (e.getSource() == backButton) {

            new CustomerDashboard(accountNo);

            dispose();
        }
    }

    public static void main(String[] args) {

        new TransactionHistory(1);
    }
}
