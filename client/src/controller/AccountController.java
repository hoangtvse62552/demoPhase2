package controller;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Account;
import service.AccountService;

public class AccountController
{

    private JTextField     usernameField;

    private JPasswordField passwordField;

    private JLabel         lbError;

    public AccountController(JTextField usernameField, JPasswordField passwordField, JLabel lbError)
    {
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.lbError = lbError;
    }

    public Account login()
    {
        Account dto = null;
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if (!("".equals(username.trim()) || "".equals(password.trim())))
        {
            AccountService accountService = new AccountService();

            dto = accountService.login(username, password);
        }
        else
        {
            lbError.setText("Username or Password can not be empty");
            lbError.setVisible(true);
        }
        return dto;
    }

}
