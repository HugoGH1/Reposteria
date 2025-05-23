package repostería;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Locale;
import java.time.format.TextStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class InicioP extends javax.swing.JPanel {

    PreparedStatement ps;
    public static Statement st;
    String id;
    private Connection con = null;
    String[] datos = new String[6];
    JTableHeader header = new JTableHeader();
    LocalDate fecha = LocalDate.now();
    String DosDias, mes;

    public InicioP() {
        initComponents();

        conectar();
        TablaProxPedidos();
        header = tablaProxPedidos.getTableHeader();
        header.setDefaultRenderer(new PedidosP.HeaderRenderer());
        //System.out.println(fecha);
        lblMonth.setText(fecha.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES")));
        lblDayWeek.setText(fecha.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES")));
        FormatoFechaDia(fecha);
        lblYear.setText(String.valueOf(fecha.getYear()));
    }

    DefaultTableModel miModelo = new DefaultTableModel() {

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public void conectar() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost/reposteria?user=root&password=");
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "conectar");
        }
    }

    public void FormatoFechaDia(LocalDate fecha) {
        if (fecha.getDayOfMonth() < 10) {
            lblDay.setText("0" + String.valueOf(fecha.getDayOfMonth()));
        } else {
            lblDay.setText(String.valueOf(fecha.getDayOfMonth()));
        }
    }

    public void TablaProxPedidos() {

        miModelo.setRowCount(0); // Limpia las filas
        if (miModelo.getColumnCount() == 0) {
            miModelo.addColumn("Cliente");
            miModelo.addColumn("Postre");
            miModelo.addColumn("Cantidad");
            miModelo.addColumn("Costo");
            miModelo.addColumn("Fecha Entrega");
            miModelo.addColumn("Entrega");
        }

        tablaProxPedidos.setModel(miModelo);
        tablaProxPedidos.setRowHeight(30);

        if (fecha.getDayOfMonth() < 10) {
            DosDias = "0" + String.valueOf(fecha.getDayOfMonth() + 2);
        } else {
            DosDias = String.valueOf(fecha.getDayOfMonth() + 2);
        }

        if (fecha.getMonthValue() < 10) {
            mes = "0" + String.valueOf(fecha.getMonthValue());
        } else {
            mes = String.valueOf(fecha.getMonthValue());
        }

        String Fecha2Dias = fecha.getYear() + "-" + mes + "-" + DosDias;

        String sentenciaSQL = "CALL pPedidosProximos(DATE_ADD(CURRENT_DATE, INTERVAL 2 DAY));";
       // String sentenciaSQL = "CALL pPedidosProximos('" + Fecha2Dias + "')";

        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sentenciaSQL);
            while (rs.next()) {
                datos[0] = rs.getString(3);
                datos[1] = rs.getString(4);
                datos[2] = rs.getString(5);
                datos[3] = rs.getString(6);
                datos[4] = rs.getString(7);
                datos[5] = rs.getString(8);
                miModelo.addRow(datos);
            }
            tablaProxPedidos.getColumnModel().getColumn(3).setPreferredWidth(70);
            tablaProxPedidos.getColumnModel().getColumn(3).setMinWidth(70);
            tablaProxPedidos.getColumnModel().getColumn(3).setMaxWidth(70);
            tablaProxPedidos.getColumnModel().getColumn(2).setMinWidth(80);
            tablaProxPedidos.getColumnModel().getColumn(5).setMinWidth(70);
            tablaProxPedidos.getColumnModel().getColumn(5).setMaxWidth(70);
            tablaProxPedidos.getColumnModel().getColumn(0).setMinWidth(100);
            tablaProxPedidos.getColumnModel().getColumn(1).setMinWidth(90);

        } catch (SQLException ex) {
            Logger.getLogger(PostreP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        CalendarPanel = new Components.Panel();
        RedPanel = new Components.Panel();
        lblMonth = new javax.swing.JLabel();
        lblDay = new javax.swing.JLabel();
        lblYear = new javax.swing.JLabel();
        lblDayWeek = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaProxPedidos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 194, 209));
        setMaximumSize(new java.awt.Dimension(950, 720));
        setMinimumSize(new java.awt.Dimension(950, 720));
        setPreferredSize(new java.awt.Dimension(950, 720));

        CalendarPanel.setBackground(new java.awt.Color(253, 253, 253));

        RedPanel.setBackground(new java.awt.Color(204, 0, 0));
        RedPanel.setOpaque(true);

        lblMonth.setBackground(new java.awt.Color(204, 0, 0));
        lblMonth.setFont(new java.awt.Font("Quicksand", 1, 36)); // NOI18N
        lblMonth.setForeground(new java.awt.Color(255, 255, 255));
        lblMonth.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMonth.setText("Jlabel");
        lblMonth.setOpaque(true);

        javax.swing.GroupLayout RedPanelLayout = new javax.swing.GroupLayout(RedPanel);
        RedPanel.setLayout(RedPanelLayout);
        RedPanelLayout.setHorizontalGroup(
            RedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RedPanelLayout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(lblMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );
        RedPanelLayout.setVerticalGroup(
            RedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RedPanelLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );

        lblDay.setBackground(new java.awt.Color(255, 255, 255));
        lblDay.setFont(new java.awt.Font("Quicksand", 1, 112)); // NOI18N
        lblDay.setForeground(new java.awt.Color(0, 0, 0));
        lblDay.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDay.setOpaque(true);

        lblYear.setBackground(new java.awt.Color(255, 255, 255));
        lblYear.setFont(new java.awt.Font("Quicksand", 1, 24)); // NOI18N
        lblYear.setForeground(new java.awt.Color(0, 0, 0));
        lblYear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblYear.setText("Jlabel");
        lblYear.setOpaque(true);

        lblDayWeek.setBackground(new java.awt.Color(255, 255, 255));
        lblDayWeek.setFont(new java.awt.Font("Quicksand", 1, 24)); // NOI18N
        lblDayWeek.setForeground(new java.awt.Color(0, 0, 0));
        lblDayWeek.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDayWeek.setText("Dia");
        lblDayWeek.setOpaque(true);

        javax.swing.GroupLayout CalendarPanelLayout = new javax.swing.GroupLayout(CalendarPanel);
        CalendarPanel.setLayout(CalendarPanelLayout);
        CalendarPanelLayout.setHorizontalGroup(
            CalendarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(RedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(CalendarPanelLayout.createSequentialGroup()
                .addGroup(CalendarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(CalendarPanelLayout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(lblYear, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(CalendarPanelLayout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(lblDayWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, CalendarPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(lblDay, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );
        CalendarPanelLayout.setVerticalGroup(
            CalendarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CalendarPanelLayout.createSequentialGroup()
                .addComponent(RedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblDayWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(lblDay, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(lblYear, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        jScrollPane1.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPane1.setBorder(null);
        jScrollPane1.setFocusable(false);
        jScrollPane1.setOpaque(true);

        tablaProxPedidos.setFont(new java.awt.Font("Quicksand SemiBold", 0, 14)); // NOI18N
        tablaProxPedidos.setForeground(new java.awt.Color(51, 51, 51));
        tablaProxPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tablaProxPedidos.setFocusable(false);
        tablaProxPedidos.setRowHeight(32);
        tablaProxPedidos.setSelectionBackground(new java.awt.Color(243, 209, 220));
        tablaProxPedidos.setSelectionForeground(new java.awt.Color(102, 102, 102));
        jScrollPane1.setViewportView(tablaProxPedidos);

        jLabel1.setFont(new java.awt.Font("Quicksand", 1, 96)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(218, 95, 128));
        jLabel1.setText("INICIO");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel2.setFont(new java.awt.Font("Quicksand", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(218, 95, 128));
        jLabel2.setText("HOY ES:");

        jLabel3.setFont(new java.awt.Font("Quicksand", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(218, 95, 128));
        jLabel3.setText("PEDIDOS PARA LOS PRÓXIMOS 2 DÍAS:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(328, 328, 328)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CalendarPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CalendarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(84, 84, 84))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Components.Panel CalendarPanel;
    private Components.Panel RedPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDay;
    private javax.swing.JLabel lblDayWeek;
    private javax.swing.JLabel lblMonth;
    private javax.swing.JLabel lblYear;
    private javax.swing.JTable tablaProxPedidos;
    // End of variables declaration//GEN-END:variables
}
