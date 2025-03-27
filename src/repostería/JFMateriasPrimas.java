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
            JOptionPane.showMessageDialog(null, e.getMessage() + "HUBO UN ERROR PARA CARGAR LOS PROYECTOS");
        }
    }

    public int id(String query, JComboBox<String> cmPostre1, int id) {
        try {
            Statement stm = con.createStatement();
            System.out.println("hola");
            ResultSet rs = stm.executeQuery(query);
            // idPostre = stm.executeQuery(secuenciaSQL);
            if (rs.next()) {
                id = rs.getInt(1);
            }
            System.out.println("Id postre listo" + rs);

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
            System.out.println("La categoría actual es: " + idC);
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
                System.out.println("La unidad medida actual es: " + idU);
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
                System.out.println("El proveedor actual es: " + idP);
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

    String sentenciaSQL = "SELECT * FROM materiasprimas";

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
            System.out.println("Se ha agregado una nueva materia prima");
            System.out.println("Se ha afectado: " + filasAfectadas);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "alta");
        }
    }
     public void actualizar(MateriasPrimas obj) {
        try {
            String secuenciaSQL = "UPDATE materiasprimas SET Nombre = ?, idCategoria = ?, Stock = ?, "
                    + "idUnidadMedida = ?, idProveedor = ? "
                    + "WHERE idMateriasPrimas =" + idmateriaprima +"";
            PreparedStatement ps = con.prepareStatement(secuenciaSQL);
            ps.setString(1, obj.getNombre());
            ps.setInt(2, obj.getIdCategoria());
            ps.setInt(3, obj.getStock());
            ps.setInt(4, obj.getidUnidadMedida());
            ps.setInt(5, obj.getIdProveedor());
         
            int filasActualizadas = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Los Datos de la materia prima fueron actualizados");
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

        txtNombre = new javax.swing.JTextField();
        cmCategoria = new javax.swing.JComboBox<>();
        cmUnidadMedida = new javax.swing.JComboBox<>();
        txtStock = new javax.swing.JTextField();
        cmProveedor = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        lblTituloMP = new javax.swing.JLabel();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtNombre.setText("Nombre");

        txtStock.setText("Stock");

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cmProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTituloMP, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                        .addComponent(cmCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmUnidadMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(jButton1)
                .addGap(53, 53, 53)
                .addComponent(btnCerrar)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblTituloMP, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmUnidadMedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addComponent(cmProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(btnCerrar))
                .addGap(35, 35, 35))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (lblTituloMP.getText().equals("Registrar Materia Prima")) {
            alta((MateriasPrimas) creacionObjeto());
        } else if (lblTituloMP.getText().equals("Actualizar Materia Prima")) {
            actualizar((MateriasPrimas) creacionObjeto());
        }
        JOptionPane.showMessageDialog(null, "Listo");
        txtNombre.setText("");
        txtStock.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

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
    private javax.swing.JComboBox<String> cmCategoria;
    private javax.swing.JComboBox<String> cmProveedor;
    private javax.swing.JComboBox<String> cmUnidadMedida;
    private javax.swing.JButton jButton1;
    public static javax.swing.JLabel lblTituloMP;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtStock;
    // End of variables declaration//GEN-END:variables
}
