package org.obiz.export;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private String url;
    private String user;
    private String password;

    public Db(Config config) {
        this.url = config.getConnectString();
        this.user = config.getUser();
        this.password = config.getPasswd();
    }

    public Connection createConnection() throws ClassNotFoundException, SQLException {
        //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        Class.forName("oracle.jdbc.OracleDriver");
//        Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println("connected. Connection class:" + conn.getClass().getCanonicalName());
        return conn;
    }
}
