package configures;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ServerConfig
{
    private String                       url      = "";
    private String                       user     = "";
    private String                       password = "";

    private static volatile ServerConfig svInstance;

    public static ServerConfig getInstance()
    {
        if (svInstance == null)
        {
            synchronized (ServerConfig.class)
            {
                if (svInstance == null)
                {
                    svInstance = new ServerConfig();
                }
            }
        }
        return svInstance;
    }

    public void loadConfig()
    {
        try
        {
            URL res = ServerConfig.class.getResource("Config.xml");
            File file = Paths.get(res.toURI()).toFile();
            
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            NodeList root = doc.getElementsByTagName("Config");
            Element config = (Element) root.item(0);
            url = config.getElementsByTagName("url").item(0).getTextContent();
            user = config.getElementsByTagName("user").item(0).getTextContent();
            password = config.getElementsByTagName("password").item(0).getTextContent();
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public String getUrl()
    {
        return url;
    }

    public String getUser()
    {
        return user;
    }

    public String getPassword()
    {
        return password;
    }

}
