package com.Bank;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

import com.myconnection.mybankCon;

public class Login extends JFrame implements ActionListener {

    JLabel titleLabel, usernameLabel, passwordLabel;
    JTextField usernameField;
    JPasswordField passwordField;

    JButton loginButton;
    JButton registerButton;
    JButton adminButton;
    JButton clearButton;
    JButton exitButton;

    public Login() {

        setTitle("Bank Management System - Login");

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        titleLabel = new JLabel("BANK MANAGEMENT SYSTEM");

        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        titleLabel.setBounds(640, 250, 350, 40);

        add(titleLabel);


        usernameLabel = new JLabel("Username:");

        usernameLabel.setBounds(637, 320, 100, 30);

        add(usernameLabel);


        usernameField = new JTextField();

        usernameField.setBounds(747, 320, 220, 30);

        add(usernameField);


        passwordLabel = new JLabel("Password:");

        passwordLabel.setBounds(637, 370, 100, 30);

        add(passwordLabel);


        passwordField = new JPasswordField();

        passwordField.setBounds(747, 370, 220, 30);

        add(passwordField);


        loginButton = new JButton("Customer Login");

        loginButton.setBounds(637, 430, 150, 35);

        loginButton.addActionListener(this);

        add(loginButton);


        adminButton = new JButton("Admin Login");

        adminButton.setBounds(807, 430, 150, 35);

        adminButton.addActionListener(e -> {

            new AdminLogin();

            dispose();

        });

        add(adminButton);


        registerButton = new JButton("Register");

        registerButton.setBounds(637, 480, 100, 35);

        registerButton.addActionListener(this);

        add(registerButton);


        clearButton = new JButton("Clear");

        clearButton.setBounds(747, 480, 100, 35);

        clearButton.addActionListener(this);

        add(clearButton);


        exitButton = new JButton("Exit");

        exitButton.setBounds(857, 480, 100, 35);

        exitButton.addActionListener(this);

        add(exitButton);


        setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loginButton) {

            customerLogin();

        } else if (e.getSource() == adminButton) {

            adminLogin();

        } else if (e.getSource() == registerButton) {

            new Register();
            dispose();

        } else if (e.getSource() == clearButton) {

            usernameField.setText("");
            passwordField.setText("");

        } else if (e.getSource() == exitButton) {

            System.exit(0);
        }
    }

   
    private void customerLogin() {

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password"
            );

            return;
        }

        try {
        	
        	  Connection con = mybankCon.myowncon();
            

            String sql = "SELECT account_no FROM users WHERE username=? AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int accountNo = rs.getInt("account_no");

                JOptionPane.showMessageDialog(
                        this,
                        "Login Successful"
                );

                new CustomerDashboard(accountNo);
                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Username or Password"
                );
            }

            con.close();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + ex.getMessage()
            );
        }
    }

   
    private void adminLogin() {

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter username and password"
            );

            return;
        }

        try {
        	
        	  Connection con = mybankCon.myowncon();
            

            String sql = "SELECT * FROM admin WHERE username=? AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Admin Login Successful"
                );

               // new AdminDashboard();
                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Admin Username or Password"
                );
            }

            con.close();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Database Error: " + ex.getMessage()
            );
        }
    }

    public static void main(String[] args) {

        new Login();
    }
}
