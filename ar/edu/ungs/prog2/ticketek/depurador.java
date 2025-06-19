package ar.edu.ungs.prog2.ticketek;

import java.time.LocalDate;
import java.util.List;

public class depurador {
    public static void main(String[] args) {
        Ticketek ticketek = new Ticketek();

        String[] sectores_teatro = {"VIP", "Comun", "Baja", "Alta"};
        int[] capacidad_teatro = {100, 200, 300, 400};
        int[] capacidad_miniestadio = {50, 100, 150, 200};
        int[] porcentajeAdicionalTeatro = {70, 40, 50, 0};
        int asientosPorFilaTeatro = 30;

        // Registrar usuarios
        try {
            ticketek.registrarUsuario("nores@campus.ungs.edu.ar", "Jose", "Nores", "1234");
            ticketek.registrarUsuario("javierm@campus.ungs.edu.ar", "Javier", "Marenco", "1234");
            ticketek.registrarUsuario("ana@mail.com", "Ana", "Gomez", "abcd");
            ticketek.registrarUsuario("juan@mail.com", "Juan", "Perez", "pass");
            System.out.println("Usuarios registrados correctamente.");
        } catch (Exception e) {
            System.out.println("Error al registrar usuario: " + e.getMessage());
        }

        // Registrar sedes
        try {
            ticketek.registrarSede("El monumental", "calle 1", 100);
            ticketek.registrarSede("La bombonera", "calle 2", 200);
            ticketek.registrarSede("Mario Kempes", "avenida 123", 300);
            ticketek.registrarSede("Estadio Único", "boulevard 456", 400);

            ticketek.registrarSede("Teatro Gran Rex", "calle 3", 1000, asientosPorFilaTeatro, sectores_teatro, capacidad_teatro, porcentajeAdicionalTeatro);
            ticketek.registrarSede("Teatro Colón", "libertad 621", 1000, asientosPorFilaTeatro, sectores_teatro, capacidad_teatro, porcentajeAdicionalTeatro);
            ticketek.registrarSede("Teatro San Martín", "avenida corrientes 1530", 1000, asientosPorFilaTeatro, sectores_teatro, capacidad_teatro, porcentajeAdicionalTeatro);

            ticketek.registrarSede("Estadio mini 1", "calle 4", 500, asientosPorFilaTeatro, 30, 25000.0, sectores_teatro, capacidad_miniestadio, porcentajeAdicionalTeatro);
            ticketek.registrarSede("Mini Arena Norte", "pasaje 5", 500, asientosPorFilaTeatro, 30, 20000.0, sectores_teatro, capacidad_miniestadio, porcentajeAdicionalTeatro);
            ticketek.registrarSede("Microestadio Sur", "pje. 10", 500, asientosPorFilaTeatro, 30, 15000.0, sectores_teatro, capacidad_miniestadio, porcentajeAdicionalTeatro);

            System.out.println("Sedes registradas correctamente.");
        } catch (Exception e) {
            System.out.println("Error al registrar sede: " + e.getMessage());
        }

        // Registrar espectáculos y funciones
        try {
            String[] fechasSirenita = {"25/07/25", "28/07/25", "30/07/25", "31/07/25", "01/08/25"};
            String[] sedesSirenita = {"Teatro Gran Rex", "Teatro Gran Rex", "Teatro Colón", "Teatro Colón", "Teatro Gran Rex"};
            String[] fechasReyLeon = {"26/07/25", "29/07/25", "31/08/25", "31/09/25", "01/10/25"};
            String[] sedesReyLeon = {"Teatro Gran Rex", "Teatro Gran Rex", "Teatro Colón", "Teatro Colón", "Teatro Gran Rex"};
            String[] fechasColdplay = {"25/07/25", "28/07/25", "30/07/25", "31/07/25", "01/08/25"};
            String[] sedesColdplay = {"La bombonera","La bombonera","La bombonera","La bombonera","La bombonera"};
            String[] fechasStandUp = {"10/04/25","25/07/25", "28/07/25", "30/07/25", "31/07/25", "01/08/25"};
            String[] sedesStandUp = {"Mini Arena Norte","Mini Arena Norte","Mini Arena Norte","Microestadio Sur","Mini Arena Norte","Mini Arena Norte"};
            String[] fechasBallet = {"01/03/25","25/07/25"};
            String[] sedesBallet = {"Microestadio Sur", "Microestadio Sur"};

            registrarEspectaculo(ticketek, "La sirenita", fechasSirenita, sedesSirenita, 50000.0);
            registrarEspectaculo(ticketek, "El Rey Leon", fechasReyLeon, sedesReyLeon, 60000.0);
            registrarEspectaculo(ticketek, "Coldplay en vivo", fechasColdplay, sedesColdplay, 90000.0);
            registrarEspectaculo(ticketek, "Stand up Comedy", fechasStandUp, sedesStandUp, 110000.0);
            registrarEspectaculo(ticketek, "Ballet Clásico", fechasBallet, sedesBallet, 180000.0);

            System.out.println("Espectáculos y funciones registrados correctamente.");
        } catch (Exception e) {
            System.out.println("Error al registrar espectáculos/funciones: " + e.getMessage());
        }

        // Vender entradas para varios usuarios y espectáculos
        try {
            ticketek.venderEntrada("Coldplay en vivo", "01/08/25", "nores@campus.ungs.edu.ar", "1234", 3);
            ticketek.venderEntrada("La sirenita", "28/07/25", "javierm@campus.ungs.edu.ar", "1234", "Comun", new int[]{1, 2, 3, 4});
            ticketek.venderEntrada("La sirenita", "25/07/25", "nores@campus.ungs.edu.ar", "1234", "Baja", new int[]{1, 2, 3, 4});
            ticketek.venderEntrada("Stand up Comedy", "30/07/25", "nores@campus.ungs.edu.ar", "1234", "Alta", new int[]{1, 2, 3, 4});
            ticketek.venderEntrada("Stand up Comedy", "10/04/25", "javierm@campus.ungs.edu.ar", "1234", "VIP", new int[]{1, 2, 3, 4});
            ticketek.venderEntrada("Stand up Comedy", "10/04/25", "nores@campus.ungs.edu.ar", "1234", "VIP", new int[]{11, 12, 13, 14});
            ticketek.venderEntrada("Ballet Clásico", "01/03/25", "nores@campus.ungs.edu.ar", "1234", "Baja", new int[]{1, 2, 3, 4});
            ticketek.venderEntrada("Coldplay en vivo", "28/07/25", "ana@mail.com", "abcd", 2);
            ticketek.venderEntrada("Coldplay en vivo", "25/07/25", "juan@mail.com", "pass", 1);
            System.out.println("Entradas vendidas correctamente.");
        } catch (Exception e) {
            System.out.println("Error al vender entradas: " + e.getMessage());
        }

        // Mostrar todas las funciones de todos los espectáculos
        for (String espectaculo : new String[]{"La sirenita", "El Rey Leon", "Coldplay en vivo", "Stand up Comedy", "Ballet Clásico"}) {
            try {
                String funciones = ticketek.listarFunciones(espectaculo);
                System.out.println("Funciones de " + espectaculo + ":\n" + funciones);
            } catch (Exception e) {
                System.out.println("Error al listar funciones de " + espectaculo + ": " + e.getMessage());
            }
        }

        // Mostrar todas las entradas de todos los usuarios
        for (String email : new String[]{"nores@campus.ungs.edu.ar", "javierm@campus.ungs.edu.ar", "ana@mail.com", "juan@mail.com"}) {
            try {
                List<IEntrada> entradas = ticketek.listarTodasLasEntradasDelUsuario(email, email.equals("ana@mail.com") ? "abcd" : (email.equals("juan@mail.com") ? "pass" : "1234"));
                System.out.println("Entradas de " + email + ": " + entradas.size());
                for (IEntrada ent : entradas) {
                    System.out.println("  " + ent);
                }
            } catch (Exception e) {
                System.out.println("Error al listar entradas del usuario " + email + ": " + e.getMessage());
            }
        }

        // Anular una entrada de Javier
        try {
            List<IEntrada> entradas = ticketek.listarTodasLasEntradasDelUsuario("javierm@campus.ungs.edu.ar", "1234");
            if (!entradas.isEmpty()) {
                boolean anulado = ticketek.anularEntrada(entradas.get(0), "1234");
                System.out.println("Anulación de entrada de Javier: " + anulado);
            }
        } catch (Exception e) {
            System.out.println("Error al anular entrada: " + e.getMessage());
        }

        // Cambiar una entrada de Ana
        try {
            List<IEntrada> entradas = ticketek.listarTodasLasEntradasDelUsuario("ana@mail.com", "abcd");
            if (!entradas.isEmpty()) {
                IEntrada nueva = ticketek.cambiarEntrada(entradas.get(0), "abcd", "28/07/25", "VIP", 1);
                System.out.println("Cambio de entrada de Ana realizado: " + nueva);
            }
        } catch (Exception e) {
            System.out.println("Error al cambiar entrada: " + e.getMessage());
        }

        // Consultar recaudación total y por sede para varios espectáculos
        for (String espectaculo : new String[]{"Stand up Comedy", "Coldplay en vivo", "La sirenita"}) {
            try {
                double total = ticketek.totalRecaudado(espectaculo);
                System.out.println("Total recaudado " + espectaculo + ": $" + total);
            } catch (Exception e) {
                System.out.println("Error al consultar recaudación de " + espectaculo + ": " + e.getMessage());
            }
        }
        for (String sede : new String[]{"Microestadio Sur", "La bombonera", "Teatro Gran Rex"}) {
            try {
                double total = ticketek.totalRecaudadoPorSede("Stand up Comedy", sede);
                System.out.println("Total recaudado Stand up Comedy en " + sede + ": $" + total);
            } catch (Exception e) {
                System.out.println("Error al consultar recaudación por sede: " + e.getMessage());
            }
        }

        // Consultar costo de entrada para diferentes sectores y fechas
        try {
            double costo1 = ticketek.costoEntrada("La sirenita", "28/07/25", "Comun");
            double costo2 = ticketek.costoEntrada("Coldplay en vivo", "30/07/25");
            System.out.println("Costo entrada La sirenita (Comun): $" + costo1);
            System.out.println("Costo entrada Coldplay en vivo (campo): $" + costo2);
        } catch (Exception e) {
            System.out.println("Error al consultar costo de entrada: " + e.getMessage());
        }
        

        // ===========================================
        //  Demostración de uso de equals y hashCode
        // ===========================================

        // Usuario
        System.out.println("\n--- Comparación de Usuario ---");
        Usuario u1 = new Usuario("Franco", "Mastantuono", "francoMastantuono@mail.com", "abcd");
        Usuario u2 = new Usuario("Franco", "Mastantuono", "francoMastantuono@mail.com", "abcd");
        System.out.println("Usuario: u1.equals(u2)? " + u1.equals(u2)); // true esperado
        System.out.println("Usuario: u1.hashCode() == u2.hashCode()? " + (u1.hashCode() == u2.hashCode())); // true esperado

        // Sede (usando Teatro como subclase concreta)
        System.out.println("\n--- Comparación de Sede (Teatro) ---");
        Sede s1 = new Teatro("El uno", "Calle 2", 200, 30, java.util.Arrays.asList(new Platea("VIP", 200, 70)));
Sede s2 = new Teatro("El uno", "Otra dirección", 500, 30, java.util.Arrays.asList(new Platea("VIP", 500, 70)));
        System.out.println("Sede: s1.equals(s2)? " + s1.equals(s2)); // true esperado
        System.out.println("Sede: s1.hashCode() == s2.hashCode()? " + (s1.hashCode() == s2.hashCode())); // true esperado

        // Espectaculo
        System.out.println("\n--- Comparación de Espectaculo ---");
        Espectaculo e1 = new Espectaculo("Los piojos en vivo");
        Espectaculo e2 = new Espectaculo("Los piojos en vivo");
        System.out.println("Espectaculo: e1.equals(e2)? " + e1.equals(e2)); // true esperado
        System.out.println("Espectaculo: e1.hashCode() == e2.hashCode()? " + (e1.hashCode() == e2.hashCode())); // true esperado

        // Sector
        System.out.println("\n--- Comparación de Sector ---");
        Sector sec1 = new Platea("VIP", 100, 0);
        Sector sec2 = new Platea("VIP", 100, 0);
        System.out.println("Sector: sec1.equals(sec2)? " + sec1.equals(sec2)); // true esperado
        System.out.println("Sector: sec1.hashCode() == sec2.hashCode()? " + (sec1.hashCode() == sec2.hashCode())); // true esperado

        // Entrada
        System.out.println("\n--- Comparación de Entrada ---");
        Entrada en1 = new Entrada(LocalDate.of(2025, 7, 25), "VIP", e1, "123", "ana@mail.com");
        Entrada en2 = new Entrada(LocalDate.of(2025, 7, 25), "VIP", e1, "123", "ana@mail.com");
        System.out.println("Entrada: en1.equals(en2)? " + en1.equals(en2)); // true esperado
        System.out.println("Entrada: en1.hashCode() == en2.hashCode()? " + (en1.hashCode() == en2.hashCode())); // true esperado

        // ===========================
        // Mostrar estado general
        // ===========================
        System.out.println(ticketek);
    }

    private static void registrarEspectaculo(Ticketek ticketek, String nombre, String[] fechas, String[] sedes, double precioBase) {
        ticketek.registrarEspectaculo(nombre);
        for (int i = 0; i < fechas.length; i++) {
            ticketek.agregarFuncion(nombre, fechas[i], sedes[i], precioBase);
        }
    }
}