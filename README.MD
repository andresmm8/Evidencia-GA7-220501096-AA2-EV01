1. Introducción 

Este repositorio contiene la elaboración de la evidencia GA7-220501096-AA2-EV01. Codificación de módulos del software elaborada de acuerdo a las características del software a desarrollar establecidas. 

2. Alcance

El desarrollo se limita al módulo de la entidad artículo (Para consultar los detalles de la entidad se sugiere consultar los 3 artefactos dentro de la carpeta [Artefactos](./Artefactos/).) por lo que no se incluyen funcionalidades dependientes de otras entidades, como la gestión de variantes de los artículos.

3. Funcionalidades 

El módulo permite realizar las siguientes operaciones sobre la entidad Artículo:

1.Registrar artículos.

2.Consultar todos los artículos registrados.

3.Consultar un artículo por su identificador.

4.Buscar artículos por nombre.

5.Contar los artículos registrados.

6.Actualizar la información de un artículo.

7.Eliminar artículos.

Para consultar las pruebas de las funcionalidades se sugiere consultar la carpeta [Pruebas](./Pruebas/)

4. Instrucciones para ejecutar el módulo

4.1. Requisitos previos

Antes de ejecutar el módulo, es necesario contar con las siguientes herramientas:

- Java JDK 17.

- Apache Maven.

- Un servidor de bases de datos MySQL

- Un gestor de bases de datos compatible con MySQL


4.2. Preparación de la base de datos

- Iniciar el servidor MySQL.

- Ejecutar el script [`crear_articulos.sql`](./sql/crear_articulos.sql) en el gestor de bases de datos para crear la base de datos "evidencia" y la tabla "articulo".

4.3. Configuración de la conexión

Crear el archivo .env en la raíz del proyecto y definir las variables de entorno necesarias para la conexión con MySQL:

- DB_URL: dirección de conexión a la base de datos.

- DB_USER: usuario de MySQL.

- DB_PASSWORD: contraseña del usuario de MySQL.

Verificar que la clase [`ConexionBD.java`](./src/main/java/com/sena/articulos/config/ConexionBD.java) utilice las variables de entorno definidas en el archivo .env para establecer la conexión.

Ejecutar la clase [`PruebaConexion.java`](./src/main/java/com/sena/articulos/PruebaConexion.java) en el entorno de desarrollo para comprobar que la conexión con la base de datos se establece correctamente.

4.4. Ejecución del módulo

Desde la raíz del repositorio, ejecutar el siguiente comando en la terminal para compilar el proyecto:

mvn clean package

Posteriormente, ejecutar la clase [`Principal.java`](./src/main/java/com/sena/articulos/Principal.java) desde el entorno de desarrollo.