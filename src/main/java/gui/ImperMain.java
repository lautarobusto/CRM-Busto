package gui;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Lautaro
 */
public class ImperMain extends javax.swing.JFrame {

    public ImperMain() {

        initComponents();
        myInitComponents();
        ImageIcon img = new ImageIcon("gear2.png");

        setIconImage(img.getImage());

    }

    private static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }

    private void loadproductos() {

        int rowa = tableMarca.getSelectedRow();

        if (rowa >= 0) {
            int row = tableMarca.convertRowIndexToModel(rowa);
            String marca = tableMarca.getModel().getValueAt(row, 0).toString();
            //tableProducto.setModel(buildTableModel(conector.getProdM(marca)));
        }
    }

    private void loadProductosFields() throws SQLException {
        int rowa = tableProducto.getSelectedRow();

        if (rowa >= 0) {
            int row = tableProducto.convertRowIndexToModel(rowa);
            String producto = tableProducto.getModel().getValueAt(row, 0).toString();
            //ResultSet rst = conector.getProd(producto);
            //jTextFieldCodigoImp.setText(rst.getString("CodImperdiel"));
            //jTextFieldCodigoOri.setText(rst.getString("CodOrigen"));
            //jTextFieldMarcap.setText(rst.getString("Marca"));
            //jTextFieldNombre.setText(rst.getString("Nombre"));
           // jTextFieldRubro.setText(rst.getString("Rubro"));
            //jFormattedTextFieldPrecio.setValue((Object) rst.getDouble("Precio"));
            //jFormattedTextFieldPrecio1.setValue((Object) rst.getDouble("PrecioIva"));
            //jFormattedTextFieldPrecio2.setValue((Object) rst.getDouble("PrecioCosto"));
        }

    }

    private void myInitComponents() {
        tableMarca.setAutoCreateRowSorter(false);
        tableProducto.setAutoCreateRowSorter(false);
        tableMarca.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableProducto.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //tableMarca.setModel(buildTableModel(conector.getMarca()));
        tableMarca.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                loadproductos();
                //Es sucio pero con esto "reinicio" el estado de la tableProducto, cada vex que selecciono otra marca
                TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableProducto.getModel());
                sorter.setRowFilter(null);
                tableProducto.setRowSorter(sorter);
            }
        });
        tableProducto.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                try {
                    loadProductosFields();
                } catch (SQLException ex) {
                    Logger.getLogger(ImperMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    private void update() {

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {

                //TODO ADD UPODATE LOGC
                return null;

            }
        }.execute();

    }


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        PrecioTab = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableMarca = new javax.swing.JTable(){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        jTextFieldMarca = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableProducto = new javax.swing.JTable(){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        jTextFieldProducto = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jFormattedTextFieldPrecio = new javax.swing.JFormattedTextField();
        jTextFieldNombre = new javax.swing.JTextField();
        jTextFieldCodigoImp = new javax.swing.JTextField();
        jTextFieldCodigoOri = new javax.swing.JTextField();
        jTextFieldRubro = new javax.swing.JTextField();
        jTextFieldMarcap = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jFormattedTextFieldPrecio1 = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        jFormattedTextFieldPrecio2 = new javax.swing.JFormattedTextField();
        jPanel5 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Imper 2.0");
        setName("Imper 2.0"); // NOI18N
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setToolTipText("Marca");
        jPanel1.setMaximumSize(new java.awt.Dimension(440, 220));
        jPanel1.setMinimumSize(new java.awt.Dimension(440, 220));
        jPanel1.setName("Marca"); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(440, 220));

        tableMarca.setAutoCreateRowSorter(false);
        tableMarca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tableMarcaFocusGained(evt);
            }
        });
        jScrollPane3.setViewportView(tableMarca);

        jTextFieldMarca.setText("Marca...");
        jTextFieldMarca.setPreferredSize(new java.awt.Dimension(363, 24));
        jTextFieldMarca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldMarcaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldMarcaFocusLost(evt);
            }
        });
        jTextFieldMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldMarcaKeyReleased(evt);
            }
        });

        jLabel7.setText("Marca:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setToolTipText("Marca");
        jPanel3.setMaximumSize(new java.awt.Dimension(440, 220));
        jPanel3.setMinimumSize(new java.awt.Dimension(440, 220));
        jPanel3.setName("Marca"); // NOI18N

        tableProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tableProducto);

        jTextFieldProducto.setText("Producto...");
        jTextFieldProducto.setPreferredSize(new java.awt.Dimension(363, 24));
        jTextFieldProducto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextFieldProductoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextFieldProductoFocusLost(evt);
            }
        });
        jTextFieldProducto.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jTextFieldProductoInputMethodTextChanged(evt);
            }
        });
        jTextFieldProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldProductoActionPerformed(evt);
            }
        });
        jTextFieldProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldProductoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldProductoKeyTyped(evt);
            }
        });

        jLabel8.setText("Producto:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 424, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jTextFieldProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setMaximumSize(new java.awt.Dimension(300, 348));
        jPanel4.setMinimumSize(new java.awt.Dimension(300, 348));

        jLabel1.setText("Nombre:");

        jLabel2.setText("Rubro:");

        jLabel3.setText("CodigoImp:");

        jLabel4.setText("CodigoOrig:");

        jLabel5.setText("Marca:");

        jLabel6.setText("Precio S/ IVA:");

        jFormattedTextFieldPrecio.setEditable(false);
        jFormattedTextFieldPrecio.setForeground(new java.awt.Color(255, 0, 0));
        jFormattedTextFieldPrecio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$#,##0.00 "))));
        jFormattedTextFieldPrecio.setFocusable(false);
        jFormattedTextFieldPrecio.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N

        jTextFieldNombre.setEditable(false);

        jTextFieldCodigoImp.setEditable(false);

        jTextFieldCodigoOri.setEditable(false);

        jTextFieldRubro.setEditable(false);

        jTextFieldMarcap.setEditable(false);

        jLabel9.setText("Precio C/ IVA:");

        jFormattedTextFieldPrecio1.setEditable(false);
        jFormattedTextFieldPrecio1.setForeground(new java.awt.Color(255, 0, 0));
        jFormattedTextFieldPrecio1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$#,##0.00 "))));
        jFormattedTextFieldPrecio1.setFocusable(false);
        jFormattedTextFieldPrecio1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N

        jLabel10.setText("Precio Costo:");

        jFormattedTextFieldPrecio2.setEditable(false);
        jFormattedTextFieldPrecio2.setForeground(new java.awt.Color(255, 0, 0));
        jFormattedTextFieldPrecio2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("$#,##0.00 "))));
        jFormattedTextFieldPrecio2.setFocusable(false);
        jFormattedTextFieldPrecio2.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jFormattedTextFieldPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextFieldPrecio1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 19, Short.MAX_VALUE))
                            .addComponent(jTextFieldNombre)
                            .addComponent(jTextFieldCodigoImp, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldCodigoOri, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldRubro, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldMarcap, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextFieldPrecio2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCodigoImp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldCodigoOri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldRubro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldMarcap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                        .addComponent(jFormattedTextFieldPrecio))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jFormattedTextFieldPrecio1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jFormattedTextFieldPrecio2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(114, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setMaximumSize(new java.awt.Dimension(300, 90));
        jPanel5.setMinimumSize(new java.awt.Dimension(300, 90));
        jPanel5.setPreferredSize(new java.awt.Dimension(300, 90));

        jButton2.setText("Actualizar DB");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout PrecioTabLayout = new javax.swing.GroupLayout(PrecioTab);
        PrecioTab.setLayout(PrecioTabLayout);
        PrecioTabLayout.setHorizontalGroup(
            PrecioTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrecioTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PrecioTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(PrecioTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE))
                .addContainerGap())
        );
        PrecioTabLayout.setVerticalGroup(
            PrecioTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PrecioTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PrecioTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PrecioTabLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PrecioTabLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.getAccessibleContext().setAccessibleName("Marca");

        jTabbedPane1.addTab("Precios Imperdiel", new javax.swing.ImageIcon(getClass().getResource("/imper/gui/gear.png")), PrecioTab); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Precios");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldMarcaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldMarcaFocusLost
        jTextFieldMarca.setText("Marca...");
    }//GEN-LAST:event_jTextFieldMarcaFocusLost

    private void jTextFieldMarcaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldMarcaFocusGained
        if ("Marca...".equals(jTextFieldMarca.getText())) {
            jTextFieldMarca.setText("");
        }
    }//GEN-LAST:event_jTextFieldMarcaFocusGained

    private void jTextFieldProductoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldProductoFocusLost
        jTextFieldProducto.setText("Producto...");
    }//GEN-LAST:event_jTextFieldProductoFocusLost

    private void jTextFieldProductoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextFieldProductoFocusGained
        if ("Producto...".equals(jTextFieldProducto.getText())) {
            jTextFieldProducto.setText("");
        }
    }//GEN-LAST:event_jTextFieldProductoFocusGained

    private void jTextFieldProductoInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextFieldProductoInputMethodTextChanged


    }//GEN-LAST:event_jTextFieldProductoInputMethodTextChanged

    private void jTextFieldProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldProductoActionPerformed

    private void jTextFieldProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldProductoKeyTyped

    }//GEN-LAST:event_jTextFieldProductoKeyTyped

    private void jTextFieldProductoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldProductoKeyReleased
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableProducto.getModel());

        String filter = jTextFieldProducto.getText().toUpperCase();
        if (filter.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter(filter));
        }
        tableProducto.setRowSorter(sorter);
    }//GEN-LAST:event_jTextFieldProductoKeyReleased

    private void tableMarcaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableMarcaFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_tableMarcaFocusGained

    private void jTextFieldMarcaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldMarcaKeyReleased
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableMarca.getModel());

        String filter = jTextFieldMarca.getText().toUpperCase();
        if (filter.length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter(filter));
        }
        tableMarca.setRowSorter(sorter);

// TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldMarcaKeyReleased

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        update();

    }//GEN-LAST:event_jButton2ActionPerformed

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
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ImperMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ImperMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ImperMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ImperMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ImperMain().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PrecioTab;
    private javax.swing.JButton jButton2;
    private javax.swing.JFormattedTextField jFormattedTextFieldPrecio;
    private javax.swing.JFormattedTextField jFormattedTextFieldPrecio1;
    private javax.swing.JFormattedTextField jFormattedTextFieldPrecio2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextFieldCodigoImp;
    private javax.swing.JTextField jTextFieldCodigoOri;
    private javax.swing.JTextField jTextFieldMarca;
    private javax.swing.JTextField jTextFieldMarcap;
    private javax.swing.JTextField jTextFieldNombre;
    private javax.swing.JTextField jTextFieldProducto;
    private javax.swing.JTextField jTextFieldRubro;
    private javax.swing.JTable tableMarca;
    private javax.swing.JTable tableProducto;
    // End of variables declaration//GEN-END:variables

}
