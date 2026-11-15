package com.sena.articulos;

import java.sql.Connection;
import java.sql.SQLException;

import com.sena.articulos.config.ConexionBD;

/** Comprueba que la aplicación puede establecer una conexión JDBC. */
public final class PruebaConexion {

    private PruebaConexion() {
    }

    public static void main(String[] args) {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            System.out.println("Conexión JDBC establecida correctamente.");
            System.out.println("Base de datos: " + conexion.getCatalog());
        } catch (SQLException error) {
            System.err.println("No fue posible conectar con la base de datos.");
            System.err.println("Detalle: " + error.getMessage());
            System.err.println("Revisa el servicio MySQL, la base de datos y el archivo .env.");
        }
    }
}
