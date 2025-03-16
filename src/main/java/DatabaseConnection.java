import java.io.*;
import java.sql.*;
import java.util.Properties;

import java.io.*;
import java.sql.*;
import java.util.Properties;

/**
 * DatabaseConnection - Manages MySQL connection using Singleton Pattern.
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        connect();
    }

    private void connect() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/DB_SwingWorker/db_config.txt"))) {
            Properties properties = new Properties();
            properties.load(br);

            String url = "jdbc:mysql://" + properties.getProperty("DB_HOST") + ":" + properties.getProperty("DB_PORT") + "/" + properties.getProperty("DB_NAME");
            String user = properties.getProperty("DB_USER");
            String password = properties.getProperty("DB_PASSWORD");

            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connect(); // Reconnect if connection was closed
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
