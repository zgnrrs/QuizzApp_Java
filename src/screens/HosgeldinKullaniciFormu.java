package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HosgeldinKullaniciFormu extends JFrame {

    public HosgeldinKullaniciFormu(String username) {
        // Pencere ayarları
        setTitle("Hoşgeldin Kullanıcı");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Pencereyi ekranın ortasına yerleştir

        // Ana panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        add(panel);

        // Hoşgeldin mesajı
        JLabel hosgeldinLabel = new JLabel("Hoşgeldin, " + username + "!", SwingConstants.CENTER);
        hosgeldinLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(hosgeldinLabel, BorderLayout.NORTH);

        // Buton paneli
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        panel.add(buttonPanel, BorderLayout.CENTER);

        // Teste Başla butonu
        JButton testeBaslaButton = new JButton("Teste Başla");
        testeBaslaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TestEkrani formuna geçiş
                TestEkrani testEkrani = new TestEkrani(getName()); // userName parametresi ekleniyor
                testEkrani.setVisible(true);
                dispose(); // Mevcut pencereyi kapat
            }
        });
        buttonPanel.add(testeBaslaButton);

        // Skor Tablosunu Görüntüle butonu
        JButton skorTablosuButton = new JButton("Skor Tablosunu Görüntüle");
        skorTablosuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // SkorTablosuFormu formuna geçiş
                SkorTablosuFormu skorTablosuFormu = new SkorTablosuFormu();
                skorTablosuFormu.setVisible(true);
            }
        });
        buttonPanel.add(skorTablosuButton);

        // Çıkış butonu
        JButton cikisButton = new JButton("Çıkış");
        cikisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Kullanıcı giriş ekranına dönüş
                KullaniciGirisForm kullaniciGirisFormu = new KullaniciGirisForm();
                kullaniciGirisFormu.setVisible(true);
                dispose(); // Mevcut pencereyi kapat
            }
        });
        buttonPanel.add(cikisButton);
    }

    public static void main(String[] args) {
        // Test için örnek kullanıcı adı
        String userName = "John Doe"; // Burada kullanıcı adı örnek olarak kullanılacak
        new HosgeldinKullaniciFormu(userName).setVisible(true);
    }
}
