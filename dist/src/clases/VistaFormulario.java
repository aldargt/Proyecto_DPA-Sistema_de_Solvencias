/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import com.itextpdf.text.pdf.languages.IndicCompositeCharacterComparator;
import com.mysql.jdbc.Connection;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Hp
 */
public class VistaFormulario extends javax.swing.JFrame {

    JButton btnVer = new JButton("VER");
    String rutaClick = "";
    DefaultTableModel modelo = new DefaultTableModel() {

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public VistaFormulario() {
        initComponents();
        btnVer.setName("ver");

        obtenerAnio();
        obternerFechaActualCF();

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Sistema de Registros de Certificados de Solvencia");

        labelTitulo.setFont(new Font("Verdana", 3, 30));
        labelTitulo.setForeground(new Color(243, 255, 255));
        String anioTitulo = cbxAnio.getSelectedItem().toString();
        labelTitulo1.setText(anioTitulo);
        labelTitulo1.setFont(new Font("Verdana", 3, 30));
        labelTitulo1.setForeground(new Color(243, 255, 255));

        jtDocumentaciones.setDefaultRenderer(Object.class, new Render());
        jtDocumentaciones.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVer.setCursor(new Cursor(Cursor.HAND_CURSOR));

        jtDocumentaciones.getTableHeader().setFont(new Font("Verdana", Font.ITALIC, 11));
        jtDocumentaciones.getTableHeader().setOpaque(false);
        jtDocumentaciones.getTableHeader().setBackground(new Color(0, 102, 102));
        jtDocumentaciones.getTableHeader().setForeground(new Color(255, 255, 255));
        jtDocumentaciones.setRowHeight(25);

        try {
            jtDocumentaciones.setModel(modelo);
            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion();

            String anioConsulta = cbxAnio.getSelectedItem().toString();
            //System.out.println(anioConsulta);

            String sql = "SELECT * FROM solvencias WHERE YEAR(fecha) = " + anioConsulta + " ORDER BY fecha";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("ID");
            modelo.addColumn("     FECHA");
            modelo.addColumn("                         NOMBRE");
            modelo.addColumn("                              UNIDAD");
            modelo.addColumn(" Nº CUENTA BANCARIA");
            modelo.addColumn(" CÉDULA DE IDENTIDAD");
            modelo.addColumn("      CELULAR");
            modelo.addColumn("         FIJO");
            modelo.addColumn("   ENCARGADO");
            modelo.addColumn("     ARCHIVO");

            int[] anchos = {0, 25, 200, 220, 75, 80, 40, 40, 40, 30};
            for (int i = 0; i < jtDocumentaciones.getColumnCount(); i++) {
                if (i == 0) {
                    jtDocumentaciones.getColumnModel().getColumn(0).setMaxWidth(anchos[i]);
                    jtDocumentaciones.getColumnModel().getColumn(0).setMinWidth(anchos[i]);
                    jtDocumentaciones.getColumnModel().getColumn(0).setPreferredWidth(anchos[i]);
                } else {
                    jtDocumentaciones.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
                }
            }

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    switch (i) {
                        case 0:
                            filas[i] = rs.getObject(i + 1);
                            break;
                        case 1:
                            String fechaRecibida = darFormato(rs.getDate(i + 1));
                            filas[i] = "  " + fechaRecibida;
                            break;
                        case 2:
                            filas[i] = "  " + rs.getObject(i + 1);
                            break;
                        case 3:
                            filas[i] = "  " + rs.getObject(i + 1);
                            break;
                        case 4:
                            filas[i] = "  " + rs.getObject(i + 1);
                            break;
                        case 5:
                            filas[i] = "  " + rs.getObject(i + 1) + "  " + rs.getString(i + 2);
                            break;
                        case 6:
                            filas[i] = "  " + rs.getObject(i + 2);
                            break;
                        case 7:
                            filas[i] = "  " + rs.getObject(i + 2);
                            break;
                        case 8:
                            filas[i] = "  " + rs.getObject(i + 2);
                            break;
                        case 9:
                            //filas[i] = "  " + rs.getObject(i + 2);
                            filas[i] = btnVer;
                            break;
                    }

                }
                modelo.addRow(filas);
            }

        } catch (SQLException ex) {
            //System.err.println(ex.toString());
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        labelTitulo = new javax.swing.JLabel();
        labelTitulo1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbxAnio = new javax.swing.JComboBox();
        cajaTextoBuscar = new javax.swing.JTextField();
        botonBuscar = new javax.swing.JButton();
        labelFechaEst = new javax.swing.JLabel();
        cajaTextoDia = new javax.swing.JTextField();
        cajaTextoMes = new javax.swing.JTextField();
        cajaTextoAnio = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtDocumentaciones = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        labelEncargado = new javax.swing.JLabel();
        cajaTextoCedula = new javax.swing.JTextField();
        cbxUnidad = new javax.swing.JComboBox();
        cajaTextoCelular = new javax.swing.JTextField();
        cajaTextoCuenta = new javax.swing.JTextField();
        cbxCedula = new javax.swing.JComboBox();
        cajaTextoNombre = new javax.swing.JTextField();
        cajaTextoFijo = new javax.swing.JTextField();
        botonGuardar = new javax.swing.JButton();
        botonModificar = new javax.swing.JButton();
        botonEliminar = new javax.swing.JButton();
        botonLimpiar = new javax.swing.JButton();
        botonGenerar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 54, 55));

        labelTitulo.setText("  REGISTRO DE CERTIFICADOS DE SOLVENCIA GESTIÓN -");

        labelTitulo1.setText("ANIO");

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("AÑO:");

        cbxAnio.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cbxAnio.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        cbxAnio.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxAnioActionPerformed(evt);
            }
        });

        cajaTextoBuscar.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cajaTextoBuscar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoBuscarActionPerformed(evt);
            }
        });

        botonBuscar.setBackground(new java.awt.Color(255, 255, 255));
        botonBuscar.setFont(new java.awt.Font("Verdana", 3, 11)); // NOI18N
        botonBuscar.setForeground(new java.awt.Color(0, 51, 102));
        botonBuscar.setText("BUSCAR NOMBRE");
        botonBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonBuscar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarActionPerformed(evt);
            }
        });

        labelFechaEst.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        labelFechaEst.setForeground(new java.awt.Color(255, 255, 255));
        labelFechaEst.setText("FECHA:");

        cajaTextoDia.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cajaTextoDia.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoDiaActionPerformed(evt);
            }
        });

        cajaTextoMes.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cajaTextoMes.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoMesActionPerformed(evt);
            }
        });

        cajaTextoAnio.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cajaTextoAnio.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoAnioActionPerformed(evt);
            }
        });

        jtDocumentaciones.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null},
                    {null, null, null, null}
                },
                new String[]{
                    "Title 1", "Title 2", "Title 3", "Title 4"
                }));
        jtDocumentaciones.setSelectionBackground(new java.awt.Color(129, 208, 207));
        jtDocumentaciones.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jtDocumentaciones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jtDocumentaciones.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtDocumentacionesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jtDocumentaciones);

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("NOMBRE:");

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("UNIDAD:");

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Nº CUENTA BANCARIA:");

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("CELULAR:");

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("FIJO:");

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("CÉDULA DE IDENTIDAD:");

        jLabel10.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("ENCARGADA:");

        labelEncargado.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        labelEncargado.setForeground(new java.awt.Color(255, 255, 255));
        labelEncargado.setText(" E. Teresa");

        cajaTextoCedula.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cajaTextoCedula.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoCedulaActionPerformed(evt);
            }
        });

        cbxUnidad.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cbxUnidad.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Rectorado", "Vice - Rectorado", "Post - Grado", "Fac. Ciencias Agricolas y Pecuarias", "Fac. Bioquímica y Farmacia", "Fac. Ciencias Económicas", "Fac. Desarrollo Rural y Territorial", "Fac. Odontología", "Fac. Medicina", "Fac. Arquitectura", "Fac. Humanidades y Ciencias de la Educación", "Fac. Ciencias Jurídicas y Políticas", "Fac. Ciencias y Tecnología", "Fac. Politécnica del Valle Alto", "Fac. Ciencias Sociales", "Fac. Enfermería", "Fac. Veterinaria y Zootecnia"}));
        cbxUnidad.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxUnidadActionPerformed(evt);
            }
        });

        cajaTextoCelular.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cajaTextoCelular.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoCelularActionPerformed(evt);
            }
        });

        cajaTextoCuenta.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cajaTextoCuenta.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoCuentaActionPerformed(evt);
            }
        });

        cbxCedula.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cbxCedula.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Chuquisaca (CH)", "La Paz (LP)", "Cochabamba (CB)", "Oruro (OR)", "Potosí (PT)", "Tarija (TJ)", "Santa Cruz (SC)", "Beni (BE)", "Pando (PD)"}));
        cbxCedula.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCedulaActionPerformed(evt);
            }
        });

        cajaTextoNombre.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cajaTextoNombre.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoNombreActionPerformed(evt);
            }
        });

        cajaTextoFijo.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        cajaTextoFijo.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cajaTextoFijoActionPerformed(evt);
            }
        });

        botonGuardar.setBackground(new java.awt.Color(255, 255, 255));
        botonGuardar.setFont(new java.awt.Font("Verdana", 3, 12)); // NOI18N
        botonGuardar.setForeground(new java.awt.Color(0, 51, 102));
        botonGuardar.setText("GUARDAR");
        botonGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonGuardar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarActionPerformed(evt);
            }
        });

        botonModificar.setBackground(new java.awt.Color(255, 255, 255));
        botonModificar.setFont(new java.awt.Font("Verdana", 3, 12)); // NOI18N
        botonModificar.setForeground(new java.awt.Color(0, 51, 102));
        botonModificar.setText("MODIFICAR");
        botonModificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonModificar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarActionPerformed(evt);
            }
        });

        botonEliminar.setBackground(new java.awt.Color(255, 255, 255));
        botonEliminar.setFont(new java.awt.Font("Verdana", 3, 12)); // NOI18N
        botonEliminar.setForeground(new java.awt.Color(0, 51, 102));
        botonEliminar.setText("ELIMINAR");
        botonEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonEliminar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarActionPerformed(evt);
            }
        });

        botonLimpiar.setBackground(new java.awt.Color(255, 255, 255));
        botonLimpiar.setFont(new java.awt.Font("Verdana", 3, 12)); // NOI18N
        botonLimpiar.setForeground(new java.awt.Color(0, 51, 102));
        botonLimpiar.setText("LIMPIAR");
        botonLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonLimpiar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLimpiarActionPerformed(evt);
            }
        });

        botonGenerar.setBackground(new java.awt.Color(255, 255, 255));
        botonGenerar.setFont(new java.awt.Font("Verdana", 3, 12)); // NOI18N
        botonGenerar.setForeground(new java.awt.Color(0, 51, 102));
        botonGenerar.setText("GENERAR REPORTE ANUAL");
        botonGenerar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botonGenerar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGenerarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jScrollPane1).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addComponent(jLabel3).addGap(3, 3, 3).addComponent(cbxAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(cajaTextoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(botonBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(labelFechaEst).addGap(4, 4, 4).addComponent(cajaTextoDia, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(cajaTextoMes, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(cajaTextoAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addComponent(cajaTextoCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(cbxCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)).addComponent(cajaTextoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel4).addComponent(jLabel9)).addGap(75, 75, 75).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(cbxUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel5).addComponent(cajaTextoCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel7)).addGap(75, 75, 75).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(cajaTextoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(cajaTextoFijo, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jLabel6).addComponent(jLabel8)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 250, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(12, 12, 12).addComponent(labelEncargado)).addComponent(jLabel10)).addGap(96, 96, 96)))).addGroup(jPanel1Layout.createSequentialGroup().addGap(162, 162, 162).addComponent(labelTitulo).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(labelTitulo1).addGap(0, 0, Short.MAX_VALUE)).addGroup(jPanel1Layout.createSequentialGroup().addComponent(botonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(96, 96, 96).addComponent(botonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(96, 96, 96).addComponent(botonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(97, 97, 97).addComponent(botonLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(botonGenerar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap()));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGap(31, 31, 31).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(labelTitulo).addComponent(labelTitulo1)).addGap(25, 25, 25).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(cbxAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(jLabel3).addComponent(cajaTextoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(botonBuscar).addComponent(cajaTextoAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(cajaTextoMes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(cajaTextoDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(labelFechaEst)).addGap(12, 12, 12).addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel4).addComponent(jLabel6)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(cajaTextoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(cajaTextoCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))).addGroup(jPanel1Layout.createSequentialGroup().addGap(13, 13, 13).addComponent(jLabel5).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(cbxUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))).addGap(13, 13, 13).addComponent(jLabel10).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(jLabel9).addComponent(jLabel7).addComponent(jLabel8).addComponent(labelEncargado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(cajaTextoCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(cajaTextoFijo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(cajaTextoCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(cbxCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))).addGap(128, 128, 128)).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(botonModificar).addComponent(botonEliminar).addComponent(botonLimpiar).addComponent(botonGuardar).addComponent(botonGenerar)).addGap(50, 50, 50)))));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
    }// </editor-fold>

    private void botonGenerarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            Conexion con = new Conexion();
            Connection conn = (Connection) con.getConexion();

            Conexion objCon =    new Conexion();
            Connection conexion = (Connection) objCon.getConexion();
            PreparedStatement ps2 = null;
            int cantidad = 0;
            ResultSet rs = null;
            ps2 = conexion.prepareStatement("SELECT COUNT(*) FROM registros_solvencias.solvencias WHERE YEAR(fecha)="+cbxAnio.getSelectedItem().toString());
            rs = ps2.executeQuery();
            while (rs.next()) {
                cantidad = rs.getInt(1);
            }

            JasperReport reporte = null;
            String path = "src\\reportes\\RepAnualSolv.jasper";

            Map parametro = new HashMap();
            parametro.put("gestion", cbxAnio.getSelectedItem().toString());
            parametro.put("cantidad", cantidad);
            parametro.put("anio", cbxAnio.getSelectedItem().toString());

            reporte = (JasperReport) JRLoader.loadObjectFromFile(path);

            JasperPrint jprint = JasperFillManager.fillReport(reporte, parametro, conn);

            JasperViewer view = new JasperViewer(jprint, false);

            view.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

            view.setVisible(true);

            view.setExtendedState(view.MAXIMIZED_BOTH);

        } catch (JRException ex) {
            Logger.getLogger(VistaFormulario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Registrar el Certificado de Solvencia", "Error", JOptionPane.ERROR_MESSAGE);
            //System.out.println(ex);
        }
    }

    private void cbxUnidadActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void cbxCedulaActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void cbxAnioActionPerformed(java.awt.event.ActionEvent evt) {
        reiniciar();
        limpiar();
        String anioTitulo = cbxAnio.getSelectedItem().toString();
        labelTitulo1.setText(anioTitulo);
    }

    private void cajaTextoBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void botonBuscarActionPerformed(java.awt.event.ActionEvent evt) {
        String campo = cajaTextoBuscar.getText();
        String anioConsulta = cbxAnio.getSelectedItem().toString();
        String sql = "";
        String where = "";
        if (!"".equals(campo)) {
            //where = "WHERE tram = '" + campo + "'";
            where = "WHERE nombre LIKE '%" + campo + "%'";
            sql = "SELECT * FROM solvencias " + where + " AND YEAR(fecha) = " + anioConsulta + " ORDER BY fecha";
        } else {
            sql = "SELECT * FROM solvencias WHERE YEAR(fecha) = " + anioConsulta + " ORDER BY fecha";
        }
        //SELECT * FROM tabla WHERE campo LIKE '%valorrecibido%'
        try {
            DefaultTableModel modelo = new DefaultTableModel() {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            jtDocumentaciones.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            Connection con = (Connection) conn.getConexion();

            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("ID");
            modelo.addColumn("     FECHA");
            modelo.addColumn("                         NOMBRE");
            modelo.addColumn("                              UNIDAD");
            modelo.addColumn(" Nº CUENTA BANCARIA");
            modelo.addColumn(" CÉDULA DE IDENTIDAD");
            modelo.addColumn("      CELULAR");
            modelo.addColumn("         FIJO");
            modelo.addColumn("   ENCARGADO");
            modelo.addColumn("     ARCHIVO");

            int[] anchos = {0, 25, 200, 220, 75, 80, 40, 40, 40, 30};
            for (int i = 0; i < jtDocumentaciones.getColumnCount(); i++) {
                if (i == 0) {
                    jtDocumentaciones.getColumnModel().getColumn(0).setMaxWidth(anchos[i]);
                    jtDocumentaciones.getColumnModel().getColumn(0).setMinWidth(anchos[i]);
                    jtDocumentaciones.getColumnModel().getColumn(0).setPreferredWidth(anchos[i]);
                } else {
                    jtDocumentaciones.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
                }
            }

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    switch (i) {
                        case 0:
                            filas[i] = rs.getObject(i + 1);
                            break;
                        case 1:
                            String fechaRecibida = darFormato(rs.getDate(i + 1));
                            filas[i] = "  " + fechaRecibida;
                            break;
                        case 2:
                            filas[i] = "  " + rs.getObject(i + 1);
                            break;
                        case 3:
                            filas[i] = "  " + rs.getObject(i + 1);
                            break;
                        case 4:
                            filas[i] = "  " + rs.getObject(i + 1);
                            break;
                        case 5:
                            filas[i] = "  " + rs.getObject(i + 1) + "  " + rs.getString(i + 2);
                            break;
                        case 6:
                            filas[i] = "  " + rs.getObject(i + 2);
                            break;
                        case 7:
                            filas[i] = "  " + rs.getObject(i + 2);
                            break;
                        case 8:
                            filas[i] = "  " + rs.getObject(i + 2);
                            break;
                        case 9:
                            //filas[i] = "  " + rs.getObject(i + 2);
                            filas[i] = btnVer;
                            break;
                    }

                }
                modelo.addRow(filas);
            }

        } catch (Exception ex) {
            //System.err.println(ex.toString());
        }
    }

    private void cajaTextoDiaActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void cajaTextoMesActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void cajaTextoAnioActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jtDocumentacionesMouseClicked(java.awt.event.MouseEvent evt) {

        int column = jtDocumentaciones.getColumnModel().getColumnIndexAtX(evt.getX());
        int row = evt.getY() / jtDocumentaciones.getRowHeight();
        String rutaCadena = "";

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Conexion objCon = new Conexion();
            java.sql.Connection conn = objCon.getConexion();

            int Fila = jtDocumentaciones.getSelectedRow();
            String id = jtDocumentaciones.getValueAt(Fila, 0).toString();

            ps = conn.prepareStatement("SELECT * FROM solvencias WHERE id=?");
            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                //cajaTextoFechaActual.setText(darFormato(rs.getDate("fecha_actual")));
                cajaTextoDia.setText(recibirDia(rs.getDate("fecha")));
                cajaTextoMes.setText(recibirMes(rs.getDate("fecha")));
                cajaTextoAnio.setText(recibirAnio(rs.getDate("fecha")));
                cajaTextoNombre.setText(rs.getString("nombre"));
                cbxUnidad.setSelectedItem(rs.getString("unidad"));
                cajaTextoCuenta.setText(rs.getString("nro_cuentab"));
                cajaTextoCedula.setText(rs.getString("ci_num"));
                cbxCedula.setSelectedItem(formatoCiudadExtenso(rs.getString("ci_exped")));
                cajaTextoCelular.setText(rs.getString("celular"));
                cajaTextoFijo.setText(rs.getString("fijo"));
                rutaCadena = rs.getString("ruta");
                rutaClick = rs.getString("ruta");
            }
            //System.out.println(rutaClick);

            if (row < jtDocumentaciones.getRowCount() && row >= 0 && column < jtDocumentaciones.getColumnCount() && column >= 0) {
                Object value = jtDocumentaciones.getValueAt(row, column);
                if (value instanceof JButton) {
                    ((JButton) value).doClick();
                    JButton boton = (JButton) value;

                    if (boton.getName().equals("ver")) {
                        try {
                            abrirarchivo(rutaCadena);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "No se ha encontrado el archivo: " + cajaTextoNombre.getText() + " - " + cajaTextoCedula.getText() + " - ID " + id + ".pdf", "Aviso", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            //System.out.println(ex.toString());
        }
    }

    private void cajaTextoCedulaActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void cajaTextoCelularActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void cajaTextoCuentaActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void cajaTextoNombreActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void cajaTextoFijoActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void botonGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        int indice = 0;
        JasperReport jasperReport;
        JasperPrint jasperPrint;
        try {
            Conexion objCon = new Conexion();
            Connection conexion = (Connection) objCon.getConexion();

            ResultSet rs = null;
            ps2 = conexion.prepareStatement("SELECT AUTO_INCREMENT FROM `information_schema`.`tables` WHERE TABLE_SCHEMA = 'registros_solvencias' AND TABLE_NAME = 'solvencias'");
            rs = ps2.executeQuery();
            while (rs.next()) {
                System.out.println("valor es  " + (rs.getInt(1)));
                indice = rs.getInt(1);
            }

            ps = conexion.prepareStatement("INSERT INTO solvencias (fecha, nombre, unidad, nro_cuentab, ci_num, ci_exped, celular, fijo, realizado, ruta) VALUES (?,?,?,?,?,?,?,?,?,?)");
            //ps.setString(1, obtenerFechaActualSF());
            ps.setString(1, cajaTextoAnio.getText() + "-" + cajaTextoMes.getText() + "-" + cajaTextoDia.getText());
            ps.setString(2, cajaTextoNombre.getText());
            ps.setString(3, cbxUnidad.getSelectedItem().toString());
            ps.setString(4, cajaTextoCuenta.getText());
            ps.setString(5, cajaTextoCedula.getText());
            ps.setString(6, formatoCiudadAbreviado(cbxCedula.getSelectedIndex()));
            ps.setString(7, cajaTextoCelular.getText());
            ps.setString(8, cajaTextoFijo.getText());
            ps.setString(9, labelEncargado.getText());
            ps.setString(10, "C:/Users/" + System.getProperty("user.name") + "/Desktop/SOLVENCIAS UMSS/" + cbxAnio.getSelectedItem().toString() + "/" + cajaTextoNombre.getText() + " - " + cajaTextoCedula.getText() + " - ID " + indice + ".pdf");
            ps.execute();

            try {
                Conexion objCon2 = new Conexion();
                Connection conexion2 = (Connection) objCon2.getConexion();

                Map parametro = new HashMap();
                parametro.put("nombre", cajaTextoNombre.getText());
                parametro.put("ci", cajaTextoCedula.getText());
                parametro.put("llave", indice);

                String path = "src\\reportes\\Solvencia.jasper";
                //se carga el reporte
                //URL in = this.getClass().getResource("../reportes/Solvencia.jasper");
                //jasperReport = (JasperReport) JRLoader.loadObject(in);
                jasperReport = (JasperReport) JRLoader.loadObjectFromFile(path);

                //se procesa el archivo jasper
                jasperPrint = JasperFillManager.fillReport(jasperReport, parametro, conexion2);
                //se crea el archivo PDF
                JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/Users/" + System.getProperty("user.name") + "/Desktop/SOLVENCIAS UMSS/" + cbxAnio.getSelectedItem().toString() + "/" + cajaTextoNombre.getText() + " - " + cajaTextoCedula.getText() + " - ID " + indice + ".pdf");


            } catch (JRException ex) {
                System.err.println("Error iReport: " + ex.getMessage());
            }

            reiniciar();

            int opcion = JOptionPane.showConfirmDialog(this, "Certificado de Solvencia ha sido Registrado. ¿Desea vizualizar el documento generado?", "Realizado", JOptionPane.YES_NO_OPTION);
            switch (opcion) {
                case 0:
                    abrirarchivo("C:/Users/" + System.getProperty("user.name") + "/Desktop/SOLVENCIAS UMSS/" + cbxAnio.getSelectedItem().toString() + "/" + cajaTextoNombre.getText() + " - " + cajaTextoCedula.getText() + " - ID " + indice + ".pdf");
                    break;
                case 1:
                    System.out.println("boton nooo");
                    break;
            }

            //JOptionPane.showMessageDialog(null, "Certificado de Solvencia Registrado", "Realizado", JOptionPane.INFORMATION_MESSAGE);

            limpiar();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Registrar el Certificado de Solvencia", "Error", JOptionPane.ERROR_MESSAGE);
            //System.out.println(ex);
        }
    }

    private void botonModificarActionPerformed(java.awt.event.ActionEvent evt) {

        PreparedStatement ps = null;

        JasperReport jasperReport;
        JasperPrint jasperPrint;
        try {
            Conexion objCon = new Conexion();
            Connection conn = (Connection) objCon.getConexion();

            int Fila = jtDocumentaciones.getSelectedRow();
            String codigo = jtDocumentaciones.getValueAt(Fila, 0).toString();

            ps = conn.prepareStatement("UPDATE solvencias SET fecha=?, nombre=?, unidad=?, nro_cuentab=?, ci_num=?, ci_exped=?, celular=?, fijo=?, realizado=?, ruta=?  WHERE id=?");

            ps.setString(1, cajaTextoAnio.getText() + "-" + cajaTextoMes.getText() + "-" + cajaTextoDia.getText());
            ps.setString(2, cajaTextoNombre.getText());
            ps.setString(3, cbxUnidad.getSelectedItem().toString());
            ps.setString(4, cajaTextoCuenta.getText());
            ps.setString(5, cajaTextoCedula.getText());
            ps.setString(6, formatoCiudadAbreviado(cbxCedula.getSelectedIndex()));
            ps.setString(7, cajaTextoCelular.getText());
            ps.setString(8, cajaTextoFijo.getText());
            ps.setString(9, labelEncargado.getText());
            ps.setString(10, "C:/Users/" + System.getProperty("user.name") + "/Desktop/SOLVENCIAS UMSS/" + cbxAnio.getSelectedItem().toString() + "/" + cajaTextoNombre.getText() + " - " + cajaTextoCedula.getText() + " - ID " + codigo + ".pdf");
            ps.setString(11, codigo);
            ps.execute();

            File fichero = new File(rutaClick);
            if (fichero.delete()) {
                System.out.println("El fichero " + rutaClick + " ha sido borrado satisfactoriamente");
            } else {
                System.out.println("El fichero no puede ser borrado");
            }

            try {
                Conexion objCon2 = new Conexion();
                Connection conexion2 = (Connection) objCon2.getConexion();

                Map parametro = new HashMap();
                parametro.put("nombre", cajaTextoNombre.getText());
                parametro.put("ci", cajaTextoCedula.getText());
                parametro.put("llave", codigo);
                System.out.println(codigo);

                String path = "src\\reportes\\Solvencia.jasper";
                //se carga el reporte
                //URL in = this.getClass().getResource("../reportes/Solvencia.jasper");
                //jasperReport = (JasperReport) JRLoader.loadObject(in);
                jasperReport = (JasperReport) JRLoader.loadObjectFromFile(path);

                //se procesa el archivo jasper
                jasperPrint = JasperFillManager.fillReport(jasperReport, parametro, conexion2);
                //se crea el archivo PDF
                JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/Users/" + System.getProperty("user.name") + "/Desktop/SOLVENCIAS UMSS/" + cbxAnio.getSelectedItem().toString() + "/" + cajaTextoNombre.getText() + " - " + cajaTextoCedula.getText() + " - ID " + codigo + ".pdf");


            } catch (JRException ex) {
                System.err.println("Error iReport: " + ex.getMessage());
            }

            reiniciar();

            int opcion = JOptionPane.showConfirmDialog(this, "El Certificado de Solvencia ha sido Modificado. ¿Desea vizualizar el documento generado?", "Realizado", JOptionPane.YES_NO_OPTION);
            switch (opcion) {
                case 0:
                    abrirarchivo("C:/Users/" + System.getProperty("user.name") + "/Desktop/SOLVENCIAS UMSS/" + cbxAnio.getSelectedItem().toString() + "/" + cajaTextoNombre.getText() + " - " + cajaTextoCedula.getText() + " - ID " + codigo + ".pdf");
                    break;
                case 1:
                    System.out.println("boton nooo");
                    break;
            }

            //JOptionPane.showMessageDialog(null, "El Certificado de Solvencia ha sido Modificado", "Realizado", JOptionPane.INFORMATION_MESSAGE);

            limpiar();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Modificar el Certificado de Solvencia", "Error", JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se ha modificado ningún registro, ya que no se escogió alguno", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void botonEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        PreparedStatement ps = null;
        try {

            Conexion objCon = new Conexion();
            Connection conn = (Connection) objCon.getConexion();

            int Fila = jtDocumentaciones.getSelectedRow();
            String codigo = jtDocumentaciones.getValueAt(Fila, 0).toString();
            //System.out.println("eliminando " + codigo);

            ps = conn.prepareStatement("DELETE FROM solvencias WHERE id=?");
            ps.setString(1, codigo);
            ps.execute();

            File fichero = new File(rutaClick);
            if (fichero.delete()) {
                System.out.println("El fichero " + rutaClick + " ha sido borrado satisfactoriamente");
            } else {
                System.out.println("El fichero no puede ser borrado");
            }

            //modelo.removeRow(Fila);
            reiniciar();
            JOptionPane.showMessageDialog(null, "El Certificado de Solvencia ha sido Eliminado", "Realizado", JOptionPane.INFORMATION_MESSAGE);
            limpiar();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al Eliminar el Certificado de Solvencia", "Error", JOptionPane.ERROR_MESSAGE);
            //System.out.println(ex.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se ha eliminado ningún registro, ya que no se escogió alguno", "Aviso", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void botonLimpiarActionPerformed(java.awt.event.ActionEvent evt) {
        limpiar();
        reiniciar();
    }

    private void reiniciar() {
        try {
            DefaultTableModel modelo = new DefaultTableModel() {

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            jtDocumentaciones.setModel(modelo);

            PreparedStatement ps = null;
            ResultSet rs = null;
            Conexion conn = new Conexion();
            java.sql.Connection con = conn.getConexion();

            String anioConsulta = cbxAnio.getSelectedItem().toString();

            String sql = "SELECT * FROM solvencias WHERE YEAR(fecha) = " + anioConsulta + " ORDER BY fecha";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            ResultSetMetaData rsMd = (ResultSetMetaData) rs.getMetaData();
            int cantidadColumnas = rsMd.getColumnCount();

            modelo.addColumn("ID");
            modelo.addColumn("     FECHA");
            modelo.addColumn("                         NOMBRE");
            modelo.addColumn("                              UNIDAD");
            modelo.addColumn(" Nº CUENTA BANCARIA");
            modelo.addColumn(" CÉDULA DE IDENTIDAD");
            modelo.addColumn("      CELULAR");
            modelo.addColumn("         FIJO");
            modelo.addColumn("   ENCARGADO");
            modelo.addColumn("     ARCHIVO");

            int[] anchos = {0, 25, 200, 220, 75, 80, 40, 40, 40, 30};
            for (int i = 0; i < jtDocumentaciones.getColumnCount(); i++) {
                if (i == 0) {
                    jtDocumentaciones.getColumnModel().getColumn(0).setMaxWidth(anchos[i]);
                    jtDocumentaciones.getColumnModel().getColumn(0).setMinWidth(anchos[i]);
                    jtDocumentaciones.getColumnModel().getColumn(0).setPreferredWidth(anchos[i]);
                } else {
                    jtDocumentaciones.getColumnModel().getColumn(i).setPreferredWidth(anchos[i]);
                }
            }

            while (rs.next()) {
                Object[] filas = new Object[cantidadColumnas];
                for (int i = 0; i < cantidadColumnas; i++) {
                    switch (i) {
                        case 0:
                            filas[i] = rs.getObject(i + 1);
                            break;
                        case 1:
                            String fechaRecibida = darFormato(rs.getDate(i + 1));
                            filas[i] = "  " + fechaRecibida;
                            break;
                        case 2:
                            filas[i] = "  " + rs.getObject(i + 1);
                            break;
                        case 3:
                            filas[i] = "  " + rs.getObject(i + 1);
                            break;
                        case 4:
                            filas[i] = "  " + rs.getObject(i + 1);
                            break;
                        case 5:
                            filas[i] = "  " + rs.getObject(i + 1) + "  " + rs.getString(i + 2);
                            break;
                        case 6:
                            filas[i] = "  " + rs.getObject(i + 2);
                            break;
                        case 7:
                            filas[i] = "  " + rs.getObject(i + 2);
                            break;
                        case 8:
                            filas[i] = "  " + rs.getObject(i + 2);
                            break;
                        case 9:
                            //filas[i] = "  " + rs.getObject(i + 2);
                            filas[i] = btnVer;
                            break;
                    }

                }
                modelo.addRow(filas);
            }

        } catch (SQLException ex) {
            //System.err.println(ex.toString());
        }
    }

    private void limpiar() {
        obternerFechaActualCF();
        cajaTextoBuscar.setText("");
        cajaTextoNombre.setText("");
        cbxUnidad.setSelectedIndex(0);
        cajaTextoCuenta.setText("");
        cajaTextoCedula.setText("");
        cbxCedula.setSelectedIndex(0);
        cajaTextoCelular.setText("");
        cajaTextoFijo.setText("");

    }

    public void obternerFechaActualCF() {
        Calendar fecha = new GregorianCalendar();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        cajaTextoDia.setText(Integer.toString(dia));
        cajaTextoMes.setText(Integer.toString(mes + 1));
        cajaTextoAnio.setText(Integer.toString(año));
    }

    public String formatoCiudadAbreviado(int codigoCiudad) {
        String ciudad = "";
        switch (codigoCiudad) {
            case 0:
                ciudad = "(CH)";
                break;
            case 1:
                ciudad = "(LP)";
                break;
            case 2:
                ciudad = "(CB)";
                break;
            case 3:
                ciudad = "(OR)";
                break;
            case 4:
                ciudad = "(PT)";
                break;
            case 5:
                ciudad = "(TJ)";
                break;
            case 6:
                ciudad = "(SC)";
                break;
            case 7:
                ciudad = "(BE)";
                break;
            case 8:
                ciudad = "(PD)";
                break;
        }
        return ciudad;
    }

    public String formatoCiudadExtenso(String ciudadCodigo) {
        String ciudad = "";
        if (ciudadCodigo.equals("(CH)")) {
            ciudad = "Chuquisaca (CH)";
        } else if (ciudadCodigo.equals("(LP)")) {
            ciudad = "La Paz (LP)";
        } else if (ciudadCodigo.equals("(CB)")) {
            ciudad = "Cochabamba (CB)";
        } else if (ciudadCodigo.equals("(OR)")) {
            ciudad = "Oruro (OR)";
        } else if (ciudadCodigo.equals("(PT)")) {
            ciudad = "Potosí (PT)";
        } else if (ciudadCodigo.equals("(TJ)")) {
            ciudad = "Tarija (TJ)";
        } else if (ciudadCodigo.equals("(SC)")) {
            ciudad = "Santa Cruz (SC)";
        } else if (ciudadCodigo.equals("(BE)")) {
            ciudad = "Beni (BE)";
        } else {
            ciudad = "Pando (PD)";
        }
        return ciudad;
    }

    public String darFormato(Date fecha) {
        String mesString = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int diaActual = cal.get(Calendar.DAY_OF_MONTH);
        int mesActual = cal.get(Calendar.MONTH);
        int año = cal.get(Calendar.YEAR);
        switch (mesActual) {
            case 0:
                mesString = "Ene.";
                break;
            case 1:
                mesString = "Feb.";
                break;
            case 2:
                mesString = "Mar.";
                break;
            case 3:
                mesString = "Abr.";
                break;
            case 4:
                mesString = "May.";
                break;
            case 5:
                mesString = "Jun.";
                break;
            case 6:
                mesString = "Jul.";
                break;
            case 7:
                mesString = "Ago.";
                break;
            case 8:
                mesString = "Sep.";
                break;
            case 9:
                mesString = "Oct.";
                break;
            case 10:
                mesString = "Nov.";
                break;
            case 11:
                mesString = "Dic.";
                break;
        }
        //System.out.println("este es el mes: "+mesString+" "+diaActual);
        return diaActual + " " + mesString + " " + año;
    }

    public void obtenerAnio() {
        String anioString = "";
        Calendar fecha = new GregorianCalendar();
        int año = fecha.get(Calendar.YEAR);
        String[] opciones = new String[10];
        for (int i = 0; i < 10; i++) {
            anioString = Integer.toString(año);
            opciones[i] = anioString;
            año = año - 1;
        }
        cbxAnio.setModel(new javax.swing.DefaultComboBoxModel(opciones));
        //cbxAnio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Pasivo", "Ningúno" }));
        //return Integer.toString(año);
    }

    public String recibirDia(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int dia = cal.get(Calendar.DAY_OF_MONTH);
        return Integer.toString(dia);
    }

    public String recibirMes(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int mes = cal.get(Calendar.MONTH);
        //System.out.println(mes);
        return Integer.toString(mes + 1);
    }

    public String recibirAnio(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int anio = cal.get(Calendar.YEAR);
        return Integer.toString(anio);
    }

    public void abrirarchivo(String archivo) {

        try {

            File objetofile = new File(archivo);
            Desktop.getDesktop().open(objetofile);

        } catch (IOException ex) {

            System.out.println(ex);

        }
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaFormulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaFormulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaFormulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaFormulario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new VistaFormulario().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify
    private javax.swing.JButton botonBuscar;
    private javax.swing.JButton botonEliminar;
    private javax.swing.JButton botonGenerar;
    private javax.swing.JButton botonGuardar;
    private javax.swing.JButton botonLimpiar;
    private javax.swing.JButton botonModificar;
    private javax.swing.JTextField cajaTextoAnio;
    private javax.swing.JTextField cajaTextoBuscar;
    private javax.swing.JTextField cajaTextoCedula;
    private javax.swing.JTextField cajaTextoCelular;
    private javax.swing.JTextField cajaTextoCuenta;
    private javax.swing.JTextField cajaTextoDia;
    private javax.swing.JTextField cajaTextoFijo;
    private javax.swing.JTextField cajaTextoMes;
    private javax.swing.JTextField cajaTextoNombre;
    private javax.swing.JComboBox cbxAnio;
    private javax.swing.JComboBox cbxCedula;
    private javax.swing.JComboBox cbxUnidad;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtDocumentaciones;
    private javax.swing.JLabel labelEncargado;
    private javax.swing.JLabel labelFechaEst;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JLabel labelTitulo1;
    // End of variables declaration
}
