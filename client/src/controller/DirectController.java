package controller;

import ui.HomePage;
import ui.LoginPage;

public class DirectController
{
    LoginPage loginPage;
    HomePage  homePage;

    public DirectController()
    {
        super();
    }

    public void login(boolean isAdmin, LoginPage loginPage)
    {
        System.out.println("DirectController: Login success");
        this.loginPage = loginPage;
        this.homePage = new HomePage(isAdmin, this);
        this.loginPage.setVisible(false);
        this.homePage.setVisible(true);
    }

    public HomePage getHomePage()
    {
        return homePage;
    }

    public void logout()
    {
        loginPage.setVisible(true);
        homePage.setVisible(false);
    }
}
