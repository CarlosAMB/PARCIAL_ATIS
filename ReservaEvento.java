import java.time.LocalDateTime;

public class ReservaEvento extends Reserva {
    private TipoEvento tipoEvento;

    public ReservaEvento(Aula aula, LocalDateTime inicio, LocalDateTime fin, String responsable, TipoEvento tipoEvento) {
        super(aula, inicio, fin, responsable);
        this.tipoEvento = tipoEvento;
    }
    
    @Override
    public void validarReglasEspecificas(LocalDateTime inicio, LocalDateTime fin) throws ReservaInvalidaException {
        if (getAula().getTipo() == TipoAula.TEORICA) {
            throw new ReservaInvalidaException("Los eventos deben ser en Auditorios o Laboratorios.");
        }
    }
}