package gym;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class InscripcionesIF extends JFrame {
    private JPanel contentPane;
    private JTextField textFieldIdCliente;
    private JTextField textFieldIdMembresia;
    private JFormattedTextField textFieldFechaInicio;
    private JFormattedTextField textFieldFechaFin;
    private JTable tableInscripciones;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    InscripcionesIF frame = new InscripcionesIF();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public InscripcionesIF() {
    	setTitle("Gestión de Inscripciones");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblIdCliente = new JLabel("ID Cliente:");
        lblIdCliente.setBounds(20, 30, 80, 20);
        contentPane.add(lblIdCliente);

        textFieldIdCliente = new JTextField();
        textFieldIdCliente.setBounds(108, 31, 130, 20);
        contentPane.add(textFieldIdCliente);

        JLabel lblIdMembresia = new JLabel("ID Membresía:");
        lblIdMembresia.setBounds(20, 70, 100, 20);
        contentPane.add(lblIdMembresia);

        textFieldIdMembresia = new JTextField();
        textFieldIdMembresia.setBounds(108, 71, 130, 20);
        contentPane.add(textFieldIdMembresia);

        JLabel lblFechaInicio = new JLabel("Fecha Inicio:");
        lblFechaInicio.setBounds(20, 110, 100, 20);
        contentPane.add(lblFechaInicio);

        
        try {
            MaskFormatter dateFormatter = new MaskFormatter("####-##-##");
            textFieldFechaInicio = new JFormattedTextField(dateFormatter);
            textFieldFechaInicio.setBounds(108, 111, 130, 20);
            contentPane.add(textFieldFechaInicio);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblFechaFin = new JLabel("Fecha Fin:");
        lblFechaFin.setBounds(20, 150, 80, 20);
        contentPane.add(lblFechaFin);

        
        try {
            MaskFormatter dateFormatter = new MaskFormatter("####-##-##");
            textFieldFechaFin = new JFormattedTextField(dateFormatter);
            textFieldFechaFin.setBounds(108, 151, 130, 20);
            contentPane.add(textFieldFechaFin);
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        JButton btnCrear = new JButton("Crear");
        btnCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idCliente = textFieldIdCliente.getText();
                String idMembresia = textFieldIdMembresia.getText();
                String fechaInicio = textFieldFechaInicio.getText();
                String fechaFin = textFieldFechaFin.getText();

                if (idCliente.isEmpty() || idMembresia.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                
                if (!fechaInicio.matches("\\d{4}-\\d{2}-\\d{2}") || !fechaFin.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese las fechas en formato YYYY-MM-DD.");
                    return;
                }

                Inscripcion inscripcion = new Inscripcion(
                        0,
                        Integer.parseInt(idCliente),
                        Integer.parseInt(idMembresia),
                        Date.valueOf(fechaInicio),
                        Date.valueOf(fechaFin)
                );
                InscripcionDAO.crearInscripcionDB(inscripcion);
                cargarInscripciones();
            }
        });
        btnCrear.setBounds(268, 42, 105, 30);
        contentPane.add(btnCrear);

        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tableInscripciones.getSelectedRow();
                if (row >= 0) {
                    int id = (int) tableInscripciones.getValueAt(row, 0);
                    InscripcionDAO.eliminarInscripcion(id);
                    cargarInscripciones();
                }
            }
        });
        btnEliminar.setBounds(396, 82, 105, 30);
        contentPane.add(btnEliminar);

        
        JButton btnEditar = new JButton("Guardar");
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tableInscripciones.getSelectedRow();
                if (row >= 0) {
                    int idInscripcion = (int) tableInscripciones.getValueAt(row, 0); 

                    
                    String idCliente = textFieldIdCliente.getText();
                    String idMembresia = textFieldIdMembresia.getText();
                    String fechaInicio = textFieldFechaInicio.getText();
                    String fechaFin = textFieldFechaFin.getText();

                    
                    if (idCliente.isEmpty() || idMembresia.isEmpty() || fechaInicio.isEmpty() || fechaFin.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                        return;
                    }

                    if (!fechaInicio.matches("\\d{4}-\\d{2}-\\d{2}") || !fechaFin.matches("\\d{4}-\\d{2}-\\d{2}")) {
                        JOptionPane.showMessageDialog(null, "Por favor, ingrese las fechas en formato YYYY-MM-DD.");
                        return;
                    }

                    
                    Inscripcion inscripcion = new Inscripcion(
                        idInscripcion,
                        Integer.parseInt(idCliente),
                        Integer.parseInt(idMembresia),
                        Date.valueOf(fechaInicio),
                        Date.valueOf(fechaFin)
                    );

                    
                    InscripcionDAO.editarInscripcion(inscripcion);
                    cargarInscripciones();
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una inscripción para editar.");
                }
            }
        });

        btnEditar.setBounds(268, 82, 105, 30);
        contentPane.add(btnEditar);
        
     
        JButton btnVisualizar = new JButton("Visualizar");
        btnVisualizar.addActionListener(e -> {
            
            int selectedRow = tableInscripciones.getSelectedRow();
            if (selectedRow != -1) {
                
                String idCliente = tableInscripciones.getValueAt(selectedRow, 1).toString();
                String idMembresia = tableInscripciones.getValueAt(selectedRow, 2).toString();
                String fechaInicio = tableInscripciones.getValueAt(selectedRow, 3).toString();
                String fechaFin = tableInscripciones.getValueAt(selectedRow, 4).toString();

                
                JDialog dialog = new JDialog();
                dialog.setTitle("Detalles de Inscripción");
                dialog.setSize(300, 250);
                dialog.setLocationRelativeTo(null);

                JPanel panel = new JPanel();
                dialog.getContentPane().add(panel);
                panel.setLayout(null);

                
                JLabel lblClienteDetalle = new JLabel("ID Cliente: " + idCliente);
                lblClienteDetalle.setBounds(20, 20, 250, 20);
                panel.add(lblClienteDetalle);

                JLabel lblMembresiaDetalle = new JLabel("ID Membresía: " + idMembresia);
                lblMembresiaDetalle.setBounds(20, 60, 250, 20);
                panel.add(lblMembresiaDetalle);

                JLabel lblFechaInicioDetalle = new JLabel("Fecha Inicio: " + fechaInicio);
                lblFechaInicioDetalle.setBounds(20, 100, 250, 20);
                panel.add(lblFechaInicioDetalle);

                JLabel lblFechaFinDetalle = new JLabel("Fecha Fin: " + fechaFin);
                lblFechaFinDetalle.setBounds(20, 140, 250, 20);
                panel.add(lblFechaFinDetalle);

                
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Por favor selecciona una inscripción de la tabla.");
            }
        });
        btnVisualizar.setBounds(396, 42, 105, 30);
        contentPane.add(btnVisualizar);

        
        tableInscripciones = new JTable();
        tableInscripciones.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] {"ID", "ID Cliente", "ID Membresía", "Fecha Inicio", "Fecha Fin"}
        ));
        JScrollPane scrollPane = new JScrollPane(tableInscripciones);
        scrollPane.setBounds(20, 190, 540, 150);
        contentPane.add(scrollPane);

        
        tableInscripciones.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableInscripciones.rowAtPoint(e.getPoint());
                if (row >= 0) {
                    
                    textFieldIdCliente.setText(tableInscripciones.getValueAt(row, 1).toString());
                    textFieldIdMembresia.setText(tableInscripciones.getValueAt(row, 2).toString());
                    textFieldFechaInicio.setText(tableInscripciones.getValueAt(row, 3).toString());
                    textFieldFechaFin.setText(tableInscripciones.getValueAt(row, 4).toString());
                }
            }
        });

        
        cargarInscripciones();
    }

    private void cargarInscripciones() {
        DefaultTableModel model = (DefaultTableModel) tableInscripciones.getModel();
        model.setRowCount(0); 

        try (Connection con = ConnectDatabase.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Inscripciones")) {

            while (rs.next()) {
                int idInscripcion = rs.getInt("id_inscripcion");
                int idCliente = rs.getInt("id_cliente");
                int idMembresia = rs.getInt("id_membresia");
                Date fechaInicio = rs.getDate("fecha_inicio");
                Date fechaFin = rs.getDate("fecha_fin");

                model.addRow(new Object[] {idInscripcion, idCliente, idMembresia, fechaInicio, fechaFin});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
