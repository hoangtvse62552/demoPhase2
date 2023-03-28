package ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.AccountController;
import controller.DirectController;
import model.Account;

public class LoginPage extends JFrame
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField        txtUsername;
    private JPasswordField    txtPassword;
    private JButton           btnLogin;
    private JLabel            lbError;

    private final JTextField  txtError         = new JTextField();

    public LoginPage()
    {
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
        btnLogin.addActionListener(e -> loginActionPerformed());

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
    }

    public void loginActionPerformed()
    {
        AccountController accountController = new AccountController(txtUsername, txtPassword, lbError);
        Account dto = accountController.login();
        if (dto != null)
        {
            DirectController directController = DirectController.getInstance();
            directController.setHomePage(new HomePage(dto.isAdmin()));
            directController.login();
        }
        else
        {
            lbError.setText("Username or Password is wrong!");
            lbError.setVisible(true);
        }
    }
}
