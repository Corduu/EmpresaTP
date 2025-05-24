package ar.edu.ungs.prog2.ticketek;

public class Platea extends Sector {
    private double procentajeAdicional;

    public Platea(String nombre, int capacidad, double procentajeAdicional) {
        super(nombre, capacidad);
        this.procentajeAdicional = procentajeAdicional;
    }

    public double getValorAdicional() { return procentajeAdicional; }
}