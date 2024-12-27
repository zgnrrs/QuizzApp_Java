package screens;

import javax.swing.JOptionPane;

public class AdminPaneliForm extends javax.swing.JFrame {

    public AdminPaneliForm() {
        initComponents();
    }

    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Admin Paneli");

        jButton1.setText("Kullanıcıları Görüntüle");
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));

        jButton2.setText("Çıkış Yap");
        jButton2.addActionListener(evt -> jButton2ActionPerformed(evt));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(128, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // Kullanıcıları görüntülemek için işlemi başlat (örneğin, veritabanından kullanıcı bilgilerini çekmek)
        JOptionPane.showMessageDialog(this, "Kullanıcılar Listeleniyor...");
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        // Admin çıkışı yapacak
        new KullaniciGirisForm().setVisible(true);
        this.dispose();  // Admin panelini kapat
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new AdminPaneliForm().setVisible(true));
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
}

