package com.sena.articulos.modelo;

import java.math.BigDecimal;

/** Representa un artículo del catálogo */
public final class Articulo {

    private final int id;
    private final String nombre;
    private final BigDecimal precio;
    private final String descripcion;

    public Articulo(int id, String nombre, BigDecimal precio, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "ID: " + id
                + " | Nombre: " + nombre
                + " | Precio: $" + precio.toPlainString()
                + " | Descripción: " + descripcion;
    }
}
