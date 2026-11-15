CREATE DATABASE IF NOT EXISTS evidencia
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE evidencia;

CREATE TABLE IF NOT EXISTS articulo (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT NOT NULL,
    precio DECIMAL(12, 2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT chk_articulo_precio CHECK (precio >= 0)
);

DESCRIBE articulo;
SELECT id, nombre, descripcion, precio FROM articulo ORDER BY id;
