package main;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ClientConfig
{
    private String                       SERVER_IP   = "";

    private int                          SERVER_PORT = 0;

    private static volatile ClientConfig svInstance;

    public static ClientConfig getInstance()
    {
        if (svInstance == null)
        {
            synchronized (ClientConfig.class)
            {
                if (svInstance == null)
                {
                    svInstance = new ClientConfig();
                }
            }
        }
        return svInstance;
    }

    public void loadConfig()
    {
        try
        {
            URL res = ClientConfig.class.getResource("Config.xml");
            File file = Paths.get(res.toURI()).toFile();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            NodeList root = doc.getElementsByTagName("Config");
            Element config = (Element) root.item(0);
            SERVER_IP = config.getElementsByTagName("ServerIp").item(0).getTextContent();
            SERVER_PORT = Integer.parseInt(config.getElementsByTagName("ServerPort").item(0).getTextContent());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public String getServerIp()
    {
        return SERVER_IP;
    }

    public int getServerPort()
    {
        return SERVER_PORT;
    }

}
