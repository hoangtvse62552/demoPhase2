package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.mindrot.jbcrypt.BCrypt;

import logger.ServerLogger;
import model.Account;
import utils.TransactionManager;

public class AccountService
{

    private final TransactionManager transactionManager = TransactionManager.getInstance();

    private Connection               con;
    private PreparedStatement        stm;
    private ResultSet                rs;

    public Account login(String username, String password)
    {
        Account dto = null;

        try
        {
            con = transactionManager.getTransaction();
            String sql = "SELECT * FROM ACCOUNTS WHERE username = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, username);
            rs = stm.executeQuery();
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
        catch (Exception e)
        {
            ServerLogger.getInstance().writeLog(e);
            e.printStackTrace();
        }
        finally
        {
            transactionManager.closeConnection(con, stm, rs);
        }
        return dto;
    }

}
