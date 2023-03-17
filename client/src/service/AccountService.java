package service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import main.ServerCfg;
import model.Account;
import request.AccountRequest;
import response.AccountResponse;
import response.ResponseModel;
import utils.Utils;

public class AccountService
{
    public Account login(String username, String password)
    {
        Account account = new Account();

        AccountRequest rq = new AccountRequest();
        rq.setAction("Login");
        rq.setPassword(password);
        rq.setUsername(username);

        Utils util = new Utils();
        String xmlRq = util.convertRequestToXml(rq);
        System.out.println(xmlRq);

        // Get connection
        ServerCfg serverCfg = new ServerCfg("127.0.0.1", 9090);
        try (Socket clientSocket = new Socket(serverCfg.getServerIp(), serverCfg.getServerPort()); PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true); BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));)
        {

            // flush data to server
            writer.print(xmlRq);

            // getting response XML string
            StringBuilder responseXML = new StringBuilder();
            String line;
            String xmlResp = "";
            while (!"".equals((line = reader.readLine())))
            {
                responseXML.append(line).append(System.lineSeparator());
                xmlResp += line;
            }

            System.out.println(responseXML);
            AccountResponse accountResponse = (AccountResponse) util.convertXmlToResponse(xmlResp);

            if ("success".equals(accountResponse.getStatus()))
            {
                account.setAdmin(accountResponse.isAdmin());
                account.setUsername(username);
                account.setPassword(password);
            }
            else
            {
                System.out.println(accountResponse.getError());
                return null;

            }

        }
        catch (Exception ex)
        {

            System.out.println("Server not found: " + ex.getMessage());

        }

        return account;
    }
}
