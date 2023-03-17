package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import response.AccountResponse;
import response.ResponseModel;
import utils.Utils;

public class ServerApp
{

    public static void main(String[] args)
    {
        System.out.println("Server is running...");
        Utils util = new Utils();
        // connect socket
        try (ServerSocket serverSocket = new ServerSocket(9090))
        {

            System.out.println("Server is listening on port " + 9090);

            while (true)
            {
                Socket socket = serverSocket.accept();

                System.out.println("New client connected");

                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

                ResponseModel accountResponseModel = new AccountResponse();
                accountResponseModel.setStatus("success");

                writer.println(util.convertResponseToXml(accountResponseModel));
            }

        }
        catch (IOException ex)
        {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
        System.out.println("Sv closed");
    }

}
