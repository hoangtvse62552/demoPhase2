package service;

import java.net.Socket;

import logger.ClientLogger;
import request.PingRequest;
import request.RequestModel;
import utils.ConnectManager;
import utils.DatetimeUtils;
import utils.XmlUtils;

/**
 * Manage the connection between server and client
 * Don't log exception when server is down.
 */
public class ServerService {

    private final ClientLogger logger = ClientLogger.getInstance();

    /**
     * Test server connection.
     * 
     * @return true if have connection, or false if on the opposite.
     */
    public boolean pingServer() {

        ConnectManager connectManager = new ConnectManager();

        RequestModel<PingRequest> rq = new RequestModel<>();
        rq.setAction("Ping");

        XmlUtils<RequestModel<PingRequest>> util = new XmlUtils<>();

        String xmlRq = util.convertObjectToXml(rq);
        Socket socket = connectManager.sendRequest(xmlRq);

        if (socket == null) {
            logger.writeLog(new NullPointerException("Server is down at: " + DatetimeUtils.getCurrentDateTime()));
            return false;
        }

        return true;
    }
}
