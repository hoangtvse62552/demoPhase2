package controller;

import model.*;
import service.AccountService;

public class AccountController
{

    public Account login(String username, String password)
    {
        AccountService sv = new AccountService();

        Account dto = sv.login(username, password);

        return dto;
    }

}
