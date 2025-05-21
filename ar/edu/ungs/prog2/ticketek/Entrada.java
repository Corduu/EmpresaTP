package ar.edu.ungs.prog2.ticketek;

import java.time.LocalDate;

public class Entrada implements IEntrada {
    private LocalDate fecha;
    private String sector;
    private Espectaculo espectaculo;
    private String codigoEntrada;
    private String emailUsuario;
    private Integer fila = null;
    private Integer asiento = null;

    public Entrada(LocalDate fecha, String sector, Espectaculo espectaculo, String codigoEntrada, String emailUsuario) {
    this.fecha = fecha;
    this.sector = sector;
    this.espectaculo = espectaculo;
    this.codigoEntrada = codigoEntrada;
    this.emailUsuario = emailUsuario;
    }

    // Constructor para asientos numerados
    public Entrada(LocalDate fecha, String sector, Espectaculo espectaculo, String codigoEntrada, String emailUsuario, Integer fila, Integer asiento) {
        this(fecha, sector, espectaculo, codigoEntrada, emailUsuario);
        this.fila = fila;
        this.asiento = asiento;
    }

    public String getCodigoEntrada() { return codigoEntrada; }
    public String getEmailUsuario() { return emailUsuario; }
    public Espectaculo getEspectaculo() { return espectaculo; }
    public LocalDate getFecha() { return fecha; }
    public String getSector() { return sector; }
    public Integer getFila() { return fila; }
    public Integer getAsiento() { return asiento; }


    @Override
    public double precio() {
        if (espectaculo != null && fecha != null) {
            Funcion funcion = espectaculo.getFunciones().get(fecha);
            if (funcion != null) {
                double base = funcion.getPrecioBase();
                // Si es estadio, solo precio base
                if (sector == null || sector.equalsIgnoreCase("CAMPO")) return base;
                // Si es teatro/miniestadio, sumar porcentaje adicional
                Sede sede = funcion.getSedeObj();
                int porcentaje = 0;
                double consumicion = 0;
                if (sede instanceof Teatro) {
                    porcentaje = ((Teatro) sede).getPorcentajeAdicional(sector);
                } else if (sede instanceof MiniEstadio) {
                    porcentaje = ((MiniEstadio) sede).getPorcentajeAdicional(sector);
                    consumicion = ((MiniEstadio) sede).getPrecioConsumicion();
                }
                return base + base * porcentaje / 100.0 + consumicion;
            }
        }
        return 0;
    }

    @Override
    public String ubicacion() {
        if (sector == null || sector.equalsIgnoreCase("CAMPO")) return "CAMPO";
        String ubic = sector;
        if (fila != null && asiento != null) {
            ubic += " f:" + fila + " a:" + asiento;
        } else if (asiento != null) {
            ubic += " a:" + asiento;
        }
        return ubic;
        // Devuelve el sector si existe, si no, "General"
        //return (sector != null && !sector.isEmpty()) ? sector : "General";
    }

    @Override
    public String toString() {
        String fechaStr = fecha != null ? fecha.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yy")) : "";
        if (fecha != null && fecha.isBefore(LocalDate.now())) fechaStr += " P";
        String sedeNombre = "";
        if (espectaculo != null && fecha != null) {
            Funcion f = espectaculo.getFunciones().get(fecha);
            if (f != null) sedeNombre = f.getSede();
        }
        return codigoEntrada + " - " +
                (espectaculo != null ? espectaculo.getNombre() : "") + " - " +
                fechaStr + " - " +
                sedeNombre + " - " +
                ubicacion();
    }
}