package controller;

import java.io.OutputStream;
import java.io.PrintWriter;

import logger.ServerLogger;
import model.Account;
import request.AccountRequest;
import request.RequestModel;
import response.AccountResponse;
import response.ResponseModel;
import service.AccountService;
import utils.XmlUtils;

public class AccountController
{
    private XmlUtils<AccountResponse> utilsResponse = new XmlUtils<>();
    private XmlUtils<AccountRequest>  utilsReq      = new XmlUtils<>();
    private OutputStream              os            = null;

    public AccountController(OutputStream os)
    {
        super();
        this.os = os;
    }

    public void login(String xmlString)
    {

        ResponseModel<AccountResponse> resp = new ResponseModel<>();
        try
        {
            RequestModel<AccountRequest> req = (RequestModel<AccountRequest>) utilsReq.convertXmlToObject(xmlString);
            String username = req.getData().getUsername();
            String password = req.getData().getPassword();

            AccountService sv = new AccountService();
            Account dto = sv.login(username, password);

            AccountResponse accResp = new AccountResponse();

            if (dto != null)
            {
                resp.setError("");
                resp.setStatus("Success");
                accResp.setAdmin(dto.isAdmin());
            }
            else
            {
                resp.setStatus("Fail");
            }
            resp.setAction(req.getAction());
            resp.setResult(accResp);
        }
        catch (Exception e)
        {
            ServerLogger.getInstance().writeLog(e);
            e.printStackTrace();
        }
        finally
        {
            sendResponse(resp);
        }
    }

    private void sendResponse(ResponseModel<AccountResponse> resp)
    {
        String responseStr = utilsResponse.convertObjectToXml(resp);
        PrintWriter writer = new PrintWriter(os, true);
        writer.println(responseStr);
    }
}
