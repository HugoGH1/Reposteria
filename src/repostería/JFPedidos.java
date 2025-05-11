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
import javax.swing.table.DefaultTableModel;
import static repostería.PedidosP.st;
import static repostería.PedidosP.tablaPedidos;

/**
 *
 * @author Carolina
 */
public class JFPedidos extends javax.swing.JFrame {

    private Connection con = null;
    String[] datos = new String[9];
    public int idpedido;
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
        RellenarCm("SELECT CONCAT(Nombre,' ',Apellido) as NombreC FROM Clientes", "NombreC", cmCliente, Clientes.class);
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
            JOptionPane.showMessageDialog(null, e.getMessage() + "HUBO UN ERROR PARA CARGAR LOS PEDIDOS");
        }
    }

    public int id(String query, JComboBox<String> cmPostre1, int id) {
        try {
            Statement stm = con.createStatement();
            //System.out.println("hola");
            ResultSet rs = stm.executeQuery(query);
            // idPostre = stm.executeQuery(secuenciaSQL);
            if (rs.next()) {
                id = rs.getInt(1);
            }
            //System.out.println("Id postre listo" + rs);

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
            //System.out.println("Id postre listo" + rs);

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
        //System.out.println(nombre);
        String apellido = partes.length > 1 ? partes[1] : "";
        //System.out.println(apellido);
        p1.setIdCliente(id("SELECT idCliente FROM clientes WHERE Nombre = '" + nombre + "' AND Apellido = '" + apellido + "'", cmCliente, idCliente));
        // System.out.println("idcliete en creacion objeto" + p1.getIdCliente());
        String nombre2 = cmPostre.getSelectedItem().toString();
        p1.setIdPostre(id("SELECT idPostre FROM postres WHERE Nombre = '" + nombre2 + "'", cmPostre, idPostre));
        p1.setCantidad(Integer.parseInt(spinnerCantidad.getValue().toString()));
        p1.setCosto(costo());
        //System.out.println("Hola antes de error de crear objeto en fecha");
        java.util.Date fecha = calendar.getDate();
        //System.out.println("hola despues de get date");
        java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
        //System.out.println("hola creando fechasql");
        p1.setFechaEntrega(fechaSQL.toString());
        //System.out.println("hola despues");
        String nombre3 = cmTipoEntrega.getSelectedItem().toString();
        p1.setIdTipoEntrega(id("SELECT idTipoEntrega FROM tipoentrega WHERE Tipo = '" + nombre3 + "'", cmTipoEntrega, idTipoEntrega));
        return p1;
    }

    public void rellenarDatosPedido(String id) {
        ResultSet rs = null, rsC = null, rsT = null, rsP = null, rsS = null;
        Statement stm;
        int idC, idT, idP, spinner;
        idpedido = Integer.parseInt(id);
        String ConsultaDatos = "SELECT * FROM pedidos WHERE idPedido=" + id + "";
        try {
            stm = con.createStatement();
            rs = stm.executeQuery(ConsultaDatos);
            if (rs.next()) {
                spinnerCantidad.setValue(rs.getInt("Cantidad"));
                calendar.setDate(rs.getDate("FechaEntrega"));
                idC = rs.getInt("idCliente");
                idP = rs.getInt("idPostre");
                idT = rs.getInt("idTipoEntrega");

                String nombreCliente = "";
                String nombrePostre = "";
                String nombreTipo = "";

                //System.out.println("El cliente actual es: " + idC);
                String ConsultaCliente = "SELECT concat(Nombre,' ',Apellido) as NombreCompleto FROM Clientes WHERE idCliente =" + idC + "";
                rsC = stm.executeQuery(ConsultaCliente);
                if (rsC.next()) {
                    nombreCliente = rsC.getString("NombreCompleto");
                    for (int i = 0; i < cmCliente.getItemCount(); i++) {
                        String comboItem = cmCliente.getItemAt(i).toString();
                        if (comboItem.equals(nombreCliente)) {
                            cmCliente.setSelectedIndex(i);
                            break;
                        }
                    }
                }

                //System.out.println("El postre actual es: " + idP);
                String ConsultaPostre = "SELECT Nombre FROM postres WHERE idPostre =" + idP + "";
                rsP = stm.executeQuery(ConsultaPostre);
                if (rsP.next()) {
                    nombrePostre = rsP.getString("Nombre");
                    for (int i = 0; i < cmPostre.getItemCount(); i++) {
                        String comboItem = cmPostre.getItemAt(i).toString();
                        if (comboItem.equals(nombrePostre)) {
                            cmPostre.setSelectedIndex(i);
                            break;
                        }
                    }
                }

                //System.out.println("El tipo entrega actual es: " + idT);
                String ConsultaTipoEntrega = "SELECT Tipo FROM tipoentrega WHERE idTipoEntrega=" + idT + "";
                rsT = stm.executeQuery(ConsultaTipoEntrega);
                if (rsT.next()) {
                    nombreTipo = rsT.getString("Tipo");
                    for (int i = 0; i < cmTipoEntrega.getItemCount(); i++) {
                        String comboItem = cmTipoEntrega.getItemAt(i).toString();
                        if (comboItem.equals(nombreTipo)) {
                            cmTipoEntrega.setSelectedIndex(i);
                            break;
                        }
                    }
                }

            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el pedido con ID: " + id);
            }
        } catch (SQLException sql) {
            JOptionPane.showMessageDialog(null, "HUBO UN ERROR PARA CARGAR LOS DATOS DEL PEDIDO");
            System.out.println(sql.getMessage());
        }
    }

    public void actualizarTabla() {
        DefaultTableModel miModelo = (DefaultTableModel) tablaPedidos.getModel();
        miModelo.setRowCount(0); // Limpiar filas existentes

        String sentenciaSQL = "SELECT pe.idPedido,CONCAT(c.Nombre,' ',c.Apellido) AS Cliente,p.Nombre AS Postre,pe.Cantidad,pe.Costo,pe.FechaEntrega,te.Tipo FROM pedidos pe INNER JOIN clientes c ON pe.idCliente = c.idCliente INNER JOIN postres p ON pe.idPostre = p.idPostre INNER JOIN tipoentrega te ON pe.idTipoEntrega = te.idTipoEntrega ORDER BY pe.FechaEntrega ASC";

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

    public void alta(Pedidos obj) {
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT NumeroPedidos FROM clientes WHERE idCliente = '" + obj.getIdCliente() + "'");
            // idPostre = stm.executeQuery(secuenciaSQL);
            if (rs.next()) {
                pedidos = rs.getInt(1);
            }
            //System.out.println("pedidos antes del update" + pedidos);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        try {
            //System.out.println("pedidos antes de aumentar" + pedidos);
            pedidos++;
            //System.out.println("pedidos despues de aumentar" + pedidos);
            PreparedStatement ps = con.prepareStatement("UPDATE clientes SET NumeroPedidos = '" + pedidos + "' "
                    + "WHERE idcliente = '" + obj.getIdCliente() + "'");
            int filasAfectadas = ps.executeUpdate();
            //System.out.println("Número de filas afectadas en update: " + filasAfectadas);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            sqle.printStackTrace();
        }
        //System.out.println(obj.getIdCliente() + "id cliente antes de hacer insert");
        String secuenciaSQL = ("INSERT INTO Pedidos (idCliente, idPostre, Cantidad, Costo,"
                + "FechaEntrega, idTipoEntrega) VALUE ('" + obj.getIdCliente() + "','" + obj.getIdPostre() + "','"
                + obj.getCantidad() + "','" + obj.getCosto() + "','" + obj.getFechaEntrega() + "','"
                + obj.getIdTipoEntrega() + "')");
        try {
            Statement stm = con.createStatement();
            int filasAfectadas = stm.executeUpdate(secuenciaSQL);
            JOptionPane.showMessageDialog(null,"¡Se ha registrado un nuevo pedido con éxito!");
            //************************************************System.out.println("Se ha afectado: " + filasAfectadas);
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage() + "alta");
        }
    }

    public void actualizar(Pedidos obj) {
        try {
            String secuenciaSQL = "UPDATE pedidos SET idCliente = ?, idPostre = ?, Cantidad = ?, "
                    + "Costo = ?, FechaEntrega = ? , idTipoEntrega = ? "
                    + "WHERE idPedido =" + idpedido + "";
            PreparedStatement ps = con.prepareStatement(secuenciaSQL);
            ps.setInt(1, obj.getIdCliente());
            ps.setInt(2, obj.getIdPostre());
            ps.setInt(3, obj.getCantidad());
            ps.setFloat(4, obj.getCosto());
            java.util.Date fecha = calendar.getDate();
            java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());
            ps.setDate(5, fechaSQL);
            ps.setInt(6, obj.getIdTipoEntrega());

            int filasActualizadas = ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "¡Los datos del pedido fueron actualizados!");
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

        jPanel1 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCerrar = new javax.swing.JButton();
        panel1 = new Components.Panel();
        lblTituloP = new javax.swing.JLabel();
        panel2 = new Components.Panel();
        cmCliente = new Components.ComboBox();
        cmPostre = new Components.ComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        spinnerCantidad = new javax.swing.JSpinner();
        panel3 = new Components.Panel();
        calendar = new com.toedter.calendar.JDateChooser();
        cmTipoEntrega = new Components.ComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(244, 227, 221));

        btnGuardar.setBackground(new java.awt.Color(225, 141, 150));
        btnGuardar.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(102, 102, 102));
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCerrar.setBackground(new java.awt.Color(138, 31, 42));
        btnCerrar.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        btnCerrar.setForeground(new java.awt.Color(204, 204, 204));
        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        panel1.setBackgroundColor(new java.awt.Color(218, 95, 128));
        panel1.setBorderColor(new java.awt.Color(218, 95, 128));
        panel1.setFocusable(false);

        lblTituloP.setFont(new java.awt.Font("Quicksand", 1, 18)); // NOI18N
        lblTituloP.setForeground(new java.awt.Color(226, 189, 220));

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblTituloP, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addComponent(lblTituloP, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panel2.setBackgroundColor(new java.awt.Color(218, 95, 128));
        panel2.setBorderColor(new java.awt.Color(218, 95, 128));
        panel2.setFocusable(false);

        cmCliente.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N

        cmPostre.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(226, 189, 220));
        jLabel1.setText("Cliente");

        jLabel2.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(226, 189, 220));
        jLabel2.setText("Postre");

        jLabel3.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(226, 189, 220));
        jLabel3.setText("Cantidad");

        spinnerCantidad.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmPostre, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addComponent(spinnerCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spinnerCantidad, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmPostre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );

        panel3.setBackgroundColor(new java.awt.Color(218, 95, 128));
        panel3.setBorderColor(new java.awt.Color(218, 95, 128));
        panel3.setFocusable(false);

        calendar.setBackground(new java.awt.Color(255, 255, 255));
        calendar.setForeground(new java.awt.Color(102, 102, 102));
        calendar.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N

        cmTipoEntrega.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(226, 189, 220));
        jLabel4.setText("Fecha de Entrega");

        jLabel5.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(226, 189, 220));
        jLabel5.setText("Entrega");

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(calendar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cmTipoEntrega, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmTipoEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(calendar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (validarCampos()) {
            Pedidos pedido = (Pedidos) creacionObjeto();
            if (lblTituloP.getText().equals("Registrar Pedido")) {
                java.util.Date fechaUtil = calendar.getDate();
                java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime());
                if (pedidoYaExiste(pedido.getIdCliente(), pedido.getIdPostre(), pedido.getCantidad(),pedido.getCosto(),fechaSql)) {
                    JOptionPane.showMessageDialog(null, "Ya está registrado este pedido!.");
                    return;
                }
                alta((Pedidos) creacionObjeto());
                SwingUtilities.invokeLater(() -> actualizarTabla());
            } else if (lblTituloP.getText().equals("Actualizar Pedido")) {
                java.util.Date fechaUtil = calendar.getDate();
                java.sql.Date fechaSql = new java.sql.Date(fechaUtil.getTime());
                if (pedidoYaExiste(pedido.getIdCliente(), pedido.getIdPostre(), pedido.getCantidad(),pedido.getCosto(),fechaSql)) {
                    JOptionPane.showMessageDialog(null, "Ya está registrado este pedido!.");
                    return;
                }
                actualizar((Pedidos) creacionObjeto());
                SwingUtilities.invokeLater(() -> actualizarTabla());
            }
            actualizarTabla();
            spinnerCantidad.setValue(1);
            calendar.setDate(null);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.dispose();
        actualizarTabla();
        //PedidosP pe = new PedidosP();
        //SwingUtilities.invokeLater(() -> pe.actualizarTabla());
    }//GEN-LAST:event_btnCerrarActionPerformed

    private boolean validarCampos() {
        if (calendar.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Por favor, selecciona una fecha válida.");
            return false;
        }

        return true;
    }

    public boolean pedidoYaExiste(int cliente, int postre, int cantidad, float costo, java.sql.Date fecha) {
    String sql = "SELECT COUNT(*) FROM pedidos WHERE idCliente = ? AND idPostre = ? AND Cantidad = ? AND Costo = ? AND FechaEntrega = ?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, cliente);
        ps.setInt(2, postre);
        ps.setInt(3, cantidad);
        ps.setFloat(4, costo);
        ps.setDate(5, fecha); // java.sql.Date
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    } catch (SQLException e) {
        System.out.println("Error al verificar existencia: " + e.getMessage());
    }
    return false;
}

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
    private javax.swing.JButton btnCerrar;
    private javax.swing.JButton btnGuardar;
    private com.toedter.calendar.JDateChooser calendar;
    private Components.ComboBox cmCliente;
    private Components.ComboBox cmPostre;
    private Components.ComboBox cmTipoEntrega;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JLabel lblTituloP;
    private Components.Panel panel1;
    private Components.Panel panel2;
    private Components.Panel panel3;
    private javax.swing.JSpinner spinnerCantidad;
    // End of variables declaration//GEN-END:variables
}
