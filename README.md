# Proyecto Spring - Tienda de Dulces

Proyecto para la administración de entidades de una tienda de dulces caseros.

# Requerimientos

* Java jdk 17

* Crear en el gerente de base de datos (Heidi, DBeaber, etc), una base de datos con nombre **tienda_dulces**.

# Ejecución

Extraer el archivo .zip, ejecutar los comandos `mvn clean install` por parte de una CLI, o desde un IDE.

Ejecutar el programa desde un IDE, o con el comando `mvn spring-boot:run`.

Al correr el programa, se añadirán registros a la base datos, con el fin de llenar las tablas para propósito de demostración.

# Flujo 

1. Presionar botón registrar. Registrar nueva cuenta de cliente.

2. Presionar el botón login, acceder con el correo y la contraseña utilizados al momento de registrarse.

3. Tambien se puede ingresar con un usuario que ya esta cargado en la base de datos usuario: **d.sanvicente.e@gmail.com** contraseña: **contrasena437**, es el único en la base que tiene un carrito cargado previamente   

4. Verificar que el login funcionó. Se verán más opciones en la barra de navegación del proyecto.

5. Interactuar con las funcionalidades CRUD del proyecto.Las clases itemCarrito y lineaOrden son internas, sólo se colocaron en el navbar para que pusieron probarse.
