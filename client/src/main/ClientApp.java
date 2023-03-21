package main;

import controller.DirectController;
import ui.LoginPage;
import utils.ConnectManager;

public class ClientApp
{
    private static DirectController controller;

    private static LoginPage        loginPage;

    public static void main(String[] args)
    {
        controller = new DirectController();

        new Thread()
        {
            public void run()
            {
                loginPage = new LoginPage(controller);
                loginPage.setVisible(true);
            }
        }.start();

        new Thread()
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
                        controller.alertServerStatus(!flag);
                    }

                    try
                    {
                        Thread.sleep(5000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
