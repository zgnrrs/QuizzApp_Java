package screens;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class KullaniciGirisForm extends JFrame {
    public static String kullaniciAdi;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton adminLoginButton;

    public KullaniciGirisForm() {
        // Form ayarları
        setTitle("Kullanıcı Girişi");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // E-posta alanı
        JLabel emailLabel = new JLabel("E-posta:");
        emailLabel.setBounds(50, 50, 100, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 50, 200, 30);
        add(emailField);

        // Şifre alanı
        JLabel passwordLabel = new JLabel("Şifre:");
        passwordLabel.setBounds(50, 100, 100, 30);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 200, 30);
        add(passwordField);

        // Giriş Yap butonu
        loginButton = new JButton("Giriş Yap");
        loginButton.setBounds(50, 150, 120, 30);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        add(loginButton);

        // Kayıt Ol butonu
        registerButton = new JButton("Kayıt Ol");
        registerButton.setBounds(200, 150, 120, 30);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Mevcut pencereyi kapat
                new KayitForm().setVisible(true); // Kayıt ekranına geçiş
            }
        });
        add(registerButton);

        // Admin Girişi butonu
        adminLoginButton = new JButton("Admin Girişi");
        adminLoginButton.setBounds(125, 200, 150, 30);
        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Mevcut pencereyi kapat
                new AdminGirisForm().setVisible(true); // Admin giriş ekranına geçiş
            }
        });
        add(adminLoginButton);

        setLocationRelativeTo(null); // Ekranın ortasında açılması için
    }

    private void handleLogin() {
        String email = emailField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Lütfen tüm alanları doldurun!");
        } else {
            boolean isAuthenticated = authenticateUser(email, password);
            if (isAuthenticated) {
                String userName = getUserName(email);
                JOptionPane.showMessageDialog(null, "Giriş başarılı! Hoş geldiniz, " + userName);
                dispose(); // Mevcut pencereyi kapat
                new HosgeldinKullaniciFormu(userName).setVisible(true); // Hoşgeldin ekranına geçiş
            } else {
                JOptionPane.showMessageDialog(null, "Geçersiz giriş bilgileri!");
            }
        }
    }

    private boolean authenticateUser(String email, String password) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + e.getMessage());
            return false;
        }
    }

    private String getUserName(String email) {
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT name FROM users WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name"); // Kullanıcı adı
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + e.getMessage());
        }
        return "Kullanıcı";
    }

    private boolean canPlayTest(String email) {
        
        return true;
    }

    public static void main(String[] args) {
        new KullaniciGirisForm().setVisible(true);
    }
}









