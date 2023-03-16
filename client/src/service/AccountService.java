package service;

import model.Account;
import request.AccountRequest;
import utils.Utils;

public class AccountService
{
    public Account login(String username, String password)
    {
        AccountRequest rq = new AccountRequest();
        rq.setAction("Login");
        rq.setPassword(password);
        rq.setUsername(username);

        Utils util = new Utils();
        String xmlRq = util.convertRequestToXml(rq);
        
        
        System.out.println(xmlRq);
        return null;
    }
}
