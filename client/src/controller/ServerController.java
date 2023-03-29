package controller;

import lombok.Setter;
import ui.HomePage;
import ui.LoginPage;
import utils.ConnectManager;
import utils.LoggerUtils;

public class ServerController
{

    private @Setter LoginPage loginPage;
    private @Setter HomePage  homePage;

    public ServerController()
    {
    }

    /**
     * Test connection between client and server.
     */
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
            LoggerUtils.alert(loginPage, "Server cannot connected!");
        }
        if (homePage != null && homePage.isVisible())
        {
            LoggerUtils.alert(homePage, "Server cannot connected!");
        }
    }
}
