package main;

import controller.DirectController;
import ui.LoginPage;

public class ClientApp
{
    private static DirectController controller;

    public static void main(String[] args)
    {
        controller = new DirectController();
        LoginPage loginPage = new LoginPage(controller);
        loginPage.setVisible(true);

        
    }
}
