package gym;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.SystemColor;

public class MembresiasIF extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldNombre;
    private JTextField textFieldPrecio;
    private JTextField textFieldDuracion;
    private JTable tableMembresias;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MembresiasIF frame = new MembresiasIF();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MembresiasIF() {
    	setTitle("Gestión de Membresias");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 30, 80, 20);
        contentPane.add(lblNombre);

        textFieldNombre = new JTextField();
        textFieldNombre.setBounds(108, 31, 150, 20);
        contentPane.add(textFieldNombre);
        textFieldNombre.setColumns(10);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(20, 70, 80, 20);
        contentPane.add(lblPrecio);

        textFieldPrecio = new JTextField();
        textFieldPrecio.setBounds(108, 71, 150, 20);
        contentPane.add(textFieldPrecio);
        textFieldPrecio.setColumns(10);

        JLabel lblDuracion = new JLabel("Duración (días):");
        lblDuracion.setBounds(20, 110, 110, 20);
        contentPane.add(lblDuracion);

        textFieldDuracion = new JTextField();
        textFieldDuracion.setBounds(148, 111, 110, 20);
        contentPane.add(textFieldDuracion);
        textFieldDuracion.setColumns(10);

        JButton btnCrear = new JButton("Crear ");
        btnCrear.setForeground(UIManager.getColor("CheckBox.highlight"));
        btnCrear.setBackground(UIManager.getColor("CheckBox.highlight"));
        btnCrear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = textFieldNombre.getText();
                String precio = textFieldPrecio.getText();
                String duracion = textFieldDuracion.getText();

                if (nombre.isEmpty() || precio.isEmpty() || duracion.isEmpty()) {
                    System.out.println("Por favor, complete todos los campos.");
                    return;
                }

                Membresia nuevaMembresia = new Membresia(nombre, Double.parseDouble(precio), Integer.parseInt(duracion));
                MembresiaDAO.crearMembresiaDB(nuevaMembresia);
                cargarMembresias();
            }
        });
        btnCrear.setBounds(300, 30, 120, 30);
        contentPane.add(btnCrear);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(UIManager.getColor("CheckBox.highlight"));
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tableMembresias.getSelectedRow();
                if (row >= 0) {
                    int id = (int) tableMembresias.getValueAt(row, 0);
                    MembresiaDAO.eliminarMembresia(id);
                    cargarMembresias();
                }
            }
        });
        btnEliminar.setBounds(428, 65, 120, 30);
        contentPane.add(btnEliminar);
        
        JButton btnEditar = new JButton("Guardar");
        btnEditar.setBackground(UIManager.getColor("CheckBox.highlight"));
        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tableMembresias.getSelectedRow();
                if (row >= 0) {
                    int id = (int) tableMembresias.getValueAt(row, 0);
                    String nombre = textFieldNombre.getText();
                    String precio = textFieldPrecio.getText();
                    String duracion = textFieldDuracion.getText();

                    if (nombre.isEmpty() || precio.isEmpty() || duracion.isEmpty()) {
                        System.out.println("Por favor, complete todos los campos.");
                        return;
                    }

                    Membresia membresiaEditada = new Membresia(nombre, Double.parseDouble(precio), Integer.parseInt(duracion));
                    MembresiaDAO.editarMembresiaDB(id, membresiaEditada);
                    cargarMembresias();
                } else {
                    System.out.println("Por favor, seleccione una membresía para editar.");
                }
            }
        });
        btnEditar.setBounds(300, 65, 120, 30);
        contentPane.add(btnEditar);


        JButton btnVisualizar = new JButton("Visualizar");
        btnVisualizar.setBackground(UIManager.getColor("CheckBox.highlight"));
        btnVisualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tableMembresias.getSelectedRow();
                if (row >= 0) {
                    String nombre = (String) tableMembresias.getValueAt(row, 1);
                    double precio = (double) tableMembresias.getValueAt(row, 2);
                    int duracion = (int) tableMembresias.getValueAt(row, 3);

                    String mensaje = "Nombre: " + nombre + "\nPrecio: " + precio + "\nDuración: " + duracion + " días";
                    JOptionPane.showMessageDialog(null, mensaje, "Detalle de Membresía", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione una membresía para visualizar.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnVisualizar.setBounds(428, 31, 120, 30);
        contentPane.add(btnVisualizar);

        // Tabla de membresías
        tableMembresias = new JTable();
        JScrollPane scrollPane = new JScrollPane(tableMembresias);
        scrollPane.setBounds(20, 150, 540, 200);
        contentPane.add(scrollPane);

        cargarMembresias();
    }

    public void cargarMembresias() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Precio");
        model.addColumn("Duración");

        String query = "SELECT * FROM Membresias";
        try (Connection con = ConnectDatabase.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] row = new Object[4];
                row[0] = rs.getInt("id_membresia");
                row[1] = rs.getString("nombre");
                row[2] = rs.getDouble("precio");
                row[3] = rs.getInt("duracion");
                model.addRow(row);
            }

            tableMembresias.setModel(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
