package com.Bank;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

import com.myconnection.mybankCon;

public class AccountDetails extends JFrame implements ActionListener {

    int accountNo;

    JLabel titleLabel;
    JLabel accountLabel;
    JLabel nameLabel;
    JLabel ageLabel;
    JLabel genderLabel;
    JLabel phoneLabel;
    JLabel emailLabel;
    JLabel addressLabel;
    JLabel typeLabel;
    JLabel balanceLabel;

    JButton backButton;

    public AccountDetails(int accountNo) {

        this.accountNo = accountNo;

        setTitle("Account Details");
        setSize(550, 600);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        titleLabel = new JLabel("ACCOUNT DETAILS");

        titleLabel.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        titleLabel.setBounds(170, 30, 250, 40);

        add(titleLabel);

        accountLabel = new JLabel();
        accountLabel.setBounds(80, 100, 400, 30);
        add(accountLabel);

        nameLabel = new JLabel();
        nameLabel.setBounds(80, 140, 400, 30);
        add(nameLabel);

        ageLabel = new JLabel();
        ageLabel.setBounds(80, 180, 400, 30);
        add(ageLabel);

        genderLabel = new JLabel();
        genderLabel.setBounds(80, 220, 400, 30);
        add(genderLabel);

        phoneLabel = new JLabel();
        phoneLabel.setBounds(80, 260, 400, 30);
        add(phoneLabel);

        emailLabel = new JLabel();
        emailLabel.setBounds(80, 300, 400, 30);
        add(emailLabel);

        addressLabel = new JLabel();
        addressLabel.setBounds(80, 340, 400, 30);
        add(addressLabel);

        typeLabel = new JLabel();
        typeLabel.setBounds(80, 380, 400, 30);
        add(typeLabel);

        balanceLabel = new JLabel();
        balanceLabel.setBounds(80, 420, 400, 30);
        add(balanceLabel);

        backButton = new JButton("Back");
        backButton.setBounds(200, 480, 120, 35);
        backButton.addActionListener(this);
        add(backButton);

        loadAccountDetails();

        setVisible(true);
    }

    private void loadAccountDetails() {

        try {
        	  Connection con = mybankCon.myowncon();
            String sql =
                    "SELECT * FROM accounts WHERE account_no=?";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, accountNo);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                accountLabel.setText(
                        "Account Number: " +
                        rs.getInt("account_no")
                );

                nameLabel.setText(
                        "Name: " +
                        rs.getString("name")
                );

                ageLabel.setText(
                        "Age: " +
                        rs.getInt("age")
                );

                genderLabel.setText(
                        "Gender: " +
                        rs.getString("gender")
                );

                phoneLabel.setText(
                        "Phone: " +
                        rs.getString("phone")
                );

                emailLabel.setText(
                        "Email: " +
                        rs.getString("email")
                );

                addressLabel.setText(
                        "Address: " +
                        rs.getString("address")
                );

                typeLabel.setText(
                        "Account Type: " +
                        rs.getString("account_type")
                );

                balanceLabel.setText(
                        "Balance: ₹" +
                        rs.getDouble("balance")
                );
            }

            con.close();

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + ex.getMessage()
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == backButton) {

            dispose();
        }
    }
}
