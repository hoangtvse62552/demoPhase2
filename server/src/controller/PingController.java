package controller;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import logger.ServerLogger;
import response.PingResponse;
import response.ResponseModel;
import utils.Utils;

public class PingController
{
    private Utils<PingResponse> utilsResponse = new Utils<>();
    private OutputStream        os            = null;

    public PingController(OutputStream os)
    {
        super();
        this.os = os;
    }

    public void ping()
    {
        ResponseModel<PingResponse> resp = new ResponseModel<>();
        try
        {
            PingResponse ping = new PingResponse();
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
            ping.setTime(timeStamp);
            resp.setResult(ping);
            resp.setStatus("Success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resp.setStatus("Fail");
            ServerLogger.getInstance().writeLog(e);
        }
        finally
        {
            resp.setAction("Ping");
            String responseStr = utilsResponse.convertObjectToXml(resp);
            PrintWriter writer = new PrintWriter(os, true);
            writer.println(responseStr);
        }
    }
}
