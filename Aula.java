import java.io.Serializable;

public class Aula implements Serializable {
    private String codigo;
    private String nombre;
    private TipoAula tipo;
    private int capacidad;
    
    // Constructor
    public Aula(String codigo, String nombre, TipoAula tipo, int capacidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.capacidad = capacidad;
    }
    
    // Getters y Setters (Encapsulamiento)
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public TipoAula getTipo() { return tipo; }
    public String getNombre() {return nombre;}
    
    @Override
    public String toString() {
        return "Aula{" + "codigo='" + codigo + '\'' + ", nombre='" + nombre + '\'' + ", tipo=" + tipo + ", capacidad=" + capacidad + '}';
    }
}