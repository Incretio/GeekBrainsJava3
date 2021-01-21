package messanger.server;

import org.sqlite.JDBC;

import java.sql.*;

/**Table: User
 * id      login       password
 * "1"     "admin"     "123456"
 * "2"     "user1"     "654321"
 */
public class DbConnection {

    private final String connectionString;
    private Connection connection;

    public DbConnection(String connectionString) {
        this.connectionString = connectionString;
    }

    public void connect() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        // Выполняем подключение к базе данных
        this.connection = DriverManager.getConnection(connectionString);
    }

    public boolean checkUser(String login, String password) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM User WHERE login = ? AND password = ?")) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
