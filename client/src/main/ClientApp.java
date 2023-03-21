package main;

import controller.DirectController;
import logger.ClientLogger;
import ui.LoginPage;
import utils.ConnectManager;

public class ClientApp
{
    private static DirectController controller;

    public static void main(String[] args)
    {
        controller = new DirectController();
        LoginPage loginPage = new LoginPage(controller);
        loginPage.setVisible(true);

        Thread serverPingThread = new Thread()
        {
            public void run()
            {
                ConnectManager connectManager = new ConnectManager();
                while (true)
                {
                    boolean flag = connectManager.pingServer();

                    if (!flag)
                    {
                        System.out.println("Server cannot connect!");
                    }
                    try
                    {
                        Thread.sleep(5000);
                    }
                    catch (InterruptedException e)
                    {
                        ClientLogger.getInstance().writeLog(e.getStackTrace());
                        e.printStackTrace();
                    }
                }
            }
        };
        serverPingThread.start();

    }
}
