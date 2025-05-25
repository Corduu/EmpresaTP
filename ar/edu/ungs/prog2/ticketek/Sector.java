package ar.edu.ungs.prog2.ticketek;

public class Sector {
    private String nombre;
    private int capacidad;

    public Sector(String nombre, int capacidad) {
        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    public String nombre() { return nombre; }
    public int capacidad() { return capacidad; }
}