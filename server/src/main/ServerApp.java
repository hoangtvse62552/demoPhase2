package main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

                    String line = reader.readLine();
                    while (line != null && !line.isEmpty())
                    {
                        xmlString += line;
                        line = reader.readLine();
                    }

                    System.out.println(xmlString);
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
                        case "Create":
                            bookController.createBook(req);
                            break;
                        case "Search":
                            bookController.searchBooks(req);
                            break;
                        case "Update":
                            bookController.updateBook(req);
                            break;
                        case "Delete":
                            bookController.deleteBook(req);
                            break;
                        case "GetAuthor":
                            bookController.getAuthor();
                            break;
                        case "GetPublisher":
                            bookController.getPublisher();
                            break;
                        case "Ping":
                            bookController.ping();
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
            }
        }
        catch (Exception ex)
        {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println("Sv is closed");
    }

}
