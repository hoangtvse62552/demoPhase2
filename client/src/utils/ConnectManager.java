package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import main.ServerCfg;
import response.ResponseModel;

public class ConnectManager
{
    public ResponseModel getResponse(String xmlRq)
    {
        ServerCfg config = new ServerCfg();
        try (Socket clientSocket = new Socket(config.getServerIp(), config.getServerPort()); PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true); BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));)
        {
            // flush data to server
            writer.println(xmlRq);

            // getting response XML string
            StringBuilder xmlRp = new StringBuilder();
            String line = reader.readLine();
            while (!line.isEmpty())
            {
                xmlRp.append(line);
                line = reader.readLine();
            }

            XmlUtils xmlUtil = new XmlUtils();
            return xmlUtil.convertXmlToResponse(String.valueOf(xmlRp));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
