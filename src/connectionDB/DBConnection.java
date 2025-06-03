package connectionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_URL= "jdbc:mysql://localhost:3306/FreeProject";
    private static final String USER = "player";
    private static final String PASSWORD = "player";

    public static Connection getConnection() throws SQLException {
       return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

}

