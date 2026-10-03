package com.Bank;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.myconnection.mybankCon;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class TransactionManagement extends JFrame implements ActionListener {

    JLabel titleLabel;
    JLabel searchLabel;

    JTextField searchField;

    JButton searchButton;
    JButton refreshButton;
    JButton backButton;

    JTable transactionTable;
    JScrollPane scrollPane;

    DefaultTableModel model;

    public TransactionManagement() {

        setTitle("Bank Management System - Transaction Management");
        setSize(1000, 600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

      

        titleLabel = new JLabel("TRANSACTION MANAGEMENT");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 25)
        );

        titleLabel.setBounds(330, 20, 600, 40);

        add(titleLabel);



        searchLabel = new JLabel("Account Number:");

        searchLabel.setBounds(
                50, 80, 120, 30
        );

        add(searchLabel);


        searchField = new JTextField();

        searchField.setBounds(
                170, 80, 150, 30
        );

        add(searchField);


        searchButton = new JButton("Search");

        searchButton.setBounds(
                340, 80, 100, 30
        );

        searchButton.addActionListener(this);

        add(searchButton);


        refreshButton = new JButton("Refresh");

        refreshButton.setBounds(
                450, 80, 100, 30
        );

        refreshButton.addActionListener(this);

        add(refreshButton);


      
        String[] columns = {

                "Transaction ID",
                "Account No",
                "Transaction Type",
                "Amount",
                "Balance",
                "Date & Time"
        };


        model = new DefaultTableModel(
                columns,
                0
        );


        transactionTable = new JTable(model);


        scrollPane = new JScrollPane(
                transactionTable
        );

        scrollPane.setBounds(
                30, 130, 920, 320
        );

        add(scrollPane);


       

        backButton = new JButton("Back");

        backButton.setBounds(
                430, 490, 120, 40
        );

        backButton.addActionListener(this);

        add(backButton);


        

        loadTransactions();


        setVisible(true);
    }



    public void loadTransactions() {

        try {

            // Remove old rows

            model.setRowCount(0);

/*
Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/bank_management",
                "root",
                "password"
            );*/
            Connection con = mybankCon.myowncon();


            String query =
                    "SELECT transaction_id, " +
                    "account_no, " +
                    "transaction_type, " +
                    "amount, " +
                    "balance, " +
                    "transaction_date " +
                    "FROM transactions " +
                    "ORDER BY transaction_id DESC";


            PreparedStatement pst =
                    con.prepareStatement(query);


            ResultSet rs =
                    pst.executeQuery();


            while (rs.next()) {

                int transactionId =
                        rs.getInt("transaction_id");


                int accountNo =
                        rs.getInt("account_no");


                String type =
                        rs.getString(
                                "transaction_type"
                        );


                double amount =
                        rs.getDouble("amount");


                double balance =
                        rs.getDouble("balance");


                Timestamp date =
                        rs.getTimestamp(
                                "transaction_date"
                        );


                model.addRow(
                        new Object[] {

                                transactionId,
                                accountNo,
                                type,
                                amount,
                                balance,
                                date
                        }
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


    // =====================================================
    // SEARCH TRANSACTIONS
    // =====================================================

    public void searchTransactions() {

        String accountText =
                searchField.getText();


        if (accountText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter account number"
            );

            return;
        }


        try {

            int accountNo =
                    Integer.parseInt(
                            accountText
                    );


          

            model.setRowCount(0);

/*
Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/bank_management",
                "root",
                "password"
            );*/
            Connection con = mybankCon.myowncon();

            String query =
                    "SELECT transaction_id, " +
                    "account_no, " +
                    "transaction_type, " +
                    "amount, " +
                    "balance, " +
                    "transaction_date " +
                    "FROM transactions " +
                    "WHERE account_no=? " +
                    "ORDER BY transaction_id DESC";


            PreparedStatement pst =
                    con.prepareStatement(query);


            pst.setInt(
                    1,
                    accountNo
            );


            ResultSet rs =
                    pst.executeQuery();


            boolean found = false;


            while (rs.next()) {

                found = true;


                int transactionId =
                        rs.getInt(
                                "transaction_id"
                        );


                int accountNumber =
                        rs.getInt(
                                "account_no"
                        );


                String type =
                        rs.getString(
                                "transaction_type"
                        );


                double amount =
                        rs.getDouble(
                                "amount"
                        );


                double balance =
                        rs.getDouble(
                                "balance"
                        );


                Timestamp date =
                        rs.getTimestamp(
                                "transaction_date"
                        );


                model.addRow(
                        new Object[] {

                                transactionId,
                                accountNumber,
                                type,
                                amount,
                                balance,
                                date
                        }
                );
            }


            if (!found) {

                JOptionPane.showMessageDialog(
                        this,
                        "No transactions found"
                );
            }


            con.close();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Enter a valid account number"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " +
                    e.getMessage()
            );
        }
    }

    // BUTTON ACTIONS
    
    @Override
    public void actionPerformed(
            ActionEvent e
    ) {


       

        if (e.getSource() ==
                searchButton) {

            searchTransactions();
        }



        if (e.getSource() ==
                refreshButton) {

            searchField.setText("");

            loadTransactions();
        }


       

        if (e.getSource() ==
                backButton) {

            new AdminDashboard();

            dispose();
        }
    }


    public static void main(String[] args) {

        new TransactionManagement();
    }
}
