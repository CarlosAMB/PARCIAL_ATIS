import java.time.LocalDateTime;

public interface Validable {
    /**
     * Valida si la reserva cumple con sus reglas específicas.
     * @param fechaHoraInicio Fecha y hora de inicio de la reserva.
     * @param fechaHoraFin Fecha y hora de fin de la reserva.
     * @throws ReservaInvalidaException Si alguna regla no se cumple.
     */
    void validarReglasEspecificas(LocalDateTime fechaHoraInicio, LocalDateTime fechaHoraFin) throws ReservaInvalidaException;
}