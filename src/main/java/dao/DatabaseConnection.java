package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DatabaseConnection {

    private static final String DB_NAME = "ConsultorImperdielDB.sqlite";
    private static final String DB_URL = "jdbc:sqlite:" + getDbPath();
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            extractDatabaseIfNeeded();
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new RuntimeException("Error initializing database connection", e);
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
                connection = DriverManager.getConnection(DB_URL);
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Error getting database connection", e);
        }
    }

    private static String getDbPath() {
        String userHome = System.getProperty("user.home");
        String appDir = userHome + File.separator + ".consultorimperdiel";
        File dir = new File(appDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return appDir + File.separator + DB_NAME;
    }

    private void extractDatabaseIfNeeded() throws IOException {
        File dbFile = new File(getDbPath());
        if (!dbFile.exists()) {
            try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DB_NAME);
                 FileOutputStream outputStream = new FileOutputStream(dbFile)) {

                if (inputStream != null) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                }
            }
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}