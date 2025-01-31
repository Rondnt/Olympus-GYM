package gym;

import java.sql.*;

import javax.swing.JOptionPane;

public class AsistenciaDAO {
    public static void crearAsistencia(Asistencia asistencia) {
        String query = "INSERT INTO Asistencias (id_cliente, fecha_asistencia, hora_entrada) VALUES (?, ?, ?)";

        try (Connection con = ConnectDatabase.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, asistencia.getIdCliente());
            pst.setDate(2, asistencia.getFechaAsistencia());
            pst.setTime(3, asistencia.getHoraEntrada());

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Asistencia creada exitosamente", "Exitoso",JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Asistencia creada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editarAsistencia(Asistencia asistencia) {
        String query = "UPDATE Asistencias SET id_cliente = ?, fecha_asistencia = ?, hora_entrada = ? WHERE id_asistencia = ?";

        try (Connection con = ConnectDatabase.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, asistencia.getIdCliente());
            pst.setDate(2, asistencia.getFechaAsistencia());
            pst.setTime(3, asistencia.getHoraEntrada());
            pst.setInt(4, asistencia.getIdAsistencia());

            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Asistencia actualizada exitosamente", "Exitoso",JOptionPane.PLAIN_MESSAGE);
            System.out.println("Asistencia actualizada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void eliminarAsistencia(int idAsistencia) {
        String query = "DELETE FROM Asistencias WHERE id_asistencia = ?";

        try (Connection con = ConnectDatabase.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, idAsistencia);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Asistencia eliminada exitosamente", "Exitoso",JOptionPane.WARNING_MESSAGE);
            System.out.println("Asistencia eliminada.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Asistencia obtenerAsistencia(int idAsistencia) {
        Asistencia asistencia = null;
        String query = "SELECT * FROM Asistencias WHERE id_asistencia = ?";

        try (Connection con = ConnectDatabase.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, idAsistencia);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int idCliente = rs.getInt("id_cliente");
                Date fechaAsistencia = rs.getDate("fecha_asistencia");
                Time horaEntrada = rs.getTime("hora_entrada");

                asistencia = new Asistencia(idAsistencia, idCliente, fechaAsistencia, horaEntrada);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asistencia;
    }
}
