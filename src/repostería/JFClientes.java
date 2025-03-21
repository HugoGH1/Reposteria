/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package repostería;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static repostería.PostreP.st;

/**
 *
 * @author Carolina
 */
public class JFClientes extends javax.swing.JFrame {

    public int idPostre;
    public int idcliente;
    private Connection con = null;
    String[] datos = new String[12];

    /**
     * Creates new form JFClientes
     */
    public void conectar() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/reposteria?user=root&password=");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "conectar");
        }
    }

    public JFClientes() {

        initComponents();
        conectar();
        RellenarCombo();

    }
    String queryPostres = "SELECT Nombre FROM Postres";

    public void RellenarCombo() {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(queryPostres);
            while (rs.next()) {
                //int idPostre = rs.getInt("idPostre");
                String Nombre = rs.getString("Nombre");
                Postres postre = new Postres(Nombre);
                cmPostre.addItem(Nombre);
                //cmPostre.addItem(postre);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "HUBO UN ERROR PARA CARGAR LOS PROYECTOS");
        }
    }

    public Object creacionObjeto() {
        Clientes c1 = new Clientes();
        c1.setNombre(txtNombre.getText());
        c1.setApellido(txtApellido.getText());
        c1.setNumeroTelefonico(txtNumTelefonico.getText());
        c1.setCalle(txtCalle.getText());
        c1.setNumeroExterior(txtNumExt.getText());
        c1.setColonia(txtColonia.getText());
        c1.setCodigoPostal(txtCP.getText());
        c1.setIdPostre(idPostre());
        return c1;
    }

    public void rellenarDatosCliente(String id) {
        ResultSet rs, rsP;
        Statement stm;
        int idP;
        idcliente = Integer.parseInt(id);
        String ConsultaDatos = "SELECT * FROM clientes WHERE idCliente=" + id + "";
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(ConsultaDatos);
            String nombrePostre = "";
            if (rs.next()) {
                txtNombre.setText(rs.getString("Nombre"));
                txtApellido.setText(rs.getString("Apellido"));
                txtNumTelefonico.setText(rs.getString("NumeroTelefonico"));
                txtCalle.setText(rs.getString("Calle"));
                txtNumExt.setText(rs.getString("NumeroExterior"));
                txtColonia.setText(rs.getString("Colonia"));
                txtCP.setText(rs.getString("CodigoPostal"));
                idP = rs.getInt("idPostre");
                System.out.println("El postre actual es: " + idP);
                String ConsultaPostre = "SELECT Nombre FROM postres WHERE idPostre =" + idP + "";
                rsP = stm.executeQuery(ConsultaPostre);
                if (rsP.next()) {
                    nombrePostre = rsP.getString("Nombre");
                    for (int i = 0; i < cmPostre.getItemCount(); i++) {
                        String comboItem = cmPostre.getItemAt(i).toString();
                        if (comboItem.equals(nombrePostre)) {
                            cmPostre.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encuentra a ese cliente");
            }
        } catch (SQLException sql) {
            JOptionPane.showMessageDialog(null, "HUBO UN ERROR PARA CARGAR LOS DATOS DEL CLIENTE");
        }
    }

    public int idPostre() {
        String nombre = cmPostre.getSelectedItem().toString();
        String secuenciaSQL = ("SELECT idPostre FROM postres WHERE Nombre = '" + nombre + "'");
        try {
            Statement stm = con.createStatement();
            System.out.println("hola");
            ResultSet rs = stm.executeQuery(secuenciaSQL);
            // idPostre = stm.executeQuery(secuenciaSQL);
            if (rs.next()) {
                idPostre = rs.getInt(1);
            }

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "idpostre");
        }
        return idPostre;
    }

    public void actualizarTabla() {
        DefaultTableModel miModelo = (DefaultTableModel) ClienteP.tablaClientes.getModel();
        miModelo.setRowCount(0); // Limpiar filas existentes

        String sentenciaSQL = "SELECT * FROM clientes";

        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sentenciaSQL);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                datos[7] = rs.getString(8);
                datos[8] = rs.getString(9);
                datos[9] = rs.getString(10);
                datos[10] = "";
                datos[11] = "";
                miModelo.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void alta(Clientes obj) {
        String secuenciaSQL = ("INSERT INTO Clientes (Nombre, Apellido, NumeroTelefonico,Calle,"
                + "NumeroExterior, Colonia, CodigoPostal,NumeroPedidos,idPostre) VALUE ('" + obj.getNombre() + "','" + obj.getApellido() + "','"
                + obj.getNumeroTelefonico() + "','" + obj.getCalle() + "','" + obj.getNumeroExterior() + "','" + obj.getColonia()
                + "','" + obj.getCodigoPostal() + "','" + 0 + "','" + obj.getIdPostre() + "')");
        try {
            Statement stm = con.createStatement();
            int filasAfectadas = stm.executeUpdate(secuenciaSQL);
            System.out.println("Se ha agregado un nuevo cliente");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "alta");
        }
    }

    public void actualizar(Clientes obj) {
        try {
            String secuenciaSQL = "UPDATE Clientes SET Nombre = ?, Apellido = ?, NumeroTelefonico = ?, "
                    + "Calle = ?, NumeroExterior = ?,Colonia = ?, CodigoPostal = ?, idPostre = ? "
                    + "WHERE idCliente =" + idcliente + "";
            PreparedStatement ps = con.prepareStatement(secuenciaSQL);
            ps.setString(1, obj.getNombre());
            ps.setString(2, obj.getApellido());
            ps.setString(3, obj.getNumeroTelefonico());
            ps.setString(4, obj.getCalle());
            ps.setString(5, obj.getNumeroExterior());
            ps.setString(6, obj.getColonia());
            ps.setString(7, obj.getCodigoPostal());
            ps.setInt(8, obj.getIdPostre());
            
            int filasActualizadas = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Los Datos del cliente fueron actualizados");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "actualizacion");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtNombre = new javax.swing.JTextField();
        txtApellido = new javax.swing.JTextField();
        txtNumTelefonico = new javax.swing.JTextField();
        txtCalle = new javax.swing.JTextField();
        txtNumExt = new javax.swing.JTextField();
        txtColonia = new javax.swing.JTextField();
        txtCP = new javax.swing.JTextField();
        cmPostre = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtNombre.setText("Nombre");

        txtApellido.setText("Apellido");

        txtNumTelefonico.setText("Numero Telefonico");

        txtCalle.setText("Calle");

        txtNumExt.setText("Numero Ext");

        txtColonia.setText("Colonia");

        txtCP.setText("CP");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmPostre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCerrar))
                            .addComponent(txtNumTelefonico, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtNumExt, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(txtColonia, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(123, 123, 123)
                        .addComponent(txtCP, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(151, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(185, 185, 185))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumTelefonico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumExt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtColonia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(cmPostre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCerrar))
                .addContainerGap(205, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (lblTitulo.getText().equals("Registrar cliente")) {
            alta((Clientes) creacionObjeto());
        } else if (lblTitulo.getText().equals("Actualizar cliente")) {
            actualizar((Clientes) creacionObjeto());
        }
        JOptionPane.showMessageDialog(null, "Listo");
        txtNombre.setText("");
        txtApellido.setText("");
        txtNumTelefonico.setText("");
        txtCalle.setText("");
        txtNumExt.setText("");
        txtColonia.setText("");
        txtCP.setText("");

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
        actualizarTabla();
    }//GEN-LAST:event_btnCerrarActionPerformed

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
            java.util.logging.Logger.getLogger(JFClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFClientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmPostre;
    public static javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtApellido;
    private javax.swing.JTextField txtCP;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JTextField txtColonia;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumExt;
    private javax.swing.JTextField txtNumTelefonico;
    // End of variables declaration//GEN-END:variables
}
