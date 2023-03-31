package logger;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import utils.DatetimeUtils;
import utils.XmlUtils;

/**
 * Manage the logging of application.
 * Custom date-time of logger
 */
public class ClientLogger
{
    private static ClientLogger logger = null;
    private static Logger       log    = Logger.getLogger("MyLog");
    private FileHandler         file;

    public static ClientLogger getInstance()
    {
        if (logger == null)
        {
            logger = new ClientLogger();

            Calendar calendar = Calendar.getInstance();
            // LocalDateTime customDate = LocalDateTime.parse(DatetimeUtils.getCurrentDateTime(), DateTimeFormatter.ISO_DATE_TIME);

            LogRecord customRecord = new LogRecord(Level.ALL, "Custom timestamp log");
            customRecord.setInstant(Instant.ofEpochMilli(calendar.getTimeInMillis()));

            log.log(customRecord);
        }

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
        // log.info(DatetimeUtils.getCurrentDateTime());
        try
        {
            file = new FileHandler(direct + "/log/exception.log");
            log.addHandler(file);

            // SimpleFormatter formatter = new SimpleFormatter();
            // file.setFormatter(formatter);
            file.setFormatter(new SimpleFormatter()
            {
                private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

                // Override format method
                @Override
                public synchronized String format(LogRecord logRecord)
                {
                    return String.format(format, new Date(DatetimeUtils.getCurrentDateTime()), logRecord.getLevel().getLocalizedName(), logRecord.getMessage());
                }
            });
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
        // log.info(DatetimeUtils.getCurrentDateTime());
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
        // log.info(DatetimeUtils.getCurrentDateTime());
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
