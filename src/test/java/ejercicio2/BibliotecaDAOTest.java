package ejercicio2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilidades.BasesDatos;

public class BibliotecaDAOTest {

    BibliotecaDAO dao;

    @BeforeEach
    void setUp() {
        String ruta_script = "src/main/java/ejercicio2/script_datos.sql";
        BasesDatos.borrarDatos("biblioteca");
        BasesDatos.volcarDatos(ruta_script, "biblioteca");

        dao = new BibliotecaDAO("localhost", "biblioteca", "root", "");
    }

    @Test
    void insertarLibro() {
        dao.nuevoLibro("123GRRR","El Hobbit","J.R. Tolkien");
    }

    @Test
    void realizarPrestamo() {
        dao.realizarPrestamo("12325432GR", "62436732H");
    }

    @Test
    void masActivo() {
        System.out.println(dao.masActivo());
    }

    @Test
    void borrarSinUso() {
        dao.borrarSinUso();
    }
}
