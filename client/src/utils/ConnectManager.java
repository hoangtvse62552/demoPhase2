package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import main.ServerCfg;
import response.ResponseModel;

public class ConnectManager {
    public ResponseModel getResponse(String xmlRq) {
        ServerCfg serverCfg = new ServerCfg();
        try (Socket clientSocket = new Socket(serverCfg.getServerIp(), serverCfg.getServerPort());
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
            // flush data to server
            writer.println(xmlRq);

            // getting response XML string
            StringBuilder xmlRp = new StringBuilder();
            String line = reader.readLine();
            while (!line.isEmpty()) {
                xmlRp.append(line);
                line = reader.readLine();
            }

            XmlUtils xmlUtil = new XmlUtils();
            return xmlUtil.convertXmlToResponse(String.valueOf(xmlRp));
        } catch (Exception e) {
            e.printStackTrace();
            // ClientLogger.getInstance().writeLog(e);
        }
        return null;
    }

    public boolean pingServer() {
        ServerCfg serverCfg = new ServerCfg();
        try (Socket socket = new Socket()) {
            InetSocketAddress address = new InetSocketAddress(serverCfg.getServerIp(), serverCfg.getServerPort());
            socket.connect(address, 1000); // timeout in milliseconds

            System.out.println("=================================");
            System.out.println("Server is up and reachable");
        } catch (Exception e) {
            System.out.println("=================================");
            System.out.println("Server is down or unreachable");
            // ClientLogger.getInstance().writeLog(e);
            return false;
        }
        return true;
    }
}
