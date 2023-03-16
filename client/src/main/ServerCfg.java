package main;

public class ServerCfg
{
    private String serverIp;

    private int    serverPort;

    public ServerCfg(String serverIp, int serverPort)
    {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public String getServerIp()
    {
        return serverIp;
    }

    public int getServerPort()
    {
        return serverPort;
    }
}
