package main;

import controller.DirectController;
import controller.ServerController;
import logger.ClientLogger;
import ui.LoginPage;

public class ClientApp
{
    private static DirectController controller;

    private static LoginPage        loginPage;

    public static void main(String[] args)
    {
        controller = new DirectController();
        loginPage = new LoginPage(controller);
        loginPage.setVisible(true);

        new Thread()
        {
            public void run()
            {
                ServerController serverController = new ServerController(loginPage, controller.getHomePage());
                while (true)
                {
                    serverController.pingServer();
                    try
                    {
                        Thread.sleep(5000);
                    }
                    catch (InterruptedException e)
                    {
                        ClientLogger.getInstance().writeLog(e);
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
