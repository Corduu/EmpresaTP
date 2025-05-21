// package ar.edu.ungs.prog2.ticketek;

// import java.util.List;
// import java.util.Map;

// public class DepuradorTicketek {

//     public static void depurarTicketek(ITicketek ticketek) {
//         System.out.println("----- INICIO DEPURACION TICKETEK -----");

//         // Usuarios
//         System.out.println("Usuarios registrados:");
//         if (ticketek instanceof Ticketek) {
//             Ticketek t = (Ticketek) ticketek;
//             for (Map.Entry<String, Usuario> entry : t.getUsuarios().entrySet()) {
//                 Usuario u = entry.getValue();
//                 System.out.println("Usuario: " + u.getEmail() + " - " + u.getNombre() + " " + u.getApellido());
//                 System.out.println("  Entradas:");
//                 for (IEntrada e : u.getEntradas()) {
//                     System.out.println("    " + e);
//                 }
//             }

//             // Espectáculos y funciones
//             System.out.println("\nEspectáculos registrados:");
//             for (Map.Entry<String, Espectaculo> entry : t.getEspectaculos().entrySet()) {
//                 Espectaculo esp = entry.getValue();
//                 System.out.println("Espectáculo: " + esp.getNombre() + " (código: " + esp.getCodigoEspectaculo() + ")");
//                 for (Funcion f : esp.getFunciones().values()) {
//                     System.out.println("  Funcion: " + f.getFecha() + " - " + f.getSede() + " - Precio base: " + f.getPrecioBase());
//                 }
//             }

//             // Sedes
//             System.out.println("\nSedes registradas:");
//             for (Map.Entry<String, Sede> entry : t.getSedes().entrySet()) {
//                 Sede s = entry.getValue();
//                 System.out.println("Sede: " + s.getNombre() + " - Dirección: " + s.getDireccion() + " - Capacidad: " + s.getCapacidadMaxima());
//             }
//         }

//         // Entradas por espectáculo
//         System.out.println("\nEntradas por espectáculo:");
//         String[] espectaculos = {"Coldplay en vivo", "La sirenita", "Stand up Comedy", "Ballet Clásico", "El Rey Leon"};
//         for (String esp : espectaculos) {
//             List<IEntrada> entradas = ticketek.listarEntradasEspectaculo(esp);
//             System.out.println("Espectáculo: " + esp + " - Entradas vendidas: " + entradas.size());
//             for (IEntrada e : entradas) {
//                 System.out.println("  " + e);
//             }
//         }

//         System.out.println("----- FIN DEPURACION TICKETEK -----");
//     }


//     //podemos agregar más métodos de depuración aquí si es necesario para comprobar muchas mas cosas
// }