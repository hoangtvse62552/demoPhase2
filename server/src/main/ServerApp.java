package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import controller.AccountController;
import controller.BookController;
import request.RequestModel;
import utils.Utils;

public class ServerApp
{
    private static OutputStream os;
    private static InputStream  is;
    private static Utils        utils;

    public static void main(String[] args)
    {
        System.out.println("Server is running...");
        utils = new Utils();

        // connect socket
        try (ServerSocket serverSocket = new ServerSocket(9090))
        {

            System.out.println("Server is listening on port " + 9090);

            while (true)
            {
                Socket socket = serverSocket.accept();

                // get input
                is = socket.getInputStream();
                os = socket.getOutputStream();
                AccountController accountController = new AccountController(os);
                BookController bookController = new BookController(os);

                try
                {
                    String xmlString = "";
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line = "";

                    while (!(line = reader.readLine()).isEmpty())
                    {
                        System.out.println(line);
                        xmlString += line;
                    }
                    System.out.println("xml request:" + xmlString);
                    if (xmlString.length() > 0)
                    {
                        RequestModel req = utils.convertXmlToRequest(xmlString);
                        switch (req.getAction())
                        {
                        case "Login":
                            accountController.login(req);
                            break;
                        case "GetBook":
                            bookController.getBooks();
                            break;
                        default:
                            throw new IllegalArgumentException("Unexpected value: " + req.getAction());
                        }
                    }
                }
                catch (Exception e)
                {
                    System.out.println(e);
                    e.printStackTrace();
                }

//                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
//
//                ResponseModel accountResponseModel = new AccountResponse();
//                accountResponseModel.setStatus("success");
//
//                writer.println(utils.convertResponseToXml(accountResponseModel));
            }

        }
        catch (Exception ex)
        {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println("Sv closed");
    }

}
