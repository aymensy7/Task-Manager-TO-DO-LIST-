package taskManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * class for connection between 
 * java and mysql
 */
public class DatabaseConnection {
    private static final String URL = "URL";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "[PASSWORD]";

    /**
     *  method for connection
     * @return DriverManager for connection
     * @throws SQLException for connection
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
