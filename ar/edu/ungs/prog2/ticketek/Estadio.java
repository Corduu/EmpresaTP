package ar.edu.ungs.prog2.ticketek;

// Representa una sede tipo Estadio (sin asientos numerados)
public class Estadio extends Sede {

    public Estadio(String nombre, String direccion, int capacidadMaxima) {
        super(nombre, direccion, capacidadMaxima);
    }

    // Devuelve la descripción de la función para el listado (usado en listarFunciones)
    @Override
    public String descripcionParaListado(Funcion funcion, String fechaStr) {
        int entradasVendidas = funcion.getEntradasVendidas();
        return " - (" + fechaStr + ") " + nombreSede + " - " + entradasVendidas + "/" + capacidadMaxima;
    }

    // El precio de la entrada es el precio base (no hay adicionales)
    @Override
    public double calcularPrecioEntrada(String nombreSector, double precioBase) {
        return precioBase;
    }

    // Valida los datos para crear un estadio
    public static void validarDatos(String nombreSede, String direccion, int capacidadMaxima) {
        Sede.validarDatos(nombreSede, direccion, capacidadMaxima);
    }
}