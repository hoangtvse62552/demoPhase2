package main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import controller.AccountController;
import controller.BookController;
import logger.ServerLogger;
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
        System.out.println(System.getProperty("user.dir"));

        utils = new Utils();

        // connect socket
        try (ServerSocket serverSocket = new ServerSocket(9090))
        {

            System.out.println("Server is listening on port " + 9090);

            Socket socket;
            while (true)
            {
                socket = serverSocket.accept();

                // get input
                is = socket.getInputStream();
                os = socket.getOutputStream();
                AccountController accountController = new AccountController(os);
                BookController bookController = new BookController(os);
                try
                {
                    StringBuilder xmlString = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line = reader.readLine();

                    while (line != null && !line.isEmpty())
                    {
                        System.out.println(line);
                        xmlString.append(line);
                        line = reader.readLine();
                    }
                    System.out.println(xmlString);
                    if (xmlString.length() > 0)
                    {
                        RequestModel req = (RequestModel) utils.convertXmlToObject(xmlString.toString());
                        switch (req.getAction())
                        {
                        case "Login":
                            accountController.login(xmlString.toString());
                            break;
                        case "GetBook":
                            bookController.getBooks();
                            break;
                        case "Create":
                            bookController.createBook(xmlString.toString());
                            break;
                        case "Search":
                            bookController.searchBooks(xmlString.toString());
                            break;
                        case "Update":
                            bookController.updateBook(xmlString.toString());
                            break;
                        case "Delete":
                            bookController.deleteBook(xmlString.toString());
                            break;
                        case "GetAuthor":
                            bookController.getAuthor();
                            break;
                        case "GetPublisher":
                            bookController.getPublisher();
                            break;
                        case "Ping":
                            bookController.getPublisher();
                            break;
                        default:
                            throw new IllegalArgumentException("Unexpected value: " + req.getAction());
                        }
                    }
                }
                catch (Exception e)
                {
                    ServerLogger.getInstance().writeLog(e);
                    e.printStackTrace();
                }
            }
        }
        catch (Exception ex)
        {
            ServerLogger.getInstance().writeLog(ex);
            ex.printStackTrace();
        }
        System.out.println("Sv is closed");
    }

}
