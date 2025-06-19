package ar.edu.ungs.prog2.ticketek;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

// Clase abstracta base para todas las sedes (Estadio, Teatro, MiniEstadio)
public abstract class Sede {
    protected int capacidadMaxima;
    protected String nombreSede;
    protected String direccion;
    // Mapa de funciones por fecha (para saber si una fecha está ocupada)
    protected Map<LocalDate, String> funcionesPorFecha = new HashMap<>();

    public Sede(String nombreSede, String direccion, int capacidadMaxima) {
        this.nombreSede = nombreSede;
        this.direccion = direccion;
        this.capacidadMaxima = capacidadMaxima;
    }

    public String nombreSede() { return nombreSede; }
    public String direccion() { return direccion; }
    public int capacidadMaxima() { return capacidadMaxima; }

    // Métodos abstractos que deben implementar las subclases
    public abstract String descripcionParaListado(Funcion funcion, String fechaStr);
    public abstract double calcularPrecioEntrada(String sector, double precioBase);

    // Devuelve true si la fecha ya está ocupada en la sede
    public boolean fechaOcupada(LocalDate fecha) {
        return funcionesPorFecha.containsKey(fecha);
    }
    
    // Agrega una función a la sede
    public void agregarFuncion(LocalDate fecha, String nombreEspectaculo) {
        funcionesPorFecha.put(fecha, nombreEspectaculo);
    }

    // Solo validación basica, lo demás lo puse en las subclases
    public static void validarDatos(String nombreSede, String direccion, int capacidadMaxima) {
        if (nombreSede == null || nombreSede.isEmpty()) throw new RuntimeException("El nombre de la sede no puede ser nulo o vacío");
        if (direccion == null || direccion.isEmpty()) throw new RuntimeException("La dirección de la sede no puede ser nula o vacía");
        if (capacidadMaxima <= 0) throw new RuntimeException("La capacidad máxima debe ser mayor a cero");
    }

    // Valida que la fecha esté disponible para agregar una función
    public void validarDisponibleParaFuncion(LocalDate fecha) {
        if (fechaOcupada(fecha)) {
            throw new RuntimeException("Fecha ocupada en la sede");
        }
    }

     // Devuelve un mapa vacío por defecto (las subclases lo sobrescriben)
    public Map<String, Integer> capacidadPorSector() {
        return new HashMap<>();
    }

    // Dos sedes son iguales si tienen el mismo nombre (ignorando mayúsculas)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sede sede = (Sede) o;
        return nombreSede.equalsIgnoreCase(sede.nombreSede);
    }

    @Override
    public int hashCode() {
        return nombreSede.toLowerCase().hashCode();
    }
}