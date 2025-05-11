/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package repostería;

import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carolina
 */
public class JFProveedores extends javax.swing.JFrame {

    private Connection con = null;
    String[] datos = new String[6];
    public int idproveedor;

    /**
     * Creates new form JFProveedores
     */
    public void conectar() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/reposteria?user=root&password=");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "conectar");
        }
    }

    public JFProveedores() {
        initComponents();
        conectar();
    }

    public Object creacionObjeto() {
        Proveedores pv1 = new Proveedores();
        pv1.setNombre(txtNombre.getText());
        pv1.setNumeroTelefonico(txtNumTel.getText());
        pv1.setCalle(txtCalle.getText());
        pv1.setNumeroExterior(txtNumExt.getText());
        pv1.setColonia(txtColonia.getText());
        pv1.setCodigoPostal(txtCP.getText());
        return pv1;
    }

    public void rellenarDatosProveedor(String id) {
        ResultSet rs, rsP;
        Statement stm;
        idproveedor = Integer.parseInt(id);
        String ConsultaDatos = "SELECT * FROM proveedores WHERE idProveedor=" + idproveedor + "";
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(ConsultaDatos);
            if (rs.next()) {
                txtNombre.setText(rs.getString("Nombre"));
                txtNumTel.setText(rs.getString("NumeroTelefonico"));
                txtCalle.setText(rs.getString("Calle"));
                txtNumExt.setText(rs.getString("NumeroExterior"));
                txtColonia.setText(rs.getString("Colonia"));
                txtCP.setText(rs.getString("CodigoPostal"));
            } else {
                JOptionPane.showMessageDialog(null, "No se encuentra a ese proveedor");
            }
        } catch (SQLException sql) {
            JOptionPane.showMessageDialog(null, "HUBO UN ERROR PARA CARGAR LOS DATOS DEL PROVEEDOR");
        }
    }

    public void alta(Proveedores obj) {
        String secuenciaSQL = ("INSERT INTO Proveedores (Nombre, NumeroTelefonico,Calle,"
                + "NumeroExterior, Colonia, CodigoPostal) VALUE ('" + obj.getNombre() + "','"
                + obj.getNumeroTelefonico() + "','" + obj.getCalle() + "','" + obj.getNumeroExterior() + "','" + obj.getColonia()
                + "','" + obj.getCodigoPostal() + "')");
        try {
            Statement stm = con.createStatement();
            int filasAfectadas = stm.executeUpdate(secuenciaSQL);
            JOptionPane.showMessageDialog(null,"¡Se ha registrado un nuevo proveedor con éxito!");
            //System.out.println("Se ha afectado: "+filasAfectadas);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "alta");
        }
    }

    public void actualizar(Proveedores obj) {
        try {
            String secuenciaSQL = "UPDATE proveedores SET Nombre = ?, NumeroTelefonico = ?, "
                    + "Calle = ?, NumeroExterior = ?,Colonia = ?, CodigoPostal = ? "
                    + "WHERE idProveedor =" + idproveedor + "";
            PreparedStatement ps = con.prepareStatement(secuenciaSQL);
            ps.setString(1, obj.getNombre());
            ps.setString(2, obj.getNumeroTelefonico());
            ps.setString(3, obj.getCalle());
            ps.setString(4, obj.getNumeroExterior());
            ps.setString(5, obj.getColonia());
            ps.setString(6, obj.getCodigoPostal());

            int filasActualizadas = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "¡Los datos del proveedor fueron actualizados!");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "actualizacion");
        }
    }

    public void actualizarTabla() {
        DefaultTableModel miModelo = (DefaultTableModel) ProveedoresP.tablaProveedores.getModel();
        miModelo.setRowCount(0); // Limpiar filas existentes

        String sentenciaSQL = "SELECT idProveedor, Nombre, NumeroTelefonico,CONCAT(Calle,' ',NumeroExterior,', ',Colonia,' ',CodigoPostal) AS Domicilio FROM proveedores";

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sentenciaSQL);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = "";
                datos[5] = "";
                miModelo.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        panel1 = new Components.Panel();
        lblTitulo = new javax.swing.JLabel();
        panel2 = new Components.Panel();
        txtNombre = new Components.TextField();
        txtNumTel = new Components.TextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panel3 = new Components.Panel();
        txtCalle = new Components.TextField();
        txtNumExt = new Components.TextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panel4 = new Components.Panel();
        txtColonia = new Components.TextField();
        txtCP = new Components.TextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(244, 227, 221));

        btnGuardar.setBackground(new java.awt.Color(225, 141, 150));
        btnGuardar.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(102, 102, 102));
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        panel1.setBackgroundColor(new java.awt.Color(218, 95, 128));
        panel1.setBorderColor(new java.awt.Color(218, 95, 128));
        panel1.setFocusable(false);

        lblTitulo.setFont(new java.awt.Font("Quicksand", 1, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(226, 189, 220));

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        panel2.setBackgroundColor(new java.awt.Color(218, 95, 128));
        panel2.setBorderColor(new java.awt.Color(218, 95, 128));
        panel2.setFocusable(false);

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        txtNumTel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumTelKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(226, 189, 220));
        jLabel1.setText("Nombre");

        jLabel2.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(226, 189, 220));
        jLabel2.setText("Num. Télefono");

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNumTel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        panel3.setBackgroundColor(new java.awt.Color(218, 95, 128));
        panel3.setBorderColor(new java.awt.Color(218, 95, 128));
        panel3.setFocusable(false);

        txtCalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCalleKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(226, 189, 220));
        jLabel3.setText("Calle");

        jLabel4.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(226, 189, 220));
        jLabel4.setText("Número Exterior");

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtCalle, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE))
                .addGap(32, 32, 32)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNumExt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumExt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        panel4.setBackgroundColor(new java.awt.Color(218, 95, 128));
        panel4.setBorderColor(new java.awt.Color(218, 95, 128));
        panel4.setFocusable(false);

        txtCP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCPActionPerformed(evt);
            }
        });
        txtCP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCPKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(226, 189, 220));
        jLabel5.setText("Colonia");

        jLabel6.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(226, 189, 220));
        jLabel6.setText("Código Postal");

        javax.swing.GroupLayout panel4Layout = new javax.swing.GroupLayout(panel4);
        panel4.setLayout(panel4Layout);
        panel4Layout.setHorizontalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtColonia, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))
                .addGap(31, 31, 31)
                .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        panel4Layout.setVerticalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtColonia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15))
        );

        btnCerrar.setBackground(new java.awt.Color(138, 31, 42));
        btnCerrar.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(204, 204, 204));
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGap(245, 245, 245)
                            .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(panel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(27, 27, 27))
        );

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

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (validarCampos()) {
             Proveedores proveedor = (Proveedores) creacionObjeto();
            if (JFProveedores.lblTitulo.getText().equals("Registrar proveedor")) {
                if (clienteYaExiste(proveedor.getNombre(),proveedor.getNumeroTelefonico(),proveedor.getCalle(),proveedor.getNumeroExterior(),proveedor.getColonia(),proveedor.getCodigoPostal())) {
                    JOptionPane.showMessageDialog(null, "Ya hay un proveedor registrado con ese nombre!.");
                    return;
                }
                alta((Proveedores) creacionObjeto());
                SwingUtilities.invokeLater(() -> actualizarTabla());
            } else if (JFProveedores.lblTitulo.getText().equals("Actualizar proveedor")) {
                if (clienteYaExiste(proveedor.getNombre(),proveedor.getNumeroTelefonico(),proveedor.getCalle(),proveedor.getNumeroExterior(),proveedor.getColonia(),proveedor.getCodigoPostal())) {
                    JOptionPane.showMessageDialog(null, "Ya hay un proveedor registrado con ese nombre!.");
                    return;
                }
                actualizar((Proveedores) creacionObjeto());
                SwingUtilities.invokeLater(() -> actualizarTabla());
            }
            //JOptionPane.showMessageDialog(null, "Listo");
            txtNombre.setText("");
            txtNumTel.setText("");
            txtCalle.setText("");
            txtNumExt.setText("");
            txtColonia.setText("");
            txtCP.setText("");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtCPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCPActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
        actualizarTabla();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isLetter(c) && c != ' ' && c != '.' && c != '-')
            evt.consume();
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtCalleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCalleKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isLetter(c) && c != ' ' && c != '.' && c != '-')
            evt.consume();
    }//GEN-LAST:event_txtCalleKeyTyped

    private void txtNumTelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumTelKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c))
            evt.consume();
    }//GEN-LAST:event_txtNumTelKeyTyped

    private void txtCPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCPKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c))
            evt.consume();
    }//GEN-LAST:event_txtCPKeyTyped

    private boolean validarCampos() {
        String nombre = txtNombre.getText().trim();
        String telefono = txtNumTel.getText().trim();
        String calle = txtCalle.getText().trim();
        String numExt = txtNumExt.getText().trim();
        String colonia = txtColonia.getText().trim();
        String cp = txtCP.getText().trim();

        if (nombre.isEmpty() || nombre.length() > 30) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio y no puede tener más de 30 caracteres.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (telefono.length() != 10 || !telefono.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "El número de teléfono debe tener 10 dígitos.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (calle.isEmpty() || calle.length() > 30) {
            JOptionPane.showMessageDialog(this, "La calle es obligatoria y no puede tener más de 30 caracteres.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!numExt.matches("^[A-Za-z0-9/-]{1,10}$")) {
            JOptionPane.showMessageDialog(this, "El número exterior es obligatorio y no puede tener más de 10 caracteres.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (colonia.isEmpty() || colonia.length() > 30) {
            JOptionPane.showMessageDialog(this, "La colonia es obligatoria y no puede tener más de 30 caracteres.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cp.isEmpty() || cp.length() != 5) {
            JOptionPane.showMessageDialog(this, "El código postal es obligatorio y debe tener 5 caracteres numéricos.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public boolean clienteYaExiste(String nombre, String telefono, String calle, String numExt, String colonia, String cp) {
        String sql = "SELECT COUNT(*) FROM proveedores WHERE Nombre = ? AND NumeroTelefonico = ? AND Calle = ? AND NumeroExterior = ? AND Colonia = ? AND CodigoPostal = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, telefono);
            ps.setString(3, calle);
            ps.setString(4, numExt);
            ps.setString(5, colonia);
            ps.setString(6, cp);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar existencia por todos los campos: " + e.getMessage());
        }
        return false;
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
            java.util.logging.Logger.getLogger(JFProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFProveedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFProveedores().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JLabel lblTitulo;
    private Components.Panel panel1;
    private Components.Panel panel2;
    private Components.Panel panel3;
    private Components.Panel panel4;
    private Components.TextField txtCP;
    private Components.TextField txtCalle;
    private Components.TextField txtColonia;
    private Components.TextField txtNombre;
    private Components.TextField txtNumExt;
    private Components.TextField txtNumTel;
    // End of variables declaration//GEN-END:variables
}
