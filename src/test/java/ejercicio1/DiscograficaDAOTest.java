package ejercicio1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utilidades.BasesDatos;

public class DiscograficaDAOTest {

    DiscograficaDAO dao;

    @BeforeEach
    void setUp() {
        String ruta_script = "src/main/java/ejercicio1/script_datos.sql";
        BasesDatos.borrarDatos("discografica");
        BasesDatos.volcarDatos(ruta_script, "discografica");

        dao = new DiscograficaDAO("localhost", "discografica", "root", "");
    }

    @Test
    void incrementarPrecio() {
        dao.aumentarPrecio("Rock", 10.0);
    }

    @Test
    void masReciente() {
        System.out.println(dao.masReciente());
    }

    @Test
    void clasificacionAlbum() {
        System.out.println(dao.clasificacionAlbum("Death Magnetsic"));
    }
}
