package ar.edu.ungs.prog2.ticketek;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// Representa un espectáculo (obra, recital, etc.)
public class Espectaculo {
    private final String nombre;
    // Mapa de funciones por fecha
    private final Map<LocalDate, Funcion> funciones = new HashMap<>();

    public Espectaculo(String nombre) {
        this.nombre = nombre;
    }

    public String nombre() { return nombre; }

    // Devuelve la función en una fecha dada
    public Funcion funcionEnFecha(LocalDate fecha) {
        return funciones.get(fecha);
    }

    // Devuelve la colección  de funciones (una copia)
    public Collection<Funcion> todasLasFunciones() {
        return new ArrayList<>(funciones.values());
    }
    // Agrega una función al espectáculo en una fecha específica
    public void agregarFuncion(LocalDate fecha, Funcion funcion) {
        funciones.put(fecha, funcion);
    }

    // Calcula la recaudación total del espectáculo
    public double calcularRecaudacionTotal() {
        double total = 0;
        for (Funcion f : funciones.values()) {
            total += f.recaudacion();
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

    // Valida los datos para crear un espectáculo
    public static void validarDatos(String nombre) {
        if (nombre == null || nombre.isEmpty()) throw new RuntimeException("Nombre inválido para el espectáculo");
    }

    // Valida que no haya una función repetida en la misma fecha
    public void validarFuncionNoRepetida(LocalDate fecha) {
        if (funciones.containsKey(fecha))throw new RuntimeException("Ya existe una función para esa fecha en el espectáculo");
    }
    // Dos espectáculos son iguales si tienen el mismo nombre (ignorando mayúsculas)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Espectaculo that = (Espectaculo) o;
        return nombre.equalsIgnoreCase(that.nombre);
    }

    @Override
    public int hashCode() {
        return nombre.toLowerCase().hashCode();
    }
}