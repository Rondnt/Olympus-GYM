package gym;

import java.sql.*;

import javax.swing.JOptionPane;

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
            JOptionPane.showMessageDialog(null, "Cliente agregado exitosamente", "Exitoso",JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Cliente agregado exitosamente.");
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
            JOptionPane.showMessageDialog(null, "Cliente editado exitosamente", "Exitoso",JOptionPane.PLAIN_MESSAGE);
            System.out.println("Cliente editado exitosamente.");
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
            JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente", "Exitoso",JOptionPane.WARNING_MESSAGE);
            System.out.println("Cliente eliminado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
