package configures;

public class Config
{
    private final String url      = "jdbc:db2://" + "localhost:50002" + "/" + "demo2";
    private final String user     = "db2inst1";
    private final String password = "Nqh1999@";

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
