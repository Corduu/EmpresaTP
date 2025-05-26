package ar.edu.ungs.prog2.ticketek;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

    @Override // Estadio
    public void registrarSede(String nombreSede, String direccion, int capacidadMaxima) {
        if (sedes.containsKey(nombreSede)) {
            throw new RuntimeException("Sede ya registrada");
        }
        Sede.validarDatos(nombreSede, direccion, capacidadMaxima);
        sedes.put(nombreSede, new Estadio(nombreSede, direccion, capacidadMaxima));
    }

    @Override // Teatro
    public void registrarSede(String nombreSede, String direccion, int capacidadMaxima, int asientosPorFila, String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
        if (sedes.containsKey(nombreSede)) {
            throw new RuntimeException("Sede ya registrada");
        }
        Sede.validarDatos(nombreSede, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad, porcentajeAdicional);
        sedes.put(nombreSede, new Teatro(nombreSede, direccion, capacidadMaxima, asientosPorFila, sectores, capacidad, porcentajeAdicional));
    }

    @Override // MiniEstadio
    public void registrarSede(String nombreSede, String direccion, int capacidadMaxima, int asientosPorFila, int cantidadPuestos, double precioConsumicion, String[] sectores, int[] capacidad, int[] porcentajeAdicional) {
        if (sedes.containsKey(nombreSede)) {
            throw new RuntimeException("Sede ya registrada");
        }
        Sede.validarDatos(nombreSede, direccion, capacidadMaxima, asientosPorFila, cantidadPuestos, precioConsumicion, sectores, capacidad, porcentajeAdicional);
        sedes.put(nombreSede, new MiniEstadio(nombreSede, direccion, capacidadMaxima, asientosPorFila, cantidadPuestos, precioConsumicion, sectores, capacidad, porcentajeAdicional));
    }

    @Override
    public void registrarUsuario(String email, String nombre, String apellido, String contrasenia) {
        if (usuarios.containsKey(email)) {
            throw new RuntimeException("Usuario ya registrado");
            }
        Usuario.validarDatos(email, nombre, apellido, contrasenia);
        usuarios.put(email, new Usuario(nombre, apellido, email, contrasenia));
    }

    @Override
    public void registrarEspectaculo(String nombre) {
        if (espectaculos.containsKey(nombre)) {
            throw new RuntimeException("Espectáculo ya registrado");
        }
        Espectaculo.validarDatos(nombre);
        espectaculos.put(nombre, new Espectaculo(nombre));
    }

    @Override
    public void agregarFuncion(String nombreEspectaculo, String fecha, String sede, double precioBase) {
        Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
        if (espectaculo == null) {
            throw new RuntimeException("Espectáculo no encontrado");
        }
        Funcion.validarDatos(nombreEspectaculo, fecha, sede, precioBase);
        LocalDate fechaFuncion = LocalDate.parse(fecha, dateFormatter);
        Sede sedeObj = sedes.get(sede);
        if (sedeObj == null) {
            throw new RuntimeException("Sede no encontrada");
        }
        sedeObj.validarDisponibleParaFuncion(fechaFuncion);
        espectaculo.validarFuncionNoRepetida(fechaFuncion);
        Funcion funcion = new Funcion(fechaFuncion, sedeObj, precioBase, nombreEspectaculo);
        espectaculo.agregarFuncion(fechaFuncion, funcion);
        sedeObj.agregarFuncion(fechaFuncion, nombreEspectaculo);
    }

    @Override // Estadio
    public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia, int cantidadEntradas) {
    if (nombreEspectaculo == null || fecha == null || email == null || contrasenia == null || cantidadEntradas <= 0) {
        throw new RuntimeException("Datos inválidos");
    }
    Usuario usuario = usuarios.get(email);
    if (usuario == null){
        throw new RuntimeException("Usuario no registrado");
    }
    usuario.validarEmail(email);
    usuario.validarContrasenia(contrasenia);
    Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
    if (espectaculo == null) {
        throw new RuntimeException("Espectáculo no registrado");
    }
    LocalDate fechaFuncion = LocalDate.parse(fecha, dateFormatter);
    Funcion funcion = espectaculo.getFunciones().get(fechaFuncion);
    if (funcion == null) {
        throw new RuntimeException("Función no encontrada");
    }
    Sede sede = sedes.get(funcion.sedeObj().nombreSede());
    sede.validarEsEstadio();
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

    @Override // MiniEstadio o Teatro
    public List<IEntrada> venderEntrada(String nombreEspectaculo, String fecha, String email, String contrasenia, String sector, int[] asientos) {
        if (nombreEspectaculo == null || fecha == null || email == null || contrasenia == null || sector == null || asientos == null || asientos.length == 0) {
            throw new RuntimeException("Datos inválidos");
        }
        Usuario usuario = usuarios.get(email);
        if (usuario == null){
            throw new RuntimeException("Usuario no encontrado");
        }
        usuario.validarEmail(email);
        usuario.validarContrasenia(contrasenia);
        Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
        if (espectaculo == null) {
            throw new RuntimeException("Espectáculo no encontrado");
        }
        LocalDate fechaFuncion = LocalDate.parse(fecha, dateFormatter);
        Funcion funcion = espectaculo.getFunciones().get(fechaFuncion);
        if (funcion == null) {
            throw new RuntimeException("Función no encontrada");
        }
        Sede sede = sedes.get(funcion.sedeObj().nombreSede());
        sede.validarEsNumerada();
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
            Sede sede = sedes.get(funcion.sedeObj().nombreSede());
            String fechaStr = fecha.format(dateFormatter);
            sb.append(funcion.descripcionParaListado(sede, fechaStr));
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public List<IEntrada> listarEntradasEspectaculo(String nombreEspectaculo) { 
        List<IEntrada> entradas = new ArrayList<>();
        for (Usuario usuario : usuarios.values()) {
            Iterator<Entrada> it = usuario.getEntradas().values().iterator();
            while (it.hasNext()) {
                Entrada entrada = it.next();
                if (entrada.espectaculo().nombre().equals(nombreEspectaculo)) {
                    entradas.add(entrada);
                }
            }
        }
        return entradas;
    }

    @Override
    public List<IEntrada> listarEntradasFuturas(String email, String contrasenia) {
        Usuario usuario = usuarios.get(email);
        if (usuario == null) throw new RuntimeException("Usuario no encontrado");
        usuario.validarEmail(email);
        usuario.validarContrasenia(contrasenia);
        List<IEntrada> futuras = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        for (Entrada entrada : usuario.getEntradas().values()) {
            if (entrada.esFutura(hoy)) {
                futuras.add(entrada);
            }
        }
        return futuras;
    }

    @Override
    public List<IEntrada> listarTodasLasEntradasDelUsuario(String email, String contrasenia) {
        Usuario usuario = usuarios.get(email);
        if (usuario == null) throw new RuntimeException("Usuario no encontrado");
        usuario.validarEmail(email);
        usuario.validarContrasenia(contrasenia);
        return new ArrayList<>(usuario.getEntradas().values());
    }

    @Override
    public boolean anularEntrada(IEntrada entrada, String contrasenia) {
        if (entrada == null || contrasenia == null) throw new RuntimeException("Datos inválidos");
        if (!(entrada instanceof Entrada)) throw new RuntimeException("Tipo de entrada inválido");
        Entrada ent = (Entrada) entrada;
        Usuario usuario = usuarios.get(ent.usuario());
        if (usuario == null) throw new RuntimeException("Usuario no encontrado");
        usuario.validarContrasenia(contrasenia);        
        if (!ent.esFutura(LocalDate.now())) return false;
        // Cambia aquí: si no existe, lanza excepción
        if (!usuario.getEntradas().containsKey(ent.codigo()))
            throw new RuntimeException("La entrada no existe");
        boolean eliminado = usuario.eliminarEntrada(ent.codigo());
        // Si maneja asientos(es numerada), libera el asiento
        Espectaculo espectaculo = ent.espectaculo();
        if (espectaculo != null) {
            Funcion funcion = espectaculo.getFunciones().get(ent.fecha());
            if (funcion != null) {
                funcion.liberarAsientoSiCorresponde(ent.sector(), ent.asiento());
            }
        }
        return eliminado;
    }

    @Override
    public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha) {
        if (entrada == null || contrasenia == null || fecha == null) throw new RuntimeException("Datos inválidos");
        if (!(entrada instanceof Entrada)) throw new RuntimeException("Tipo de entrada inválido");
        Entrada ent = (Entrada) entrada;
        Usuario usuario = usuarios.get(ent.usuario());
        if (usuario == null) throw new RuntimeException("Usuario no encontrado");
        usuario.validarContrasenia(contrasenia);
        if (!ent.esFutura(LocalDate.now())) throw new RuntimeException("La entrada original está en el pasado");
        anularEntrada(ent, contrasenia);
        List<IEntrada> nuevas = venderEntrada(ent.espectaculo().nombre(), fecha, ent.usuario(), contrasenia, 1);
        return nuevas.get(0);
    }

    @Override
    public IEntrada cambiarEntrada(IEntrada entrada, String contrasenia, String fecha, String sector, int asiento) {
        if (entrada == null || contrasenia == null || fecha == null || sector == null) throw new RuntimeException("Datos inválidos");
        if (!(entrada instanceof Entrada)) throw new RuntimeException("Tipo de entrada inválido");
        Entrada ent = (Entrada) entrada;
        Usuario usuario = usuarios.get(ent.usuario());
        if (usuario == null) throw new RuntimeException("Usuario no encontrado");
        usuario.validarContrasenia(contrasenia);
        if (!ent.esFutura(LocalDate.now())) throw new RuntimeException("La entrada original está en el pasado");
        anularEntrada(ent, contrasenia);
        int[] asientos = {asiento};
        List<IEntrada> nuevas = venderEntrada(ent.espectaculo().nombre(), fecha, ent.usuario(), contrasenia, sector, asientos);
        return nuevas.get(0);
    }

    @Override
    public double costoEntrada(String nombreEspectaculo, String fecha) {
        Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
        if (espectaculo == null) throw new RuntimeException("Espectáculo no encontrado");
        LocalDate fechaFuncion = LocalDate.parse(fecha, dateFormatter);
        Funcion funcion = espectaculo.getFunciones().get(fechaFuncion);
        if (funcion == null) throw new RuntimeException("Función no encontrada");
        return funcion.precioBase();
    }

    @Override
    public double costoEntrada(String nombreEspectaculo, String fecha, String sector) {
        Espectaculo espectaculo = espectaculos.get(nombreEspectaculo);
        if (espectaculo == null) throw new RuntimeException("Espectáculo no encontrado");
        LocalDate fechaFuncion = LocalDate.parse(fecha, dateFormatter);
        Funcion funcion = espectaculo.getFunciones().get(fechaFuncion);
        if (funcion == null) throw new RuntimeException("Función no encontrada");
        return funcion.calcularPrecioEntrada(sector);
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
                    Funcion funcion = esp.getFunciones().get(ent.fecha());
                    if (funcion != null && funcion.sedeObj().nombreSede().equals(nombreSede)) {
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
