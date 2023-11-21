DROP TABLE IF EXISTS CANCIONES;
DROP TABLE IF EXISTS ALBUMES;

CREATE TABLE albumes
       (id INTEGER(4) AUTO_INCREMENT,
        titulo VARCHAR(40) UNIQUE,
        autor VARCHAR(40),
        genero VARCHAR(30),
        fecha DATE,
        precio DOUBLE,
        PRIMARY KEY(id));


CREATE TABLE canciones
       (id INTEGER(4) AUTO_INCREMENT,
        titulo VARCHAR(40) UNIQUE,
        duracion INTEGER(2),
        orden INTEGER(2),
        id_album INTEGER(4),
        PRIMARY KEY(id));
