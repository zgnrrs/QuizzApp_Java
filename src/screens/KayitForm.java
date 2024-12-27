package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class KayitForm extends JFrame {
    private JTextField nameField, mailField;
    private JPasswordField passwordField;
    private JButton btnKayitOl, btnGeriDon;

    public KayitForm() {
        setTitle("Kayıt Ol");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Ad alanı
        JLabel labelName = new JLabel("Ad:");
        labelName.setBounds(50, 50, 100, 30);
        add(labelName);

        nameField = new JTextField();
        nameField.setBounds(150, 50, 200, 30);
        add(nameField);

        // E-posta alanı
        JLabel labelMail = new JLabel("Mail:");
        labelMail.setBounds(50, 100, 100, 30);
        add(labelMail);

        mailField = new JTextField();
        mailField.setBounds(150, 100, 200, 30);
        add(mailField);

        // Şifre alanı
        JLabel labelPassword = new JLabel("Şifre:");
        labelPassword.setBounds(50, 150, 100, 30);
        add(labelPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 150, 200, 30);
        add(passwordField);

        // Kayıt Ol butonu
        btnKayitOl = new JButton("Kayıt Ol");
        btnKayitOl.setBounds(50, 200, 120, 30);
        btnKayitOl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String mail = mailField.getText();
                String password = new String(passwordField.getPassword());

                // Veritabanına kayıt işlemi
                try (Connection connection = DBConnection.getConnection()) {
                    String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
                    PreparedStatement pst = connection.prepareStatement(query);
                    pst.setString(1, name);
                    pst.setString(2, mail);
                    pst.setString(3, password);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Kayıt başarılı!");
                    new KullaniciGirisForm().setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Veritabanına kayıt yapılırken hata oluştu: " + ex.getMessage());
                }
            }
        });
        add(btnKayitOl);

        // Geri Dön butonu
        btnGeriDon = new JButton("Geri Dön");
        btnGeriDon.setBounds(200, 200, 120, 30);
        btnGeriDon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new KullaniciGirisForm().setVisible(true);
                dispose(); // Kullanıcı giriş ekranına dön
            }
        });
        add(btnGeriDon);

        setLocationRelativeTo(null); // Ekranın ortasında açılması için
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new KayitForm().setVisible(true);
            }
        });
    }
}



