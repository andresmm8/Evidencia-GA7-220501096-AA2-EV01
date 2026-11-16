package com.sena.articulos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.sena.articulos.config.ConexionBD;
import com.sena.articulos.modelo.Articulo;

/** Contiene las operaciones SQL (CRUD) de la entidad Articulo. */
public final class ArticuloDAO {

    public int insertar(Articulo articulo) throws SQLException {
        String sql = "INSERT INTO articulo (nombre, precio, descripcion) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            sentencia.setString(1, articulo.getNombre());
            sentencia.setBigDecimal(2, articulo.getPrecio());
            sentencia.setString(3, articulo.getDescripcion());

            int filasAfectadas = sentencia.executeUpdate();
            if (filasAfectadas != 1) {
                throw new SQLException("El artículo no pudo registrarse.");
            }

            try (ResultSet claves = sentencia.getGeneratedKeys()) {
                if (claves.next()) {
                    return claves.getInt(1);
                }
                throw new SQLException("No se recibió el ID generado para el artículo.");
            }
        }
    }

    public List<Articulo> listar() throws SQLException {
        String sql = "SELECT id, nombre, precio, descripcion FROM articulo ORDER BY id";
        List<Articulo> articulos = new ArrayList<>();

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql);
             ResultSet resultados = sentencia.executeQuery()) {

            while (resultados.next()) {
                articulos.add(convertirEnArticulo(resultados));
            }
        }

        return articulos;
    }

    public Optional<Articulo> buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, nombre, precio, descripcion FROM articulo WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);

            try (ResultSet resultados = sentencia.executeQuery()) {
                if (resultados.next()) {
                    return Optional.of(convertirEnArticulo(resultados));
                }
            }
        }

        return Optional.empty();
    }

    public boolean actualizar(Articulo articulo) throws SQLException {
        String sql = "UPDATE articulo SET nombre = ?, precio = ?, descripcion = ? WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, articulo.getNombre());
            sentencia.setBigDecimal(2, articulo.getPrecio());
            sentencia.setString(3, articulo.getDescripcion());
            sentencia.setInt(4, articulo.getId());

            return sentencia.executeUpdate() > 0;
        }
    }

    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM articulo WHERE id = ?";

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, id);
            return sentencia.executeUpdate() > 0;
        }
    }

    private Articulo convertirEnArticulo(ResultSet resultados) throws SQLException {
        return new Articulo(
                resultados.getInt("id"),
                resultados.getString("nombre"),
                resultados.getBigDecimal("precio"),
                resultados.getString("descripcion")
        );
    }
}
