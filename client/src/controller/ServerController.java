package controller;

import ui.HomePage;
import ui.LoginPage;
import utils.ConnectManager;
import utils.LoggerUtils;

public class ServerController
{

    private LoginPage loginPage;
    private HomePage  homePage;

    public ServerController(LoginPage loginPage, HomePage homePage)
    {
        this.loginPage = loginPage;
        this.homePage = homePage;
    }

    public void pingServer()
    {
        ConnectManager connectManager = new ConnectManager();
        boolean isConnected = connectManager.pingServer();
        if (!isConnected)
        {
            alertServerStatus();
        }
    }

    /**
     * Use to alert warning popup in current window of application.
     */
    private void alertServerStatus()
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
}