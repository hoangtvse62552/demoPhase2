package logger;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import utils.DatetimeUtils;
import utils.XmlUtils;

public class ServerLogger
{
    private static ServerLogger logger = null;
    private Logger              log    = Logger.getLogger("MyLog");
    private FileHandler         file;

    public static ServerLogger getInstance()
    {
        if (logger == null)
            logger = new ServerLogger();

        return logger;
    }

    /**
     * For logging exception.
     * write exception into working directory + "/log/exception.log"
     * 
     * @param e: exception
     */
    public void writeLog(Exception e)
    {
        String direct = System.getProperty("user.dir");
        log.info("===============================================================");
        log.info(DatetimeUtils.getCurrentDateTime());
        try
        {
            file = new FileHandler(direct + "/log/exception.log");
            log.addHandler(file);

            SimpleFormatter formatter = new SimpleFormatter();
            file.setFormatter(formatter);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        log.warning(e.toString());

        for (StackTraceElement stackTraceElement : e.getStackTrace())
        {
            log.info(stackTraceElement.toString());
        }

    }

    /**
     * For logging XML request, response model
     * write xml string into working directory + "/log/xml.log"
     * 
     * @param <T>: request or response model
     */
    public <T> void writeLog(T t)
    {
        log.info("===============================================================");
        log.info(DatetimeUtils.getCurrentDateTime());
        try
        {
            String direct = System.getProperty("user.dir");
            file = new FileHandler(direct + "/log/xml.log");
            log.addHandler(file);

            SimpleFormatter formatter = new SimpleFormatter();
            file.setFormatter(formatter);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        XmlUtils<T> xmlUtils = new XmlUtils<>();
        log.info(xmlUtils.convertObjectToXml(t));
    }

    /**
     * For logging XML request, response string
     * write xml string into working directory + "/log/xml.log"
     * 
     * @param xmlStr: xml string
     */
    public void writeLog(String xmlStr)
    {
        log.info("===============================================================");
        log.info(DatetimeUtils.getCurrentDateTime());
        try
        {
            String direct = System.getProperty("user.dir");
            file = new FileHandler(direct + "/log/xml.log");
            log.addHandler(file);

            SimpleFormatter formatter = new SimpleFormatter();
            file.setFormatter(formatter);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        log.info(xmlStr);
    }
}
