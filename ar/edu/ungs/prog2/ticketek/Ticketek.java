package ar.edu.ungs.prog2.ticketek;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ticketek implements ITicketek {
    
    private Map<String, Usuario> usuarios = new HashMap<>();
    private Map<String, Sede> sedes = new HashMap<>();
    private Map<String, Espectaculo> espectaculos = new HashMap<>();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy");

    public Ticketek() {
        // Constructor vacío
    }

    @Override
    public void registrarSede(String nombre, String direccion, int capacidadMaxima) {
        if (sedes.containsKey(nombre)) {
            throw new RuntimeException("Sede ya registrada");
        }
        if (nombre == null|| nombre.isEmpty() || direccion == null || direccion.isEmpty() || capacidadMaxima <= 0) { 
            throw new RuntimeException("Datos inválidos");
        }
        sedes.put(nombre, new Estadio(nombre, direccion, capacidadMaxima));
    }

    @Override
    public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
        if (sedes.containsKey(nombre)) {
            throw new RuntimeException("Sede ya registrada");
        }
        if(nombre == null || nombre.isEmpty() || direccion == null || direccion.isEmpty() || capacidadMaxima <= 0 || asientosPorFila <= 0 || sectores == null || sectores.length == 0 || capacidad == null || capacidad.length == 0 || porcentajeAdicional == null || porcentajeAdicional.length == 0){
            throw new RuntimeException("Datos inválidos");
        }
        sedes.put(nombre, new Teatro(nombre, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad, porcentajeAdicional));
    }

    @Override
    public void registrarSede(String nombre, String direccion, int capacidadMaxima, int asientosPorFila, int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
        if (sedes.containsKey(nombre)) {
            throw new RuntimeException("Sede ya registrada");
        }
        if (nombre == null || nombre.isEmpty() || direccion == null || direccion.isEmpty() || capacidadMaxima <= 0 || asientosPorFila <= 0 || cantidadPuestos <= 0 || precioConsumicion <= 0 || sectores == null || sectores.length == 0 || capacidad == null || capacidad.length == 0 || porcentajeAdicional == null || porcentajeAdicional.length == 0) {
            throw new RuntimeException("Datos inválidos");
            }
        sedes.put(nombre, new MiniEstadio(nombre, direccion, capacidadMaxima, asientosPorFila, cantidadPuestos, precioConsumicion, sectores, capacidad, porcentajeAdicional));
    }

    @Override
    public void registrarUsuario(String email, String nombre, String apellido, String contrasenia) {
        if (usuarios.containsKey(email)) {
            throw new RuntimeException("Usuario ya registrado");
            }
        if (email == null || email.isEmpty() || nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty() || contrasenia == null || contrasenia.isEmpty()) {
            throw new RuntimeException("Datos inválidos");
            }
        usuarios.put(email, new Usuario(nombre, apellido, email, contrasenia));
    }

    @Override
    public void registrarEspectaculo(String nombre) {
        if (espectaculos.containsKey(nombre)) {
            throw new RuntimeException("Espectáculo ya registrado");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new RuntimeException("Nombre inválido");
        } 
        espectaculos.put(nombre, new Espectaculo(nombre));
    }

    @Override
    public void agregarFuncion(String nombreEspectaculo, String fecha, String sede, double precioBase) {
        Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
        if (espectaculo == null) {
            throw new RuntimeException("Espectáculo no encontrado");
        }
        if (fecha == null || fecha.isEmpty() || sede == null || sede.isEmpty() || precioBase <= 0 || sede.length() < 3 || nombreEspectaculo.length() < 3) {
            throw new RuntimeException("Datos inválidos");
        }      
        LocalDate fechaFuncion = LocalDate.parse(fecha, dateFormatter);
        Sede sedeObj = sedes.get(sede);
        if (sedeObj == null) {
            throw new RuntimeException("Sede no encontrada");
        }
        
        if (sedeObj.fechaOcupada(fechaFuncion)) {
            throw new RuntimeException("Fecha ocupada en la sede");
        }
        if (espectaculo.getFunciones().containsKey(fechaFuncion)) {
            throw new RuntimeException("Ya existe una función para esa fecha en el espectáculo");
        }
        
        Funcion funcion = new Funcion(fechaFuncion, sedeObj, precioBase, nombreEspectaculo);
        espectaculo.agregarFuncion(fechaFuncion, funcion);
        sedeObj.agregarFuncion(fechaFuncion, nombreEspectaculo);
    }

    @Override
    public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia, int cantidadEntradas) {
    if (nombreEspectaculo == null || fecha == null || email == null || contrasenia == null || cantidadEntradas <= 0) {
        throw new RuntimeException("Datos inválidos");
    }
    Usuario usuario = usuarios.get(email);
    if (usuario == null || !usuario.getContrasenia().equals(contrasenia)) {
        throw new RuntimeException("Usuario o contraseña incorrectos");
    }
    Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
    if (espectaculo == null) {
        throw new RuntimeException("Espectáculo no encontrado");
    }
    LocalDate fechaFuncion = LocalDate.parse(fecha, dateFormatter);
    Funcion funcion = espectaculo.getFunciones().get(fechaFuncion);
    if (funcion == null) {
        throw new RuntimeException("Función no encontrada");
    }
    Sede sede = sedes.get(funcion.getSede());
    if (!(sede instanceof Estadio)) {
        throw new RuntimeException("La sede no es un estadio (no numerada)");
    }
    List<IEntrada> entradasVendidas = new ArrayList<>();
    for (int i = 0; i < cantidadEntradas; i++) {
        String codigo = generarCodigoEntrada();
        Entrada entrada = new Entrada(fechaFuncion, "CAMPO", espectaculo, codigo, email);
        usuario.agregarEntrada(entrada);
        funcion.reservarEntrada("CAMPO", -1); // -1 porque no hay asiento
        entradasVendidas.add(entrada);
        }
    return entradasVendidas;
    }

    @Override
    public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia, String sector, int[] asientos) {
        if (nombreEspectaculo == null || fecha == null || email == null || contrasenia == null || sector == null || asientos == null || asientos.length == 0) {
            throw new RuntimeException("Datos inválidos");
        }
        Usuario usuario = usuarios.get(email);
        if (usuario == null || !usuario.getContrasenia().equals(contrasenia)) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }
        Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
        if (espectaculo == null) {
            throw new RuntimeException("Espectáculo no encontrado");
        }
        LocalDate fechaFuncion = LocalDate.parse(fecha, dateFormatter);
        Funcion funcion = espectaculo.getFunciones().get(fechaFuncion);
        if (funcion == null) {
            throw new RuntimeException("Función no encontrada");
        }
        Sede sede = sedes.get(funcion.getSede());
        if (!(sede instanceof Teatro) && !(sede instanceof MiniEstadio)) {
            throw new RuntimeException("La sede no es numerada");
        }
        List<IEntrada> entradasVendidas = new ArrayList<>();
        for (int asiento : asientos) {
            if (!funcion.asientoDisponible(sector, asiento)) {
                throw new RuntimeException("Asiento no disponible");
            }
            String codigo = generarCodigoEntrada();
            Entrada entrada = new Entrada(fechaFuncion, sector, espectaculo, codigo, email);
            usuario.agregarEntrada(entrada);
            funcion.reservarEntrada(sector, asiento);
            entradasVendidas.add(entrada);
        }
        return entradasVendidas;
    }

    @Override
    public String listarFunciones(String nombreEspectaculo) {
        Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
        if (espectaculo == null) throw new RuntimeException("Espectáculo no encontrado");
        StringBuilder sb = new StringBuilder();
        // Ordenar las fechas
        List<LocalDate> fechas = new ArrayList<>(espectaculo.getFunciones().keySet());
        fechas.sort(LocalDate::compareTo);
        for (LocalDate fecha : fechas) {
            Funcion funcion = espectaculo.getFunciones().get(fecha);
            Sede sede = sedes.get(funcion.getSede());
            String fechaStr = fecha.format(dateFormatter);
            sb.append(" - (").append(fechaStr).append(") ").append(sede.getNombre()).append(" - ");
            if (sede instanceof Estadio estadio) {
                int entradasVendidas = funcion.getEntradasVendidas();
                sb.append(entradasVendidas).append("/").append(estadio.getCapacidadMaxima());
            } else if (sede instanceof Teatro teatro) {
                String[] sectores = teatro.getSectores();
                int[] capacidades = teatro.getCapacidadSectores();
                for (int i = 0; i < sectores.length; i++) {
                    int vendidas = funcion.getEntradasVendidasPorSector(sectores[i]);
                    sb.append(sectores[i]).append(": ").append(vendidas).append("/").append(capacidades[i]);
                    if (i < sectores.length - 1) sb.append(" | ");
                }
            } else if (sede instanceof MiniEstadio mini) {
                String[] sectores = mini.getSectores();
                int[] capacidades = mini.getCapacidadSectores();
                for (int i = 0; i < sectores.length; i++) {
                    int vendidas = funcion.getEntradasVendidasPorSector(sectores[i]);
                    sb.append(sectores[i]).append(": ").append(vendidas).append("/").append(capacidades[i]);
                    if (i < sectores.length - 1) sb.append(" | ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public List<IEntrada> listarEntradasEspectaculo(String nombreEspectaculo) { 
        List<IEntrada> entradas = new ArrayList<>();
        for (Usuario usuario : usuarios.values()) {
            for (Entrada entrada : usuario.getEntradas().values()) {
                if (entrada.getEspectaculo().getNombre().equals(nombreEspectaculo)) {
                    entradas.add(entrada);
                }
            }
        }
        return entradas;
    }

    @Override
    public List<IEntrada> listarEntradasFuturas(String email, String contrasenia) {
        Usuario usuario = usuarios.get(email);
        if (usuario == null || !usuario.getContrasenia().equals(contrasenia)) throw new RuntimeException("Usuario o contraseña incorrectos");
        List<IEntrada> futuras = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        for (Entrada entrada : usuario.getEntradas().values()) {
            if (entrada.getFecha().isAfter(hoy)) {
                futuras.add(entrada);
            }
        }
        return futuras;
    }

    @Override
    public List<IEntrada> listarTodasLasEntradasDelUsuario(String email, String contrasenia) {
        Usuario usuario = usuarios.get(email);
        if (usuario == null || !usuario.getContrasenia().equals(contrasenia)) throw new RuntimeException("Usuario o contraseña incorrectos");
        return new ArrayList<>(usuario.getEntradas().values());
    }

    @Override
    public boolean anularEntrada(IEntrada entrada, String contrasenia) {
        if (entrada == null || contrasenia == null) throw new RuntimeException("Datos inválidos");
        if (!(entrada instanceof Entrada)) throw new RuntimeException("Tipo de entrada inválido");
        Entrada ent = (Entrada) entrada;
        Usuario usuario = usuarios.get(ent.getEmailUsuario());
        if (usuario == null || !usuario.getContrasenia().equals(contrasenia)) throw new RuntimeException("Usuario o contraseña incorrectos");
        if (ent.getFecha().isBefore(LocalDate.now())) return false;
        // Cambia aquí: si no existe, lanza excepción
        if (!usuario.getEntradas().containsKey(ent.getCodigoEntrada()))
            throw new RuntimeException("La entrada no existe");
        boolean eliminado = usuario.eliminarEntrada(ent.getCodigoEntrada());
        // Si manejas asientos, aquí deberías liberar el asiento en Funcion
        return eliminado;
    }

    @Override
    public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String nuevaFecha) {
        if (entrada == null || contrasenia == null || nuevaFecha == null) throw new RuntimeException("Datos inválidos");
        if (!(entrada instanceof Entrada)) throw new RuntimeException("Tipo de entrada inválido");
        Entrada ent = (Entrada) entrada;
        Usuario usuario = usuarios.get(ent.getEmailUsuario());
        if (usuario == null || !usuario.getContrasenia().equals(contrasenia)) throw new RuntimeException("Usuario o contraseña incorrectos");
        if (ent.getFecha().isBefore(LocalDate.now())) throw new RuntimeException("La entrada original está en el pasado");
        anularEntrada(ent, contrasenia);
        List<IEntrada> nuevas = venderEntrada(ent.getEspectaculo().getNombre(), nuevaFecha, ent.getEmailUsuario(), contrasenia, 1);
        return nuevas.get(0);
    }

    @Override
    public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String nuevaFecha, String nuevoSector, int nuevoAsiento) {
        if (entrada == null || contrasenia == null || nuevaFecha == null || nuevoSector == null) throw new RuntimeException("Datos inválidos");
        if (!(entrada instanceof Entrada)) throw new RuntimeException("Tipo de entrada inválido");
        Entrada ent = (Entrada) entrada;
        Usuario usuario = usuarios.get(ent.getEmailUsuario());
        if (usuario == null || !usuario.getContrasenia().equals(contrasenia)) throw new RuntimeException("Usuario o contraseña incorrectos");
        if (ent.getFecha().isBefore(LocalDate.now())) throw new RuntimeException("La entrada original está en el pasado");
        anularEntrada(ent, contrasenia);
        int[] asientos = {nuevoAsiento};
        List<IEntrada> nuevas = venderEntrada(ent.getEspectaculo().getNombre(), nuevaFecha, ent.getEmailUsuario(), contrasenia, nuevoSector, asientos);
        return nuevas.get(0);
    }

    @Override
    public double costoEntrada(String nombreEspectaculo, String fecha) {
        Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
        if (espectaculo == null) throw new RuntimeException("Espectáculo no encontrado");
        LocalDate fechaFuncion = LocalDate.parse(fecha, dateFormatter);
        Funcion funcion = espectaculo.getFunciones().get(fechaFuncion);
        if (funcion == null) throw new RuntimeException("Función no encontrada");
        return funcion.getPrecioBase();
    }

    @Override
    public double costoEntrada(String nombreEspectaculo, String fecha, String sector) {
        Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
        if (espectaculo == null) throw new RuntimeException("Espectáculo no encontrado");
        LocalDate fechaFuncion = LocalDate.parse(fecha, dateFormatter);
        Funcion funcion = espectaculo.getFunciones().get(fechaFuncion);
        if (funcion == null) throw new RuntimeException("Función no encontrada");
        Sede sede = sedes.get(funcion.getSede());
        double base = funcion.getPrecioBase();
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


    @Override
    public double totalRecaudado(String nombreEspectaculo) {
        double total = 0;
        for (IEntrada entrada : listarEntradasEspectaculo(nombreEspectaculo)) {
            total += entrada.precio();
        }
        return total;
    }

    @Override
    public double totalRecaudadoPorSede(String nombreEspectaculo, String nombreSede) {
        double total = 0;
        for (IEntrada entrada : listarEntradasEspectaculo(nombreEspectaculo)) {
            if (entrada instanceof Entrada ent) {
                Espectaculo esp = espectaculos.get(nombreEspectaculo);
                if (esp != null) {
                    Funcion funcion = esp.getFunciones().get(ent.getFecha());
                    if (funcion != null && funcion.getSede().equals(nombreSede)) {
                        total += entrada.precio();
                    }
                }
            }
        }
        return total;
    }


    @Override
    public String toString() {
        return "Ticketek{" +
                "usuarios=" + usuarios.size() +
                ", sedes=" + sedes.size() +
                ", espectaculos=" + espectaculos.size() +
                '}';
    }

    // Método auxiliar para generar códigos únicos de entrada
    private String generarCodigoEntrada() {
    return String.valueOf(System.nanoTime());
    }
}