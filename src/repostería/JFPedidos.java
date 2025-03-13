/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package repostería;

import com.toedter.calendar.JDateChooser;
import java.lang.reflect.Constructor;
import java.sql.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Carolina
 */
public class JFPedidos extends javax.swing.JFrame {

    private Connection con = null;
    public int idPostre, idCliente, idTipoEntrega, pedidos;

    public void conectar() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/reposteria?user=root&password=");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "conectar");
        }
    }

    public JFPedidos() throws NoSuchMethodException {
        initComponents();
        conectar();
        configurarJDateChooser(calendar);
        RellenarCm("SELECT CONCAT_WS(' ',Nombre, Apellido) as NombreC FROM Clientes", "NombreC", cmCliente, Clientes.class);
        RellenarCm("SELECT Nombre FROM Postres", "Nombre", cmPostre, Postres.class);
        RellenarCm("SELECT Tipo FROM TipoEntrega", "Tipo", cmTipoEntrega, TipoEntregas.class);

    }

    public void configurarJDateChooser(JDateChooser jDateChooser) {
        // Obtener la fecha actual
        Calendar calendario = Calendar.getInstance();

        // Establecer la fecha al día siguiente (mañana)
        calendario.add(Calendar.DAY_OF_YEAR, 1);
        java.util.Date fechaMañana = calendario.getTime();
        jDateChooser.setMinSelectableDate(fechaMañana);
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
            System.out.println(sqle.getMessage());
        }
        return id;
    }

    public int costo() {
        int Cantidad = Integer.parseInt(spinnerCantidad.getValue().toString());
        int precio = 0;
        try {
            Statement stm = con.createStatement();
            String postre = cmPostre.getSelectedItem().toString();
            ResultSet rs = stm.executeQuery("SELECT PrecioVenta FROM postres WHERE Nombre = '" + postre + "'");
            // idPostre = stm.executeQuery(secuenciaSQL);
            if (rs.next()) {
                precio = rs.getInt(1);
            }
            System.out.println("Id postre listo" + rs);

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "idpostreCOSTO");
        }
        int Costo = Cantidad * precio;
        return Costo;
    }

    public Object creacionObjeto() {
        Pedidos p1 = new Pedidos();
        String nombre1 = cmCliente.getSelectedItem().toString();
        nombre1 = nombre1.trim().replaceAll(" +", " ");
        String[] partes = nombre1.split(" ", 2); // Divide en nombre y apellido
        String nombre = partes[0];
        System.out.println(nombre);
        String apellido = partes.length > 1 ? partes[1] : "";
        System.out.println(apellido);
        p1.setIdCliente(id("SELECT idCliente FROM clientes WHERE Nombre = '" + nombre + "' AND Apellido = '" + apellido + "'", cmCliente, idCliente));
        System.out.println("idcliete en creacion objeto" + p1.getIdCliente());
        String nombre2 = cmPostre.getSelectedItem().toString();
        p1.setIdPostre(id("SELECT idPostre FROM postres WHERE Nombre = '" + nombre2 + "'", cmPostre, idPostre));
        p1.setCantidad(Integer.parseInt(spinnerCantidad.getValue().toString()));
        p1.setCosto(costo());
        java.util.Date fecha = calendar.getDate();
        java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
        p1.setFechaEntrega(fechaSQL.toString());
        String nombre3 = cmTipoEntrega.getSelectedItem().toString();
        p1.setIdTipoEntrega(id("SELECT idTipoEntrega FROM tipoentrega WHERE Tipo = '" + nombre3 + "'", cmTipoEntrega, idTipoEntrega));
        return p1;
    }

    public void alta(Pedidos obj) {
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT NumeroPedidos FROM clientes WHERE idCliente = '" + obj.getIdCliente() + "'");
            // idPostre = stm.executeQuery(secuenciaSQL);
            if (rs.next()) {
                pedidos = rs.getInt(1);
            }
            System.out.println("pedidos antes del update" + pedidos);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        try {
            System.out.println("pedidos antes de aumentar" + pedidos);
            pedidos++;
            System.out.println("pedidos despues de aumentar" + pedidos);
            PreparedStatement ps = con.prepareStatement("UPDATE clientes SET NumeroPedidos = '" + pedidos + "' "
                    + "WHERE idcliente = '" + obj.getIdCliente() + "'");
            int filasAfectadas = ps.executeUpdate();
            System.out.println("Número de filas afectadas en update: " + filasAfectadas);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            sqle.printStackTrace();
        }
        System.out.println(obj.getIdCliente() + "id cliente antes de hacer insert");
        String secuenciaSQL = ("INSERT INTO Pedidos (idCliente, idPostre, Cantidad, Costo,"
                + "FechaEntrega, idTipoEntrega) VALUE ('" + obj.getIdCliente() + "','" + obj.getIdPostre() + "','"
                + obj.getCantidad() + "','" + obj.getCosto() + "','" + obj.getFechaEntrega() + "','" + 
                obj.getIdTipoEntrega() + "')");
        try {
            Statement stm = con.createStatement();
            int filasAfectadas = stm.executeUpdate(secuenciaSQL);
            System.out.println("Se ha agregado un nuevo pedido");
            System.out.println("Se ha afectado: " + filasAfectadas);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "alta");
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

        cmCliente = new javax.swing.JComboBox<>();
        cmPostre = new javax.swing.JComboBox<>();
        spinnerCantidad = new javax.swing.JSpinner();
        cmTipoEntrega = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        calendar = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        spinnerCantidad.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(cmCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmPostre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(spinnerCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(calendar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmTipoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(283, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(158, 158, 158))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmPostre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spinnerCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(78, 78, 78)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmTipoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calendar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addComponent(jButton1)
                .addContainerGap(77, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        alta((Pedidos) creacionObjeto());
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(JFPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new JFPedidos().setVisible(true);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(JFPedidos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser calendar;
    private javax.swing.JComboBox<String> cmCliente;
    private javax.swing.JComboBox<String> cmPostre;
    private javax.swing.JComboBox<String> cmTipoEntrega;
    private javax.swing.JButton jButton1;
    private javax.swing.JSpinner spinnerCantidad;
    // End of variables declaration//GEN-END:variables
}
