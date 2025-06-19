package ar.edu.ungs.prog2.ticketek;

// Representa el sector "CAMPO" de un estadio (sin asientos numerados)
public class Campo extends Sector {
    public Campo(int capacidad, int porcentajeAdicional) {
        super("CAMPO", capacidad, porcentajeAdicional);
    }
}