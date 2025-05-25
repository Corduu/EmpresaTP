package ar.edu.ungs.prog2.ticketek;

import java.util.List;

public class depurador {
    public static void main(String[] args) {
        Ticketek ticketek = new Ticketek();

        // Registrar usuarios
        try {
            ticketek.registrarUsuario("juan@mail.com", "Juan", "Perez", "1234");
            ticketek.registrarUsuario("ana@mail.com", "Ana", "Gomez", "abcd");
            System.out.println("Usuarios registrados correctamente.");
        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }

        // Registrar sedes
        try {
            ticketek.registrarSede("Estadio1", "Calle 123", 10000);
            ticketek.registrarSede("Teatro1", "Avenida 456", 500, 10, new String[]{"Platea", "Palco"}, new int[]{300, 200}, new int[]{10, 20});
            System.out.println("Sedes registradas correctamente.");
        } catch (Exception e) {
            System.out.println("Error al registrar sede: " + e.getMessage());
        }

        // Registrar espectáculo
        try {
            ticketek.registrarEspectaculo("Show1");
            System.out.println("Espectáculo registrado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al registrar espectáculo: " + e.getMessage());
        }

        // Agregar función
        try {
            ticketek.agregarFuncion("Show1", "01/06/25", "Estadio1", 5000.0);
            ticketek.agregarFuncion("Show1", "02/06/25", "Teatro1", 3000.0);
            System.out.println("Funciones agregadas correctamente.");
        } catch (Exception e) {
            System.out.println("Error al agregar función: " + e.getMessage());
        }

        // Vender entradas para estadio
        try {
            List<IEntrada> entradas = ticketek.venderEntrada("Show1", "01/06/25", "juan@mail.com", "1234", 2);
            System.out.println("Entradas vendidas (estadio): " + entradas.size());
        } catch (Exception e) {
            System.out.println("Error al vender entrada (estadio): " + e.getMessage());
        }

        // Vender entradas para teatro
        try {
            int[] asientos = {1, 2};
            List<IEntrada> entradas = ticketek.venderEntrada("Show1", "02/06/25", "ana@mail.com", "abcd", "Platea", asientos);
            System.out.println("Entradas vendidas (teatro): " + entradas.size());
        } catch (Exception e) {
            System.out.println("Error al vender entrada (teatro): " + e.getMessage());
        }

        // Listar funciones
        try {
            String funciones = ticketek.listarFunciones("Show1");
            System.out.println("Funciones de Show1:\n" + funciones);
        } catch (Exception e) {
            System.out.println("Error al listar funciones: " + e.getMessage());
        }

        // Listar entradas de un usuario
        try {
            List<IEntrada> entradas = ticketek.listarTodasLasEntradasDelUsuario("juan@mail.com", "1234");
            System.out.println("Entradas de Juan: " + entradas.size());
            for (IEntrada ent : entradas) {
                if (ent instanceof Entrada) {
                    Entrada e = (Entrada) ent;
                    System.out.println("Entrada: fecha=" + e.fecha() + ", sector=" + e.sector() + ", precio=" + e.precio());
                } else {
                    System.out.println("Entrada: " + ent);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al listar entradas del usuario: " + e.getMessage());
        }

        // Anular una entrada
        try {
            List<IEntrada> entradas = ticketek.listarTodasLasEntradasDelUsuario("juan@mail.com", "1234");
            if (!entradas.isEmpty()) {
                boolean anulado = ticketek.anularEntrada(entradas.get(0), "1234");
                System.out.println("Anulación de entrada: " + anulado);
            }
        } catch (Exception e) {
            System.out.println("Error al anular entrada: " + e.getMessage());
        }

        // Cambiar una entrada (teatro)
        try {
            List<IEntrada> entradas = ticketek.listarTodasLasEntradasDelUsuario("ana@mail.com", "abcd");
            if (!entradas.isEmpty()) {
                IEntrada nueva = ticketek.cambiarEntrada(entradas.get(0), "abcd", "02/06/25", "Platea", 3);
                if (nueva instanceof Entrada) {
                    Entrada e = (Entrada) nueva;
                    System.out.println("Cambio de entrada realizado. Nuevo asiento: " + e.asiento());
                } else {
                    System.out.println("Cambio de entrada realizado.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cambiar entrada: " + e.getMessage());
        }

        // Consultar recaudación total
        try {
            double total = ticketek.totalRecaudado("Show1");
            System.out.println("Total recaudado Show1: $" + total);
        } catch (Exception e) {
            System.out.println("Error al consultar recaudación: " + e.getMessage());
        }

        // Consultar costo de entrada para diferentes sectores y fechas
        try {
            double costo1 = ticketek.costoEntrada("Show1", "01/06/25");
            double costo2 = ticketek.costoEntrada("Show1", "02/06/25", "Platea");
            System.out.println("Costo entrada Estadio1: $" + costo1);
            System.out.println("Costo entrada Teatro1 Platea: $" + costo2);
        } catch (Exception e) {
            System.out.println("Error al consultar costo de entrada: " + e.getMessage());
        }

        // Consultar recaudación por sede
        try {
            double recaudadoEstadio = ticketek.totalRecaudadoPorSede("Show1", "Estadio1");
            double recaudadoTeatro = ticketek.totalRecaudadoPorSede("Show1", "Teatro1");
            System.out.println("Recaudado en Estadio1: $" + recaudadoEstadio);
            System.out.println("Recaudado en Teatro1: $" + recaudadoTeatro);
        } catch (Exception e) {
            System.out.println("Error al consultar recaudación por sede: " + e.getMessage());
        }

        // Intentar operación errónea para probar validación
        try {
            ticketek.venderEntrada("Show1", "01/06/25", "noexiste@mail.com", "1234", 1);
        } catch (Exception e) {
            System.out.println("Prueba error usuario no existente: " + e.getMessage());
        }

        // Mostrar todas las entradas de todos los usuarios
        try {
            List<IEntrada> entradasJuan = ticketek.listarTodasLasEntradasDelUsuario("juan@mail.com", "1234");
            List<IEntrada> entradasAna = ticketek.listarTodasLasEntradasDelUsuario("ana@mail.com", "abcd");
            System.out.println("Entradas de Juan: " + entradasJuan.size());
            System.out.println("Entradas de Ana: " + entradasAna.size());
        } catch (Exception e) {
            System.out.println("Error al listar todas las entradas: " + e.getMessage());
        }

        // Mostrar estado general
        System.out.println(ticketek);
    }
}