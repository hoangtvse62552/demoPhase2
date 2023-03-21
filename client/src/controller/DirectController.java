package controller;

import ui.HomePage;
import ui.LoginPage;
import utils.LoggerUtils;

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

    public void alertServerStatus(boolean isConnected)
    {
        if (loginPage != null && loginPage.isVisible())
        {
            LoggerUtils.alert(loginPage, "Server cannot connected!", "Warning");
        }
        if (homePage != null && homePage.isVisible())
        {
            LoggerUtils.alert(homePage, "Server cannot connected!", "Warning");
        }
    }

    public void logout()
    {
        loginPage.setVisible(true);
        homePage.setVisible(false);
    }

    public void setLoginPage(LoginPage loginPage)
    {
        this.loginPage = loginPage;
    }

}
