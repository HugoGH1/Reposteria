/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package repostería;

import java.awt.Component;
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
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Carolina
 */
public class PostreP extends javax.swing.JPanel {
    //public static JTable tablaPostre;
    PreparedStatement ps;
    public static Statement st;
    private Connection con = null;
    String[] datos = new String[6];
    
    public PostreP() {
        initComponents();
        conectar();
        TablaPostres();
    }
    public void conectar() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/reposteria?user=root&password=");
            System.out.println("Conexion Correcta");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "conectar");
        }
    }
    
    public void TablaPostres() {
        DefaultTableModel miModelo = new DefaultTableModel();
        //titulos
        miModelo.addColumn("ID");
        miModelo.addColumn("Nombre");
        miModelo.addColumn("PrecioVenta");
        miModelo.addColumn("Cantidad");
        miModelo.addColumn("Editar");
        miModelo.addColumn("Eliminar");
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
                miModelo.addRow(datos);
            }
            tablaPostre.setModel(miModelo);

        } catch (SQLException ex) {
            Logger.getLogger(PostreP.class.getName()).log(Level.SEVERE, null, ex);
        }

        tablaPostre.setModel(miModelo);

        // Agrega los botones con imágenes
        tablaPostre.getColumnModel().getColumn(4).setCellRenderer(new PostreP.ButtonRenderer("/Images/edit.png"));
        tablaPostre.getColumnModel().getColumn(4).setCellEditor(new PostreP.ButtonEditor(new JCheckBox(), "/Images/edit.png", 4));

        tablaPostre.getColumnModel().getColumn(5).setCellRenderer(new PostreP.ButtonRenderer("/Images/trash.png"));
        tablaPostre.getColumnModel().getColumn(5).setCellEditor(new PostreP.ButtonEditor(new JCheckBox(), "/Images/trash.png", 5));
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
                    JOptionPane.showMessageDialog(button, "Editando " + id);
                    // Aquí puedes abrir un formulario para editar el registro
                } else if (columnIndex == 5) {
                    JOptionPane.showMessageDialog(button, "Eliminando " + id);
                    try {
                        PreparedStatement ps = con.prepareStatement("DELETE FROM Postres WHERE idPostre = ?");

                        ps.setString(1, id);
                        int filasAfectadas = ps.executeUpdate();
                        System.out.println("Número de filas afectadas: " + filasAfectadas);
                        System.out.println("Postre eliminado");
                        SwingUtilities.invokeLater(() -> actualizarTabla());
                    } catch (SQLException sqle) {
                        //System.out.println(sqle.getMessage());
                        JOptionPane.showMessageDialog(null, "No puedes eliminar un postre");
                        //sqle.printStackTrace();
                    }
                    // miModelo.removeRow(row); // Elimina la fila visualmente (falta eliminar de BD)
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
        tablaPostre = new javax.swing.JTable();
        btnInsertar = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(950, 720));
        setMinimumSize(new java.awt.Dimension(950, 720));
        setPreferredSize(new java.awt.Dimension(950, 720));

        jScrollPane1.setViewportView(tablaPostre);

        btnInsertar.setText("+");
        btnInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInsertar, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(btnInsertar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(137, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
        JFPostres IPos = new JFPostres();
        IPos.setVisible(true);
    }//GEN-LAST:event_btnInsertarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnInsertar;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tablaPostre;
    // End of variables declaration//GEN-END:variables
}
