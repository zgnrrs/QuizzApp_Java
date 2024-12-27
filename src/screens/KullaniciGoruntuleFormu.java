package screens;

import db.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class KullaniciGoruntuleFormu extends JFrame {

    private JTable userTable;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;

    public KullaniciGoruntuleFormu() {
        // Pencereyi yapılandırma
        setTitle("Kullanıcıları Görüntüle");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Tabloyu ve modelini oluşturma
        tableModel = new DefaultTableModel();
        tableModel.addColumn("İsim");
        tableModel.addColumn("E-posta");
        tableModel.addColumn("Skor");

        userTable = new JTable(tableModel);
        scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        // Kullanıcıları veritabanından al
        fetchUserData();

        // Geri Dön butonu
        JButton backButton = new JButton("Geri Dön");
        backButton.addActionListener(e -> {
            dispose();
            new HosgeldinAdminFormu().setVisible(true); // Admin sayfasına geri dön
        });
        add(backButton, BorderLayout.SOUTH);
    }

    private void fetchUserData() {
        try (Connection connection = DBConnection.getConnection()) {
            // Kullanıcılar ve skorları birleştiren sorgu
            String query = "SELECT u.name, u.email, IFNULL(s.score, 0) AS score " +
                           "FROM users u " +
                           "LEFT JOIN scores s ON u.name = s.username"; // Kullanıcı adı üzerinden ilişkilendirme
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Tabloyu temizleyelim, her seferinde eski verileri temizleyelim
            tableModel.setRowCount(0);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                int score = resultSet.getInt("score");

                // Tabloya satır ekle
                tableModel.addRow(new Object[]{name, email, score});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + e.getMessage(),
                                          "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KullaniciGoruntuleFormu().setVisible(true));
    }
}




