import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConfig {
    private static Connection client;

    private static final String URL = "jdbc:mysql://localhost:3306/scores"; // Ganti dengan nama database Anda
    private static final String USER = "root"; // Ganti dengan user database Anda
    private static final String PASSWORD = ""; // Ganti dengan password database Anda

    // Membuat koneksi ke database
    public static Connection getConnection() throws SQLException {
        if (client == null || client.isClosed()) {
            client = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return client;
    }

    // Menginisialisasi koneksi (opsional, hanya untuk testing koneksi)
    public static void initialize() {
        try (Connection connection = getConnection()) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }

    // Menyimpan data username dan score ke database
    public static void saveScore(String username, int score) {
        String query = "INSERT INTO scores (username, score) VALUES (?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, score);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Failed to save score: " + e.getMessage());
        }
    }

    // Mendapatkan top 3 scores dari database
    public static List<String> getTopScores() {
        List<String> topScores = new ArrayList<>();
        String query = "SELECT username, score FROM scores ORDER BY score DESC LIMIT 3";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                int score = resultSet.getInt("score");
                topScores.add(username + ": " + score);
            }
        } catch (SQLException e) {
            System.err.println("Failed to fetch scores: " + e.getMessage());
        }
        return topScores;
    }
}
