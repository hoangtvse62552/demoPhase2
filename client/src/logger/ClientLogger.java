package logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ClientLogger {
    private static ClientLogger logger = null;
    private Logger log = Logger.getLogger("MyLog");
    private FileHandler file;

    public static ClientLogger getInstance() {
        if (logger == null)
            logger = new ClientLogger();

        return logger;
    }

    public void writeLog(Exception e, String filePath) {
        String direct = System.getProperty("user.dir");
        log.info("===============================================================");
        try {
            file = new FileHandler(direct + filePath);
            log.addHandler(file);

            SimpleFormatter formatter = new SimpleFormatter();
            file.setFormatter(formatter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        log.warning(e.toString());

        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            log.info(stackTraceElement.toString());
        }

    }

    public void writeLog(String action, String filePath) {
        log.info("===============================================================");
        try {
            String direct = System.getProperty("user.dir");
            file = new FileHandler(direct + filePath);
            log.addHandler(file);

            SimpleFormatter formatter = new SimpleFormatter();
            file.setFormatter(formatter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // log.info(action);

        // for (StackTraceElement stackTraceElement : e.getStackTrace()) {
        //     log.info(stackTraceElement.toString());
        // }
    }
}
