package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import controller.AccountController;
import controller.BookController;
import logger.ServerLogger;
import request.RequestModel;
import utils.Utils;

public class MulDemoApp
{

    private static volatile boolean      isStopped;

    private static ServerSocket          server;

    private static final ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args)
    {
        try
        {
            server = new ServerSocket(9090);
            server.setSoTimeout(0);
            server.setReuseAddress(true);

            Socket client;
            while (!isStopped)
            {

                client = server.accept();

                System.out.println("New client connected: " + client.getInetAddress().getHostAddress());
                threadPool.execute(new ClientHandler(client));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            ServerLogger.getInstance().writeLog(e);
        }

    }

    /**
     * Power off server and shut down thread pool.
     * If server is null, then only shut down thread pool.
     * Write log into log file if occurs an IOException.
     */
    public synchronized void stop()
    {
        System.out.println("Stopping server...");
        isStopped = true;
        if (server != null)
        {
            try
            {
                server.close();
                System.out.println("Sv is closed");
            }
            catch (IOException e)
            {
                ServerLogger.getInstance().writeLog(e);
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
    }

    /**
     * ClientHandler class: handle xml request from client.
     * Return a XML response denpend on XML request from client.
     * Binding client socket to handle XML request and then depend on request's action to invoke correcsponding controller handle method.
     */
    private static class ClientHandler implements Runnable
    {
        private final Socket clientSocket;

        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
        }

        public void run()
        {
            System.out.println("Server is running ...");
            AccountController accountController = null;
            BookController bookController = null;

            StringBuilder xmlString = new StringBuilder();
            try (OutputStream os = clientSocket.getOutputStream(); InputStream is = clientSocket.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(is)))
            {
                accountController = new AccountController(os);
                bookController = new BookController(os);

                String line = reader.readLine();
                while (line != null && !line.isEmpty())
                {
                    xmlString.append(line);
                    line = reader.readLine();
                }
                System.out.println(xmlString.toString());

                Utils utils = new Utils();
                if (xmlString.length() > 0)
                {
                    RequestModel req = utils.convertXmlToRequest(xmlString.toString());
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
                    default:
                        ServerLogger.getInstance().writeLog(new IllegalArgumentException("Unexpected value: " + req.getAction()));
                        System.err.println("Unexpected value: " + req.getAction());
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
                ServerLogger.getInstance().writeLog(e);
            }

        }
    }
}