package controller;

import ui.HomePage;
import ui.LoginPage;

public class DirectController
{
    LoginPage loginPage;
    HomePage  homepage;

    public DirectController()
    {
        super();
    }

    public void login(boolean isAdmin, LoginPage loginPage)
    {
        System.out.println("DirectController: Login success");
//        this.loginPage = loginPage;
//        homepage = new HomePage(isAdmin, this);
//        this.loginPage.setVisible(false);
//        homepage.setVisible(true);
    }

    public void logout()
    {
        loginPage.setVisible(true);
        homepage.setVisible(false);
    }
}
