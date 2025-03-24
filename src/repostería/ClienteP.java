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
import static repostería.PostreP.st;
import static repostería.PostreP.tablaPostre;

/**
 *
 * @author Carolina
 */
public class ClienteP extends javax.swing.JPanel {

    PreparedStatement ps;
    public static Statement st;
    String id;
    private Connection con = null;
    String[] datos = new String[12];
    JTableHeader header = new JTableHeader();
    public ClienteP() {
        initComponents();
        conectar();
        TablaClientes();
        header = tablaClientes.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer());
    }

    public void conectar() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/reposteria?user=root&password=");
            System.out.println("Conexion Correcta");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "conectar");
        }
    }

    DefaultTableModel miModelo = new DefaultTableModel(){
        
        @Override
        public boolean isCellEditable(int row, int column){
            return column == 10 || column == 11;
        }
    };
    
    public void TablaClientes() {
        miModelo.addColumn("ID");
        miModelo.addColumn("Nombre");
        miModelo.addColumn("Apellido");
        miModelo.addColumn("Teléfono");
        miModelo.addColumn("Calle");
        miModelo.addColumn("Num. Exterior");
        miModelo.addColumn("Colonia");
        miModelo.addColumn("CP");
        miModelo.addColumn("Num. Pedidos");
        miModelo.addColumn("Postre fav");
        miModelo.addColumn("Editar");
        miModelo.addColumn("Eliminar");
        
        tablaClientes.setModel(miModelo);
        
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
            tablaClientes.setModel(miModelo);
            tablaClientes.getColumnModel().getColumn(0).setMaxWidth(0);
            tablaClientes.getColumnModel().getColumn(0).setMinWidth(0);
            tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(0);
            
        } catch (SQLException ex) {
            Logger.getLogger(PostreP.class.getName()).log(Level.SEVERE, null, ex);
        }

        tablaClientes.getColumnModel().getColumn(10).setCellRenderer(new ClienteP.ButtonRenderer("/Images/lapiz.png"));
        tablaClientes.getColumnModel().getColumn(10).setCellEditor(new ClienteP.ButtonEditor(new JCheckBox(), "/Images/lapiz.png", 10));

        tablaClientes.getColumnModel().getColumn(11).setCellRenderer(new ClienteP.ButtonRenderer("/Images/borrar.png"));
        tablaClientes.getColumnModel().getColumn(11).setCellEditor(new ClienteP.ButtonEditor(new JCheckBox(), "/Images/borrar.png", 11));
    }

    public void actualizarTabla() {
        DefaultTableModel miModelo = (DefaultTableModel) tablaClientes.getModel();
        miModelo.setRowCount(0);

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
            
            setBackground(new Color(0,0,0,0));
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

                if (columnIndex == 10) {
                    JOptionPane.showMessageDialog(button, "Editando " + id);
                    JFClientes ICli = new JFClientes();
                    ICli.setVisible(true);
                    lblTitulo.setText("Actualizar cliente");
                    ICli.rellenarDatosCliente(id);

                } else if (columnIndex == 11) {
                    int resp = JOptionPane.showConfirmDialog(null, "" + "¿Quieres continuar?", "¡Estás a punto de eliminar este cliente!", JOptionPane.YES_NO_OPTION);
                    if (resp == 0) { // respuesta NO
                        try {
                            PreparedStatement ps = con.prepareStatement("DELETE FROM clientes WHERE idCliente = ?");
                            ps.setString(1, id);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaClientes = new javax.swing.JTable();
        btnAltaCliente = new Components.Button();

        setMaximumSize(new java.awt.Dimension(950, 720));
        setMinimumSize(new java.awt.Dimension(950, 720));

        tablaClientes.setFont(new java.awt.Font("Quicksand SemiBold", 0, 14)); // NOI18N
        tablaClientes.setForeground(new java.awt.Color(51, 51, 51));
        tablaClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablaClientes.setFocusable(false);
        tablaClientes.setGridColor(new java.awt.Color(204, 204, 204));
        tablaClientes.setRowHeight(32);
        tablaClientes.setSelectionBackground(new java.awt.Color(243, 209, 220));
        jScrollPane1.setViewportView(tablaClientes);

        btnAltaCliente.setText("Agregar Nuevo");
        btnAltaCliente.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        btnAltaCliente.setRadius(20);
        btnAltaCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAltaClienteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAltaClienteMouseExited(evt);
            }
        });
        btnAltaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAltaClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 865, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAltaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(btnAltaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAltaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAltaClienteActionPerformed
        JFClientes ICli = new JFClientes();
        ICli.setVisible(true);
        lblTitulo.setText("Registrar cliente");
    }//GEN-LAST:event_btnAltaClienteActionPerformed

    private void btnAltaClienteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAltaClienteMouseEntered
        btnAltaCliente.setBackground(new Color(93, 179, 75));
    }//GEN-LAST:event_btnAltaClienteMouseEntered

    private void btnAltaClienteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAltaClienteMouseExited
        btnAltaCliente.setBackground(Color.decode("#4B933C"));
    }//GEN-LAST:event_btnAltaClienteMouseExited

    
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
    private Components.Button btnAltaCliente;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tablaClientes;
    // End of variables declaration//GEN-END:variables
}
