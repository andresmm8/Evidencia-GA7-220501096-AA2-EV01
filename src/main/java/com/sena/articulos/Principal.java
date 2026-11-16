
package com.sena.articulos;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.sena.articulos.dao.ArticuloDAO;
import com.sena.articulos.modelo.Articulo;

/** Menú de consola para administrar artículos. */
public final class Principal {

    private static final Scanner ENTRADA = new Scanner(System.in);
    private static final ArticuloDAO ARTICULO_DAO = new ArticuloDAO();

    private Principal() {
    }

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Selecciona una opción: ");

            try {
                switch (opcion) {
                    case 1 -> registrarArticulo();
                    case 2 -> listarArticulos();
                    case 3 -> contarArticulos();
                    case 4 -> consultarArticulo();
                    case 5 -> buscarPorNombre();
                    case 6 -> actualizarArticulo();
                    case 7 -> eliminarArticulo();
                    case 0 -> System.out.println("Aplicación finalizada.");
                    default -> System.out.println("Opción fuera del menú.");
                }
            } catch (SQLException error) {
                System.err.println("Error al acceder a MySQL: " + error.getMessage());
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n========== TIENDAXYZ - ARTÍCULOS ==========");
        System.out.println("1. Registrar artículo");
        System.out.println("2. Listar artículos");
        System.out.println("3. Contar artículos registrados");
        System.out.println("4. Consultar artículo por ID");
        System.out.println("5. Consultar artículo por nombre");
        System.out.println("6. Actualizar artículo");
        System.out.println("7. Eliminar artículo");
        System.out.println("0. Salir");
        System.out.println("===========================================");
    }

    private static void registrarArticulo() throws SQLException {
        System.out.println("\n--- REGISTRO DE ARTÍCULO ---");
        String nombre = leerTexto("Nombre: ", 150);
        BigDecimal precio = leerPrecio("Precio: ");
        String descripcion = leerTexto("Descripción: ", 2000);

        Articulo nuevoArticulo = new Articulo(0, nombre, precio, descripcion);
        int idGenerado = ARTICULO_DAO.insertar(nuevoArticulo);
        System.out.println("Artículo registrado correctamente. ID generado: " + idGenerado);
    }

    private static void listarArticulos() throws SQLException {
        System.out.println("\n--- LISTADO DE ARTÍCULOS ---");
        List<Articulo> articulos = ARTICULO_DAO.listar();

        if (articulos.isEmpty()) {
            System.out.println("No hay artículos registrados.");
            return;
        }

        for (Articulo articulo : articulos) {
            System.out.println(articulo);
        }
    }

    private static void contarArticulos() throws SQLException {
        System.out.println("\n--- TOTAL DE ARTÍCULOS ---");
        int total = ARTICULO_DAO.contar();

        System.out.println("Total de artículos registrados: " + total);
    }

    private static void consultarArticulo() throws SQLException {
        System.out.println("\n--- CONSULTA POR ID ---");
        int id = leerEnteroPositivo("ID del artículo: ");
        Optional<Articulo> articulo = ARTICULO_DAO.buscarPorId(id);

        if (articulo.isPresent()) {
            System.out.println(articulo.get());
        } else {
            System.out.println("No existe un artículo con el ID " + id + ".");
        }
    }

    private static void buscarPorNombre() throws SQLException {
        System.out.println("\n--- CONSULTA POR NOMBRE ---");
        String nombre = leerTexto("Nombre o fragmento del nombre: ", 150);
        List<Articulo> articulos = ARTICULO_DAO.buscarPorNombre(nombre);

        if (articulos.isEmpty()) {
            System.out.println("No se encontraron artículos con ese nombre.");
            return;
        }

        System.out.println("Artículos encontrados: " + articulos.size());

        for (Articulo articulo : articulos) {
            System.out.println(articulo);
        }
    }

    private static void actualizarArticulo() throws SQLException {
        System.out.println("\n--- ACTUALIZACIÓN DE ARTÍCULO ---");
        int id = leerEnteroPositivo("ID del artículo que deseas actualizar: ");
        Optional<Articulo> articuloActual = ARTICULO_DAO.buscarPorId(id);

        if (articuloActual.isEmpty()) {
            System.out.println("No existe un artículo con el ID " + id + ".");
            return;
        }

        System.out.println("Registro actual: " + articuloActual.get());
        String nombre = leerTexto("Nuevo nombre: ", 150);
        BigDecimal precio = leerPrecio("Nuevo precio: ");
        String descripcion = leerTexto("Nueva descripción: ", 2000);

        Articulo articuloModificado = new Articulo(id, nombre, precio, descripcion);
        boolean actualizado = ARTICULO_DAO.actualizar(articuloModificado);
        System.out.println(actualizado
                ? "Artículo actualizado correctamente."
                : "No se modificó ningún artículo.");
    }

    private static void eliminarArticulo() throws SQLException {
        System.out.println("\n--- ELIMINACIÓN DE ARTÍCULO ---");
        int id = leerEnteroPositivo("ID del artículo que deseas eliminar: ");
        Optional<Articulo> articulo = ARTICULO_DAO.buscarPorId(id);

        if (articulo.isEmpty()) {
            System.out.println("No existe un artículo con el ID " + id + ".");
            return;
        }

        System.out.println("Registro encontrado: " + articulo.get());
        System.out.print("¿Confirmas la eliminación? (s/n): ");
        String respuesta = ENTRADA.nextLine().trim();

        if (!respuesta.equalsIgnoreCase("s")) {
            System.out.println("Eliminación cancelada.");
            return;
        }

        boolean eliminado = ARTICULO_DAO.eliminar(id);
        System.out.println(eliminado
                ? "Artículo eliminado correctamente."
                : "No se eliminó ningún artículo.");
    }

    private static String leerTexto(String mensaje, int longitudMaxima) {
        while (true) {
            System.out.print(mensaje);
            String texto = ENTRADA.nextLine().trim();

            if (!texto.isEmpty() && texto.length() <= longitudMaxima) {
                return texto;
            }

            System.out.println("El texto debe tener entre 1 y " + longitudMaxima + " caracteres.");
        }
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = ENTRADA.nextLine().trim();

            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException error) {
                System.out.println("Ingresa un número entero válido.");
            }
        }
    }

    private static int leerEnteroPositivo(String mensaje) {
        while (true) {
            int numero = leerEntero(mensaje);

            if (numero > 0) {
                return numero;
            }

            System.out.println("El ID debe ser mayor que cero.");
        }
    }

    private static BigDecimal leerPrecio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = ENTRADA.nextLine().trim().replace(',', '.');

            try {
                BigDecimal precio = new BigDecimal(texto);

                if (precio.signum() >= 0
                        && precio.scale() <= 2
                        && precio.precision() - precio.scale() <= 10) {
                    return precio;
                }
            } catch (NumberFormatException error) {
                // Se solicita nuevamente el valor si no es un decimal válido.
            }

            System.out.println("Ingresa un precio no negativo con máximo 2 decimales. Ejemplo: 85000.00");
        }
    }
}
