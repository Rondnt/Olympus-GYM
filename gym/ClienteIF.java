package gym;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

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
                ClienteDAO.mostrarClientes(tableClientes);
            }
        });
        
     
        tableClientes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tableClientes.getSelectedRow();
                if (filaSeleccionada != -1) {
                    idSeleccionado = Integer.parseInt(tableClientes.getValueAt(filaSeleccionada, 0).toString()); // Obtener el ID de la primera columna
                    textFieldNombre.setText(tableClientes.getValueAt(filaSeleccionada, 1).toString());
                    textFieldDni.setText(tableClientes.getValueAt(filaSeleccionada, 2).toString());
                    textFieldTelefono.setText(tableClientes.getValueAt(filaSeleccionada, 3).toString());
                    textFieldEmail.setText(tableClientes.getValueAt(filaSeleccionada, 4).toString());
                }
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
                    ClienteDAO.mostrarClientes(tableClientes);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un cliente para editar.");
                }
            }
        });

        btnEliminarCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (idSeleccionado != -1) {
                    ClienteDAO.eliminarCliente(idSeleccionado);
                    ClienteDAO.mostrarClientes(tableClientes);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un cliente para eliminar.");
                }
            }
        });

    
        btnVisualizarCliente.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (idSeleccionado != -1) {
                    ClienteDAO.visualizarCliente(idSeleccionado);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecciona un cliente para visualizar.");
                }
            }
        });

        ClienteDAO.mostrarClientes(tableClientes);
    }
}
