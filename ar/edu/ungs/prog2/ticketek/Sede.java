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

    public static void validarDatos(String nombre, String direccion, int capacidadMaxima) {
        if (nombre == null || nombre.isEmpty()) {
            throw new RuntimeException("El nombre de la sede no puede ser nulo o vacío");
        }
        if (direccion == null || direccion.isEmpty()) {
            throw new RuntimeException("La dirección de la sede no puede ser nula o vacía");
        }
        if (capacidadMaxima <= 0) {
            throw new RuntimeException("La capacidad máxima debe ser mayor a cero");
        }
    }

    public static void validarDatos(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
        validarDatos(nombre, direccion, capacidadMaxima);
        if (asientosPorFila <= 0) {
            throw new RuntimeException("La cantidad de asientos por fila debe ser mayor a cero");
        }
        if (sectores == null || sectores.length == 0) {
            throw new RuntimeException("Debe haber al menos un sector");
        }
        if (capacidad == null || capacidad.length != sectores.length) {
            throw new RuntimeException("La cantidad de capacidades debe coincidir con la cantidad de sectores");
        }
        if (porcentajeAdicional == null || porcentajeAdicional.length != sectores.length) {
            throw new RuntimeException("La cantidad de porcentajes adicionales debe coincidir con la cantidad de sectores");
        }
    }

    public static void validarDatos(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
        validarDatos(nombre, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad, porcentajeAdicional);
        if (cantidadPuestos <= 0) {
            throw new RuntimeException("La cantidad de puestos debe ser mayor a cero");
        }
        if (precioConsumicion <= 0) {
            throw new RuntimeException("El precio de consumición debe ser mayor a cero");
        }
    }

    public void validarDisponibleParaFuncion(LocalDate fecha) {
    if (fechaOcupada(fecha)) {
        throw new RuntimeException("Fecha ocupada en la sede");
        }
    }

    public void validarEsEstadio() {
    if (!(this instanceof Estadio)) {
        throw new RuntimeException("La sede no es un estadio (no numerada)");
        }
    }

    public void validarEsNumerada() {
    if (!(this instanceof Teatro) && !(this instanceof MiniEstadio)) {
        throw new RuntimeException("La sede no es numerada (Teatro o MiniEstadio)");
        }
    }
}
