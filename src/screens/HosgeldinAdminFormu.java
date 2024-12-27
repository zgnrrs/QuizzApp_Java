package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HosgeldinAdminFormu extends JFrame {

    public HosgeldinAdminFormu() {
        setTitle("Hoşgeldin Admin");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Soru Atölyesi butonu
        JButton btnSoruAtolyesi = new JButton("Soru Atölyesi");
        btnSoruAtolyesi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SoruAtolyesiFormu().setVisible(true);
                dispose();
            }
        });

        // Kullanıcıları Görüntüle butonu
        JButton btnKullaniciGoruntule = new JButton("Kullanıcıları Görüntüle");
        btnKullaniciGoruntule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new KullaniciGoruntuleFormu().setVisible(true);
                dispose();
            }
        });

        // Çıkış butonu
        JButton btnCikis = new JButton("Çıkış");
        btnCikis.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new KullaniciGirisForm().setVisible(true);
            }
        });

        // Butonları ekleyelim
        add(btnSoruAtolyesi);
        add(btnKullaniciGoruntule);
        add(btnCikis);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HosgeldinAdminFormu().setVisible(true);
            }
        });
    }
}



