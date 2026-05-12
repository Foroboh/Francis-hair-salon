package com.francishairsalon.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Utility class for managing SQL Server connections.
public class DBUtil {

    private static final String JDBC_URL
            = "jdbc:sqlserver://DESKTOP-RAL3QI3\\FRANCISSQLSERVER:1433;"
            + "databaseName=FrancisHairSalonDB;"
            + "encrypt=true;trustServerCertificate=true;";

    private static final String USERNAME = "sa";

    private static final String PASSWORD = "MyBristol";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("[DBUtil] MSSQL JDBC driver not found.");
            e.printStackTrace();
        }
    }

    //Returns a new database connection using the configured URL, username, and password.
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    //Safely closes a database connection
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("[DBUtil] Error closing connection.");
                e.printStackTrace();
            }
        }
    }

    private DBUtil() {
    }
}
