package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.AccountController;
import controller.DirectController;
import model.Account;

public class LoginPage extends JFrame implements ActionListener
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField        txtUsername;
    private JPasswordField    txtPassword;
    private JButton           btnLogin;
    private JLabel            lbError;
    private DirectController  directController;

    private final JTextField  txtError         = new JTextField();

    public LoginPage(DirectController directController)
    {
        this.directController = directController;
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
            String password = String.valueOf(txtPassword.getPassword());
            String username = txtUsername.getText();
            if (!password.trim().equals("") && !username.trim().equals(""))
            {
                lbError.setVisible(false);
                AccountController accController = new AccountController();
                Account dto = accController.login(username, password);
                if (dto != null)
                {
                    System.out.println("Login Successl");
                    // chuyển map
                    directController.login(dto.isAdmin(), this);
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
