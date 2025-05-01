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
import javax.swing.SwingUtilities;
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

        jPanel1 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        panel1 = new Components.Panel();
        jLabel8 = new javax.swing.JLabel();
        cmPostre = new Components.ComboBox();
        panel2 = new Components.Panel();
        txtCalle = new Components.TextField();
        jLabel4 = new javax.swing.JLabel();
        txtNumExt = new Components.TextField();
        jLabel5 = new javax.swing.JLabel();
        txtColonia = new Components.TextField();
        jLabel7 = new javax.swing.JLabel();
        txtCP = new Components.TextField();
        jLabel6 = new javax.swing.JLabel();
        panel3 = new Components.Panel();
        txtNombre = new Components.TextField();
        jLabel1 = new javax.swing.JLabel();
        txtApellido = new Components.TextField();
        jLabel2 = new javax.swing.JLabel();
        txtNumTelefonico = new Components.TextField();
        jLabel3 = new javax.swing.JLabel();
        panel4 = new Components.Panel();
        lblTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(820, 520));

        jPanel1.setBackground(new java.awt.Color(244, 227, 221));

        btnGuardar.setBackground(new java.awt.Color(225, 141, 150));
        btnGuardar.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(102, 102, 102));
        btnGuardar.setText("Guardar");
        btnGuardar.setBorderPainted(false);
        btnGuardar.setOpaque(true);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCerrar.setBackground(new java.awt.Color(138, 31, 42));
        btnCerrar.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(204, 204, 204));
        btnCerrar.setText("Cerrar");
        btnCerrar.setBorderPainted(false);
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        panel1.setBackgroundColor(new java.awt.Color(218, 95, 128));
        panel1.setBorderColor(new java.awt.Color(218, 95, 128));
        panel1.setFocusable(false);

        jLabel8.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(226, 189, 220));
        jLabel8.setText("Postre Favorito");

        cmPostre.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmPostre, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(64, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmPostre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        panel2.setBackgroundColor(new java.awt.Color(218, 95, 128));
        panel2.setBorderColor(new java.awt.Color(218, 95, 128));
        panel2.setFocusable(false);

        txtCalle.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(226, 189, 220));
        jLabel4.setText("Calle");

        txtNumExt.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        txtNumExt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumExtActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(226, 189, 220));
        jLabel5.setText("Número Exterior");

        txtColonia.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(226, 189, 220));
        jLabel7.setText("Colonia");

        txtCP.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(226, 189, 220));
        jLabel6.setText("Código Postal");

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNumExt, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(26, 26, 26)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtColonia, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCP, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(21, 21, 21))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumExt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtColonia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        panel3.setBackgroundColor(new java.awt.Color(218, 95, 128));
        panel3.setBorderColor(new java.awt.Color(218, 95, 128));
        panel3.setFocusable(false);

        txtNombre.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(226, 189, 220));
        jLabel1.setText("Nombre");

        txtApellido.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(226, 189, 220));
        jLabel2.setText("Apellido");

        txtNumTelefonico.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(226, 189, 220));
        jLabel3.setText("Número de Teléfono");

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtNumTelefonico, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumTelefonico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        panel4.setBackgroundColor(new java.awt.Color(218, 95, 128));
        panel4.setBorderColor(new java.awt.Color(218, 95, 128));
        panel4.setFocusable(false);
        panel4.setPreferredSize(new java.awt.Dimension(332, 37));

        lblTitulo.setFont(new java.awt.Font("Quicksand", 1, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(226, 189, 220));

        javax.swing.GroupLayout panel4Layout = new javax.swing.GroupLayout(panel4);
        panel4.setLayout(panel4Layout);
        panel4Layout.setHorizontalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel4Layout.setVerticalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(6, 6, 6)
                    .addComponent(panel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(405, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(439, Short.MAX_VALUE)))
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
        //SwingUtilities.invokeLater(() -> actualizarTabla());
        actualizarTabla();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtNumExtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumExtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumExtActionPerformed

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
    private Components.ComboBox cmPostre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JLabel lblTitulo;
    private Components.Panel panel1;
    private Components.Panel panel2;
    private Components.Panel panel3;
    private Components.Panel panel4;
    private Components.TextField txtApellido;
    private Components.TextField txtCP;
    private Components.TextField txtCalle;
    private Components.TextField txtColonia;
    private Components.TextField txtNombre;
    private Components.TextField txtNumExt;
    private Components.TextField txtNumTelefonico;
    // End of variables declaration//GEN-END:variables
}
