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
        this.sede = sedeObj.getNombreSede();
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
        recaudacion += precioBase; // O logica con sector/asiento
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

    public static void validarDatos(String nombreEspectaculo, String fecha, String sede, double precioBase) {
        if (nombreEspectaculo == null || nombreEspectaculo.isEmpty()) {
            throw new RuntimeException("El nombre del espectáculo no puede ser nulo o vacío");
        }
        if (nombreEspectaculo.length() < 3) {
            throw new RuntimeException("El nombre del espectáculo debe tener al menos 3 caracteres");
        }
        if (fecha == null || fecha.isEmpty()) {
            throw new RuntimeException("La fecha no puede ser nula o vacía");
        }
        if (sede == null || sede.isEmpty()) {
            throw new RuntimeException("La sede no puede ser nula o vacía");
        }
        if (sede.length() < 3) {
            throw new RuntimeException("El nombre de la sede debe tener al menos 3 caracteres");
        }
        if (precioBase <= 0) {
            throw new RuntimeException("El precio base debe ser mayor a cero");
        }
    }
    
    public String descripcionParaListado(Sede sede, String fechaStr) {
        StringBuilder sb = new StringBuilder();
        sb.append(" - (").append(fechaStr).append(") ").append(sede.getNombreSede()).append(" - ");
        if (sede instanceof Estadio estadio) {
            int entradasVendidas = this.getEntradasVendidas();
            sb.append(entradasVendidas).append("/").append(estadio.getCapacidadMaxima());
        } else if (sede instanceof Teatro teatro) {
            String[] sectores = teatro.getSectores();
            int[] capacidades = teatro.getCapacidadSectores();
            for (int i = 0; i < sectores.length; i++) {
                int vendidas = this.getEntradasVendidasPorSector(sectores[i]);
                sb.append(sectores[i]).append(": ").append(vendidas).append("/").append(capacidades[i]);
                if (i < sectores.length - 1) sb.append(" | ");
            }
        } else if (sede instanceof MiniEstadio mini) {
            String[] sectores = mini.getSectores();
            int[] capacidades = mini.getCapacidadSectores();
            for (int i = 0; i < sectores.length; i++) {
                int vendidas = this.getEntradasVendidasPorSector(sectores[i]);
                sb.append(sectores[i]).append(": ").append(vendidas).append("/").append(capacidades[i]);
                if (i < sectores.length - 1) sb.append(" | ");
            }
        }
        return sb.toString();
    }

    public double calcularPrecioEntrada(String sector) {
        Sede sede = this.getSedeObj();
        double base = this.getPrecioBase();
        int porcentaje = 0;
        double adicional = 0;
        if (sede instanceof Teatro teatro) {
            porcentaje = teatro.getPorcentajeAdicional(sector);
        } else if (sede instanceof MiniEstadio mini) {
            porcentaje = mini.getPorcentajeAdicional(sector);
            adicional = mini.getPrecioConsumicion();
        }
        return base + base * porcentaje / 100.0 + adicional;
    }

    public void liberarAsientoSiCorresponde(String sector, Integer asiento) {
    if (sector != null && asiento != null) {
        liberarAsiento(sector, asiento);
    }
}
}