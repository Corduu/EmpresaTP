package ar.edu.ungs.prog2.ticketek;

public class Platea extends Sector {
    private double valorAdicional;

    public Platea(String nombre, int capacidad, double valorAdicional) {
        super(nombre, capacidad);
        this.valorAdicional = valorAdicional;
    }

    public double getValorAdicional() { return valorAdicional; }
}