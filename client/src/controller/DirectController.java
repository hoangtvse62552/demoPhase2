package controller;

import lombok.Getter;
import lombok.Setter;
import ui.HomePage;
import ui.LoginPage;

public class DirectController
{
    private static volatile DirectController obj = null;
    private @Getter @Setter LoginPage        loginPage;
    private @Getter @Setter HomePage         homePage;

    private DirectController()
    {
    }

    public static DirectController getInstance()
    {
        if (obj == null)
        {
            synchronized (DirectController.class)
            {
                if (obj == null)
                {
                    obj = new DirectController();
                }
            }
        }
        return obj;
    }

    public void login()
    {
        System.out.println("DirectController: Login success");
        this.loginPage.setVisible(false);
        this.homePage.setVisible(true);
    }

    public void logout()
    {
        loginPage.setVisible(true)                  ;
        homePage.setVisible(false);
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
