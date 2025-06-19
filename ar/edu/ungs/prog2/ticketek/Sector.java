package ar.edu.ungs.prog2.ticketek;

// Clase abstracta para los sectores de una sede (Platea, Campo, etc.)
public abstract class Sector {
    protected String nombre;
    protected int capacidad;
    protected int porcentajeAdicional;

    public Sector(String nombre, int capacidad, int porcentajeAdicional) {
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.porcentajeAdicional = porcentajeAdicional;
    }

    public String getNombre() { return nombre; }
    public int getCapacidad() { return capacidad; }
    public int getPorcentajeAdicional() { return porcentajeAdicional; }

    // Delegación: pide a Funcion la cantidad vendida de este sector
    // Devuelve la descripción del sector con la cantidad de entradas vendidas
    public String descripcionConVendidas(Funcion funcion) {
        int vendidas = funcion.getEntradasVendidasPorSector(nombre);
        return nombre + ": " + vendidas + "/" + capacidad;
    }

    // Método para calcular el precio de una entrada en este sector
    public double calcularPrecio(double precioBase, double adicionalConsumicion) {
        return precioBase + precioBase * porcentajeAdicional / 100.0 + adicionalConsumicion;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sector sector = (Sector) o;
        return nombre.equalsIgnoreCase(sector.nombre) && capacidad == sector.capacidad;
    }

    @Override
    public int hashCode() {
        int result = nombre.toLowerCase().hashCode();
        result = 31 * result + capacidad;
        return result;
    }
}