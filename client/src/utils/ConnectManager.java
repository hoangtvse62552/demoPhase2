package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import main.ClientConfig;
import response.ResponseModel;

public class ConnectManager
{
    public ResponseModel getResponse(String xmlRq)
    {
        try (Socket clientSocket = new Socket(ClientConfig.getInstance().getServerIp(), ClientConfig.getInstance().getServerPort()); PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true); BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));)
        {
            // flush data to server
            writer.println(xmlRq);

            // getting response XML string
            StringBuilder xmlRp = new StringBuilder();
            String line = reader.readLine();
            while (!line.isEmpty())
            {
                System.out.println(line);
                xmlRp.append(line);
                line = reader.readLine();
            }

            XmlUtils xmlUtil = new XmlUtils();
            return (ResponseModel) xmlUtil.convertXmlToObject(String.valueOf(xmlRp));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            // ClientLogger.getInstance().writeLog(e);
        }
        return null;
    }

    public Socket sendRequest(String xmlRq)
    {
        Socket clientSocket = null;
        PrintWriter writer = null;
        try
        {
            clientSocket = new Socket(ClientConfig.getInstance().getServerIp(), ClientConfig.getInstance().getServerPort());
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
            // flush data to server
            writer.println(xmlRq);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
//            ClientLogger.getInstance().writeLog(e);
        }
        return clientSocket;
    }

    public ResponseModel getResponse(Socket clientSocket)
    {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));)
        {
            // getting response XML string
            StringBuilder xmlRp = new StringBuilder();
            String line = reader.readLine();
            while (!line.isEmpty())
            {
                System.out.println(line);
                xmlRp.append(line);
                line = reader.readLine();
            }
            XmlUtils xmlUtil = new XmlUtils();
            return (ResponseModel) xmlUtil.convertXmlToObject(String.valueOf(xmlRp));
        }
        catch (Exception e)
        {
            e.printStackTrace();
//            ClientLogger.getInstance().writeLog(e);
        }
        finally
        {
            try
            {
                if (clientSocket != null)
                {
                    clientSocket.close();
                }
            }
            catch (Exception e2)
            {
//                ClientLogger.getInstance().writeLog(e2);
            }
        }
        return null;
    }

    public boolean pingServer()
    {
        ClientConfig serverCfg = new ClientConfig();
        try (Socket socket = new Socket())
        {
            InetSocketAddress address = new InetSocketAddress(serverCfg.getServerIp(), serverCfg.getServerPort());
            socket.connect(address, 1000); // timeout in milliseconds

            System.out.println("=================================");
            System.out.println("Server is up and reachable");
        }
        catch (Exception e)
        {
            System.out.println("=================================");
            System.out.println("Server is down or unreachable");
            // ClientLogger.getInstance().writeLog(e);
            return false;
        }
        return true;
    }
}
