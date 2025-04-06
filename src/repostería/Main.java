/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package repostería;

import java.awt.BorderLayout;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JOptionPane;


public class Main extends javax.swing.JFrame {

    PostreP postres = new PostreP();
    ClienteP clientes = new ClienteP();
    MateriaPrimaP materiasprimas = new MateriaPrimaP();
    PedidosP pedidos = new PedidosP();
    InicioP inicio = new InicioP();
    
    public Main() {
        initComponents();
        LocalDate fecha = LocalDate.now();
        content.removeAll();
        content.add(inicio, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
        inicio.setSize(1000, 720);
        inicio.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        content = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnPostres = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        btnMateriasPrimas = new javax.swing.JButton();
        btnPedidos = new javax.swing.JButton();
        btnInicio = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1250, 720));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        content.setBackground(new java.awt.Color(204, 204, 204));
        content.setMaximumSize(new java.awt.Dimension(1000, 720));
        content.setMinimumSize(new java.awt.Dimension(1000, 720));

        javax.swing.GroupLayout contentLayout = new javax.swing.GroupLayout(content);
        content.setLayout(contentLayout);
        contentLayout.setHorizontalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1050, Short.MAX_VALUE)
        );
        contentLayout.setVerticalGroup(
            contentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 720, Short.MAX_VALUE)
        );

        jPanel1.add(content, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 1050, 720));

        jPanel2.setBackground(new java.awt.Color(218, 95, 128));
        jPanel2.setForeground(new java.awt.Color(218, 95, 128));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnPostres.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        btnPostres.setForeground(new java.awt.Color(255, 255, 255));
        btnPostres.setText("POSTRES");
        btnPostres.setBorderPainted(false);
        btnPostres.setContentAreaFilled(false);
        btnPostres.setFocusPainted(false);
        btnPostres.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPostresMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPostresMouseExited(evt);
            }
        });
        btnPostres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPostresActionPerformed(evt);
            }
        });
        jPanel2.add(btnPostres, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 300, 90));

        btnClientes.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        btnClientes.setForeground(new java.awt.Color(255, 255, 255));
        btnClientes.setText("CLIENTES");
        btnClientes.setBorderPainted(false);
        btnClientes.setContentAreaFilled(false);
        btnClientes.setMaximumSize(new java.awt.Dimension(103, 29));
        btnClientes.setMinimumSize(new java.awt.Dimension(103, 29));
        btnClientes.setPreferredSize(new java.awt.Dimension(103, 29));
        btnClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnClientesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnClientesMouseExited(evt);
            }
        });
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });
        jPanel2.add(btnClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 300, 90));

        btnMateriasPrimas.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        btnMateriasPrimas.setForeground(new java.awt.Color(255, 255, 255));
        btnMateriasPrimas.setText("MATERIA PRIMA");
        btnMateriasPrimas.setBorderPainted(false);
        btnMateriasPrimas.setContentAreaFilled(false);
        btnMateriasPrimas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMateriasPrimasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMateriasPrimasMouseExited(evt);
            }
        });
        btnMateriasPrimas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMateriasPrimasActionPerformed(evt);
            }
        });
        jPanel2.add(btnMateriasPrimas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 490, 300, 90));

        btnPedidos.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        btnPedidos.setForeground(new java.awt.Color(255, 255, 255));
        btnPedidos.setText("PEDIDOS");
        btnPedidos.setBorderPainted(false);
        btnPedidos.setContentAreaFilled(false);
        btnPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPedidosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPedidosMouseExited(evt);
            }
        });
        btnPedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPedidosActionPerformed(evt);
            }
        });
        jPanel2.add(btnPedidos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 300, 90));

        btnInicio.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        btnInicio.setForeground(new java.awt.Color(255, 255, 255));
        btnInicio.setText("INICIO");
        btnInicio.setBorderPainted(false);
        btnInicio.setContentAreaFilled(false);
        btnInicio.setPreferredSize(new java.awt.Dimension(300, 90));
        btnInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnInicioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnInicioMouseExited(evt);
            }
        });
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });
        jPanel2.add(btnInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 300, 720));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPostresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPostresActionPerformed
        content.removeAll();
        content.add(postres, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
        postres.setSize(1000, 720);
        postres.setVisible(true);

    }//GEN-LAST:event_btnPostresActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        content.removeAll();
        content.add(clientes, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
        clientes.setSize(1000, 720);
        clientes.setVisible(true);

    }//GEN-LAST:event_btnClientesActionPerformed

    private void btnPostresMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPostresMouseEntered
        MouseEnteredButtons(btnPostres);
    }//GEN-LAST:event_btnPostresMouseEntered

    private void btnPostresMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPostresMouseExited
        MouseExitedButtons(btnPostres);
    }//GEN-LAST:event_btnPostresMouseExited

    private void btnClientesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClientesMouseEntered
        MouseEnteredButtons(btnClientes);
    }//GEN-LAST:event_btnClientesMouseEntered

    private void btnClientesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnClientesMouseExited
        MouseExitedButtons(btnClientes);
    }//GEN-LAST:event_btnClientesMouseExited

    private void btnMateriasPrimasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMateriasPrimasActionPerformed
        content.removeAll();
        content.add(materiasprimas, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
        materiasprimas.setSize(1000, 720);
        materiasprimas.setVisible(true);
    }//GEN-LAST:event_btnMateriasPrimasActionPerformed

    private void btnMateriasPrimasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMateriasPrimasMouseEntered
        MouseEnteredButtons(btnMateriasPrimas);
    }//GEN-LAST:event_btnMateriasPrimasMouseEntered

    private void btnMateriasPrimasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMateriasPrimasMouseExited
        MouseExitedButtons(btnMateriasPrimas);
    }//GEN-LAST:event_btnMateriasPrimasMouseExited

    private void btnPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPedidosActionPerformed
        content.removeAll();
        content.add(pedidos, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
        pedidos.setSize(1000, 720);
        pedidos.setVisible(true);
    }//GEN-LAST:event_btnPedidosActionPerformed

    private void btnPedidosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPedidosMouseEntered
        MouseEnteredButtons(btnPedidos);
    }//GEN-LAST:event_btnPedidosMouseEntered

    private void btnPedidosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPedidosMouseExited
        MouseExitedButtons(btnPedidos);
    }//GEN-LAST:event_btnPedidosMouseExited

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        content.removeAll();
        content.add(inicio, BorderLayout.CENTER);
        content.revalidate();
        content.repaint();
        inicio.setSize(1000, 720);
        inicio.setVisible(true);
    }//GEN-LAST:event_btnInicioActionPerformed

    private void btnInicioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInicioMouseEntered
        MouseEnteredButtons(btnInicio);
    }//GEN-LAST:event_btnInicioMouseEntered

    private void btnInicioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInicioMouseExited
        MouseExitedButtons(btnInicio);
    }//GEN-LAST:event_btnInicioMouseExited

    public void MouseExitedButtons(JButton button) {
        button.setBackground(Color.decode("#DA5F80"));
        button.setForeground(Color.decode("#FFFFFF"));
        button.setOpaque(true);
    }

    public void MouseEnteredButtons(JButton button) {
        button.setBackground(Color.decode("#e889a4"));
        button.setForeground(Color.decode("#DA5F80"));
        button.setOpaque(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnMateriasPrimas;
    private javax.swing.JButton btnPedidos;
    private javax.swing.JButton btnPostres;
    private javax.swing.JPanel content;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
