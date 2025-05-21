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
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getContrasenia() { return contrasenia; }
    public String getEmail() { return email; }

    public boolean isRegistrado() { return true; } // O lógica real
    public boolean isEmailValido() { return email != null && email.contains("@"); }
    public boolean isContraseniaValida() { return contrasenia != null && contrasenia.length() >= 4; }

    public void agregarEntrada(Entrada entrada) {
        entradas.put(entrada.getCodigoEntrada(), entrada);
    }
    public Entrada getEntrada(String codigo) {
        return entradas.get(codigo);
    }

    public boolean eliminarEntrada(String codigo) {
        return entradas.remove(codigo) != null;
    }

    public Map<String, Entrada> getEntradas() {
        return entradas;
    }
}