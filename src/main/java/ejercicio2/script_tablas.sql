DROP TABLE IF EXISTS PRESTAMOS;
DROP TABLE IF EXISTS LIBROS;
DROP TABLE IF EXISTS SOCIOS;


    CREATE TABLE SOCIOS
           (
            id INTEGER(4) AUTO_INCREMENT,
            dni VARCHAR(9) UNIQUE,
            nombre VARCHAR(30),
            telefono VARCHAR(9),
            PRIMARY KEY(id)
           );

    CREATE TABLE LIBROS
           (
            id INTEGER(4) AUTO_INCREMENT,
            ISBN VARCHAR(20) UNIQUE,
            titulo VARCHAR(50),
            autor VARCHAR(50),
            PRIMARY KEY(id)
           );

    CREATE TABLE PRESTAMOS
          (
            id INTEGER(4) AUTO_INCREMENT,
            libro INTEGER(4),
            socio INTEGER(4),
            fecha_prestamo DATE,
            fecha_devolucion DATE,
            PRIMARY KEY(id)
           );
