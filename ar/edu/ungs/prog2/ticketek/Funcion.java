package ar.edu.ungs.prog2.ticketek;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Funcion {
    private LocalDate fecha;
    private String sede;
    private Sede sedeObj;
    // En caso de que no se pase la sede como objeto, la guardamos como String
    private double precioBase;
    private String nombreEspectaculo;
    private double recaudacion = 0;
    // por si necesitamos manejar asientos y sectores:
    private Map<String, Integer> entradasPorSector = new HashMap<>();
    private Map<String, boolean[]> asientosPorSector = new HashMap<>();
    

    public Funcion(LocalDate fecha, Sede sedeObj, double precioBase, String nombreEspectaculo) {
        this.fecha = fecha;
        this.sede = sedeObj.getNombre();
        this.sedeObj = sedeObj;
        this.precioBase = precioBase;
        this.nombreEspectaculo = nombreEspectaculo;
    }

    public String getSede() { return sede; }
    public double getPrecioBase() { return precioBase; }
    public double getRecaudacion() { return recaudacion; }
    public LocalDate getFecha() { return fecha; }
    public String getNombreEspectaculo() { return nombreEspectaculo; }

    // Para obtener la sede como objeto (requiere acceso a Ticketek o pasarla por constructor)
    public Sede getSedeObj() { return sedeObj; }
    

    // Simula la reserva de una entrada
    public void reservarEntrada(String sector, int asiento) {
        recaudacion += precioBase; // O lógica con sector/asiento
        // para manejar los asientos
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

    public int getEntradasVendidas() {
        int total = 0;
        for (int vendidas : entradasPorSector.values()) total += vendidas;
        return total;
    }

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
}