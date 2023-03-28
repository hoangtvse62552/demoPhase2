package configures;

public class Config {

    private static final String url = "jdbc:db2://" + "localhost:50002" + "/" + "demo2";
    private static final String user = "db2inst1";
    private static final String password = "Nqh1999@";

    public static String getUrl() {
        return url;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

}
