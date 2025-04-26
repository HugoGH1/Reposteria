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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.sql.*;
import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import static repostería.JFPedidos.lblTituloP;

/**
 *
 * @author Carolina
 */
public class PedidosP extends javax.swing.JPanel {

    PreparedStatement ps;
    public static Statement st;
    String id;
    private Connection con = null;
    String[] datos = new String[9];
    JTableHeader header = new JTableHeader();

    public PedidosP() {
        initComponents();
        conectar();
        TablaPedidos();
        header = tablaPedidos.getTableHeader();
        header.setDefaultRenderer(new PedidosP.HeaderRenderer());
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
            return column == 7 || column == 8;
        }
    };

    public void TablaPedidos() {
        DefaultTableModel miModelo = new DefaultTableModel();
        miModelo.addColumn("ID");
        miModelo.addColumn("Cliente");
        miModelo.addColumn("Postre");
        miModelo.addColumn("Cantidad");
        miModelo.addColumn("Costo");
        miModelo.addColumn("Fecha Entrega");
        miModelo.addColumn("Tipo Entrega");
        miModelo.addColumn("Editar");
        miModelo.addColumn("Eliminar");

        tablaPedidos.setModel(miModelo);
        tablaPedidos.setRowHeight(30);

        String sentenciaSQL = "SELECT * FROM pedidos";
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
                datos[7] = "";
                datos[8] = "";
                miModelo.addRow(datos);
            }
            tablaPedidos.setModel(miModelo);
            tablaPedidos.getColumnModel().getColumn(0).setMaxWidth(0);
            tablaPedidos.getColumnModel().getColumn(0).setMinWidth(0);
            tablaPedidos.getColumnModel().getColumn(0).setPreferredWidth(0);

        } catch (SQLException ex) {
            Logger.getLogger(PostreP.class.getName()).log(Level.SEVERE, null, ex);
        }

        tablaPedidos.getColumnModel().getColumn(7).setCellRenderer(new PedidosP.ButtonRenderer("/Images/lapiz.png"));
        tablaPedidos.getColumnModel().getColumn(7).setCellEditor(new PedidosP.ButtonEditor(new JCheckBox(), "/Images/lapiz.png", 7));

        tablaPedidos.getColumnModel().getColumn(8).setCellRenderer(new PedidosP.ButtonRenderer("/Images/borrar.png"));
        tablaPedidos.getColumnModel().getColumn(8).setCellEditor(new PedidosP.ButtonEditor(new JCheckBox(), "/Images/borrar.png", 8));
    }

    public void actualizarTabla() {
        DefaultTableModel miModelo = (DefaultTableModel) tablaPedidos.getModel();
        miModelo.setRowCount(0);

        String sentenciaSQL = "SELECT * FROM pedidos";

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
                datos[7] = "";
                datos[8] = "";
                miModelo.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PedidosP.class.getName()).log(Level.SEVERE, null, ex);
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

                if (columnIndex == 7) {
                    JOptionPane.showMessageDialog(button, "Editando " + id);
                    JFPedidos JFPed = null;
                    try {
                        JFPed = new JFPedidos();
                    } catch (NoSuchMethodException ex) {
                        Logger.getLogger(PedidosP.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JFPed.setVisible(true);
                    lblTituloP.setText("Actualizar Pedido");
                    JFPed.rellenarDatosPedido(id);

                } else if (columnIndex == 8) {
                    int resp = JOptionPane.showConfirmDialog(null, "" + "¿Quieres continuar?", "¡Estás a punto de eliminar este pedido!", JOptionPane.YES_NO_OPTION);
                    if (resp == 0) { // respuesta NO
                        try {
                            PreparedStatement ps = con.prepareStatement("DELETE FROM pedidos WHERE idPedido = ?");
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

        btnAltaPedido = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaPedidos = new javax.swing.JTable();
        btnGenerarPDF = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(950, 720));
        setMinimumSize(new java.awt.Dimension(950, 720));

        btnAltaPedido.setBackground(new java.awt.Color(115, 223, 92));
        btnAltaPedido.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        btnAltaPedido.setText("Agregar Nuevo");
        btnAltaPedido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAltaPedidoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAltaPedidoMouseExited(evt);
            }
        });
        btnAltaPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAltaPedidoActionPerformed(evt);
            }
        });

        tablaPedidos.setFont(new java.awt.Font("Quicksand SemiBold", 0, 14)); // NOI18N
        tablaPedidos.setForeground(new java.awt.Color(51, 51, 51));
        jScrollPane1.setViewportView(tablaPedidos);

        btnGenerarPDF.setBackground(new java.awt.Color(218, 95, 128));
        btnGenerarPDF.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnGenerarPDF.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerarPDF.setText("Generar PDF");
        btnGenerarPDF.setBorderPainted(false);
        btnGenerarPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarPDFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAltaPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGenerarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 889, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAltaPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGenerarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        btnGenerarPDF.getAccessibleContext().setAccessibleName("btnReporte");
    }// </editor-fold>//GEN-END:initComponents

    private void btnAltaPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAltaPedidoActionPerformed
        JFPedidos JFPed = null;
        try {
            JFPed = new JFPedidos();
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(PedidosP.class.getName()).log(Level.SEVERE, null, ex);
        }
        JFPed.setVisible(true);
        lblTituloP.setText("Registrar Pedido");
    }//GEN-LAST:event_btnAltaPedidoActionPerformed

    private void btnAltaPedidoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAltaPedidoMouseEntered
        btnAltaPedido.setBackground(new Color(93, 179, 75));
    }//GEN-LAST:event_btnAltaPedidoMouseEntered

    private void btnAltaPedidoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAltaPedidoMouseExited
        btnAltaPedido.setBackground(Color.decode("#4B933C"));
    }//GEN-LAST:event_btnAltaPedidoMouseExited

    private void btnGenerarPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarPDFActionPerformed

        try {
            JasperReport reporte = null;
            String path = "src\\resources\\reportes\\ReportePedidos.jasper";
            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);
            
            JasperPrint jprint = JasperFillManager.fillReport(reporte,null,con);
            
            JasperViewer view = new JasperViewer(jprint, false);
            
            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            
            view.setVisible(true);
            
        } catch (JRException ex) {
            Logger.getLogger(PedidosP.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("HUBO ERROR EN EL PDF");
        }
    }//GEN-LAST:event_btnGenerarPDFActionPerformed

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
    private javax.swing.JButton btnAltaPedido;
    private javax.swing.JButton btnGenerarPDF;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTable tablaPedidos;
    // End of variables declaration//GEN-END:variables
}
