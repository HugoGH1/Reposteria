/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package repostería;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import static repostería.JFPostres.lblTituloPostre;

/**
 *
 * @author Carolina
 */
public class PostreP extends javax.swing.JPanel {

    //public static JTable tablaPostre;
    PreparedStatement ps;
    public static Statement st;
    private Connection con = null;
    String[] datos = new String[7];
    JTableHeader header = new JTableHeader();

    public PostreP() {
        initComponents();
        conectar();
        TablaPostres();
        header = tablaPostre.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer());
    }

    public void conectar() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/reposteria?user=root&password=");
            //System.out.println("Conexion Correcta");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "conectar");
        }
    }

    DefaultTableModel miModelo = new DefaultTableModel() {

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 4 || column == 5 || column == 6;
        }
    };
    public void TablaPostres() {
        DefaultTableModel miModelo = new DefaultTableModel();
        miModelo.addColumn("ID");
        miModelo.addColumn("Nombre");
        miModelo.addColumn("PrecioVenta");
        miModelo.addColumn("Cantidad");
        miModelo.addColumn("Editar");
        miModelo.addColumn("Eliminar");
        miModelo.addColumn("Hornear");
        tablaPostre.setModel(miModelo);
        tablaPostre.setRowHeight(30);
        String sentenciaSQL = "SELECT * FROM postres";
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sentenciaSQL);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = "";
                datos[5] = "";
                datos[6] = "";
                miModelo.addRow(datos);
            }
            tablaPostre.setModel(miModelo);
            tablaPostre.getColumnModel().getColumn(0).setMaxWidth(0);
            tablaPostre.getColumnModel().getColumn(0).setMinWidth(0);
            tablaPostre.getColumnModel().getColumn(0).setPreferredWidth(0);
        } catch (SQLException ex) {
            Logger.getLogger(PostreP.class.getName()).log(Level.SEVERE, null, ex);
        }

        tablaPostre.getColumnModel().getColumn(4).setCellRenderer(new PostreP.ButtonRenderer("/Images/lapiz.png"));
        tablaPostre.getColumnModel().getColumn(4).setCellEditor(new PostreP.ButtonEditor(new JCheckBox(), "/Images/lapiz.png", 4));
        tablaPostre.getColumnModel().getColumn(5).setCellRenderer(new PostreP.ButtonRenderer("/Images/borrar.png"));
        tablaPostre.getColumnModel().getColumn(5).setCellEditor(new PostreP.ButtonEditor(new JCheckBox(), "/Images/borrar.png", 5));
        tablaPostre.getColumnModel().getColumn(6).setCellRenderer(new PostreP.ButtonRenderer("/Images/oven.png"));
        tablaPostre.getColumnModel().getColumn(6).setCellEditor(new PostreP.ButtonEditor(new JCheckBox(), "/Images/oven.png", 6));
    }

    public void actualizarTabla() {
        DefaultTableModel miModelo = (DefaultTableModel) tablaPostre.getModel();
        miModelo.setRowCount(0); // Limpiar filas existentes

        String sentenciaSQL = "SELECT * FROM postres";

        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sentenciaSQL);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = "";
                datos[5] = "";
                datos[6] = "";
                miModelo.addRow(datos);
            }
        } catch (SQLException ex) {
            System.out.println("Hubo error al recargar la tabla");
            Logger.getLogger(PostreP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer(String iconPath) {
            setOpaque(true);
            setIcon(new ImageIcon(getClass().getResource(iconPath)));
            setBorderPainted(false);
            setContentAreaFilled(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Clase para editar celdas con botones
    class ButtonEditor extends DefaultCellEditor {

        private JButton button;
        private String label;
        private boolean clicked;
        private int columnIndex;
        private JTable table;

        public ButtonEditor(JCheckBox checkBox, String iconPath, int columnIndex) {
            super(checkBox);
            this.columnIndex = columnIndex;
            button = new JButton(new ImageIcon(getClass().getResource(iconPath)));
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setContentAreaFilled(false);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped(); // Para detener la edición cuando se hace clic
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table = table;
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                int selectedRow = table.getSelectedRow();
                String id = table.getValueAt(selectedRow, 0).toString();

                if (columnIndex == 4) {
                    //JOptionPane.showMessageDialog(button, "Editando " + id);
                    JFPostres IPos = new JFPostres();
                    IPos.setVisible(true);
                    IPos.rellenarDatosPostres(id);
                    lblTituloPostre.setText("Actualizar postre");
                } else if (columnIndex == 5) {
                  //  JOptionPane.showMessageDialog(button, "Eliminando " + id);
                    try {
                        PreparedStatement ps = con.prepareStatement("DELETE FROM Postres WHERE idPostre = ?");

                        ps.setString(1, id);
                        int filasAfectadas = ps.executeUpdate();
                       /// System.out.println("Número de filas afectadas: " + filasAfectadas);
                       // System.out.println("Postre eliminado");
                        SwingUtilities.invokeLater(() -> actualizarTabla());
                    } catch (SQLException sqle) {
                        //System.out.println(sqle.getMessage());
                        JOptionPane.showMessageDialog(null, "No puedes eliminar un postre");
                        //sqle.printStackTrace();
                    }
                    // miModelo.removeRow(row); // Elimina la fila visualmente (falta eliminar de BD)
                } else if (columnIndex == 6) {
                   // JOptionPane.showMessageDialog(button, "Horneando " + id);
                    ResultSet rs;
                    PreparedStatement pstm;
                    String sentencia = "SELECT idMateriasPrimas, Cantidad FROM recetas WHERE idPostre = ?";
                    try {
                        pstm = con.prepareStatement(sentencia);
                        pstm.setString(1, id);
                        rs = pstm.executeQuery();
                        ResultSet rs1 = null;
                        boolean todoCorrecto = true;
                        int contador = 0;
                        int conta = 0;
                        try {
                            con.setAutoCommit(false);
                            while (rs.next()) {
                                //System.out.println(rs.getString("cantidad"));

                                PreparedStatement pstm1;
                                String sentencia1 = "SELECT stock FROM materiasprimas WHERE idmateriasprimas = ?";
                                try {
                                    pstm1 = con.prepareStatement(sentencia1);
                                    pstm1.setString(1, rs.getString("idmateriasprimas"));
                                    rs1 = pstm1.executeQuery();
                                    List<ActualizacionStock> actualizaciones = new ArrayList<>();

                                    while (rs1.next()) {
                                        int stock = Integer.parseInt(rs1.getString("stock").trim());
                                        int cantidad = Integer.parseInt(rs.getString("cantidad").trim());
                                        int resto = stock - cantidad;

                                        if (resto >= 0) {
                                            // Guardamos temporalmente lo que se actualizará
                                            actualizaciones.add(new ActualizacionStock(resto, rs.getString("idmateriasprimas")));
                                            conta++;
                                            //System.out.println(todoCorrecto);
                                        } else {

                                            todoCorrecto = false;
                                            JOptionPane.showMessageDialog(null, "Falta " + Math.abs(resto) + " de la materia prima " + rs.getString("idmateriasprimas"));

                                        }
                                        contador++;
                                    }
                                    if (todoCorrecto == false) {
                                        break; // Rompe el while externo
                                    }

                                    if (todoCorrecto && !actualizaciones.isEmpty()) {
                                        //System.out.println(todoCorrecto);
                                        for (ActualizacionStock act : actualizaciones) {
                                            String sql = "UPDATE materiasprimas SET stock = ? WHERE idmateriasprimas = ?";
                                            PreparedStatement ps = con.prepareStatement(sql);
                                            ps.setInt(1, act.nuevoStock);
                                            ps.setString(2, act.idMateriaPrima);
                                            ps.executeUpdate();
                                           // System.out.println(todoCorrecto);
                                        }
                                       // System.out.println(conta + " " + contador);

                                    } else {
                                        con.rollback(); //  Cancelamos cualquier posible cambio
                                        //System.out.println("Transacción cancelada.");
                                    }

                                    // con.setAutoCommit(true); // Restauramos auto-commit
                                } catch (SQLException sqle) {
                                    System.out.println(sqle.getMessage());
                                    sqle.printStackTrace();
                                }
                            }
                        } catch (SQLException sqle) {
                            System.out.println(sqle.getMessage());
                            sqle.printStackTrace();

                        } catch (Exception e) {
                            try {
                                con.rollback();
                                con.setAutoCommit(true);
                            } catch (SQLException se) {
                                se.printStackTrace();
                            }
                            e.printStackTrace();
                           // JOptionPane.showMessageDialog(null, "Error al procesar la transacción.");
                        }
                        if (todoCorrecto && conta == contador) {
                            con.commit();
                            con.setAutoCommit(true);
                            int suma = 0;
                            ResultSet rs2;
                            PreparedStatement pstm2;
                            String sentencia2 = "SELECT cantidad FROM postres WHERE idPostre = ?";
                            try {
                                pstm = con.prepareStatement(sentencia2);
                                pstm.setString(1, id);
                                rs2 = pstm.executeQuery();
                                ResultSet rs3 = null;
                                try {
                                    while (rs2.next()) {
                                        PreparedStatement pstm1;
                                        String sentencia1 = "SELECT porciones FROM recetas WHERE idpostre = ?";
                                        try {
                                            pstm1 = con.prepareStatement(sentencia1);
                                            pstm1.setString(1, id);
                                            rs3 = pstm1.executeQuery();

                                            while (rs3.next()) {
                                                int porciones = Integer.parseInt(rs3.getString("porciones").trim());
                                                int cantidadP = Integer.parseInt(rs2.getString("cantidad").trim());
                                                suma = cantidadP + porciones;
                                            }
                                            try {
                                                String sql = "UPDATE postres SET cantidad = ? WHERE idpostre = ?";
                                                PreparedStatement ps = con.prepareStatement(sql);
                                                ps.setInt(1, suma);
                                                ps.setString(2, id);
                                                ps.executeUpdate();
                                            } catch (SQLException exs) {
                                                JOptionPane.showMessageDialog(null, "No se pudo actualizar la cantidad" + exs.getMessage());
                                            }
                                        } catch (SQLException ex) {
                                            JOptionPane.showMessageDialog(null, "Error en consulta de porciones");
                                        }
                                    }
                                } catch (SQLException ex) {
                                    JOptionPane.showMessageDialog(null, "Error en consulta de cantidad");
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(null, "Error en consulta de cantidad");
                            }
                            SwingUtilities.invokeLater(() -> actualizarTabla());
                        } else {
                            con.rollback();
                            con.setAutoCommit(true);
                        }//  Confirmamos la transacción
                      //  System.out.println(todoCorrecto);
                        //JOptionPane.showMessageDialog(null, "Todos los stocks fueron actualizados correctamente.");
                    } catch (SQLException ex) {
                        Logger.getLogger(PostreP.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
            clicked = false;
            return null;
        }

        @Override
        public boolean stopCellEditing() {
            clicked = false;
            return super.stopCellEditing();
        }

        @Override
        public boolean isCellEditable(EventObject e
        ) {
            return true;
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPostre = new javax.swing.JTable();
        btnInsertar = new Components.Button();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 194, 209));
        setMaximumSize(new java.awt.Dimension(950, 720));
        setMinimumSize(new java.awt.Dimension(950, 720));
        setPreferredSize(new java.awt.Dimension(950, 720));

        jScrollPane1.setBorder(null);
        jScrollPane1.setFocusable(false);

        tablaPostre.setFont(new java.awt.Font("Quicksand SemiBold", 0, 14)); // NOI18N
        tablaPostre.setForeground(new java.awt.Color(51, 51, 51));
        tablaPostre.setFocusable(false);
        tablaPostre.setRowHeight(32);
        tablaPostre.setSelectionBackground(new java.awt.Color(243, 209, 220));
        tablaPostre.setSelectionForeground(new java.awt.Color(153, 153, 153));
        jScrollPane1.setViewportView(tablaPostre);

        btnInsertar.setText("Agregar Nuevo");
        btnInsertar.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        btnInsertar.setRadius(20);
        btnInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Quicksand", 1, 84)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(218, 95, 128));
        jLabel1.setText("POSTRES");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 865, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnInsertar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(278, 278, 278)
                        .addComponent(jLabel1)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnInsertar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        JFPostres IPos = new JFPostres();
        IPos.setVisible(true);
        lblTituloPostre.setText("Registrar postre");
    }//GEN-LAST:event_btnInsertarActionPerformed

    static class HeaderRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Personalizar el renderizador del encabezado para centrar el texto
            setHorizontalAlignment(SwingConstants.CENTER);
            setBackground(Color.decode("#E18D96"));
            setFont(new java.awt.Font("Quicksand SemiBold", 0, 14));
            setForeground(Color.WHITE);
            setPreferredSize(new Dimension(865, 30));

            return this;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Components.Button btnInsertar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tablaPostre;
    // End of variables declaration//GEN-END:variables
}
