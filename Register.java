package com.Bank;

import java.awt.*;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

import com.myconnection.mybankCon;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
//import com.myconnection.mycon;

public class Register extends JFrame implements ActionListener {

    JLabel titleLabel;
    JLabel nameLabel, ageLabel, genderLabel;
    JLabel phoneLabel, emailLabel, addressLabel;
    JLabel accountTypeLabel, depositLabel;
    JLabel usernameLabel, passwordLabel, confirmPasswordLabel;

    JTextField nameField, ageField, phoneField;
    JTextField emailField, addressField;
    JTextField depositField, usernameField;

    JPasswordField passwordField, confirmPasswordField;

    JRadioButton maleButton, femaleButton, otherButton;

    JComboBox<String> accountTypeBox;

    JButton createButton, clearButton, backButton;

    public Register() {

        setTitle("Bank Management System - Registration");
        setSize(600, 700);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

        titleLabel = new JLabel("CREATE BANK ACCOUNT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(170, 20, 300, 40);
        add(titleLabel);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(70, 80, 150, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(230, 80, 280, 30);
        add(nameField);

        ageLabel = new JLabel("Age:");
        ageLabel.setBounds(70, 120, 150, 30);
        add(ageLabel);

        ageField = new JTextField();
        ageField.setBounds(230, 120, 280, 30);
        add(ageField);

        genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(70, 160, 150, 30);
        add(genderLabel);

        maleButton = new JRadioButton("Male");
        maleButton.setBounds(230, 160, 80, 30);

        femaleButton = new JRadioButton("Female");
        femaleButton.setBounds(310, 160, 90, 30);

        otherButton = new JRadioButton("Other");
        otherButton.setBounds(400, 160, 80, 30);
        
        

        add(maleButton);
        add(femaleButton);
        add(otherButton);
        
        maleButton.addActionListener(e -> {
            femaleButton.setSelected(false);
            otherButton.setSelected(false);
        });


   
        femaleButton.addActionListener(e -> {
            maleButton.setSelected(false);
            otherButton.setSelected(false);
        });


    
        otherButton.addActionListener(e -> {
            maleButton.setSelected(false);
            femaleButton.setSelected(false);
        });

        phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(70, 200, 150, 30);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(230, 200, 280, 30);
        add(phoneField);

        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(70, 240, 150, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(230, 240, 280, 30);
        add(emailField);

        addressLabel = new JLabel("Address:");
        addressLabel.setBounds(70, 280, 150, 30);
        add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(230, 280, 280, 30);
        add(addressField);

        accountTypeLabel = new JLabel("Account Type:");
        accountTypeLabel.setBounds(70, 320, 150, 30);
        add(accountTypeLabel);

        String[] accountTypes = {
                "Savings",
                "Current"
        };

        accountTypeBox = new JComboBox<>(accountTypes);
        accountTypeBox.setBounds(230, 320, 280, 30);
        add(accountTypeBox);

        depositLabel = new JLabel("Initial Deposit:");
        depositLabel.setBounds(70, 360, 150, 30);
        add(depositLabel);

        depositField = new JTextField();
        depositField.setBounds(230, 360, 280, 30);
        add(depositField);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(70, 400, 150, 30);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(230, 400, 280, 30);
        add(usernameField);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(70, 440, 150, 30);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(230, 440, 280, 30);
        add(passwordField);

        confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(70, 480, 150, 30);
        add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(230, 480, 280, 30);
        add(confirmPasswordField);

        createButton = new JButton("Create Account");
        createButton.setBounds(100, 540, 150, 35);
        createButton.addActionListener(this);
        add(createButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(270, 540, 100, 35);
        clearButton.addActionListener(this);
        add(clearButton);

        backButton = new JButton("Back");
        backButton.setBounds(390, 540, 100, 35);
        backButton.addActionListener(this);
       add(backButton);   
        
 
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == createButton) {

            createAccount();

        } else if (e.getSource() == clearButton) {

            clearFields();

        } else if (e.getSource() == backButton) {

            new Login();
            dispose();
        }
    }

    private void createAccount() {

        String name = nameField.getText();
        String ageText = ageField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String depositText = depositField.getText();

        String username = usernameField.getText();

        String password =
                new String(passwordField.getPassword());

        String confirmPassword =
                new String(confirmPasswordField.getPassword());

        String gender = "";

        if (maleButton.isSelected()) {

            gender = "Male";

        } else if (femaleButton.isSelected()) {

            gender = "Female";

        } else if (otherButton.isSelected()) {

            gender = "Other";
        }
         
     
        String accountType =
                accountTypeBox.getSelectedItem().toString();

        if (name.isEmpty() ||
                ageText.isEmpty() ||
                gender.isEmpty() ||
                phone.isEmpty() ||
                email.isEmpty() ||
                address.isEmpty() ||
                depositText.isEmpty() ||
                username.isEmpty() ||
                password.isEmpty() ||
                confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields"
            );

            return;
        }

        if (!password.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match"
            );

            return;
        }

        try {

            int age = Integer.parseInt(ageText);
            double deposit = Double.parseDouble(depositText);

            if (deposit < 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Deposit cannot be negative"
                );

                return;
            }
           
            Connection con = mybankCon.myowncon();
            
            
              
            String accountSQL =
                    "INSERT INTO accounts " +
                    "(name, age, gender, phone, email, address, account_type, balance) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement accountPS = con.prepareStatement(  accountSQL, Statement.RETURN_GENERATED_KEYS );

            accountPS.setString(1, name);
            accountPS.setInt(2, age);
            accountPS.setString(3, gender);
            accountPS.setString(4, phone);
            accountPS.setString(5, email);
            accountPS.setString(6, address);
            accountPS.setString(7, accountType);
            accountPS.setDouble(8, deposit);

            accountPS.executeUpdate();
            
            
           
            ResultSet keys =
                    accountPS.getGeneratedKeys();

            int accountNo = 0;

            if (keys.next()) {

                accountNo = keys.getInt(1);
            }
           

            String userSQL =
                    "INSERT INTO users " +
                    "(account_no, username, password) " +
                    "VALUES (?, ?, ?)";

            PreparedStatement userPS =
                    con.prepareStatement(userSQL);

            userPS.setInt(1, accountNo);
            userPS.setString(2, username);
            userPS.setString(3, password);

            userPS.executeUpdate();

            if (deposit > 0) {

                String transactionSQL =
                        "INSERT INTO transactions " +
                        "(account_no, transaction_type, amount, balance) " +
                        "VALUES (?, ?, ?, ?)";

                PreparedStatement transactionPS =
                        con.prepareStatement(transactionSQL);

                transactionPS.setInt(1, accountNo);
                transactionPS.setString(2, "DEPOSIT");
                transactionPS.setDouble(3, deposit);
                transactionPS.setDouble(4, deposit);

                transactionPS.executeUpdate();
            }

            JOptionPane.showMessageDialog(
                    this,
                    "Account Created Successfully!\n" +
                    "Your Account Number: " + accountNo
            );

            con.close();

            new Login();
            dispose();

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Age and Deposit must be numbers"
            );

        } catch (SQLIntegrityConstraintViolationException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Username already exists"
            );

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage()
            );
        }
    }

    private void clearFields() {

        nameField.setText("");
        ageField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
        depositField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");

        maleButton.setSelected(false);
        femaleButton.setSelected(false);
        otherButton.setSelected(false);

        accountTypeBox.setSelectedIndex(0);
    }
    
    

    public static void main(String[] args) {

        new Register();
    }
}