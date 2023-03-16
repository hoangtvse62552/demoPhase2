package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import main.ServerCfg;
import model.Account;
import request.AccountRequest;
import response.AccountResponse;
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
        try (Socket clientSocket = new Socket(serverCfg.getServerIp(), serverCfg.getServerPort()))
        {

            OutputStream outputStream = clientSocket.getOutputStream();

            PrintWriter writer = new PrintWriter(outputStream, true);
            writer.print(xmlRq);

            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String responseXml = reader.readLine();
            System.out.println(responseXml + "/n response");

            AccountResponse accountResponse = (AccountResponse) util.convertXmlToResponse(responseXml);

            if ("success".equals(accountResponse.getStatus()))
            {
                account.setAdmin(accountResponse.isAdmin());
                account.setUsername(username);
                account.setPassword(password);
            }
            else
            {
                System.out.println(accountResponse.getError());
            }

        }
        catch (UnknownHostException ex)
        {

            System.out.println("Server not found: " + ex.getMessage());

        }
        catch (IOException ex)
        {

            System.out.println("I/O error: " + ex.getMessage());
        }

        return account;
    }
}
