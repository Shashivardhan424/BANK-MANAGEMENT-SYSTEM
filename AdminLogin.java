package com.Bank;

import javax.swing.*;

import com.myconnection.mybankCon;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminLogin extends JFrame implements ActionListener {

    JLabel titleLabel;
    JLabel usernameLabel;
    JLabel passwordLabel;

    JTextField usernameField;
    JPasswordField passwordField;

    JButton loginButton;
    JButton clearButton;
    JButton backButton;

    public AdminLogin() {

        setTitle("Bank Management System - Admin Login");
        setSize(500, 400);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

     
        titleLabel = new JLabel("ADMIN LOGIN");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setBounds(165, 40, 200, 40);
        add(titleLabel);

        usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(80, 120, 120, 30);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(210, 120, 180, 30);
        add(usernameField);

        
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(80, 170, 120, 30);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(210, 170, 180, 30);
        add(passwordField);

        
        loginButton = new JButton("Login");
        loginButton.setBounds(70, 240, 110, 35);
        loginButton.addActionListener(this);
        add(loginButton);

        // Clear button
        clearButton = new JButton("Clear");
        clearButton.setBounds(195, 240, 110, 35);
        clearButton.addActionListener(this);
        add(clearButton);

    
        backButton = new JButton("Back");
        backButton.setBounds(320, 240, 110, 35);
        backButton.addActionListener(this);
        add(backButton);

        setVisible(true);
    }

  
    public void actionPerformed(ActionEvent e) {

     
        if (e.getSource() == loginButton) {

            String username =
                    usernameField.getText();

            String password =
                    new String(passwordField.getPassword());

         
            if (username.isEmpty() || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please enter username and password"
                );

                return;
            }

            try {
            
            	  Connection con = mybankCon.myowncon();


                String query =
                        "SELECT * FROM admin " +
                        "WHERE username=? AND password=?";

                PreparedStatement pst =
                        con.prepareStatement(query);

                pst.setString(1, username);
                pst.setString(2, password);

                ResultSet rs =
                        pst.executeQuery();

                if (rs.next()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Admin Login Successful"
                    );

                    new AdminDashboard();

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
                        "Database Error: " +
                        ex.getMessage()
                );
            }
        }

     
        if (e.getSource() == clearButton) {

            usernameField.setText("");
            passwordField.setText("");
        }

        if (e.getSource() == backButton) {

            new Login();

            dispose();
        }
    }

    public static void main(String[] args) {

        new AdminLogin();
    }
}
