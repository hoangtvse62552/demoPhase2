package logger;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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

    public void writeLog(StackTraceElement[] ex)
    {
        try
        {
            String direct = System.getProperty("user.dir");
            file = new FileHandler(direct + "\\log\\LogFile.log");
            log.addHandler(file);

            SimpleFormatter formatter = new SimpleFormatter();
            file.setFormatter(formatter);

            for (StackTraceElement stackTraceElement : ex)
            {
                log.info(stackTraceElement.toString());
            }
        }
        catch (Exception e)
        {
            System.out.println("Error Create LogFile: " + e);
        }
    }
}
