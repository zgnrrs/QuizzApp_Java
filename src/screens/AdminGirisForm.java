package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminGirisForm extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;

    public AdminGirisForm() {
        setTitle("Admin Girişi");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
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
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // Admin doğrulama işlemi
                if (email.equals("admin@java.com") && password.equals("admin123")) {
                    JOptionPane.showMessageDialog(null, "Admin girişi başarılı!");
                    dispose();
                    new HosgeldinAdminFormu().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Geçersiz giriş bilgileri!");
                }
            }
        });
        add(loginButton);

        // Geri Dön butonu
        backButton = new JButton("Geri Dön");
        backButton.setBounds(200, 150, 120, 30);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new KullaniciGirisForm().setVisible(true); // Kullanıcı giriş sayfasına dön
            }
        });
        add(backButton);

        setLocationRelativeTo(null); // Ekranın ortasında açılması için
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AdminGirisForm().setVisible(true);
            }
        });
    }
}



