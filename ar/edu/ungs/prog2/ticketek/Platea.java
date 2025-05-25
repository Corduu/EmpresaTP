package ar.edu.ungs.prog2.ticketek;

public class Platea extends Sector {
    private double porcentajeAdicional;

    public Platea(String nombre, int capacidad, double porcentajeAdicional) {
        super(nombre, capacidad);
        this.porcentajeAdicional = porcentajeAdicional;
    }

    public double porcentajeAdicional() { return porcentajeAdicional; }
}