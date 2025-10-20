import java.time.LocalDateTime;

public class ReservaPractica extends Reserva {
    
    public ReservaPractica(Aula aula, LocalDateTime inicio, LocalDateTime fin, String responsable) {
        super(aula, inicio, fin, responsable);
    }

    @Override
    public void validarReglasEspecificas(LocalDateTime inicio, LocalDateTime fin) throws ReservaInvalidaException {
        if (getAula().getTipo() != TipoAula.LABORATORIO) {
            throw new ReservaInvalidaException("Las prácticas solo se permiten en Laboratorios.");
        }
        if (getHorasReservadas() < 1) {
             throw new ReservaInvalidaException("Las prácticas deben durar al menos 1 hora.");
        }
    }
}