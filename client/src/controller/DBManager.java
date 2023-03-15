package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager
{
    String            url      = "jdbc:db2:books";
    String            user     = "db2admin";
    String            password = "1234";
    Connection        con;
    PreparedStatement stm;
    ResultSet         rs;

    public Connection connectDB2()
    {
        try
        {
            // Load the driver
            Class.forName("com.ibm.db2.jcc.DB2Driver");
//            Class.forName("com.ibm.db2.jdbc.app.DB2Driver");

            // Create the connection using the IBM Data Server Driver for JDBC and SQLJ
            con = DriverManager.getConnection(url, user, password);
            // Commit changes manually
            con.setAutoCommit(false);

            // Create the Statement

        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        return con;
    }

    public void closeConnection()
    {
        try
        {
            if (rs != null)
            {
                rs.close();
            }
            if (stm != null)
            {
                stm.close();
            }
            if (con != null)
            {
                con.commit();
                con.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
