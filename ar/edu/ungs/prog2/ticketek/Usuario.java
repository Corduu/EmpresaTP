package ar.edu.ungs.prog2.ticketek;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Representa un usuario del sistema Ticketek
public class Usuario {
    private final String nombre;
    private final String apellido;
    private final String email;
    private final String contrasenia;
    // Entradas compradas por el usuario, indexadas por código
    private final Map<String, Entrada> entradas = new HashMap<>();

    public Usuario(String nombre, String apellido, String email, String contrasenia) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contrasenia = contrasenia;
    }

        // Devuelve todas las entradas (copia)
    public List<Entrada> todasLasEntradas() {
        return new ArrayList<>(entradas.values());
    }

    // Devuelve solo las entradas futuras (según la fecha actual)
    public List<Entrada> entradasFuturas(LocalDate hoy) {
        List<Entrada> futuras = new ArrayList<>();
        for (Entrada entrada : entradas.values()) {
            if (entrada.esFutura(hoy)) {
                futuras.add(entrada);
            }
        }
        return futuras;
    }

    // Devuelve las entradas de un espectáculo específico
    public List<Entrada> entradasDeEspectaculo(String nombreEspectaculo) {
        List<Entrada> resultado = new ArrayList<>();
        for (Entrada entrada : entradas.values()) {
            if (entrada.espectaculo().nombre().equals(nombreEspectaculo)) {
                resultado.add(entrada);
            }
        }
        return resultado;
    }
    // Agrega una entrada al usuario
    public void agregarEntrada(Entrada entrada) {
        entradas.put(entrada.codigo(), entrada);
    }
    // Obtiene una entrada por su código
    public Entrada getEntrada(String codigo) {
        return entradas.get(codigo);
    }
    // solo elimina la entrada del mapa de entradas del usuario. (no anula la entrada a nivel de sistema)
    public boolean eliminarEntrada(String codigo) {
        return entradas.remove(codigo) != null;
    }
    // quité el getter de entradas para no romper el encapsulamiento
    // Verifica si el usuario tiene una entrada con ese código
    public boolean tieneEntrada(String codigo) {
        return entradas.containsKey(codigo);
    }

    // Validaciones de datos de usuario
    public static void validarDatos(String email, String nombre, String apellido, String contrasenia){
        if (email == null || !email.contains("@")) throw new RuntimeException("Email inválido");
        if (nombre == null || nombre.isEmpty()) throw new RuntimeException("Nombre inválido");
        if (apellido == null || apellido.isEmpty()) throw new RuntimeException("Apellido inválido");
        if (contrasenia == null || contrasenia.length() < 4) throw new RuntimeException("Contraseña inválida");
    }

    // Valida que el email coincida con el del usuario
    public void validarEmail(String email) {
        if (!this.email.equals(email)) throw new RuntimeException("Email incorrecto");
    }
    
    // Valida que la contraseña coincida con la del usuario
    public void validarContrasenia(String contrasenia) {
        if (!this.contrasenia.equals(contrasenia)) throw new RuntimeException("Contraseña incorrecta");
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " (" + email + ")";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return email.equals(usuario.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }
}