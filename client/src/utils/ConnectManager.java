package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import main.ServerCfg;
import response.AccountResponse;
import response.BookResponse;
import response.ResponseModel;

public class ConnectManager
{
    public ResponseModel getResponse(ServerCfg serverCfg, String xmlRq)
    {
        try (Socket clientSocket = new Socket(serverCfg.getServerIp(), serverCfg.getServerPort()); PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true); BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));)
        {
            // flush data to server
            writer.print(xmlRq);

            // getting response XML string
            StringBuilder xmlRp = new StringBuilder();
            String line = reader.readLine();
            while (!line.isEmpty())
            {
                xmlRp.append(line);
                line = reader.readLine();
            }

            XmlUtils xmlUtil = new XmlUtils();
            ResponseModel response = xmlUtil.convertXmlToResponse(String.valueOf(xmlRp));
            if (response instanceof AccountResponse)
            {
                return (AccountResponse) response;
            }
            else
            {
                return (BookResponse) response;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
