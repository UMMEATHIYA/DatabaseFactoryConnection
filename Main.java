import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

interface DatabaseConnection {
    Connection getConnection();
}

class MySqlConnection implements DatabaseConnection {
    private final String url;
    private final String user;
    private final String password;

    public MySqlConnection(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

class OracleConnection implements DatabaseConnection {
    private final String url;
    private final String user;
    private final String password;

    public OracleConnection(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

class ConnectionFactory {
    public DatabaseConnection createConnection(String dbType, String url, String user, String password) {
        if (dbType.equalsIgnoreCase("mysql")) {
            return new MySqlConnection(url, user, password);
        } else if (dbType.equalsIgnoreCase("oracle")) {
            return new OracleConnection(url, user, password);
        } else {
            throw new IllegalArgumentException("Unsupported database type");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();

        // For MySQL
        String mysqlUrl = "jdbc:mysql://localhost:3306/test_db";
        String mysqlUser = "root";
        String mysqlPassword = "password";

        DatabaseConnection mysqlConnection = factory.createConnection("mysql", mysqlUrl, mysqlUser, mysqlPassword);
        Connection mysqlConn = mysqlConnection.getConnection();

        if (mysqlConn != null) {
            System.out.println("MySQL Connection established successfully.");
            // Perform operations with MySQL connection
            try {
                mysqlConn.close(); // Close the connection when done
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to establish MySQL connection.");
        }

        // For Oracle
        String oracleUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
        String oracleUser = "username";
        String oraclePassword = "password";

        DatabaseConnection oracleConnection = factory.createConnection("oracle", oracleUrl, oracleUser, oraclePassword);
        Connection oracleConn = oracleConnection.getConnection();

        if (oracleConn != null) {
            System.out.println("Oracle Connection established successfully.");
            // Perform operations with Oracle connection
            try {
                oracleConn.close(); // Close the connection when done
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to establish Oracle connection.");
        }
    }
}
