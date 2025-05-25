package ar.edu.ungs.prog2.ticketek;

import java.util.HashMap;
import java.util.Map;

public class Usuario {
    private String nombre;
    private String apellido;
    private String email;
    private String contrasenia;
    private Map<String, Entrada> entradas = new HashMap<>();

    public Usuario(String nombre, String apellido, String email, String contrasenia) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contrasenia = contrasenia;
    }

    public boolean isRegistrado() { return true; } // O lógica real
    public boolean isEmailValido() { return email != null && email.contains("@"); }
    public boolean isContraseniaValida() { return contrasenia != null && contrasenia.length() >= 4; }

    public void agregarEntrada(Entrada entrada) {
        entradas.put(entrada.codigo(), entrada);
    }
    public Entrada getEntrada(String codigo) {
        return entradas.get(codigo);
    }
    // solo elimina la entrada del mapa de entradas del usuario. (no anula la entrada a nivel de sistema)
    public boolean eliminarEntrada(String codigo) {
        return entradas.remove(codigo) != null;
    }

    public Map<String, Entrada> getEntradas() {
        return entradas;
    }

    public static void validarDatos(String email, String nombre, String apellido, String contrasenia){
        if (email == null || email.isEmpty()) {
            throw new RuntimeException("El email no puede ser nulo o vacío");
        }
        if (!email.contains("@")) {
            throw new RuntimeException("El email debe contener '@'");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new RuntimeException("El nombre no puede ser nulo o vacío");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new RuntimeException("El apellido no puede ser nulo o vacío");
        }
        if (contrasenia == null || contrasenia.isEmpty()) {
            throw new RuntimeException("La contraseña no puede ser nula o vacía");
        }
        if (contrasenia.length() < 4) {
            throw new RuntimeException("La contraseña debe tener al menos 4 caracteres");
        }
    }
    
    public void validarEmail(String email) {
    if (!this.email.equals(email)) {
        throw new RuntimeException("Email incorrecto");
        }
    }

    public void validarContrasenia(String contrasenia) {
        if (!this.contrasenia.equals(contrasenia)) {
            throw new RuntimeException("Contraseña incorrecta");
        }
    }
}