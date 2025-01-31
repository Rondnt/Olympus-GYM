package gym;

import java.sql.Date;
import java.sql.Time;

public class Asistencia {
    private int idAsistencia;
    private int idCliente;
    private Date fechaAsistencia;
    private Time horaEntrada;

    public Asistencia(int idAsistencia, int idCliente, Date fechaAsistencia, Time horaEntrada) {
        this.idAsistencia = idAsistencia;
        this.idCliente = idCliente;
        this.fechaAsistencia = fechaAsistencia;
        this.horaEntrada = horaEntrada;
    }

    public int getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFechaAsistencia() {
        return fechaAsistencia;
    }

    public void setFechaAsistencia(Date fechaAsistencia) {
        this.fechaAsistencia = fechaAsistencia;
    }

    public Time getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Time horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
}
