package ui;

import javax.swing.*;

import controller.AccountController;
import controller.DirectController;
import model.Account;

import java.awt.*;
import java.awt.event.*;

public class LoginPage extends Frame implements ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField        txtUsername;
    private JPasswordField    txtPassword;
    private JButton           btnLogin;
    private JLabel            lbError;
    private DirectController  controller;

    private final JTextField  txtError         = new JTextField();

    public LoginPage(DirectController controller)
    {
        this.controller = controller;
        txtError.setColumns(10);
        JLabel lbUsername = new JLabel("Username");
        lbUsername.setBounds(50, 50, 100, 20);

        txtUsername = new JTextField();
        txtUsername.setBounds(150, 50, 150, 20);

        JLabel lbPassword = new JLabel("Password");
        lbPassword.setBounds(50, 100, 100, 20);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(150, 100, 150, 20);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(70, 150, 150, 20);
        btnLogin.addActionListener(this);

        lbError = new JLabel("Error");
        lbError.setBounds(50, 170, 300, 20);
        lbError.setVisible(false);

        txtUsername.setText("admin");
        txtPassword.setText("1234");

        add(lbUsername);
        add(txtUsername);
        add(lbPassword);
        add(txtPassword);
        add(btnLogin);
        add(lbError);
        setSize(400, 300);
        setBounds(800, 300, 350, 250);
        setLayout(null);
//        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {

        if (e.getSource() == btnLogin)
        {
            String password = txtPassword.getText();
            String username = txtUsername.getText();
            if (!password.trim().equals("") && !username.trim().equals(""))
            {
                lbError.setVisible(false);
                AccountController manager = new AccountController();
                Account dto = manager.login(username, password);
                if (dto != null)
                {
                    System.out.println("Login Successl");
                    // chuyển map
                    controller.login(dto.isAdmin(), this);
                }
                else
                {
                    lbError.setText("Username or Password is wrong!");
                    lbError.setVisible(true);
                }

            }
            else
            {
                lbError.setText("Username or Password can not be empty");
                lbError.setVisible(true);
            }
        }
    }
}
