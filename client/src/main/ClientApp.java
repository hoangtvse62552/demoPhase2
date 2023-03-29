package main;

import controller.DirectController;
import controller.ServerController;
import ui.LoginPage;

public class ClientApp
{

    public static void main(String[] args)
    {
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
        DirectController directController = DirectController.getInstance();
        directController.setLoginPage(loginPage);

        // New simultaneously thread. It is used to test connection between client and server.
        new Thread()
        {
            public void run()
            {
                System.out.println("Main app: ==============");
                System.out.println(directController.getHomePage());
                ServerController serverController = new ServerController();
                while (true)
                {
                    serverController.setLoginPage(loginPage);
                    serverController.setHomePage(directController.getHomePage());
                    serverController.pingServer();
                    try
                    {
                        Thread.sleep(5000);
                    }
                    catch (InterruptedException e)
                    {
                        // ClientLogger.getInstance().writeLog(e);
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
