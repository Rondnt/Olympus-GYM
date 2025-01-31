package gym;

import java.sql.*;

import javax.swing.JOptionPane;

public class InscripcionDAO {

    
    public static void crearInscripcionDB(Inscripcion inscripcion) {
        String query = "INSERT INTO Inscripciones (id_cliente, id_membresia, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?)";

        try (Connection con = ConnectDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            
            stmt.setInt(1, inscripcion.getIdCliente());
            stmt.setInt(2, inscripcion.getIdMembresia());
            stmt.setDate(3, inscripcion.getFechaInicio());
            stmt.setDate(4, inscripcion.getFechaFin());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Inscripción creada exitosamente", "Exitoso",JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Inscripción creada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
    public static void eliminarInscripcion(int id) {
        String query = "DELETE FROM Inscripciones WHERE id_inscripcion = ?";

        try (Connection con = ConnectDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Inscripción eliminada exitosamente", "Exitoso",JOptionPane.WARNING_MESSAGE);
            System.out.println("Inscripción eliminada.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editarInscripcion(Inscripcion inscripcion) {
        String sql = "UPDATE Inscripciones SET id_cliente = ?, id_membresia = ?, fecha_inicio = ?, fecha_fin = ? WHERE id_inscripcion = ?";
        try (Connection con = ConnectDatabase.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, inscripcion.getIdCliente());
            pstmt.setInt(2, inscripcion.getIdMembresia());
            pstmt.setDate(3, inscripcion.getFechaInicio());
            pstmt.setDate(4, inscripcion.getFechaFin());
            pstmt.setInt(5, inscripcion.getIdInscripcion());

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Inscripción actualizada exitosamente", "Exitoso",JOptionPane.PLAIN_MESSAGE);
            System.out.println("Inscripción actualizada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
    public static Inscripcion obtenerInscripcion(int id) {
        String query = "SELECT * FROM Inscripciones WHERE id_inscripcion = ?";
        Inscripcion inscripcion = null;

        try (Connection con = ConnectDatabase.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                inscripcion = new Inscripcion(
                        rs.getInt("id_inscripcion"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_membresia"),
                        rs.getDate("fecha_inicio"),
                        rs.getDate("fecha_fin")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inscripcion;
    }
}
