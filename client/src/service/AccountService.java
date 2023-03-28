package service;

import model.Account;
import request.AccountRequest;
import request.RequestModel;
import response.AccountResponse;
import response.ResponseModel;
import utils.ConnectManager;
import utils.XmlUtils;

/**
 * Account service
 * 
 * @author ttl
 *
 */
public class AccountService
{
    public Account login(String username, String password)
    {
        Account account = new Account();

        RequestModel<AccountRequest> rq = new RequestModel<>();
        AccountRequest accountReq = new AccountRequest();
        accountReq.setPassword(password);
        accountReq.setUsername(username);
        rq.setData(accountReq);
        rq.setAction("Login");

        XmlUtils<RequestModel<AccountRequest>> util = new XmlUtils<>();
        String xmlRq = util.convertObjectToXml(rq);

        System.out.println(xmlRq);
        // Get connection
        ConnectManager connectManager = new ConnectManager();

        ResponseModel<AccountResponse> accountResponse = connectManager.getResponse(xmlRq);

        if (accountResponse != null)
        {
            if ("Success".equals(accountResponse.getStatus()))
            {
                account.setAdmin(accountResponse.getResult().isAdmin());
            }
            else
            {
                System.out.println(accountResponse.getError());
                return null;
            }
        }

        return account;
    }
}
