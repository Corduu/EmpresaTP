package ar.edu.ungs.prog2.ticketek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Espectaculo {
    private String nombre;
    private Map<LocalDate, Funcion> funciones = new HashMap<>();
    private double totalRecaudado = 0;
    private String codigo = ""; // Si lo necesitas

    public Espectaculo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { 
        return nombre;
    }
    public String getCodigoEspectaculo() { 
        return codigo; 
    }
    public Map<LocalDate, Funcion> getFunciones() { 
        return funciones; 
    }



    public void agregarFuncion(LocalDate fecha, Funcion funcion) {
        funciones.put(fecha, funcion);
    }

    public double calcularRecaudacionTotal() {
        double total = 0;
        for (Funcion f : funciones.values()) {
            total += f.getRecaudacion();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Espectaculo{" +
                "nombre='" + nombre + '\'' +
                ", funciones=" + funciones.keySet() +
                ", totalRecaudado=" + calcularRecaudacionTotal() +
                '}';
    }
}