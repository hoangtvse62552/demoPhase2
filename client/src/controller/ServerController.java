package controller;

import java.net.Socket;

import lombok.Setter;
import request.PingRequest;
import request.RequestModel;
import response.PingResponse;
import response.ResponseModel;
import ui.HomePage;
import ui.LoginPage;
import utils.ConnectManager;
import utils.LoggerUtils;
import utils.XmlUtils;

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
        RequestModel<PingRequest> rq = new RequestModel<>();
        rq.setAction("Ping");
        XmlUtils<RequestModel<PingRequest>> util = new XmlUtils<>();
        String xmlRq = util.convertObjectToXml(rq);
        Socket socket = connectManager.sendRequest(xmlRq);

        if (socket != null)
        {
            ResponseModel<PingResponse> resp = connectManager.getResponse(socket);
        }
        else
        {
            alertServerStatus();
        }
//        ConnectManager connectManager = new ConnectManager();
//        boolean isConnected = connectManager.pingServer();
//        if (!isConnected)
//        {
//            alertServerStatus();
//        }
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

    public LoginPage getLoginPage()
    {
        return loginPage;
    }

    public void setLoginPage(LoginPage loginPage)
    {
        this.loginPage = loginPage;
    }

    public HomePage getHomePage()
    {
        return homePage;
    }

    public void setHomePage(HomePage homePage)
    {
        this.homePage = homePage;
    }

}
