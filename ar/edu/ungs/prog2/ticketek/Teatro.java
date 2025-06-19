package ar.edu.ungs.prog2.ticketek;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Representa una sede tipo Teatro, con sectores y asientos numerados
public class Teatro extends Sede {
    private final int asientosPorFila;
    private final List<Sector> sectores;

    public Teatro(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, List<Sector> sectores) {
        super(nombre, direccion, capacidadMaxima);
        this.asientosPorFila = asientosPorFila;
        this.sectores = sectores;
    }

    // Busca un sector por nombre (case-insensitive)
    public Sector getSectorPorNombre(String nombre) {
        for (Sector s : sectores) {
            if (s.getNombre().equalsIgnoreCase(nombre)) return s;
        }
        return null;
    }
    // Devuelve la cantidad de asientos por fila
    public int getAsientosPorFila() { return asientosPorFila; }

    // Devuelve el porcentaje adicional de un sector
    public int porcentajeAdicional(String nombre) {
        Sector s = getSectorPorNombre(nombre);
        return s != null ? s.getPorcentajeAdicional() : 0;
    }

    // Calcula el precio de una entrada en un sector
    @Override
    public double calcularPrecioEntrada(String nombreSector, double precioBase) {
        Sector s = getSectorPorNombre(nombreSector);
        if (s != null) {
            return s.calcularPrecio(precioBase, 0);
        }
        return precioBase;
    }

    // Devuelve la descripción de la función para el listado (usado en listarFunciones)
    @Override
    public String descripcionParaListado(Funcion funcion, String fechaStr) {
        StringBuilder sb = new StringBuilder();
        sb.append(" - (").append(fechaStr).append(") ").append(nombreSede())
        .append(" - Asientos por fila: ").append(asientosPorFila)
        .append(" - ");
        for (int i = 0; i < sectores.size(); i++) {
            sb.append(sectores.get(i).descripcionConVendidas(funcion));
            if (i < sectores.size() - 1) sb.append(" | ");
        }
        return sb.toString();
    }

    // Valida los datos para crear un teatro
    public static void validarDatos(String nombreSede, String direccion, int capacidadMaxima, int asientosPorFila, String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
        Sede.validarDatos(nombreSede, direccion, capacidadMaxima);
        if (asientosPorFila <= 0) throw new RuntimeException("La cantidad de asientos por fila debe ser mayor a cero");
        if (sectores == null || sectores.length == 0) throw new RuntimeException("Debe haber al menos un sector");
        if (capacidad == null || capacidad.length != sectores.length) throw new RuntimeException("La cantidad de capacidades debe coincidir con la cantidad de sectores");
        if (porcentajeAdicional == null || porcentajeAdicional.length != sectores.length) throw new RuntimeException("La cantidad de porcentajes adicionales debe coincidir con la cantidad de sectores");
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