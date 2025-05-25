package ar.edu.ungs.prog2.ticketek;

public class Campo extends Sector {
    private int porcentajeAdicional;
    private int cantidadPuestos;

    public Campo(String nombre, int capacidad, int porcentajeAdicional, int cantidadPuestos) {
        super(nombre, capacidad);
        this.porcentajeAdicional = porcentajeAdicional;
        this.cantidadPuestos = cantidadPuestos;
    }

    public int porcentajeAdicional() { return porcentajeAdicional; }
    public int cantidadPuestos() { return cantidadPuestos; }
}