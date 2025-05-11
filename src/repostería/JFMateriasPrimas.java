/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package repostería;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import static repostería.JFClientes.lblTitulo;
import static repostería.MateriaPrimaP.st;
import static repostería.MateriaPrimaP.tablaMaterias;

/**
 *
 * @author Carolina
 */
public class JFMateriasPrimas extends javax.swing.JFrame {

    private Connection con = null;
    String[] datos = new String[8];
    public int idmateriaprima;
    public int idCategoria, idProveedor, idUnidadMedida;

    /**
     * Creates new form JFMateriasPrimas
     */
    public void conectar() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/reposteria?user=root&password=");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "conectar");
        }
    }

    public JFMateriasPrimas() throws NoSuchMethodException {
        initComponents();
        conectar();
        RellenarCm("SELECT Categoria FROM Categorias", "Categoria", cmCategoria, Categorias.class);
        RellenarCm("SELECT Unidad FROM UnidadesMedidas", "Unidad", cmUnidadMedida, UnidadesMedidas.class);
        RellenarCm("SELECT Nombre FROM Proveedores", "Nombre", cmProveedor, Proveedores.class);
    }

    public <T> void RellenarCm(String Consulta, String Columna, JComboBox Combo, Class<T> clase) throws NoSuchMethodException {

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(Consulta);
            Constructor<T> constructor = clase.getDeclaredConstructor(String.class);
            while (rs.next()) {
                String dato = rs.getString(Columna);
                Combo.addItem(dato);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "HUBO UN ERROR PARA CARGAR LOS DATOS");
        }
    }

    public int id(String query, JComboBox<String> cmPostre1, int id) {
        try {
            Statement stm = con.createStatement();
            //System.out.println("hola");
            ResultSet rs = stm.executeQuery(query);
            // idPostre = stm.executeQuery(secuenciaSQL);
            if (rs.next()) {
                id = rs.getInt(1);
            }
            //System.out.println("Id postre listo" + rs);

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "idpostreID");
        }
        return id;
    }

    public Object creacionObjeto() {
        MateriasPrimas mp1 = new MateriasPrimas();
        mp1.setNombre(txtNombre.getText());
        String nombre1 = cmCategoria.getSelectedItem().toString();
        mp1.setIdCategoria(id("SELECT idCategoria FROM categorias WHERE Categoria = '" + nombre1 + "'", cmCategoria, idCategoria));
        mp1.setStock(Integer.parseInt(txtStock.getText()));
        String nombre2 = cmUnidadMedida.getSelectedItem().toString();
        mp1.setidUnidadMedida(id("SELECT idUnidadMedida FROM UnidadesMedidas WHERE Unidad = '" + nombre2 + "'", cmUnidadMedida, idUnidadMedida));
        String nombre3 = cmProveedor.getSelectedItem().toString();
        mp1.setIdProveedor(id("SELECT idProveedor FROM proveedores WHERE Nombre= '" + nombre3 + "'", cmProveedor, idProveedor));
        return mp1;
    }

    public void rellenarDatosMateria(String id) {
        ResultSet rs, rsC, rsU, rsP;
        Statement stm;
        int idC, idU, idP;
        idmateriaprima = Integer.parseInt(id);
        String ConsultaDatos = "SELECT * FROM materiasprimas WHERE idMateriasPrimas=" + id + "";
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(ConsultaDatos);
            String nombreCategoria = "";
            String nombreUnidad = "";
            String nombreProveedor = "";
            if (rs.next()) {
                txtNombre.setText(rs.getString("Nombre"));
                txtStock.setText(rs.getString("Stock"));
            }
            idC = rs.getInt("idCategoria");
            //System.out.println("La categoría actual es: " + idC);
            String ConsultaCategoria = "SELECT Categoria FROM categorias WHERE idCategoria =" + idC + "";
            rsC = stm.executeQuery(ConsultaCategoria);
            if (rsC.next()) {
                nombreCategoria = rsC.getString("Categoria");
                for (int i = 0; i < cmCategoria.getItemCount(); i++) {
                    String comboItem = cmCategoria.getItemAt(i).toString();
                    if (comboItem.equals(nombreCategoria)) {
                        cmCategoria.setSelectedIndex(i);
                        break;
                    }
                }
            }
            rs = stm.executeQuery(ConsultaDatos);
            if (rs.next()) {
                idU = rs.getInt("idUnidadMedida");
                //System.out.println("La unidad medida actual es: " + idU);
                String ConsultaUnidadMedida = "SELECT Unidad FROM unidadesmedidas WHERE idUnidadMedida =" + idU + "";
                rsU = stm.executeQuery(ConsultaUnidadMedida);
                if (rsU.next()) {
                    nombreUnidad = rsU.getString("Unidad");
                    for (int i = 0; i < cmUnidadMedida.getItemCount(); i++) {
                        String comboItem = cmUnidadMedida.getItemAt(i).toString();
                        if (comboItem.equals(nombreUnidad)) {
                            cmUnidadMedida.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
            rs = stm.executeQuery(ConsultaDatos);
            if (rs.next()) {
                idP = rs.getInt("idProveedor");
                //System.out.println("El proveedor actual es: " + idP);
                String ConsultaProveedor = "SELECT Nombre FROM proveedores WHERE idProveedor =" + idP + "";
                rsP = stm.executeQuery(ConsultaProveedor);
                if (rsP.next()) {
                    nombreProveedor = rsP.getString("Nombre");
                    for (int i = 0; i < cmProveedor.getItemCount(); i++) {
                        String comboItem = cmProveedor.getItemAt(i).toString();
                        if (comboItem.equals(nombreProveedor)) {
                            cmProveedor.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        } catch (SQLException sql) {
            JOptionPane.showMessageDialog(null, "HUBO UN ERROR PARA CARGAR LOS DATOS DEL CLIENTE");
            System.out.println(sql.getMessage());
        }
    }

    public void actualizarTabla() {
        DefaultTableModel miModelo = (DefaultTableModel) tablaMaterias.getModel();
        miModelo.setRowCount(0); // Limpiar filas existentes

        String sentenciaSQL = "CALL pSelectMateria()";

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
                datos[6] = "";
                datos[7] = "";
                miModelo.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MateriaPrimaP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void alta(MateriasPrimas obj) {
        String secuenciaSQL = ("INSERT INTO materiasprimas (Nombre, idCategoria, Stock, idUnidadMedida,"
                + "idProveedor) VALUE ('" + obj.getNombre() + "','" + obj.getIdCategoria() + "','"
                + obj.getStock() + "','" + obj.getidUnidadMedida() + "','" + obj.getIdProveedor() + "')");
        try {
            Statement stm = con.createStatement();
            int filasAfectadas = stm.executeUpdate(secuenciaSQL);
            //System.out.println("Se ha agregado una nueva materia prima");
            //System.out.println("Se ha afectado: " + filasAfectadas);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "alta");
        }
    }

    public void actualizar(MateriasPrimas obj) {
        try {
            String secuenciaSQL = "UPDATE materiasprimas SET Nombre = ?, idCategoria = ?, Stock = ?, "
                    + "idUnidadMedida = ?, idProveedor = ? "
                    + "WHERE idMateriasPrimas =" + idmateriaprima + "";
            PreparedStatement ps = con.prepareStatement(secuenciaSQL);
            ps.setString(1, obj.getNombre());
            ps.setInt(2, obj.getIdCategoria());
            ps.setInt(3, obj.getStock());
            ps.setInt(4, obj.getidUnidadMedida());
            ps.setInt(5, obj.getIdProveedor());

            int filasActualizadas = ps.executeUpdate();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "actualizacion");
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
        btnCerrar = new javax.swing.JButton();
        panel1 = new Components.Panel();
        lblTituloMP = new javax.swing.JLabel();
        panel2 = new Components.Panel();
        txtNombre = new Components.TextField();
        cmCategoria = new Components.ComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        panel3 = new Components.Panel();
        txtStock = new Components.TextField();
        cmProveedor = new Components.ComboBox();
        cmUnidadMedida = new Components.ComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(244, 227, 221));

        btnGuardar.setBackground(new java.awt.Color(225, 141, 150));
        btnGuardar.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(102, 102, 102));
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCerrar.setBackground(new java.awt.Color(138, 31, 42));
        btnCerrar.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(204, 204, 204));
        btnCerrar.setText("CERRAR");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        panel1.setBackground(new java.awt.Color(218, 95, 128));
        panel1.setBackgroundColor(new java.awt.Color(218, 95, 128));
        panel1.setBorderColor(new java.awt.Color(218, 95, 128));
        panel1.setFocusable(false);

        lblTituloMP.setFont(new java.awt.Font("Quicksand", 1, 18)); // NOI18N
        lblTituloMP.setForeground(new java.awt.Color(226, 189, 220));

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblTituloMP, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTituloMP, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel2.setBackgroundColor(new java.awt.Color(218, 95, 128));
        panel2.setBorderColor(new java.awt.Color(218, 95, 128));
        panel2.setFocusable(false);

        txtNombre.setForeground(new java.awt.Color(153, 153, 153));
        txtNombre.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        cmCategoria.setForeground(new java.awt.Color(153, 153, 153));
        cmCategoria.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(226, 189, 220));
        jLabel1.setText("Nombre de Materia");

        jLabel2.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(226, 189, 220));
        jLabel2.setText("Categoría");

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        panel3.setBackgroundColor(new java.awt.Color(218, 95, 128));
        panel3.setBorderColor(new java.awt.Color(218, 95, 128));
        panel3.setFocusable(false);

        txtStock.setForeground(new java.awt.Color(153, 153, 153));
        txtStock.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        txtStock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtStockKeyTyped(evt);
            }
        });

        cmProveedor.setForeground(new java.awt.Color(153, 153, 153));
        cmProveedor.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N

        cmUnidadMedida.setForeground(new java.awt.Color(153, 153, 153));
        cmUnidadMedida.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(226, 189, 220));
        jLabel3.setText("Stock");

        jLabel4.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(226, 189, 220));
        jLabel4.setText("Proveedor");

        jLabel5.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(226, 189, 220));
        jLabel5.setText("Unidad de Medida");

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtStock, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmProveedor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                .addGap(27, 27, 27)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmUnidadMedida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmUnidadMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(24, 24, 24)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(0, 37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (validarCampos()) {
            MateriasPrimas materia = (MateriasPrimas) creacionObjeto();
            if (lblTituloMP.getText().equals("Registrar Materia Prima")) {
                if (materiaYaExiste(materia.getNombre(), materia.getIdCategoria(), materia.getStock(),materia.getidUnidadMedida(),materia.getIdProveedor())) {
                    JOptionPane.showMessageDialog(null, "Ya está registrada esta materia prima!.");
                    return;
                }
                alta((MateriasPrimas) creacionObjeto());
                SwingUtilities.invokeLater(() -> MateriaPrimaP.actualizarTabla());
            } else if (lblTituloMP.getText().equals("Actualizar Materia Prima")) {
                if (materiaYaExiste(materia.getNombre(), materia.getIdCategoria(), materia.getStock(),materia.getidUnidadMedida(),materia.getIdProveedor())) {
                    JOptionPane.showMessageDialog(null, "Ya está registrada esta materia prima!.");
                    return;
                }
                actualizar((MateriasPrimas) creacionObjeto());
                SwingUtilities.invokeLater(() -> MateriaPrimaP.actualizarTabla());
            }
            txtNombre.setText("");
            txtStock.setText("");
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
        actualizarTabla();
    }//GEN-LAST:event_btnCerrarActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtStockKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtStockKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c))
            evt.consume();
    }//GEN-LAST:event_txtStockKeyTyped

    private boolean validarCampos() {
        String nombre = txtNombre.getText().trim();
        String stock = txtStock.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio y no puede tener más de 30 caracteres.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (stock.isEmpty()) {
            JOptionPane.showMessageDialog(this, "¡Por favor, ingresa la cantidad de stock disponible!.", "Error de validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public boolean materiaYaExiste(String nombre, int categoria, int stock, int unidadm, int proveedor) {
        String sql = "SELECT COUNT(*) FROM materiasprimas WHERE Nombre = ? AND idCategoria = ? AND Stock = ? AND idUnidadMedida = ? AND idProveedor = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setInt(2, categoria);
            ps.setInt(3, stock);
            ps.setInt(4, unidadm);
            ps.setInt(5, proveedor);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar existencia: " + e.getMessage());
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
            java.util.logging.Logger.getLogger(JFMateriasPrimas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFMateriasPrimas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFMateriasPrimas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFMateriasPrimas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new JFMateriasPrimas().setVisible(true);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(JFMateriasPrimas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnGuardar;
    private Components.ComboBox cmCategoria;
    private Components.ComboBox cmProveedor;
    private Components.ComboBox cmUnidadMedida;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JLabel lblTituloMP;
    private Components.Panel panel1;
    private Components.Panel panel2;
    private Components.Panel panel3;
    private Components.TextField txtNombre;
    private Components.TextField txtStock;
    // End of variables declaration//GEN-END:variables
}
