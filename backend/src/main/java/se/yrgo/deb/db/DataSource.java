package se.yrgo.deb.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataSource { 
    private static BasicDataSource ds = new BasicDataSource();

    static {
        String server = System.getenv("JDBC_SERVER");
        String db = System.getenv("JDBC_DB");
        String user = System.getenv("JDBC_USER");
        String pwd = System.getenv("JDBC_PASSWORD");

        String url = String.format("jdbc:sqlserver://%s;database=%s;user=%s;", server, db, user)
            + String.format("password=%s;encrypt=false;trustServerCertificate=false;loginTimeout=30;", pwd);

        ds.setUrl(url);
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }
    
    private DataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}