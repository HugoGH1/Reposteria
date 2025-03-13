package repostería;

import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.EventObject;
import javax.swing.*;

public class Postre extends javax.swing.JFrame {

    PreparedStatement ps;
    Statement st;
    private Connection con = null;
    String[] datos = new String[6];

    public Postre() {
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
        TablaPostres.setModel(miModelo);

        TablaPostres.setRowHeight(30);
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
            TablaPostres.setModel(miModelo);

        } catch (SQLException ex) {
            Logger.getLogger(Postre.class.getName()).log(Level.SEVERE, null, ex);
        }

        TablaPostres.setModel(miModelo);

        // Agrega los botones con imágenes
        TablaPostres.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer("/Images/edit.png"));
        TablaPostres.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), "/Images/edit.png", 4));

        TablaPostres.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer("/Images/trash.png"));
        TablaPostres.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), "/Images/trash.png", 5));
    }

    // Clase para renderizar botones en la tabla
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
                    } catch (SQLException sqle) {
                        System.out.println(sqle.getMessage());
                        sqle.printStackTrace();
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaPostres = new javax.swing.JTable();
        btnInsertar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        TablaPostres.setBackground(new java.awt.Color(204, 204, 204));
        TablaPostres.setFont(new java.awt.Font("Montserrat Thin", 0, 10)); // NOI18N
        TablaPostres.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(TablaPostres);

        btnInsertar.setText("+");
        btnInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 776, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(btnInsertar)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(111, Short.MAX_VALUE)
                .addComponent(btnInsertar)
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
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

    private void btnInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarActionPerformed
       
    }//GEN-LAST:event_btnInsertarActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaPostres;
    private javax.swing.JButton btnInsertar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
