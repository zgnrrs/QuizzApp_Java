package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import db.DBConnection;

public class AdminSoruAtolyesiFormu extends JFrame {
    private JTextArea txtSoru, txtCevapA, txtCevapB, txtCevapC, txtCevapD;
    private JButton btnEkle, btnDuzenle, btnSil, btnGeriDon;
    private JComboBox<String> soruComboBox;
    private int currentQuestionId;

    public AdminSoruAtolyesiFormu() {
        setTitle("Admin - Soru Atölyesi");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        txtSoru = new JTextArea(2, 30);
        txtCevapA = new JTextArea(1, 30);
        txtCevapB = new JTextArea(1, 30);
        txtCevapC = new JTextArea(1, 30);
        txtCevapD = new JTextArea(1, 30);

        btnEkle = new JButton("Soru Ekle");
        btnDuzenle = new JButton("Soru Düzenle");
        btnSil = new JButton("Soru Sil");
        btnGeriDon = new JButton("Geri Dön");

        soruComboBox = new JComboBox<>();
        loadQuestions(); // Veritabanından soruları yükle

        btnEkle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addQuestion();
            }
        });

        btnDuzenle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editQuestion();
            }
        });

        btnSil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteQuestion();
            }
        });

        btnGeriDon.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new HosgeldinAdminFormu().setVisible(true);
                dispose();
            }
        });

        add(new JLabel("Soru Seç:"));
        add(soruComboBox);
        add(new JLabel("Soru:"));
        add(txtSoru);
        add(new JLabel("A Şıkkı:"));
        add(txtCevapA);
        add(new JLabel("B Şıkkı:"));
        add(txtCevapB);
        add(new JLabel("C Şıkkı:"));
        add(txtCevapC);
        add(new JLabel("D Şıkkı:"));
        add(txtCevapD);
        add(btnEkle);
        add(btnDuzenle);
        add(btnSil);
        add(btnGeriDon);
    }

    private void loadQuestions() {
        try (Connection conn = DBConnection.getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM questions");

            while (rs.next()) {
                soruComboBox.addItem(rs.getString("soru"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addQuestion() {
        String soru = txtSoru.getText();
        String cevapA = txtCevapA.getText();
        String cevapB = txtCevapB.getText();
        String cevapC = txtCevapC.getText();
        String cevapD = txtCevapD.getText();

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO questions (soru, cevap_a, cevap_b, cevap_c, cevap_d) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, soru);
            stmt.setString(2, cevapA);
            stmt.setString(3, cevapB);
            stmt.setString(4, cevapC);
            stmt.setString(5, cevapD);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Soru eklendi!");
            loadQuestions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editQuestion() {
        String soru = txtSoru.getText();
        String cevapA = txtCevapA.getText();
        String cevapB = txtCevapB.getText();
        String cevapC = txtCevapC.getText();
        String cevapD = txtCevapD.getText();

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("UPDATE questions SET soru = ?, cevap_a = ?, cevap_b = ?, cevap_c = ?, cevap_d = ? WHERE id = ?");
            stmt.setString(1, soru);
            stmt.setString(2, cevapA);
            stmt.setString(3, cevapB);
            stmt.setString(4, cevapC);
            stmt.setString(5, cevapD);
            stmt.setInt(6, currentQuestionId);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Soru güncellendi!");
            loadQuestions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteQuestion() {
        int confirm = JOptionPane.showConfirmDialog(this, "Bu soruyu silmek istediğinizden emin misiniz?");
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DBConnection.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM questions WHERE id = ?");
                stmt.setInt(1, currentQuestionId);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Soru silindi!");
                loadQuestions();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AdminSoruAtolyesiFormu().setVisible(true);
            }
        });
    }
}
