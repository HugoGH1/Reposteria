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
import static repostería.JFClientes.lblTitulo;

/**
 *
 * @author hecto
 */
public class ProveedoresP extends javax.swing.JPanel {
    
    PreparedStatement ps;
    public static Statement st;
    String id;
    private Connection con = null;
    String[] datos = new String[6];
    JTableHeader header = new JTableHeader();
    
    public ProveedoresP() {
        initComponents();
        conectar();
        TablaProveedores();
        header = tablaProveedores.getTableHeader();
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
            return column == 4 || column == 5;
        }
    };
    
    public void TablaProveedores() {
        miModelo.addColumn("idProveedor");
        miModelo.addColumn("Nombre");
        miModelo.addColumn("NumeroTelefonico");
        miModelo.addColumn("Domicilio");
        miModelo.addColumn("Editar");
        miModelo.addColumn("Eliminar"); 

        tablaProveedores.setModel(miModelo);

        String sentenciaSQL = "SELECT idProveedor, Nombre, NumeroTelefonico,CONCAT(Calle,' ',NumeroExterior,', ',Colonia,' ',CodigoPostal) AS Domicilio FROM proveedores";
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
                miModelo.addRow(datos);
            }
            tablaProveedores.setModel(miModelo);
            tablaProveedores.getColumnModel().getColumn(0).setMaxWidth(0);
            tablaProveedores.getColumnModel().getColumn(0).setMinWidth(0);
            tablaProveedores.getColumnModel().getColumn(0).setPreferredWidth(0);

        } catch (SQLException ex) {
            Logger.getLogger(PostreP.class.getName()).log(Level.SEVERE, null, ex);
        }

        tablaProveedores.getColumnModel().getColumn(4).setCellRenderer(new ProveedoresP.ButtonRenderer("/Images/lapiz.png"));
        tablaProveedores.getColumnModel().getColumn(4).setCellEditor(new ProveedoresP.ButtonEditor(new JCheckBox(), "/Images/lapiz.png", 4));

        tablaProveedores.getColumnModel().getColumn(5).setCellRenderer(new ProveedoresP.ButtonRenderer("/Images/borrar.png"));
        tablaProveedores.getColumnModel().getColumn(5).setCellEditor(new ProveedoresP.ButtonEditor(new JCheckBox(), "/Images/borrar.png", 5));
    }
    
    public void actualizarTabla() {
        DefaultTableModel miModelo = (DefaultTableModel) tablaProveedores.getModel();
        miModelo.setRowCount(0);

        String sentenciaSQL = "SELECT idProveedor, Nombre, NumeroTelefonico,CONCAT(Calle,' ',NumeroExterior,', ',Colonia,' ',CodigoPostal) AS Domicilio FROM proveedores";

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
                miModelo.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteP.class.getName()).log(Level.SEVERE, null, ex);
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

                if (columnIndex == 4) {
                    //JOptionPane.showMessageDialog(button, "Editando " + id);
                    JFProveedores IPro = new JFProveedores();
                    IPro.setVisible(true);
                    IPro.lblTitulo.setText("Actualizar proveedor");
                    IPro.rellenarDatosProveedor(id);

                } else if (columnIndex == 5) {
                    int resp = JOptionPane.showConfirmDialog(null, "" + "¿Quieres continuar?", "¡Estás a punto de eliminar este proveedor!", JOptionPane.YES_NO_OPTION);
                    if (resp == 0) { // respuesta NO
                        try {
                            PreparedStatement ps = con.prepareStatement("DELETE FROM proveedores WHERE idProveedor = ?");
                            ps.setString(1, id);
                        int filasAfectadas = ps.executeUpdate();
                        //System.out.println("Número de filas afectadas: " + filasAfectadas);
                        SwingUtilities.invokeLater(() -> actualizarTabla());
                    }catch (SQLException sqle) {
                            System.out.println(sqle.getMessage());
                            sqle.printStackTrace();
                        }
                }
            }
        }
        clicked  = false;
    

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

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProveedores = new javax.swing.JTable();
        btnAltaProveedor = new Components.Button();

        setBackground(new java.awt.Color(255, 194, 209));
        setPreferredSize(new java.awt.Dimension(950, 720));

        jLabel1.setFont(new java.awt.Font("Quicksand", 1, 84)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(218, 95, 128));
        jLabel1.setText("PROVEEDORES");

        tablaProveedores.setBackground(new java.awt.Color(255, 255, 255));
        tablaProveedores.setFont(new java.awt.Font("Quicksand SemiBold", 0, 14)); // NOI18N
        tablaProveedores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaProveedores.setGridColor(new java.awt.Color(252, 228, 236));
        tablaProveedores.setRowHeight(30);
        tablaProveedores.setSelectionBackground(new java.awt.Color(243, 209, 220));
        tablaProveedores.setSelectionForeground(new java.awt.Color(153, 153, 153));
        jScrollPane1.setViewportView(tablaProveedores);

        btnAltaProveedor.setText("Agregar Nuevo");
        btnAltaProveedor.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        btnAltaProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAltaProveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAltaProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 865, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(164, 164, 164)
                        .addComponent(jLabel1)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAltaProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAltaProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAltaProveedorActionPerformed
        JFProveedores IPro = new JFProveedores();
        IPro.setVisible(true);
        IPro.lblTitulo.setText("Registrar proveedor");
    }//GEN-LAST:event_btnAltaProveedorActionPerformed

    private void EstilosHeader(JTable tabla){
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
    private Components.Button btnAltaProveedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tablaProveedores;
    // End of variables declaration//GEN-END:variables
}
