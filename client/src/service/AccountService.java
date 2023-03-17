package service;

import main.ServerCfg;
import model.Account;
import request.AccountRequest;
import response.AccountResponse;
import utils.ConnectManager;
import utils.XmlUtils;

public class AccountService
{
    public Account login(String username, String password)
    {
        Account account = new Account();

        AccountRequest rq = new AccountRequest();
        rq.setAction("Login");
        rq.setPassword(password);
        rq.setUsername(username);

        XmlUtils util = new XmlUtils();
        String xmlRq = util.convertRequestToXml(rq);
        System.out.println(xmlRq);

        // Get connection
        ServerCfg serverCfg = new ServerCfg("127.0.0.1", 9090);
        ConnectManager connectManager = new ConnectManager();

        AccountResponse accountResponse = (AccountResponse) connectManager.getResponse(serverCfg, xmlRq);

        if ("Success".equals(accountResponse.getStatus()))
        {
            account.setAdmin(accountResponse.isAdmin());
        }
        else
        {
            System.out.println(accountResponse.getError());
            return null;
        }
        return account;
    }
}
