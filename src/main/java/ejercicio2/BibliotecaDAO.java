package ejercicio2;

import java.sql.*;
import java.time.LocalDate;

public class BibliotecaDAO {

    private String host;
    private String base_datos;
    private String usuario;
    private String password;

    public BibliotecaDAO(String host, String base_datos, String usuario, String password) {
        this.host = host;
        this.base_datos = base_datos;
        this.usuario = usuario;
        this.password = password;
    }

    public Connection establecerConexion() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.base_datos, this.usuario, this.password);
    }

    public void cerrarConexion(Connection conexion, PreparedStatement sentencia, ResultSet resultado) {
        try {
            if (resultado != null) resultado.close();

            if (sentencia != null) sentencia.close();

            if (conexion != null) conexion.close();

        } catch (SQLException exception) {
            System.out.println("Error al cerrar la conexión\n" + exception.getMessage());
            exception.printStackTrace();
        }
    }

    /*
    MÉTODOS AUXILIARES
     */
    private Integer returnId(Connection connection, String parameterValue, String tableName, String parameter) {
        PreparedStatement sentencia = null;
        ResultSet result = null;
        Integer id = null;

        try {
            String sql_id = "SELECT id FROM " + tableName +
                    " WHERE " + parameter + " = ?";

            sentencia = connection.prepareStatement(sql_id);
            sentencia.setString(1, parameterValue);
            result = sentencia.executeQuery();

            if(result.next()) {
                id = result.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println("Error de SQL\n" + e.getMessage());
        } finally {
            // Do not close the connection here
            cerrarConexion(null, sentencia, result);
        }
        return id;
    }

    public void nuevoLibro(String ISBN, String titulo, String autor) {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = establecerConexion();

            Integer idLibro = returnId(conexion, ISBN, "libros", "ISBN");

            if (idLibro != null) {
                throw new RuntimeException("El libro con ISBN - " + ISBN + " ya existe");
            }

            // Inserta nuevo libro
            String sql_insertarLibro = "INSERT INTO libros " +
                    "VALUES (NULL, ?, ?, ?)";

            sentencia = conexion.prepareStatement(sql_insertarLibro);
            sentencia.setString(1, ISBN);
            sentencia.setString(2, titulo);
            sentencia.setString(3, autor);

            int filasInsertadas = sentencia.executeUpdate();

            if (filasInsertadas > 0) {
                System.out.println("Libro insertado correctamente!");
            } else {
                System.out.println("Ha habido un problema al insertar el libro");
            }
        } catch (SQLException e) {
            System.out.println("Error de SQL\n" + e.getMessage());
        } finally {
            cerrarConexion(conexion, sentencia, resultado);
        }
    }

    public void realizarPrestamo(String ISBN, String dni) {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = establecerConexion();

            Integer idLibro = returnId(conexion, ISBN, "libros", "ISBN");
            Integer idSocio = returnId(conexion, dni, "socios", "dni");

            if (idLibro == null) {
                throw new RuntimeException("No existe el libro con ISBN - " + ISBN);
            } else if (idSocio == null) {
                throw new RuntimeException("No existe el socio con DNI - " + dni);
            }

            LocalDate fechaInicio = LocalDate.now();
            LocalDate fechaDevolucion = fechaInicio.plusDays(10);

            // Añadir un préstamo con fecha actual y devolución en 10 días
            String sql_insertarPrestamo = "INSERT INTO prestamos " +
                    "VALUES (NULL, ?, ?, ?, ?)";

            sentencia = conexion.prepareStatement(sql_insertarPrestamo);
            sentencia.setInt(1, idLibro);
            sentencia.setInt(2, idSocio);
            sentencia.setDate(3, Date.valueOf(fechaInicio));
            sentencia.setDate(4, Date.valueOf(fechaDevolucion));

            int filasInsertadas = sentencia.executeUpdate();

            if (filasInsertadas > 0) {
                System.out.println("Préstamo realizado correctamente!");
            } else {
                System.out.println("Ha habido un problema al realizar el préstamo");
            }

        } catch (SQLException e) {
            System.out.println("Error de SQL\n" + e.getMessage());
        } finally {
            cerrarConexion(conexion, sentencia, resultado);
        }
    }

    public String masActivo() {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        StringBuilder res = new StringBuilder();

        try {
            conexion = establecerConexion();

            // Devolver el socio que hace más préstamos
            String sql_masActivo = "SELECT s.dni, s.nombre, COUNT(p.id) AS cantidad_prestamos " +
                    "FROM socios s " +
                    "JOIN prestamos p ON s.id = p.socio " +
                    "GROUP BY s.id, s.dni, s.nombre " +
                    "ORDER BY cantidad_prestamos DESC " +
                    "LIMIT 1";

            sentencia = conexion.prepareStatement(sql_masActivo);
            resultado = sentencia.executeQuery();

            while(resultado.next()) {
                String dni = resultado.getString("dni");
                String nombre = resultado.getString("nombre");
                int cantidadPrestamos = resultado.getInt("cantidad_prestamos");

                res.append("El socio más activo es: ").append(nombre).append(", con DNI: ").append(dni).append(", con ").append(cantidadPrestamos).append(" préstamos.");
            }

        } catch (SQLException e) {
            System.out.println("Error de SQL\n" + e.getMessage());
        } finally {
            cerrarConexion(conexion, sentencia, resultado);
        }

        return res.toString();
    }

    public void borrarSinUso() {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            conexion = establecerConexion();

            String sql_borrarLibros = "DELETE FROM libros " +
                    "WHERE id NOT IN (SELECT DISTINCT libro FROM prestamos)";

            sentencia = conexion.prepareStatement(sql_borrarLibros);

            int filasBorradas = sentencia.executeUpdate();

            if (filasBorradas > 0) {
                System.out.println("\nSe han borrado correctamente " + filasBorradas + " libros que no tenian préstamo.");
            } else {
                System.out.println("No hay libros en préstamo para borrar");
            }
        } catch (SQLException e) {
            System.out.println("Error de SQL\n" + e.getMessage());
        } finally {
            cerrarConexion(conexion, sentencia, resultado);
        }
    }
}
