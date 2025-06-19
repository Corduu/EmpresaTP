package ar.edu.ungs.prog2.ticketek;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

// Representa una función (fecha y sede) de un espectáculo
public class Funcion {
    private final LocalDate fecha;
    private final String sede;
    private final Sede sedeObj;
    private final double precioBase;
    private final String nombreEspectaculo;
    private double recaudacion = 0;
    // Entradas vendidas por sector
    private final Map<String, Integer> entradasPorSector = new HashMap<>();
    // Mapa de asientos ocupados por sector (solo para sedes numeradas)
    private final Map<String, boolean[]> asientosPorSector = new HashMap<>();
    

    public Funcion(LocalDate fecha, Sede sedeObj, double precioBase, String nombreEspectaculo) {
        this.fecha = fecha;
        this.sede = sedeObj.nombreSede();
        this.sedeObj = sedeObj;
        this.precioBase = precioBase;
        this.nombreEspectaculo = nombreEspectaculo;

        // Inicializar asientosPorSector usando polimorfismo
        Map<String, Integer> capacidades = sedeObj.capacidadPorSector();
        for (Map.Entry<String, Integer> entry : capacidades.entrySet()) {
            asientosPorSector.put(entry.getKey(), new boolean[entry.getValue()]);
        }
    }

    public String sede() { return sede; }
    public double precioBase() { return precioBase; }
    public double recaudacion() { return recaudacion; }
    public LocalDate fecha() { return fecha; }
    public String nombreEspectaculo() { return nombreEspectaculo; }
    // para poder obtener la sede como objeto (requiere acceso a Ticketek o pasarla por constructor)
    public Sede sedeObj() { return sedeObj; }
    
    
    // Reserva una entrada en un sector y asiento (si corresponde)
    public void reservarEntrada(String sector, int asiento) {
        recaudacion += precioBase;
        if (sector != null) {
            entradasPorSector.put(sector, entradasPorSector.getOrDefault(sector, 0) + 1);
            if (asientosPorSector.containsKey(sector) && asiento >= 0) {
                boolean[] asientos = asientosPorSector.get(sector);
                if (asiento < asientos.length) asientos[asiento] = true;
            }
        }
    }

    // utilizamos para chequear si un asiento está disponible
    public boolean asientoDisponible(String sector, int asiento) {
        if (sector != null && asientosPorSector.containsKey(sector)) {
            boolean[] asientos = asientosPorSector.get(sector);
            return asiento >= 0 && asiento < asientos.length && !asientos[asiento];
        }
        return true; // Si no hay info, asumimos disponible
    }

    // Devuelve la cantidad total de entradas vendidas
    public int getEntradasVendidas() {
        int total = 0;
        for (int vendidas : entradasPorSector.values()) total += vendidas;
        return total;
    }

    // Devuelve la cantidad de entradas vendidas en un sector
    public int getEntradasVendidasPorSector(String sector) {
        return entradasPorSector.getOrDefault(sector, 0);
    }
    
    // Utilizado para liberar un asiento al anular una entrada
    public void liberarAsiento(String sector, int asiento) {
        if (asientosPorSector.containsKey(sector) && asiento >= 0) {
            boolean[] asientos = asientosPorSector.get(sector);
            if (asiento < asientos.length) asientos[asiento] = false;
        }
        if (entradasPorSector.containsKey(sector)) {
            int actuales = entradasPorSector.get(sector);
            if (actuales > 0) entradasPorSector.put(sector, actuales - 1);
        }
    }

    @Override
    public String toString() {
        return "Funcion{" +
                "fecha=" + fecha +
                ", sede='" + sede + '\'' +
                ", precioBase=" + precioBase +
                ", espectaculo='" + nombreEspectaculo + '\'' +
                ", recaudacion=" + recaudacion +
                '}';
    }

    public static void validarDatos(String nombreEspectaculo, String fecha, String sede, double precioBase) {
        if (nombreEspectaculo == null || nombreEspectaculo.isEmpty()) throw new RuntimeException("El nombre del espectáculo no puede ser nulo o vacío");
        if (nombreEspectaculo.length() < 3) throw new RuntimeException("El nombre del espectáculo debe tener al menos 3 caracteres");
        if (fecha == null || fecha.isEmpty()) throw new RuntimeException("La fecha no puede ser nula o vacía");
        if (sede == null || sede.isEmpty()) throw new RuntimeException("La sede no puede ser nula o vacía");
        if (sede.length() < 3) throw new RuntimeException("El nombre de la sede debe tener al menos 3 caracteres");
        if (precioBase <= 0) throw new RuntimeException("El precio base debe ser mayor a cero");
    }
    

    // Calcula el precio de una entrada en un sector
    public double calcularPrecioEntrada(String sector) {
        return sedeObj.calcularPrecioEntrada(sector, precioBase);
    }

    // Libera un asiento si corresponde (usado al anular una entrada)
    public void liberarAsientoSiCorresponde(String sector, Integer asiento) {
        if (sector != null && asiento != null) {
            liberarAsiento(sector, asiento);
        }
    }
}