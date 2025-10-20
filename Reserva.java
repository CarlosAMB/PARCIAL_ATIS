import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.io.Serializable;

public abstract class Reserva implements Validable, Serializable {
    private static int contadorId = 0; 
    private int id;
    private Aula aula;
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private String responsable;
    private EstadoReserva estado;

    public Reserva(Aula aula, LocalDateTime inicio, LocalDateTime fin, String responsable) {
        this.id = ++contadorId;
        this.aula = aula;
        this.inicio = inicio;
        this.fin = fin;
        this.responsable = responsable;
        this.estado = EstadoReserva.ACTIVA;
    }

    // Validacion general
    public void validarGeneral() throws ReservaInvalidaException {
        if (inicio.isAfter(fin) || inicio.isEqual(fin)) {
            throw new ReservaInvalidaException("La hora de inicio debe ser anterior a la hora de fin.");
        }
        if (inicio.isBefore(LocalDateTime.now())) {
            throw new ReservaInvalidaException("No se pueden hacer reservas en el pasado.");
        }
    }
    
    @Override
    public abstract void validarReglasEspecificas(LocalDateTime inicio, LocalDateTime fin) throws ReservaInvalidaException;
    
    // Getters y Setters
    public int getId() { return id; }
    public Aula getAula() { return aula; }
    public LocalDateTime getInicio() { return inicio; }
    public LocalDateTime getFin() { return fin; }
    public long getHorasReservadas() {
        return ChronoUnit.HOURS.between(inicio, fin);
    }
    public String getResponsable() { return responsable; }
    public EstadoReserva getEstado() { return estado; }
    public void setEstado(EstadoReserva estado) { this.estado = estado; }
    
}