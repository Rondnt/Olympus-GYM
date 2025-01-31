package gym;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class ClienteIF extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldNombre;
    private JTextField textFieldDni;
    private JTextField textFieldTelefono;
    private JTextField textFieldEmail;
    private JTable tableClientes;
    private int idSeleccionado; 

    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClienteIF frame = new ClienteIF();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
    public ClienteIF() {
        setTitle("Gestión de Clientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(30, 20, 80, 20);
        contentPane.add(lblNombre);

        textFieldNombre = new JTextField();
        textFieldNombre.setBounds(120, 20, 150, 20);
        contentPane.add(textFieldNombre);
        textFieldNombre.setColumns(10);

        JLabel lblDni = new JLabel("DNI:");
        lblDni.setBounds(30, 60, 80, 20);
        contentPane.add(lblDni);

        textFieldDni = new JTextField();
        textFieldDni.setBounds(120, 60, 150, 20);
        contentPane.add(textFieldDni);
        textFieldDni.setColumns(10);

        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setBounds(30, 100, 80, 20);
        contentPane.add(lblTelefono);

        textFieldTelefono = new JTextField();
        textFieldTelefono.setBounds(120, 100, 150, 20);
        contentPane.add(textFieldTelefono);
        textFieldTelefono.setColumns(10);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 140, 80, 20);
        contentPane.add(lblEmail);

        textFieldEmail = new JTextField();
        textFieldEmail.setBounds(120, 140, 150, 20);
        contentPane.add(textFieldEmail);
        textFieldEmail.setColumns(10);

        
        JButton btnAgregarCliente = new JButton("Agregar");
        btnAgregarCliente.setBounds(332, 35, 97, 30);
        contentPane.add(btnAgregarCliente);

        
        JButton btnEditarCliente = new JButton("Guardar");
        btnEditarCliente.setBounds(332, 75, 97, 30);
        contentPane.add(btnEditarCliente);

        
        JButton btnEliminarCliente = new JButton("Eliminar");
        btnEliminarCliente.setBounds(437, 75, 97, 30);
        contentPane.add(btnEliminarCliente);

        
        JButton btnVisualizarCliente = new JButton("Visualizar");
        btnVisualizarCliente.setBounds(437, 35, 97, 30);
        contentPane.add(btnVisualizarCliente);

        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 240, 500, 115);
        contentPane.add(scrollPane);

        tableClientes = new JTable();
        scrollPane.setViewportView(tableClientes);

        
        btnAgregarCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombre = textFieldNombre.getText();
                String dni = textFieldDni.getText();
                String telefono = textFieldTelefono.getText();
                String email = textFieldEmail.getText();

                
                Cliente cliente = new Cliente(nombre, dni, telefono, email);
                ClienteDAO.agregarCliente(cliente);
                mostrarClientes();
            }
        });

        
        btnEditarCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (idSeleccionado != -1) { 
                    String nombre = textFieldNombre.getText();
                    String dni = textFieldDni.getText();
                    String telefono = textFieldTelefono.getText();
                    String email = textFieldEmail.getText();
                    Cliente cliente = new Cliente(nombre, dni, telefono, email);
                    ClienteDAO.editarCliente(idSeleccionado, cliente);
                    mostrarClientes();
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un cliente para editar.");
                }
            }
        });

        
        btnEliminarCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (idSeleccionado != -1) {
                    ClienteDAO.eliminarCliente(idSeleccionado);
                    mostrarClientes();
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un cliente para eliminar.");
                }
            }
        });

        
        btnVisualizarCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (idSeleccionado != -1) {
                    visualizarCliente(idSeleccionado);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un cliente para visualizar.");
                }
            }
        });

        
        mostrarClientes();
    }

    
    private void mostrarClientes() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("DNI");
        model.addColumn("Teléfono");
        model.addColumn("Email");

        try (Connection con = ConnectDatabase.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Clientes")) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id_cliente"),
                    rs.getString("nombre"),
                    rs.getString("dni"),
                    rs.getString("telefono"),
                    rs.getString("email")
                });
            }
            tableClientes.setModel(model);

            //listener para cuando se seleccione un cliente en la tabla
            tableClientes.getSelectionModel().addListSelectionListener(e -> {
                int row = tableClientes.getSelectedRow();
                if (row != -1) {
                    idSeleccionado = (int) tableClientes.getValueAt(row, 0);
                    textFieldNombre.setText((String) tableClientes.getValueAt(row, 1));
                    textFieldDni.setText((String) tableClientes.getValueAt(row, 2));
                    textFieldTelefono.setText((String) tableClientes.getValueAt(row, 3));
                    textFieldEmail.setText((String) tableClientes.getValueAt(row, 4));
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    private void visualizarCliente(int id) {
        try (Connection con = ConnectDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement("SELECT * FROM Clientes WHERE id_cliente = ?")) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null,
                        "ID: " + rs.getInt("id_cliente") +
                                "\nNombre: " + rs.getString("nombre") +
                                "\nDNI: " + rs.getString("dni") +
                                "\nTeléfono: " + rs.getString("telefono") +
                                "\nEmail: " + rs.getString("email"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
