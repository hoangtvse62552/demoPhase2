package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

public class TransactionManager
{
    private static volatile TransactionManager transactionManager = null;

    private final HikariDataSource             dataSource;

    private TransactionManager()
    {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:db2://" + "localhost:50002" + "/" + "demo2");
        dataSource.setUsername("db2inst1");
        dataSource.setPassword("Nqh1999@");
        dataSource.setMaximumPoolSize(10);
        dataSource.setAutoCommit(false);
    };

    public static TransactionManager getInstance() {
        if (transactionManager == null) {
            synchronized (TransactionManager.class) {
                if (transactionManager == null) {
                    transactionManager = new TransactionManager();
                }
            }
        }
        return transactionManager;
    }

    public Connection getTransaction() throws SQLException
    {
        return dataSource.getConnection();
    }

    public void shutdownPool()
    {
        dataSource.close();
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
