package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class SoruAtolyesiFormu extends JFrame {
    private JLabel lblSoruSayisi;
    private JTable soruTablosu;
    private JButton btnEkle, btnDuzenle, btnGeri;
    private static ArrayList<String> sorular;
    private static ArrayList<String[]> cevaplar;
    private static ArrayList<String> dogruCevaplar; // Doğru cevaplar için ayrı bir liste
    private String[] columnNames = {"Soru No", "Soru", "A", "B", "C", "D", "Doğru Cevap"};
    private String[][] data;
    private Connection conn;

    public SoruAtolyesiFormu() {
        setTitle("Soru Atölyesi");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        sorular = new ArrayList<>();
        cevaplar = new ArrayList<>();
        dogruCevaplar = new ArrayList<>(); // Doğru cevaplar listesini başlatıyoruz

        // Veritabanından soruları al
        fetchQuestionsFromDatabase();

        lblSoruSayisi = new JLabel("Toplam Soru: " + sorular.size());
        add(lblSoruSayisi, BorderLayout.NORTH);

        // JTable veri yapısını ayarlama
        initializeTableData();
        soruTablosu = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(soruTablosu);
        add(scrollPane, BorderLayout.CENTER);

        // Ekle ve Düzenle butonları
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        btnEkle = new JButton("Soru Ekle");
        btnDuzenle = new JButton("Soru Düzenle");
        btnGeri = new JButton("Geri Dön");
        panel.add(btnEkle);
        panel.add(btnDuzenle);
        panel.add(btnGeri);
        add(panel, BorderLayout.SOUTH);

        // Buttonlara aksiyon eklenmesi
        btnEkle.addActionListener(e -> ekleSoru());
        btnDuzenle.addActionListener(e -> duzenleSoru());
        btnGeri.addActionListener(e -> {
            dispose();
            new HosgeldinAdminFormu().setVisible(true);
        });
    }

    private void fetchQuestionsFromDatabase() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp", "root", "okEK100718?");
            String sql = "SELECT * FROM sorular"; // Veritabanından soruları al
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String questionText = rs.getString("soru");
                String optionA = rs.getString("secenek_a");
                String optionB = rs.getString("secenek_b");
                String optionC = rs.getString("secenek_c");
                String optionD = rs.getString("secenek_d");
                String correctAnswer = rs.getString("dogru_cevap");

                sorular.add(questionText);
                cevaplar.add(new String[]{optionA, optionB, optionC, optionD});
                dogruCevaplar.add(correctAnswer); // Doğru cevabı listeye ekliyoruz
            }

            initializeTableData(); // Veriler alındıktan sonra tabloyu güncelle
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Veritabanı bağlantısında bir hata oluştu.");
        }
    }

    private void initializeTableData() {
        data = new String[sorular.size()][7];
        for (int i = 0; i < sorular.size(); i++) {
            data[i][0] = String.valueOf(i + 1);
            data[i][1] = sorular.get(i);
            data[i][2] = cevaplar.get(i)[0];
            data[i][3] = cevaplar.get(i)[1];
            data[i][4] = cevaplar.get(i)[2];
            data[i][5] = cevaplar.get(i)[3];
            data[i][6] = dogruCevaplar.get(i); // Doğru cevap
        }
    }

    private void ekleSoru() {
        String soru = JOptionPane.showInputDialog("Yeni Soru:");
        String[] cevaplarInput = new String[4];
        for (int i = 0; i < 4; i++) {
            cevaplarInput[i] = JOptionPane.showInputDialog("Cevap " + (char) ('A' + i) + ":");
        }
        String dogruCevap = JOptionPane.showInputDialog("Doğru Cevap (a, b, c, d):");

        sorular.add(soru);
        cevaplar.add(cevaplarInput);
        dogruCevaplar.add(dogruCevap); // Doğru cevabı listeye ekliyoruz

        updateTable();
    }

    private void duzenleSoru() {
        int selectedRow = soruTablosu.getSelectedRow();
        if (selectedRow != -1) {
            String updatedSoru = JOptionPane.showInputDialog("Yeni Soru:", sorular.get(selectedRow));
            String[] updatedCevaplar = new String[4];
            for (int i = 0; i < 4; i++) {
                updatedCevaplar[i] = JOptionPane.showInputDialog("Cevap " + (char) ('A' + i) + ":",
                        data[selectedRow][i + 2]);
            }
            String updatedDogruCevap = JOptionPane.showInputDialog("Doğru Cevap (a, b, c, d):", data[selectedRow][6]);

            sorular.set(selectedRow, updatedSoru);
            cevaplar.set(selectedRow, updatedCevaplar);
            dogruCevaplar.set(selectedRow, updatedDogruCevap); // Doğru cevabı güncelle

            updateTable();
        }
    }

    private void updateTable() {
        initializeTableData();
        soruTablosu.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        lblSoruSayisi.setText("Toplam Soru: " + sorular.size());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SoruAtolyesiFormu().setVisible(true));
    }
}





