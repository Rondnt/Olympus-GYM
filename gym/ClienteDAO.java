package gym;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ClienteDAO {

    public static void agregarCliente(Cliente cliente) {
        String query = "INSERT INTO Clientes (nombre, dni, telefono, email) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getDni());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getEmail());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente agregado exitosamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editarCliente(int id, Cliente cliente) {
        String query = "UPDATE Clientes SET nombre = ?, dni = ?, telefono = ?, email = ? WHERE id_cliente = ?";
        try (Connection con = ConnectDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getDni());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getEmail());
            stmt.setInt(5, id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente editado exitosamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void eliminarCliente(int id) {
        String query = "DELETE FROM Clientes WHERE id_cliente = ?";
        try (Connection con = ConnectDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void mostrarClientes(JTable tableClientes) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void visualizarCliente(int id) {
        String query = "SELECT * FROM Clientes WHERE id_cliente = ?";
        try (Connection con = ConnectDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

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
