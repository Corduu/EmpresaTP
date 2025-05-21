package ar.edu.ungs.prog2.ticketek;

public class Teatro extends Sede {
    private int asientosPorFila;
    private String[] sectores;
    private int[] capacidad;
    private int[] porcentajeAdicional;

    public Teatro(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
        super(nombre, direccion, capacidadMaxima);
        this.asientosPorFila = asientosPorFila;
        this.sectores = sectores;
        this.capacidad = capacidad;
        this.porcentajeAdicional = porcentajeAdicional;
    }

    public String[] getSectores() { return sectores; }
    public int[] getCapacidadSectores() { return capacidad; }
    public int getPorcentajeAdicional(String sector) {
        for (int i = 0; i < sectores.length; i++) {
            if (sectores[i].equalsIgnoreCase(sector)) return porcentajeAdicional[i];
        }
        return 0;
    }
}