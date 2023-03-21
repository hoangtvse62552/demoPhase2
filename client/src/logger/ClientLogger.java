package logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ClientLogger
{
    private static ClientLogger logger = null;
    private Logger              log    = Logger.getLogger("MyLog");
    private FileHandler         file;

    public static ClientLogger getInstance()
    {
        if (logger == null)
            logger = new ClientLogger();

        return logger;
    }

//    public void writeLog(StackTraceElement[] ex)
//    {
//        try
//        {
//            String direct = System.getProperty("user.dir");
//            file = new FileHandler(direct + "/log/logfile.log");
//            log.addHandler(file);
//
//            SimpleFormatter formatter = new SimpleFormatter();
//            file.setFormatter(formatter);
//
//            for (StackTraceElement stackTraceElement : ex)
//            {
//                log.info(stackTraceElement.toString());
//            }
//        }
//        catch (Exception e)
//        {
//            System.out.println("Error Create LogFile: " + e);
//        }
//    }

    public void writeLog(Exception e)
    {
        try
        {
            String direct = System.getProperty("user.dir");
            file = new FileHandler(direct + "/log/logfile.log");
            log.addHandler(file);

            SimpleFormatter formatter = new SimpleFormatter();
            file.setFormatter(formatter);

        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
        }

        log.warning(e.toString());

        for (StackTraceElement stackTraceElement : e.getStackTrace())
        {
            log.info(stackTraceElement.toString());
        }

        log.info("===============================================================");
    }
}
