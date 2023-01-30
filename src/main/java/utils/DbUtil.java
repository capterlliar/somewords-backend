package utils;

import handlers.DbHandler;

import java.sql.*;

public class DbUtil {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/somewords?" +
            "useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "ayjl9523";
    Connection conn = null;
    Statement stmt = null;
    public void init() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stmt = conn.createStatement();
    }
    public String exec(String sql, DbHandler handler) throws SQLException {
        String res;
        ResultSet rs = stmt.executeQuery(sql);
        res=handler.handle(rs);
        rs.close();
        return res;
    }

    public void exec(String sql) throws SQLException {
        stmt.execute(sql);
    }

    public void close() throws SQLException {
        stmt.close();
        conn.close();
    }
}
