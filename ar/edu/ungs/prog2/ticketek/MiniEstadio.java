package ar.edu.ungs.prog2.ticketek;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Representa una sede tipo MiniEstadio, con asientos y puestos de consumición
public class MiniEstadio extends Sede {
    // El test espera el formato original, sin mostrar "Asientos por fila" ni "Puestos" en la descripcion
    private final int cantidadPuestos;
    private final double precioConsumicion;
    private final List<Sector> sectores;

    public MiniEstadio(String nombre, String direccion, int capacidadMaxima, int cantidadPuestos, double precioConsumicion, List<Sector> sectores) {
        super(nombre, direccion, capacidadMaxima);
        this.cantidadPuestos = cantidadPuestos;
        this.precioConsumicion = precioConsumicion;
        this.sectores = sectores;
    }

    // Getter para la cantidad de puestos
    public int getCantidadPuestos() { return cantidadPuestos; }

    public Sector getSectorPorNombre(String nombre) {
        for (Sector s : sectores) {
            if (s.getNombre().equalsIgnoreCase(nombre)) return s;
        }
        return null;
    }

    // Devuelve el porcentaje adicional de un sector
    public int porcentajeAdicional(String nombre) {
        Sector s = getSectorPorNombre(nombre);
        return s != null ? s.getPorcentajeAdicional() : 0;
    }

    // Devuelve el precio de consumición extra
    public double precioConsumicion() { return precioConsumicion; }

    // Devuelve la descripción de la función para el listado (usado en listarFunciones)
    @Override
    public String descripcionParaListado(Funcion funcion, String fechaStr) {
        StringBuilder sb = new StringBuilder();
        sb.append(" - (").append(fechaStr).append(") ").append(nombreSede()).append(" - ");
        for (int i = 0; i < sectores.size(); i++) {
            sb.append(sectores.get(i).descripcionConVendidas(funcion));
            if (i < sectores.size() - 1) sb.append(" | ");
        }
        return sb.toString();
    }

    // Método para obtener el sector por nombre y calcular el precio de entrada
    // Calcula el precio de una entrada en un sector (incluye consumición)
    @Override
    public double calcularPrecioEntrada(String nombreSector, double precioBase) {
        Sector s = getSectorPorNombre(nombreSector);
        if (s != null) {
            return s.calcularPrecio(precioBase, precioConsumicion);
        }
        return precioBase + precioConsumicion;
    }

    public static void validarDatos(String nombreSede, String direccion, int capacidadMaxima, int asientosPorFila, int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
        Teatro.validarDatos(nombreSede, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad, porcentajeAdicional);
        if (cantidadPuestos <= 0) throw new RuntimeException("La cantidad de puestos debe ser mayor a cero");
        if (precioConsumicion <= 0) throw new RuntimeException("El precio de consumición debe ser mayor a cero");
    }

    // Devuelve un mapa con la capacidad de cada sector
    @Override
    public Map<String, Integer> capacidadPorSector() {
        Map<String, Integer> map = new HashMap<>();
        for (Sector s : sectores) {
            map.put(s.getNombre(), s.getCapacidad());
        }
        return map;
    }
}