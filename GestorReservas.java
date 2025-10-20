import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GestorReservas {
    private List<Aula> aulas; 
    private List<Reserva> reservas; 
    private static final String ARCHIVO_RESERVAS = "reservas.csv";

    public GestorReservas() {
        this.aulas = new ArrayList<>();
        this.reservas = new ArrayList<>();
        cargarDatos(); 
    }
    
    // Gestión de Aulas
    public void registrarAula(Aula aula) {
        // Validación de duplicados
        if (aulas.stream().anyMatch(a -> a.getCodigo().equalsIgnoreCase(aula.getCodigo()))) {
            System.out.println("Error: El código de aula ya existe.");
            return;
        }
        aulas.add(aula);
    }

    public List<Aula> listarAulas() {
        return new ArrayList<>(aulas); 
    }
    
    // Registro de Reservas 
    public void registrarReserva(Reserva nuevaReserva) throws ReservaInvalidaException {
        
        nuevaReserva.validarGeneral();
        nuevaReserva.validarReglasEspecificas(nuevaReserva.getInicio(), nuevaReserva.getFin());

        // Validación Horario
        boolean conflicto = reservas.stream()
            .filter(r -> r.getAula().equals(nuevaReserva.getAula())) 
            .filter(r -> r.getEstado() == EstadoReserva.ACTIVA) 
            .anyMatch(r -> 
                nuevaReserva.getInicio().isBefore(r.getFin()) && 
                nuevaReserva.getFin().isAfter(r.getInicio()));

        if (conflicto) {
            throw new ReservaInvalidaException("Conflicto de horario: El aula ya está reservada en ese lapso.");
        }

        // 3. Registro
        reservas.add(nuevaReserva);
        guardarReservas(); 
        System.out.println("Reserva registrada con ID: " + nuevaReserva.getId());
    }
    
    // Búsqueda, Modificación, Cancelación 
    public Reserva buscarReservaPorId(int id) {
        return reservas.stream()
            .filter(r -> r.getId() == id)
            .findFirst()
            .orElse(null);
    }
    
    public void cancelarReserva(int id) throws ReservaInvalidaException {
        Reserva reserva = buscarReservaPorId(id);
        if (reserva == null) {
            throw new ReservaInvalidaException("Reserva no encontrada.");
        }
        if (reserva.getEstado() != EstadoReserva.ACTIVA) {
            throw new ReservaInvalidaException("La reserva ya está " + reserva.getEstado());
        }
        reserva.setEstado(EstadoReserva.CANCELADA);
        guardarReservas();
        System.out.println("Reserva ID " + id + " cancelada exitosamente.");
    }
    
    public List<Reserva> buscarReservasPorResponsable(String texto) {
        
        return reservas.stream()
            .filter(r -> r.getResponsable().toLowerCase().contains(texto.toLowerCase()))
            .collect(Collectors.toList());
    }
    
    // Reportes 
    
    // Reporte: Top 3 Aulas con más horas reservadas
    public Map<Aula, Long> top3AulasMasHoras() {
        return reservas.stream()
            .filter(r -> r.getEstado() == EstadoReserva.ACTIVA)
            .collect(Collectors.groupingBy(
                Reserva::getAula,
                Collectors.summingLong(Reserva::getHorasReservadas)
            ))
            .entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(3)
            .collect(Collectors.toMap(
                Map.Entry::getKey, 
                Map.Entry::getValue, 
                (e1, e2) -> e1, 
                java.util.LinkedHashMap::new 
            ));
    }
    
    // Reporte: Ocupación por Tipo de Aula
    public Map<TipoAula, Long> ocupacionPorTipoAula() {
        return reservas.stream()
            .filter(r -> r.getEstado() == EstadoReserva.ACTIVA)
            .collect(Collectors.groupingBy(
                r -> r.getAula().getTipo(),
                Collectors.summingLong(Reserva::getHorasReservadas)
            ));
    }
    
    // Persistencia y Exportación
    
    // Método para guardar Reservas en CSV/TXT
    private void guardarReservas() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_RESERVAS))) {
            
            for (Reserva r : reservas) {
                pw.println(r.getId() + "," + 
                           r.getClass().getSimpleName() + "," + 
                           r.getAula().getCodigo() + "," + 
                           r.getInicio() + "," + 
                           r.getFin() + "," + 
                           r.getResponsable() + "," + 
                           r.getEstado());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar reservas: " + e.getMessage());
        }
    }
    
    // Método para cargar datos (agregados manualmente)
    private void cargarDatos() {
        aulas.add(new Aula("A101", "Lab 1", TipoAula.LABORATORIO, 30));
        aulas.add(new Aula("T201", "Aula Teorica A", TipoAula.TEORICA, 50));
    }
    
    
    // Ordenamiento 
    public List<Reserva> ordenarReservas(Comparator<Reserva> comparador) {
        return reservas.stream()
            .sorted(comparador)
            .collect(Collectors.toList());
    }
}