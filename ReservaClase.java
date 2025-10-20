import java.time.LocalDateTime;

public class ReservaClase extends Reserva {
    private String materia;

    public ReservaClase(Aula aula, LocalDateTime inicio, LocalDateTime fin, String responsable, String materia) {
        super(aula, inicio, fin, responsable);
        this.materia = materia;
    }

    @Override
    public void validarReglasEspecificas(LocalDateTime inicio, LocalDateTime fin) throws ReservaInvalidaException {
        if (getHorasReservadas() > 3) {
            throw new ReservaInvalidaException("Las reservas de clase no pueden durar más de 3 horas.");
        }
        if (getAula().getTipo() == TipoAula.AUDITORIO) {
            throw new ReservaInvalidaException("Las clases no pueden reservarse en Auditorios.");
        }
    }
}
