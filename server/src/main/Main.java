package main;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import response.AccountResponse;
import response.ResponseModel;
import utils.Utils;

public class Main
{

    public static void main(String[] args)
    {
        System.out.println("Server is running...");
        Utils util = new Utils();
        // connect socket
        try (ServerSocket serverSocket = new ServerSocket(9090); Socket socket = serverSocket.accept();)
        {

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            ResponseModel responeModel = new AccountResponse();
            responeModel.setStatus("success");
            responeModel.setError("no error");
            writer.println(util.convertResponseToXml(responeModel));

        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // get input

        // convert input

        // get action

        // filter action -> controller
    }

}
