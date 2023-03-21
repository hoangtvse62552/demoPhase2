package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.mindrot.jbcrypt.BCrypt;

import logger.ServerLogger;
import model.*;
import utils.DBManager;

public class AccountService
{
    private Connection        con;
    private PreparedStatement stm;
    private ResultSet         rs;

    public Account login(String username, String password)
    {
        Account dto = null;
        DBManager db = new DBManager();
        try
        {
            con = db.connectDB2();
            if (con != null)
            {
                String sql = "SELECT * FROM ACCOUNTS WHERE username = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                rs = stm.executeQuery();
                if (rs != null)
                {
                    while (rs.next())
                    {
                        boolean valuate = BCrypt.checkpw(password, rs.getString("password"));
                        boolean isAdmin = rs.getBoolean("role");
                        System.out.println(valuate);
                        if (valuate)
                        {
                            dto = new Account();
                            dto.setAdmin(isAdmin);
                            return dto;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            ServerLogger.getInstance().writeLog(e);
            e.printStackTrace();
        }
        finally
        {
            closeConnection();
        }
        return dto;
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
            ServerLogger.getInstance().writeLog(e);
            e.printStackTrace();
        }
    }
}
