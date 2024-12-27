package db;

import java.sql.*;

public class DBConnection {
    // Veritabanı bilgileri
    private static final String URL = "jdbc:mysql://localhost:3306/QuizApp";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "okEK100718?"; // MySQL root şifrenizi buraya yazın

    // Veritabanı bağlantısı almak için kullanılan metot
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // JDBC Driver yükleme
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Veritabanına bağlanma
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Veritabanına başarılı bir şekilde bağlanıldı!");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver bulunamadı: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Veritabanına bağlanırken hata oluştu: " + e.getMessage());
        }
        return connection;
    }

    // Kullanıcı skorlarını kaydetmek için kullanılan metot
    public static void saveUserScore(String userName, int score) {
        String sql = "INSERT INTO kullanıcılar (isim, skor, oynama_tarihi) VALUES (?, ?, CURDATE())";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, userName); // Kullanıcı adı
            stmt.setInt(2, score); // Kullanıcı skoru
            stmt.executeUpdate(); // Veritabanına kaydetme işlemi
            System.out.println("Skor başarıyla kaydedildi!");
        } catch (SQLException e) {
            System.out.println("Skor kaydedilirken hata oluştu: " + e.getMessage());
        }
    }

    // Tüm kullanıcı skorlarını sıralı şekilde almak için kullanılan metot
    public static void getUserScores() {
        String sql = "SELECT isim, skor FROM kullanıcılar ORDER BY skor DESC";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Kullanıcı Skorları:");
            while (rs.next()) {
                String userName = rs.getString("isim");
                int score = rs.getInt("skor");
                System.out.println(userName + " | " + score);
            }
        } catch (SQLException e) {
            System.out.println("Skorlar alınırken hata oluştu: " + e.getMessage());
        }
    }

    // Yeni bir kullanıcı kaydetmek için kullanılan metot
    public static void saveUser(String name, String email, String password) {
        String sql = "INSERT INTO Kullanıcılar (isim, email, sifre) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name); // Kullanıcı adı
            stmt.setString(2, email); // Kullanıcı emaili
            stmt.setString(3, password); // Kullanıcı şifresi
            stmt.executeUpdate(); // Veritabanına kaydetme işlemi
            System.out.println("Kullanıcı başarıyla kaydedildi!");
        } catch (SQLException e) {
            System.out.println("Kullanıcı kaydedilirken hata oluştu: " + e.getMessage());
        }
    }

    // Admin giriş doğrulaması için metot
    public static boolean validateAdmin(String email, String password) {
        String sql = "SELECT * FROM Kullanıcılar WHERE email = ? AND sifre = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email); // Admin emaili
            stmt.setString(2, password); // Admin şifresi
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Admin kontrolü
                return rs.getString("email").equals("admin@java.com") && rs.getString("sifre").equals("1234");
            }
        } catch (SQLException e) {
            System.out.println("Admin doğrulama sırasında hata oluştu: " + e.getMessage());
        }
        return false;
    }

 



    // Kullanıcıların skorlarını güncellemek için placeholder metod
    public static void scores(String string, String string2, int i) {
        // TODO: Gerekli işlemler burada yapılacak
    }
}








