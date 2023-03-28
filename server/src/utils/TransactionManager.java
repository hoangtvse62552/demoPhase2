package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class TransactionManager extends HikariDataSource
{
    private static volatile TransactionManager transactionManager = null;

    private TransactionManager()
    {
    };

    public static TransactionManager getInstance()
    {
        if (transactionManager == null)
        {
            synchronized (TransactionManager.class)
            {
                if (transactionManager == null)
                {
                    
                    HikariConfig config = new HikariConfig();
                    config.setJdbcUrl("jdbc:db2://" + "localhost:50002" + "/" + "demo2");
                    config.setUsername("db2inst1");
                    config.setPassword("Nqh1999@");
                    // transactionManager.setJdbcUrl();
                    // transactionManager.setUsername("db2inst1");
                    // transactionManager.setPassword("Nqh1999@");
                    // transactionManager.setDataSourceClassName("com.ibm.db2.jcc.DB2SimpleDataSource");
                    transactionManager = new TransactionManager(config);
                    transactionManager.setMaximumPoolSize(10);
                    transactionManager.setAutoCommit(false);
                }
            }
        }
        return transactionManager;
    }

    public void closeConnection(Connection con, PreparedStatement stm, ResultSet rs)
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
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
