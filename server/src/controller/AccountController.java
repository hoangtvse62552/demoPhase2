package controller;

import java.io.OutputStream;
import java.io.PrintWriter;

import model.Account;
import request.AccountRequest;
import request.RequestModel;
import response.AccountResponse;
import service.AccountService;
import utils.Utils;

public class AccountController
{
    private Utils        utils = new Utils();
    private OutputStream os    = null;

    public AccountController(OutputStream os)
    {
        super();
        this.os = os;
    }

    public void login(RequestModel request)
    {
        AccountResponse resp = new AccountResponse();
        try
        {
            AccountRequest req = (AccountRequest) request;
            String username = req.getUsername();
            String password = req.getPassword();

            AccountService sv = new AccountService();
            Account dto = sv.login(username, password);

            if (dto != null)
            {
                resp.setError("");
                resp.setStatus("Success");
                resp.setAdmin(dto.isAdmin());
            }
            else
            {
                resp.setStatus("Fail");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            sendResponse(resp);
        }
    }

    private void sendResponse(AccountResponse resp)
    {
        String responseStr = utils.convertResponseToXml(resp);
        PrintWriter writer = new PrintWriter(os, true);
        writer.println(responseStr);
    }
}
