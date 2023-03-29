package controller;

import service.ServerService;
import ui.HomePage;
import ui.LoginPage;
import utils.LoggerUtils;

public class ServerController {

    private LoginPage loginPage;
    private HomePage homePage;
    private ServerService serverService;

    public ServerController() {
        serverService = new ServerService();
    }

    /**
     * Test connection between client and server.
     */
    public void pingServer() {
        boolean isConnected = serverService.pingServer();

        if (!isConnected) {
            alertServerStatus();
        }
    }

    /**
     * Use to alert warning popup in current window of application.
     */
    private void alertServerStatus() {
        if (loginPage != null && loginPage.isVisible()) {
            LoggerUtils.alert(loginPage, "Server cannot connected!");
        }
        if (homePage != null && homePage.isVisible()) {
            LoggerUtils.alert(homePage, "Server cannot connected!");
        }
    }

    public LoginPage getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public HomePage getHomePage() {
        return homePage;
    }

    public void setHomePage(HomePage homePage) {
        this.homePage = homePage;
    }

}
