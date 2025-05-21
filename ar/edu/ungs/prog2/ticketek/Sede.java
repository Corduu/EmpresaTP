package ar.edu.ungs.prog2.ticketek;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public abstract class Sede {
    protected int capacidadMaxima;
    protected String nombre;
    protected String direccion;
    protected Map<LocalDate, String> funcionesPorFecha = new HashMap<>();

    public Sede(String nombre, String direccion, int capacidadMaxima) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidadMaxima = capacidadMaxima;
    }

    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public int getCapacidadMaxima() { return capacidadMaxima; }

    public boolean fechaOcupada(LocalDate fecha) {
        return funcionesPorFecha.containsKey(fecha);
    }

    public void agregarFuncion(LocalDate fecha, String nombreEspectaculo) {
        funcionesPorFecha.put(fecha, nombreEspectaculo);
    }
}
