package controller;

import ui.*;

public class DirectController
{
    LoginPage loginPage;
    HomePage  homepage;

    public DirectController()
    {
        super();
    }

    public void login(boolean isAdmin, ui.LoginPage loginPage)
    {
        this.loginPage = loginPage;
        homepage = new HomePage(isAdmin, this);
        this.loginPage.setVisible(false);
        homepage.setVisible(true);
    }

    public void logout()
    {
        loginPage.setVisible(true);
        homepage.setVisible(false);
    }
}
