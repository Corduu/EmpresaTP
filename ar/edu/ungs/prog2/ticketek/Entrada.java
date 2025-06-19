package ar.edu.ungs.prog2.ticketek;

import java.time.LocalDate;

// Representa una entrada comprada por un usuario para una función
public class Entrada implements IEntrada {
    private LocalDate fecha;
    private String sector;
    private Espectaculo espectaculo;
    private String codigoEntrada;
    private String emailUsuario;
    private Integer fila = null;
    private Integer asiento = null;

    // Constructor para entradas generales o de sector
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

    // Constructor para asientos numerados
    public boolean esDeCodigo(String codigo) { return codigoEntrada.equals(codigo); }
    public boolean esDeUsuario(String email) { return emailUsuario.equals(email); }
    public boolean esDeEspectaculo(String nombre) { return espectaculo != null && espectaculo.nombre().equals(nombre); }
    public boolean esDeFecha(LocalDate f) { return fecha.equals(f); }
    public boolean esDeSector(String s) { return sector != null && sector.equalsIgnoreCase(s); }
    public boolean esDeFila(Integer f) { return fila != null && fila.equals(f); }
    public boolean esDeAsiento(Integer a) { return asiento != null && asiento.equals(a); }
    public boolean esFutura(LocalDate hoy) { return !fecha.isBefore(hoy); }

    // para buscar por código, usuario o espectaculo
    public String codigo() { return codigoEntrada; } 
    public String usuario() { return emailUsuario; }
    public Espectaculo espectaculo() { return espectaculo; }
    public LocalDate fecha() { return fecha; }
    public String sector() { return sector; }
    public Integer asiento() { return asiento; }


    // Calcula el precio de la entrada usando la función correspondiente
    @Override
    public double precio() {
        if (espectaculo != null && fecha != null) {
            Funcion funcion = espectaculo.funcionEnFecha(fecha);
            if (funcion != null) {
                return funcion.calcularPrecioEntrada(sector);
            }
        }
        return 0;
    }

    // Devuelve la ubicación de la entrada (CAMPO o sector y asiento)
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
    }

    // Formato de string de la entrada (usado en listados y tests)
    @Override
    public String toString() {
        String fechaStr = fecha != null ? fecha.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yy")) : "";
        boolean pasada = fecha != null && fecha.isBefore(LocalDate.now());
        if (pasada) fechaStr += " P";
        String sedeNombre = "";
        if (espectaculo != null && fecha != null) {
            Funcion f = espectaculo.funcionEnFecha(fecha);
            if (f != null) sedeNombre = f.sede();
        }
        return codigoEntrada + " - " +
                (espectaculo != null ? espectaculo.nombre() : "") + " - " +
                fechaStr + " - " +
                sedeNombre + " - " +
                ubicacion() +
                " - $" + String.format("%.2f", precio());
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entrada entrada = (Entrada) o;
        return codigoEntrada.equals(entrada.codigoEntrada);
    }

    @Override
    public int hashCode() {
        return codigoEntrada.hashCode();
    }
}