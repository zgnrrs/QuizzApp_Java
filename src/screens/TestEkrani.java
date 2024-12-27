package screens;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;

public class TestEkrani extends JFrame {
    private JLabel lblSoru, lblSure;
    private JButton btnA, btnB, btnC, btnD, btnSonrakiSoru, btnTestiBitir, btnOnayla;
    private JPanel panel;
    private Timer timer;
    private int remainingTime = 100; // 100 saniye toplam süre
    private int currentQuestionIndex = 0;
    private ArrayList<String> sorular;
    private ArrayList<String[]> cevaplar;
    private ArrayList<String> dogruCevaplar;
    private String[] userAnswers; // Kullanıcının verdiği cevaplar
    private int correctAnswers = 0;
    private String username; // Kullanıcı ismini tutan değişken

    // Kullanıcı ismini parametre olarak alıyoruz
    public TestEkrani(String username) {
        this.username = username; // Kullanıcı ismini alıp değişkene atıyoruz

        setTitle("Test Ekranı");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        sorular = new ArrayList<>();
        cevaplar = new ArrayList<>();
        dogruCevaplar = new ArrayList<>();
        userAnswers = new String[10]; 

       
        loadQuestionsFromDatabase();

       
        createUIComponents();

        
        startTimer();

        
        displayNextQuestion();
    }

    private void createUIComponents() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1)); 

        lblSoru = new JLabel("Soru", SwingConstants.CENTER);
        lblSoru.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(lblSoru);

        btnA = new JButton("A");
        btnB = new JButton("B");
        btnC = new JButton("C");
        btnD = new JButton("D");

        panel.add(btnA);
        panel.add(btnB);
        panel.add(btnC);
        panel.add(btnD);

        btnSonrakiSoru = new JButton("Sonraki Soru");
        btnSonrakiSoru.setEnabled(false); 
        panel.add(btnSonrakiSoru);

        btnTestiBitir = new JButton("Testi Bitir");
        btnTestiBitir.setEnabled(false); 
        panel.add(btnTestiBitir);

        btnOnayla = new JButton("Onayla");
        btnOnayla.setEnabled(false); 
        panel.add(btnOnayla);

        add(panel, BorderLayout.CENTER);

        lblSure = new JLabel("Süre: 100", SwingConstants.CENTER);
        lblSure.setFont(new Font("Arial", Font.PLAIN, 20));
        add(lblSure, BorderLayout.NORTH);

       
        addActionListeners();
    }

    private void addActionListeners() {
        btnA.addActionListener(e -> handleAnswer("a"));
        btnB.addActionListener(e -> handleAnswer("b"));
        btnC.addActionListener(e -> handleAnswer("c"));
        btnD.addActionListener(e -> handleAnswer("d"));

        btnSonrakiSoru.addActionListener(e -> {
            moveToNextQuestion();
            btnSonrakiSoru.setEnabled(false); // Sonraki soru butonunu tekrar inaktif et
        });

        btnTestiBitir.addActionListener(e -> calculateScore());

        btnOnayla.addActionListener(e -> {
            int score = correctAnswers * 5; 
            saveScoreToDatabase(score); 
        });
    }

    private void handleAnswer(String answer) {
        userAnswers[currentQuestionIndex] = answer; 
        btnSonrakiSoru.setEnabled(true); 
    }

    // Soruları veritabanından al
    private void loadQuestionsFromDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp", "root", "okEK100718?")) {
            String query = "SELECT * FROM sorular ORDER BY RAND() LIMIT 10"; 
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                sorular.add(rs.getString("soru"));
                cevaplar.add(new String[]{rs.getString("secenek_a"), rs.getString("secenek_b"), rs.getString("secenek_c"), rs.getString("secenek_d")});
                dogruCevaplar.add(rs.getString("dogru_cevap"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Veritabanı hatası");
        }
    }

    // Timer'ı başlat
    private void startTimer() {
        timer = new Timer(1000, e -> {
            if (remainingTime > 0) {
                remainingTime--;
                lblSure.setText("Süre: " + remainingTime);
            } else if (currentQuestionIndex < 10) {
                moveToNextQuestion(); 
            } else {
                btnTestiBitir.setEnabled(true); 
            }
        });
        timer.start();
    }

    //sonraki soruya geç
    private void moveToNextQuestion() {
        currentQuestionIndex++; 

        if (currentQuestionIndex < 10) {
            
            displayNextQuestion();
        } else {
            
            btnTestiBitir.setEnabled(true);
            btnSonrakiSoru.setEnabled(false); 
        }
    }

    // Soruyu ve şıkları ekrana yazdır
    private void displayNextQuestion() {
        lblSoru.setText(sorular.get(currentQuestionIndex)); 
        btnA.setText("A: " + cevaplar.get(currentQuestionIndex)[0]);
        btnB.setText("B: " + cevaplar.get(currentQuestionIndex)[1]);
        btnC.setText("C: " + cevaplar.get(currentQuestionIndex)[2]);
        btnD.setText("D: " + cevaplar.get(currentQuestionIndex)[3]);
    }

    // Skoru hesapla ve sonucu göster
    private void calculateScore() {
        correctAnswers = 0;

        // Kullanıcının cevapları ile doğru cevapları karşılaştır
        for (int i = 0; i < 10; i++) {
            if (userAnswers[i].equals(dogruCevaplar.get(i))) {
                correctAnswers++;
            }
        }

        int score = correctAnswers * 5;
        JOptionPane.showMessageDialog(this, "Skorunuz: " + score);

        
        btnOnayla.setEnabled(true);
        btnTestiBitir.setEnabled(false); 
    }

    // Skoru veritabanına kaydet
    private void saveScoreToDatabase(int score) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizapp", "root", "okEK100718?")) {
            String query = "INSERT INTO scores (username, score) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            // Kullanıcı adı ve skoru veritabanına ekliyoruz
            stmt.setString(1, username); 
            stmt.setInt(2, score);        
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Skor başarıyla kaydedildi!");
            dispose(); 
            new HosgeldinKullaniciFormu(username).setVisible(true); 
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Skor kaydedilirken hata oluştu!");
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TestEkrani("KullaniciAdi").setVisible(true));
    }


    
}
