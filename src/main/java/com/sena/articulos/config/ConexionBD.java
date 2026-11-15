package com.sena.articulos.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Centraliza la apertura de conexiones JDBC con la base de datos. */
public final class ConexionBD {

    private static final String URL_DEFECTO =
            "jdbc:mysql://localhost:3306/evidencia?connectionTimeZone=UTC";

    private ConexionBD() {
        // Evita crear instancias de esta clase de configuración.
    }

    public static Connection obtenerConexion() throws SQLException {
        String url = System.getenv("DB_URL");
        String usuario = System.getenv("DB_USER");
        String contrasena = System.getenv("DB_PASSWORD");

        if (url == null || url.isBlank()) {
            url = URL_DEFECTO;
        }
        if (usuario == null || usuario.isBlank()) {
            throw new SQLException("Falta DB_USER. Configura el archivo .env.");
        }
        if (contrasena == null) {
            throw new SQLException("Falta DB_PASSWORD. Configura el archivo .env.");
        }

        return DriverManager.getConnection(url, usuario, contrasena);
    }
}
