package screens;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import db.DBConnection;

import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class SkorTablosuFormu extends JFrame {
    private JTable skorTablosu;
    private JButton btnAnaMenu;
    private JPanel mainPanel;

    public SkorTablosuFormu() {
        setTitle("Skor Tablosu");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainPanel = new JPanel(new BorderLayout());
        add(mainPanel);

        initializeUIComponents();
        loadScoresFromDatabase();

        setVisible(true);
    }

    private void initializeUIComponents() {
        skorTablosu = new JTable();
        skorTablosu.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(skorTablosu);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        btnAnaMenu = new JButton("Ana Menüye Dön");
        btnAnaMenu.addActionListener(e -> {
            dispose();
            new HosgeldinKullaniciFormu(getName()).setVisible(true); // Ana menüye dön
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(btnAnaMenu);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }
    private void skorTablosunuDoldur() {
        // Tablo modelini oluştur
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Kullanıcı Adı");
        model.addColumn("Skor");

        try (Connection conn = DBConnection.getConnection()) {
            // users ve scores tablolarını birleştirerek kullanıcı adını al
            String sql = """
                         SELECT u.name AS username, s.score 
                         FROM users u 
                         INNER JOIN scores s ON u.email = s.username 
                         ORDER BY s.score DESC
                         """;

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Skor verilerini tabloya ekle
            while (rs.next()) {
                String userName = rs.getString("username");
                int score = rs.getInt("score");
                model.addRow(new Object[]{userName, score});
            }

            skorTablosu.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Skorlar yüklenirken hata oluştu: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    private void loadScoresFromDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp", "root", "okEK100718?")) {
            String query = "SELECT username, score FROM scores ORDER BY score DESC";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // Tablo modelini oluştur
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Kullanıcı Adı");
            model.addColumn("Skor");

            // Veritabanından gelen verileri tabloya ekle
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                row.add(rs.getString("username"));
                row.add(String.valueOf(rs.getInt("score")));
                model.addRow(row);
            }

            skorTablosu.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Veritabanı hatası: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SkorTablosuFormu::new);
    }
}




