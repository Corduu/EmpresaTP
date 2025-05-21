package ar.edu.ungs.prog2.ticketek;

public class MiniEstadio extends Sede {
    private int cantidadPuestos;
    private double precioConsumicion;
    private int asientosPorFila;
    private String[] sectores;
    private int[] capacidad;
    private int[] porcentajeAdicional;

    public MiniEstadio(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
        super(nombre, direccion, capacidadMaxima);
        this.asientosPorFila = asientosPorFila;
        this.cantidadPuestos = cantidadPuestos;
        this.precioConsumicion = precioConsumicion;
        this.sectores = sectores;
        this.capacidad = capacidad;
        this.porcentajeAdicional = porcentajeAdicional;
    }

    // Métodos específicos de MiniEstadio 
    public String[] getSectores() { return sectores; }
    public int[] getCapacidadSectores() { return capacidad; }
    public int getPorcentajeAdicional(String sector) {
        for (int i = 0; i < sectores.length; i++) {
            if (sectores[i].equalsIgnoreCase(sector)) return porcentajeAdicional[i];
        }
        return 0;
    }
    public double getPrecioConsumicion() { return precioConsumicion; }
}