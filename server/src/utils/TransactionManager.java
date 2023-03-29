package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;

import configures.Config;

public class TransactionManager extends HikariDataSource {
    private static volatile TransactionManager transactionManager = null;

    private TransactionManager() {
    };

    public static TransactionManager getInstance() {
        if (transactionManager == null) {
            synchronized (TransactionManager.class) {
                if (transactionManager == null) {
                    transactionManager = new TransactionManager();
                    transactionManager.setJdbcUrl(Config.getUrl());
                    transactionManager.setUsername(Config.getUser());
                    transactionManager.setPassword(Config.getPassword());
                    transactionManager.setDataSourceClassName("com.ibm.db2.jcc.DB2SimpleDataSource");
                    transactionManager.setMaximumPoolSize(10);
                    transactionManager.setAutoCommit(false);
                }
            }
        }
        return transactionManager;
    }

    public void closeConnection(Connection con, PreparedStatement stm, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.commit();
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
