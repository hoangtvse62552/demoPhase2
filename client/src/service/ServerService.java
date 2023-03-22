package service;

import ui.HomePage;
import ui.LoginPage;
import utils.LoggerUtils;

public class ServerService
{

    public void alertServerStatus(boolean isConnected, LoginPage loginPage, HomePage homePage)
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
