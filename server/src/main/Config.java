package main;

public class Config
{
    private final String url      = "jdbc:db2:books";
    private final String user     = "db2admin";
    private final String password = "1234";

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
