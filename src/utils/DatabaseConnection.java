package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/card_battle_db";
    private static final String user = "postgres";
    private static final String password = "18112007Amir";

    public static Connection getConnection() throws SQLException {
        try{
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found", e);
        }
    }

    public static void closeConnection(Connection conn) {
        if (conn !=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}