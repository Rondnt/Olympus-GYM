package gym;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class MembresiaDAO {

    public static void crearMembresiaDB(Membresia membresia) {
        String query = "INSERT INTO Membresias (nombre, precio, duracion) VALUES (?, ?, ?)";

        try (Connection con = ConnectDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            
            stmt.setString(1, membresia.getNombre());
            stmt.setDouble(2, membresia.getPrecio());
            stmt.setInt(3, membresia.getDuracion());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Membresía creada exitosamente", "Exitoso",JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Membresía creada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void editarMembresiaDB(int id, Membresia membresia) {
        String query = "UPDATE Membresias SET nombre = ?, precio = ?, duracion = ? WHERE id_membresia = ?";

        try (Connection con = ConnectDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, membresia.getNombre());
            stmt.setDouble(2, membresia.getPrecio());
            stmt.setInt(3, membresia.getDuracion());
            stmt.setInt(4, id);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Membresía actualizada exitosamente", "Exitoso",JOptionPane.PLAIN_MESSAGE);
            System.out.println("Membresía actualizada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet obtenerMembresias() {
        String query = "SELECT * FROM Membresias";
        try {
            Connection con = ConnectDatabase.getConnection();
            Statement stmt = con.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    

    public static void eliminarMembresia(int id) {
        String query = "DELETE FROM Membresias WHERE id_membresia = ?";

        try (Connection con = ConnectDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Membresía eliminada", "Exitoso",JOptionPane.WARNING_MESSAGE);
            System.out.println("Membresía eliminada.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
