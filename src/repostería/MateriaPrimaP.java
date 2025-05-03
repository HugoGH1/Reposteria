/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package repostería;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import static repostería.ClienteP.st;
import static repostería.ClienteP.tablaClientes;
import static repostería.JFMateriasPrimas.lblTituloMP;
import static repostería.PostreP.tablaPostre;

/**
 *
 * @author Carolina
 */
public class MateriaPrimaP extends javax.swing.JPanel {

    PreparedStatement ps;
    static Statement st;
    String id;
    static Connection con = null;
    static String[] datos = new String[8];
    JTableHeader header = new JTableHeader();

    public MateriaPrimaP() {
        initComponents();
        conectar();
        TablaMaterias();
        header = tablaMaterias.getTableHeader();
        header.setDefaultRenderer(new MateriaPrimaP.HeaderRenderer());
    }

    public void conectar() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/reposteria?user=root&password=");
            System.out.println("Conexion Correcta");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "conectar");
        }
    }
    DefaultTableModel miModelo = new DefaultTableModel() {

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 6 || column == 7;
        }
    };

    public void TablaMaterias() {
        miModelo.addColumn("ID");
        miModelo.addColumn("Nombre");
        miModelo.addColumn("Categoría");
        miModelo.addColumn("Stock");
        miModelo.addColumn("Unidad Medida");
        miModelo.addColumn("Proveedor");
        miModelo.addColumn("Editar");
        miModelo.addColumn("Eliminar");

        tablaMaterias.setModel(miModelo);
        tablaMaterias.setRowHeight(30);

        String sentenciaSQL = "SELECT mp.idMateriasPrimas,mp.Nombre,cat.Categoria,mp.Stock,um.Unidad,pr.Nombre AS Proveedor FROM materiasprimas mp INNER JOIN categorias cat ON mp.idCategoria = cat.idCategoria INNER JOIN unidadesmedidas um ON mp.idUnidadMedida = um.idUnidadMedida INNER JOIN proveedores pr ON mp.idProveedor = pr.idProveedor ORDER BY mp.Stock DESC";
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
            tablaMaterias.setModel(miModelo);
            tablaMaterias.getColumnModel().getColumn(0).setMaxWidth(0);
            tablaMaterias.getColumnModel().getColumn(0).setMinWidth(0);
            tablaMaterias.getColumnModel().getColumn(0).setPreferredWidth(0);

        } catch (SQLException ex) {
            Logger.getLogger(PostreP.class.getName()).log(Level.SEVERE, null, ex);
        }

        tablaMaterias.getColumnModel().getColumn(6).setCellRenderer(new MateriaPrimaP.ButtonRenderer("/Images/lapiz.png"));
        tablaMaterias.getColumnModel().getColumn(6).setCellEditor(new MateriaPrimaP.ButtonEditor(new JCheckBox(), "/Images/lapiz.png", 6));

        tablaMaterias.getColumnModel().getColumn(7).setCellRenderer(new MateriaPrimaP.ButtonRenderer("/Images/borrar.png"));
        tablaMaterias.getColumnModel().getColumn(7).setCellEditor(new MateriaPrimaP.ButtonEditor(new JCheckBox(), "/Images/borrar.png", 7));
    }

    public static void actualizarTabla() {
        DefaultTableModel miModelo = (DefaultTableModel) tablaMaterias.getModel();
        miModelo.setRowCount(0);

        String sentenciaSQL = "CALL pSelectMateria()";

        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sentenciaSQL);
            while (rs.next()) {
                datos[0] = rs.getString(1); // ID
                datos[1] = rs.getString(2); // Nombre
                datos[2] = rs.getString(3); // Categoría
                datos[3] = rs.getString(4); // Stock
                datos[4] = rs.getString(5); // Unidad Medida
                datos[5] = rs.getString(6); // Proveedor
                datos[6] = "";              // Editar (botón)
                datos[7] = "";              // Eliminar (botón)
                miModelo.addRow(datos);
            }
            tablaMaterias.setModel(miModelo);

            // Ocultar columna ID
            tablaMaterias.getColumnModel().getColumn(0).setMaxWidth(0);
            tablaMaterias.getColumnModel().getColumn(0).setMinWidth(0);
            tablaMaterias.getColumnModel().getColumn(0).setPreferredWidth(0);
        } catch (SQLException ex) {
            Logger.getLogger(MateriaPrimaP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer(String iconPath) {
            setOpaque(false);
            setIcon(new ImageIcon(getClass().getResource(iconPath)));
            setBorderPainted(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            setBackground(new Color(0, 0, 0, 0));
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
            button.setOpaque(false);
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
                id = table.getValueAt(selectedRow, 0).toString();

                if (columnIndex == 6) {
                    JOptionPane.showMessageDialog(button, "Editando " + id);
                    JFMateriasPrimas IMat = null;
                    try {
                        IMat = new JFMateriasPrimas();
                    } catch (NoSuchMethodException ex) {
                        Logger.getLogger(MateriaPrimaP.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    IMat.setVisible(true);
                    lblTituloMP.setText("Actualizar Materia Prima");
                    IMat.rellenarDatosMateria(id);

                } else if (columnIndex == 7) {
                    int resp = JOptionPane.showConfirmDialog(null, "" + "¿Quieres continuar?", "¡Estás a punto de eliminar esta materia prima!", JOptionPane.YES_NO_OPTION);
                    if (resp == 0) { // respuesta NO
                        try {
                            PreparedStatement ps = con.prepareStatement("DELETE FROM materiasprimas WHERE idMateriasPrimas = ?");
                            ps.setString(1, id);
                            int filasAfectadas = ps.executeUpdate();
                            System.out.println("Número de filas afectadas: " + filasAfectadas);
                            SwingUtilities.invokeLater(() -> actualizarTabla());
                        } catch (SQLException sqle) {
                            System.out.println(sqle.getMessage());
                            sqle.printStackTrace();
                        }
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
        public boolean isCellEditable(EventObject e) {
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

        btnAltaMateria = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMaterias = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 194, 209));
        setMaximumSize(new java.awt.Dimension(950, 720));
        setMinimumSize(new java.awt.Dimension(950, 720));
        setPreferredSize(new java.awt.Dimension(950, 720));

        btnAltaMateria.setBackground(new java.awt.Color(115, 223, 92));
        btnAltaMateria.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        btnAltaMateria.setText("Agregar Nuevo");
        btnAltaMateria.setOpaque(true);
        btnAltaMateria.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAltaMateriaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAltaMateriaMouseExited(evt);
            }
        });
        btnAltaMateria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAltaMateriaActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(null);
        jScrollPane1.setFocusable(false);

        tablaMaterias.setFont(new java.awt.Font("Quicksand SemiBold", 0, 14)); // NOI18N
        tablaMaterias.setForeground(new java.awt.Color(51, 51, 51));
        tablaMaterias.setFocusable(false);
        tablaMaterias.setSelectionBackground(new java.awt.Color(243, 209, 220));
        jScrollPane1.setViewportView(tablaMaterias);

        jLabel1.setFont(new java.awt.Font("Quicksand", 1, 84)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(218, 95, 128));
        jLabel1.setText("INVENTARIO");

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
                            .addComponent(btnAltaMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(203, 203, 203)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAltaMateria, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAltaMateriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAltaMateriaActionPerformed
        JFMateriasPrimas IMat = null;
        try {
            IMat = new JFMateriasPrimas();
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MateriaPrimaP.class.getName()).log(Level.SEVERE, null, ex);
        }
        IMat.setVisible(true);
        lblTituloMP.setText("Registrar Materia Prima");
    }//GEN-LAST:event_btnAltaMateriaActionPerformed

    private void btnAltaMateriaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAltaMateriaMouseEntered
        btnAltaMateria.setBackground(new Color(93, 179, 75));
    }//GEN-LAST:event_btnAltaMateriaMouseEntered

    private void btnAltaMateriaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAltaMateriaMouseExited
        btnAltaMateria.setBackground(Color.decode("#4B933C"));
    }//GEN-LAST:event_btnAltaMateriaMouseExited

    private void EstilosHeader(JTable tabla) {
        //tabla.getTableHeader().setFont();
        tabla.getTableHeader().setBackground(Color.decode("#E18D96"));
        tabla.getTableHeader().setForeground(Color.decode("#FFFFFF"));

    }

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
    private javax.swing.JButton btnAltaMateria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tablaMaterias;
    // End of variables declaration//GEN-END:variables
}
