package gym;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class AsistenciasIF extends JFrame {
    private JPanel contentPane;
    private JTextField textFieldIdCliente;
    private JFormattedTextField textFieldFechaAsistencia;
    private JFormattedTextField textFieldHoraEntrada;
    private JTable tableAsistencias;
    private int selectedAsistenciaId = -1; //almacenar el ID de la asistencia seleccionada

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AsistenciasIF frame = new AsistenciasIF();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AsistenciasIF() {
    	setTitle("Gestión de Asistencias");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 450);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblIdCliente = new JLabel("ID Cliente:");
        lblIdCliente.setBounds(20, 30, 80, 20);
        contentPane.add(lblIdCliente);

        textFieldIdCliente = new JTextField();
        textFieldIdCliente.setBounds(134, 31, 130, 20);
        contentPane.add(textFieldIdCliente);

        JLabel lblFechaAsistencia = new JLabel("Fecha Asistencia:");
        lblFechaAsistencia.setBounds(20, 70, 120, 20);
        contentPane.add(lblFechaAsistencia);

        try {
            MaskFormatter dateFormatter = new MaskFormatter("####-##-##");
            textFieldFechaAsistencia = new JFormattedTextField(dateFormatter);
            textFieldFechaAsistencia.setBounds(134, 71, 130, 20);
            contentPane.add(textFieldFechaAsistencia);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel lblHoraEntrada = new JLabel("Hora Entrada:");
        lblHoraEntrada.setBounds(20, 110, 100, 20);
        contentPane.add(lblHoraEntrada);

        try {
            MaskFormatter timeFormatter = new MaskFormatter("##:##");
            textFieldHoraEntrada = new JFormattedTextField(timeFormatter);
            textFieldHoraEntrada.setBounds(134, 111, 130, 20);
            contentPane.add(textFieldHoraEntrada);
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        JButton btnCrear = new JButton("Crear");
        btnCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String idCliente = textFieldIdCliente.getText();
                String fechaAsistencia = textFieldFechaAsistencia.getText();
                String horaEntrada = textFieldHoraEntrada.getText();

                if (idCliente.isEmpty() || fechaAsistencia.isEmpty() || horaEntrada.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                Asistencia asistencia = new Asistencia(
                        0, 
                        Integer.parseInt(idCliente),
                        Date.valueOf(fechaAsistencia),
                        Time.valueOf(horaEntrada + ":00")
                );
                AsistenciaDAO.crearAsistencia(asistencia);
                cargarAsistencias();
            }
        });
        btnCrear.setBounds(300, 30, 106, 30);
        contentPane.add(btnCrear);

        
        JButton btnEditar = new JButton("Guardar");
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedAsistenciaId == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una asistencia.");
                    return;
                }

                String idCliente = textFieldIdCliente.getText();
                String fechaAsistencia = textFieldFechaAsistencia.getText();
                String horaEntrada = textFieldHoraEntrada.getText();

                if (idCliente.isEmpty() || fechaAsistencia.isEmpty() || horaEntrada.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
                    return;
                }

                Asistencia asistencia = new Asistencia(
                        selectedAsistenciaId,
                        Integer.parseInt(idCliente),
                        Date.valueOf(fechaAsistencia),
                        Time.valueOf(horaEntrada + ":00")
                );
                AsistenciaDAO.editarAsistencia(asistencia);
                cargarAsistencias();
            }
        });
        btnEditar.setBounds(300, 70, 106, 30);
        contentPane.add(btnEditar);

        
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedAsistenciaId == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una asistencia.");
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar esta asistencia?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    AsistenciaDAO.eliminarAsistencia(selectedAsistenciaId);
                    cargarAsistencias();
                }
            }
        });
        btnEliminar.setBounds(419, 70, 106, 30);
        contentPane.add(btnEliminar);
        
     
        JButton btnVisualizar = new JButton("Visualizar");
        btnVisualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedAsistenciaId == -1) {
                    JOptionPane.showMessageDialog(null, "Por favor, seleccione una asistencia.");
                    return;
                }

                
                try (Connection con = ConnectDatabase.getConnection()) {
                    String query = "SELECT * FROM Asistencias WHERE id_asistencia = ?";
                    try (PreparedStatement stmt = con.prepareStatement(query)) {
                        stmt.setInt(1, selectedAsistenciaId);
                        ResultSet rs = stmt.executeQuery();

                        if (rs.next()) {
                            String idCliente = rs.getString("id_cliente");
                            String fechaAsistencia = rs.getString("fecha_asistencia");
                            String horaEntrada = rs.getString("hora_entrada");

                            
                            JDialog dialog = new JDialog();
                            dialog.setTitle("Detalles de Asistencia");
                            dialog.setSize(300, 250);
                            dialog.setLocationRelativeTo(null);

                            JPanel panel = new JPanel();
                            dialog.getContentPane().add(panel);
                            panel.setLayout(null);

                            JLabel lblCliente = new JLabel("ID Cliente: " + idCliente);
                            lblCliente.setBounds(20, 20, 250, 20);
                            panel.add(lblCliente);

                            JLabel lblFechaAsistencia = new JLabel("Fecha Asistencia: " + fechaAsistencia);
                            lblFechaAsistencia.setBounds(20, 60, 250, 20);
                            panel.add(lblFechaAsistencia);

                            JLabel lblHoraEntrada = new JLabel("Hora Entrada: " + horaEntrada);
                            lblHoraEntrada.setBounds(20, 100, 250, 20);
                            panel.add(lblHoraEntrada);

                            
                            dialog.setVisible(true);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnVisualizar.setBounds(419, 30, 106, 30);
        contentPane.add(btnVisualizar);

        
        tableAsistencias = new JTable();
        tableAsistencias.setModel(new DefaultTableModel(
                new Object[][] {},
                new String[] {"ID", "ID Cliente", "Fecha Asistencia", "Hora Entrada"}
        ));
        tableAsistencias.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tableAsistencias.getSelectedRow();
                selectedAsistenciaId = (int) tableAsistencias.getValueAt(row, 0);
                textFieldIdCliente.setText(tableAsistencias.getValueAt(row, 1).toString());
                textFieldFechaAsistencia.setText(tableAsistencias.getValueAt(row, 2).toString());
                textFieldHoraEntrada.setText(tableAsistencias.getValueAt(row, 3).toString());
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableAsistencias);
        scrollPane.setBounds(20, 150, 550, 200);
        contentPane.add(scrollPane);

        
        cargarAsistencias();
    }

    private void cargarAsistencias() {
        DefaultTableModel model = (DefaultTableModel) tableAsistencias.getModel();
        model.setRowCount(0); 

        try (Connection con = ConnectDatabase.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Asistencias")) {

            while (rs.next()) {
                int idAsistencia = rs.getInt("id_asistencia");
                int idCliente = rs.getInt("id_cliente");
                Date fechaAsistencia = rs.getDate("fecha_asistencia");
                Time horaEntrada = rs.getTime("hora_entrada");

                model.addRow(new Object[] {idAsistencia, idCliente, fechaAsistencia, horaEntrada});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
