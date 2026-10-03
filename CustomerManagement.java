
package com.Bank;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.myconnection.mybankCon;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CustomerManagement extends JFrame implements ActionListener {

    JLabel titleLabel;
    JLabel searchLabel;

    JTextField searchField;

    JButton searchButton;
    JButton refreshButton;
    JButton addButton;
    JButton updateButton;
    JButton deleteButton;
    JButton backButton;

    JTable customerTable;
    JScrollPane scrollPane;

    DefaultTableModel model;

    public CustomerManagement() {

        setTitle(" Customer Management");
        setSize(1100, 650);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

       

        titleLabel = new JLabel("CUSTOMER MANAGEMENT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setBounds(390, 20, 650, 40);
        add(titleLabel);

    

        searchLabel = new JLabel("Account Number:");
        searchLabel.setBounds(50, 80, 120, 30);
        add(searchLabel);

        searchField = new JTextField();
        searchField.setBounds(170, 80, 150, 30);
        add(searchField);

        searchButton = new JButton("Search");
        searchButton.setBounds(340, 80, 100, 30);
        searchButton.addActionListener(this);
        add(searchButton);

        refreshButton = new JButton("Refresh");
        refreshButton.setBounds(450, 80, 100, 30);
        refreshButton.addActionListener(this);
        add(refreshButton);


        String[] columns = {
                "Account No",
                "Name",
                "Age",
                "Gender",
                "Phone",
                "Email",
                "Address",
                "Account Type",
                "Balance"
        };

        model = new DefaultTableModel(columns, 0);

        customerTable = new JTable(model);

        scrollPane = new JScrollPane(customerTable);
        scrollPane.setBounds(30, 130, 1020, 300);

        add(scrollPane);


        addButton = new JButton("Add");
        addButton.setBounds(170, 480, 120, 40);
        addButton.addActionListener(this);
        add(addButton);

        updateButton = new JButton("Update");
        updateButton.setBounds(310, 480, 120, 40);
        updateButton.addActionListener(this);
        add(updateButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(450, 480, 120, 40);
        deleteButton.addActionListener(this);
        add(deleteButton);

        backButton = new JButton("Back");
        backButton.setBounds(590, 480, 120, 40);
        backButton.addActionListener(this);
        add(backButton);

     
        loadCustomers();

        setVisible(true);
    }

    // =====================================================
    // LOAD ALL CUSTOMERS
    // =====================================================

    public void loadCustomers() {

        try {

            model.setRowCount(0);

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/bank_management",
                "root",
                "password"
            );
            String query =
                    "SELECT * FROM accounts";

            PreparedStatement pst =
                    con.prepareStatement(query);

            ResultSet rs =
                    pst.executeQuery();

            while (rs.next()) {

                model.addRow(new Object[] {

                        rs.getInt("account_no"),

                        rs.getString("name"),

                        rs.getInt("age"),

                        rs.getString("gender"),

                        rs.getString("phone"),

                        rs.getString("email"),

                        rs.getString("address"),

                        rs.getString("account_type"),

                        rs.getDouble("balance")
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

   //search customer

    public void searchCustomer() {

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
                    Integer.parseInt(accountText);

            model.setRowCount(0);

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/bank_management",
                "root",
                "password"
            );
            String query =
                    "SELECT * FROM accounts " +
                    "WHERE account_no=?";

            PreparedStatement pst =
                    con.prepareStatement(query);

            pst.setInt(1, accountNo);

            ResultSet rs =
                    pst.executeQuery();

            if (rs.next()) {

                model.addRow(new Object[] {

                        rs.getInt("account_no"),

                        rs.getString("name"),

                        rs.getInt("age"),

                        rs.getString("gender"),

                        rs.getString("phone"),

                        rs.getString("email"),

                        rs.getString("address"),

                        rs.getString("account_type"),

                        rs.getDouble("balance")
                });

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Customer not found"
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

    // =====================================================
    // ADD CUSTOMER
    // =====================================================

    public void addCustomer() {

        JTextField nameField =
                new JTextField();

        JTextField ageField =
                new JTextField();

        JTextField genderField =
                new JTextField();

        JTextField phoneField =
                new JTextField();

        JTextField emailField =
                new JTextField();

        JTextField addressField =
                new JTextField();

        JTextField typeField =
                new JTextField();

        JTextField balanceField =
                new JTextField();

        JPanel panel = new JPanel();

        panel.setLayout(
                new GridLayout(8, 2, 5, 5)
        );

        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Age:"));
        panel.add(ageField);

        panel.add(new JLabel("Gender:"));
        panel.add(genderField);

        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);

        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        panel.add(new JLabel("Address:"));
        panel.add(addressField);

        panel.add(new JLabel("Account Type:"));
        panel.add(typeField);

        panel.add(new JLabel("Initial Balance:"));
        panel.add(balanceField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Add Customer",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {

            try {

                String name =
                        nameField.getText();

                int age =
                        Integer.parseInt(
                                ageField.getText()
                        );

                String gender =
                        genderField.getText();

                String phone =
                        phoneField.getText();

                String email =
                        emailField.getText();

                String address =
                        addressField.getText();

                String accountType =
                        typeField.getText();

                double balance =
                        Double.parseDouble(
                                balanceField.getText()
                        );

                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                
                Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bank_management",
                    "root",
                    "password"
                );

                String query =
                        "INSERT INTO accounts " +
                        "(name, age, gender, phone, email, " +
                        "address, account_type, balance) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                PreparedStatement pst =
                        con.prepareStatement(query);

                pst.setString(1, name);
                pst.setInt(2, age);
                pst.setString(3, gender);
                pst.setString(4, phone);
                pst.setString(5, email);
                pst.setString(6, address);
                pst.setString(7, accountType);
                pst.setDouble(8, balance);

                pst.executeUpdate();

                con.close();

                JOptionPane.showMessageDialog(
                        this,
                        "Customer Added Successfully"
                );

                loadCustomers();

            } catch (NumberFormatException e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Enter valid Age and Balance"
                );

            } catch (Exception e) {

                JOptionPane.showMessageDialog(
                        this,
                        "Database Error: " +
                        e.getMessage()
                );
            }
        }
    }

    
    //  UPDATE CUSTOMER
    

    public void updateCustomer() {

        int row =
                customerTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a customer"
            );

            return;
        }

        int accountNo =
                (int) model.getValueAt(row, 0);

        String name =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Name:",
                        model.getValueAt(row, 1)
                );

        String ageText =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Age:",
                        model.getValueAt(row, 2)
                );

        String phone =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Phone:",
                        model.getValueAt(row, 4)
                );

        String email =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Email:",
                        model.getValueAt(row, 5)
                );

        String address =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Address:",
                        model.getValueAt(row, 6)
                );

        String accountType =
                JOptionPane.showInputDialog(
                        this,
                        "Enter Account Type:",
                        model.getValueAt(row, 7)
                );

        if (name == null ||
                ageText == null ||
                phone == null ||
                email == null ||
                address == null ||
                accountType == null) {

            return;
        }

        try {

            int age =
                    Integer.parseInt(ageText);
         
            Connection con = mybankCon.myowncon();
            String query =
                    "UPDATE accounts SET " +
                    "name=?, age=?, phone=?, email=?, " +
                    "address=?, account_type=? " +
                    "WHERE account_no=?";

            PreparedStatement pst =
                    con.prepareStatement(query);

            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3, phone);
            pst.setString(4, email);
            pst.setString(5, address);
            pst.setString(6, accountType);
            pst.setInt(7, accountNo);

            pst.executeUpdate();

            con.close();

            JOptionPane.showMessageDialog(
                    this,
                    "Customer Updated Successfully"
            );

            loadCustomers();

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age must be a number"
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " +
                    e.getMessage()
            );
        }
    }

    // =====================================================
    // DELETE CUSTOMER
    // =====================================================

    public void deleteCustomer() {

        int row =
                customerTable.getSelectedRow();

        if (row == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a customer"
            );

            return;
        }

        int accountNo =
                (int) model.getValueAt(row, 0);

        int choice =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete this customer?",
                        "Delete Customer",
                        JOptionPane.YES_NO_OPTION
                );

        if (choice != JOptionPane.YES_OPTION) {

            return;
        }

        try {
             
        	  Connection con = mybankCon.myowncon();

            // Delete login details first
            String userQuery =
                    "DELETE FROM users WHERE account_no=?";

            PreparedStatement userPst =
                    con.prepareStatement(userQuery);

            userPst.setInt(1, accountNo);

            userPst.executeUpdate();

            // Delete transactions
            String transactionQuery =
                    "DELETE FROM transactions " +
                    "WHERE account_no=?";

            PreparedStatement transactionPst =
                    con.prepareStatement(transactionQuery);

            transactionPst.setInt(1, accountNo);

            transactionPst.executeUpdate();

            // Delete account
            String accountQuery =
                    "DELETE FROM accounts " +
                    "WHERE account_no=?";

            PreparedStatement accountPst =
                    con.prepareStatement(accountQuery);

            accountPst.setInt(1, accountNo);

            accountPst.executeUpdate();

            con.close();

            JOptionPane.showMessageDialog(
                    this,
                    "Customer Deleted Successfully"
            );

            loadCustomers();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " +
                    e.getMessage()
            );
        }
    }

    

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == searchButton) {

            searchCustomer();
        }

        if (e.getSource() == refreshButton) {

            searchField.setText("");

            loadCustomers();
        }

        if (e.getSource() == addButton) {

            addCustomer();
        }

        if (e.getSource() == updateButton) {

            updateCustomer();
        }

        if (e.getSource() == deleteButton) {

            deleteCustomer();
        }

        if (e.getSource() == backButton) {

            new AdminDashboard();

            dispose();
        }
    }

    
    public static void main(String[] args) {

        new CustomerManagement();
    }
}