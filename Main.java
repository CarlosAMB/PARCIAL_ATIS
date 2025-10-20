import java.util.Comparator;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Main {
    private static GestorReservas gestor = new GestorReservas();
    private static Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void main(String[] args) {
        System.out.println(">>> GESTOR DE RESERVAS DE AULAS ITCA <<<");
        menuPrincipal(); 
    }

    // --- MÉTODOS DE LÓGICA DE MENÚ ---

    public static void menuPrincipal() {
        int opcion;
        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("1. Gestión de Aulas");
            System.out.println("2. Gestión de Reservas (Registro, Búsqueda, Modificación)");
            System.out.println("3. Reportes y Estadísticas");
            System.out.println("0. Salir y Guardar Datos");

            try {
                System.out.print("Seleccione una opción: ");
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        menuGestionAulas();
                        break;
                    case 2:
                        menuGestionReservas();
                        break;
                    case 3:
                        menuReportes();
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema.");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Entrada inválida. Ingrese un número.");
                opcion = -1; 
            }
        } while (opcion != 0);
    }

    public static void menuGestionAulas() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE AULAS ---");
            System.out.println("1. Registrar Aula");
            System.out.println("2. Listar Aulas");
            System.out.println("3. Modificar Aula");
            System.out.println("0. Volver al Menú Principal");

            try {
                System.out.print("Seleccione una opción: ");
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrarNuevaAula();
                        break;
                    case 2:
                        gestor.listarAulas().forEach(System.out::println);
                        break;
                    case 3:
                        System.out.println("Modificado...");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Entrada inválida. Ingrese un número.");
                opcion = -1;
            }
        } while (opcion != 0);
    }

    public static void menuGestionReservas() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE RESERVAS ---");
            System.out.println("1. Registrar Nueva Reserva (Clase, Evento, Práctica)");
            System.out.println("2. Buscar Reserva por ID");
            System.out.println("3. Buscar Reservas por Responsable (Texto)");
            System.out.println("4. Cancelar Reserva");
            System.out.println("5. Listar Reservas (con ordenamiento configurable)");
            System.out.println("0. Volver al Menú Principal");

            try {
                System.out.print("Seleccione una opción: ");
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        registrarNuevaReserva();
                        break;
                    case 2:
                        buscarReservaPorIdMenu();
                        break;
                    case 3:
                        buscarReservasPorResponsableMenu();
                        break;
                    case 4:
                        cancelarReservaMenu();
                        break;
                    case 5:
                        listarReservasOrdenadasMenu();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Entrada inválida. Ingrese un número.");
                opcion = -1;
            }
        } while (opcion != 0);
    }

    public static void menuReportes() {
        int opcion;
        do {
            System.out.println("\n--- REPORTES ---");
            System.out.println("1. Top 3 Aulas con más Horas Reservadas");
            System.out.println("2. Ocupación por Tipo de Aula");
            System.out.println("3. Distribución por Tipo de Reserva");
            System.out.println("4. Exportar Reporte ");
            System.out.println("0. Volver al Menú Principal");

            try {
                System.out.print("Seleccione una opción: ");
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        gestor.top3AulasMasHoras().forEach((aula, horas) -> System.out
                                .println(" - " + aula.getNombre() + ": " + horas + " horas"));
                        break;
                    case 2:
                        gestor.ocupacionPorTipoAula()
                                .forEach((tipo, horas) -> System.out.println(" - " + tipo + ": " + horas + " horas"));
                        break;
                    case 3:
                        System.out.println("Reporte de Distribución por Tipo de Reserva pendiente.");
                        break;
                    case 4:
                        System.out.println("Exportación de reportes a archivo de texto pendiente.");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Entrada inválida. Ingrese un número.");
                opcion = -1;
            }
        } while (opcion != 0);
    }

    private static void registrarNuevaAula() {
        try {
            System.out.print("Código del Aula: ");
            String codigo = scanner.nextLine().trim();
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Tipo (TEORICA, LABORATORIO, AUDITORIO): ");
            TipoAula tipo = TipoAula.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Capacidad: ");
            int capacidad = Integer.parseInt(scanner.nextLine());

            gestor.registrarAula(new Aula(codigo, nombre, tipo, capacidad));
            System.out.println("Aula registrada correctamente.");
        } catch (IllegalArgumentException e) {
            System.err.println("Error: El tipo de aula o la capacidad son inválidos.");
        }
    }

    private static void buscarReservaPorIdMenu() {
        try {
            System.out.print("Ingrese ID de la reserva a buscar: ");
            int id = Integer.parseInt(scanner.nextLine());
            Reserva r = gestor.buscarReservaPorId(id);
            if (r != null) {
                System.out.println("Reserva encontrada: " + r.toString());
            } else {
                System.out.println("Reserva no encontrada.");
            }
        } catch (NumberFormatException e) {
            System.err.println("El ID debe ser un número entero.");
        }
    }

    private static void cancelarReservaMenu() {
        try {
            System.out.print("Ingrese ID de la reserva a cancelar: ");
            int id = Integer.parseInt(scanner.nextLine());
            gestor.cancelarReserva(id);
        } catch (NumberFormatException e) {
            System.err.println("El ID debe ser un número entero.");
        } catch (ReservaInvalidaException e) {
            System.err.println("Error al cancelar: " + e.getMessage());
        }
    }

    private static void buscarReservasPorResponsableMenu() {
        System.out.print("Ingrese texto a buscar en el responsable: ");
        String texto = scanner.nextLine();

        System.out.println("\n--- RESULTADOS ---");
        gestor.buscarReservasPorResponsable(texto).forEach(r -> {
            System.out.println("ID " + r.getId() + " - Aula: " + r.getAula().getCodigo() +
                    " - Inicio: " + r.getInicio().format(DATE_TIME_FORMAT) +
                    " - Responsable: " + r.getResponsable());
        });
    }

    private static void listarReservasOrdenadasMenu() {
        System.out.println("Ordenar por: 1. ID (Asc), 2. Responsable (Asc), 3. Inicio (Asc)");
        try {
            int opc = Integer.parseInt(scanner.nextLine());
            Comparator<Reserva> comparador = null;

            switch (opc) {
                case 1:
                    comparador = Comparator.comparing(Reserva::getId);
                    break;
                case 2:
                    comparador = Comparator.comparing(Reserva::getResponsable);
                    break;
                case 3:
                    comparador = Comparator.comparing(Reserva::getInicio);
                    break;
                default:
                    System.out.println("Opción no válida. Listando sin orden.");
                    return;
            }

            gestor.ordenarReservas(comparador).forEach(System.out::println);

        } catch (NumberFormatException e) {
            System.err.println("Entrada inválida. Ingrese un número.");
        }
    }

    public static void registrarNuevaReserva() {
        System.out.println("--- REGISTRO DE RESERVA ---");
        try {
            System.out.print("Ingrese ID del Aula: ");
            String codigoAula = scanner.nextLine().trim();
            Aula aula = gestor.listarAulas().stream()
                    .filter(a -> a.getCodigo().equalsIgnoreCase(codigoAula))
                    .findFirst().orElse(null);

            if (aula == null) {
                System.out.println("Aula no encontrada.");
                return;
            }

            LocalDateTime inicio = leerFechaHora("Fecha y Hora de Inicio (yyyy-MM-dd HH:mm): ");
            LocalDateTime fin = leerFechaHora("Fecha y Hora de Fin (yyyy-MM-dd HH:mm): ");
            System.out.print("Responsable: ");
            String responsable = scanner.nextLine();

            System.out.println("Tipo de Reserva: 1. Clase, 2. Evento, 3. Práctica");
            int tipo = Integer.parseInt(scanner.nextLine());

            Reserva nuevaReserva = null;
            switch (tipo) {
                case 1:
                    System.out.print("Materia: ");
                    String materia = scanner.nextLine();
                    nuevaReserva = new ReservaClase(aula, inicio, fin, responsable, materia);
                    break;
                case 2:
                    System.out.print("Tipo de Evento (CONFERENCIA, TALLER, REUNION): ");
                    TipoEvento tipoE = TipoEvento.valueOf(scanner.nextLine().toUpperCase());
                    nuevaReserva = new ReservaEvento(aula, inicio, fin, responsable, tipoE);
                    break;
                case 3:
                    nuevaReserva = new ReservaPractica(aula, inicio, fin, responsable);
                    break;
                default:
                    System.out.println("Opción de tipo de reserva inválida.");
                    return;
            }

            gestor.registrarReserva(nuevaReserva);

        } catch (DateTimeParseException e) {
            System.err.println("Error de formato de fecha/hora. Use yyyy-MM-dd HH:mm.");
        } catch (NumberFormatException e) {
            System.err.println("Error de formato numérico.");
        } catch (ReservaInvalidaException e) {
            System.err.println("Error de Validación: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ocurrió un error inesperado: " + e.getMessage());
        }
    }

    private static LocalDateTime leerFechaHora(String prompt) throws DateTimeParseException {
        while (true) {
            System.out.print(prompt);
            String fechaHoraStr = scanner.nextLine().trim();
            try {
                return LocalDateTime.parse(fechaHoraStr, DATE_TIME_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Formato incorrecto. Intente de nuevo (yyyy-MM-dd HH:mm).");
            }
        }
    }
}