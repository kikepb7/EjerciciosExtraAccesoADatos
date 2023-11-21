package ejercicio1;

import java.sql.*;

public class DiscograficaDAO {

    private String host;
    private String base_datos;
    private String usuario;
    private String password;

    public DiscograficaDAO(String host, String base_datos, String usuario, String password) {
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

    public Double aumentarPrecio(String genero, Double porcentaje) {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        double res = 0.0;

        try {
            conexion = establecerConexion();

            Integer albumId = returnId(conexion, genero, "albumes", "genero");

            if (albumId == null) {
                throw new RuntimeException("No existe el género " + genero);
            }

            // Incrementamos el precio un porcentaje de todos los albumes con el género especificado
            String sql_incrementarPorcentaje = "UPDATE albumes SET precio = ROUND(precio * (1 + ? / 100), 2) " +
                    "WHERE genero = ?";

            sentencia = conexion.prepareStatement(sql_incrementarPorcentaje);
            sentencia.setDouble(1, porcentaje);
            sentencia.setString(2, genero);

            int filasModificadas = sentencia.executeUpdate();

            if(filasModificadas > 0) {
                System.out.println("Se ha modificado el precio correctamente");
            } else {
                System.out.println("No existe el género " + genero);
            }

        } catch (SQLException e) {
            System.out.println("Error de SQL\n" + e.getMessage());
        } finally {
            cerrarConexion(conexion, sentencia, resultado);
        }

        return res;
    }

    public String masReciente() {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        StringBuilder res = new StringBuilder();

        try {
            conexion = establecerConexion();

            // Mostrar el album más reciente
            String sql_masReciente = "SELECT titulo FROM albumes " +
                    "ORDER BY fecha DESC LIMIT 1";

            sentencia = conexion.prepareStatement(sql_masReciente);
            resultado = sentencia.executeQuery();

            if(resultado.next()) {
                String tituloAlbum = resultado.getString("titulo");
                res.append("Albun más reciente: ").append(tituloAlbum);
            } else {
                res.append("Sin resultados");
            }

        } catch (SQLException e) {
            System.out.println("Error de SQL\n" + e.getMessage());
        } finally {
            cerrarConexion(conexion, sentencia, resultado);
        }

        return res.toString();
    }

    public String clasificacionAlbum(String titulo) {
        Connection conexion = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        StringBuilder res = new StringBuilder();

        try {
            conexion = establecerConexion();

            // Clasificación del album
            String sql_clasificacionAlbum = "SELECT a.titulo AS album, SUM(c.duracion) AS duracion_total " +
                    "FROM albumes a " +
                    "JOIN canciones c ON a.id = c.id_album " +
                    "WHERE a.titulo = ?";

            sentencia = conexion.prepareStatement(sql_clasificacionAlbum);
            sentencia.setString(1, titulo);
            resultado = sentencia.executeQuery();

            if(resultado.next()) {

                int duracionTotal = resultado.getInt("duracion_total");

                System.out.println("Duración total: " + duracionTotal);

                if(duracionTotal > 0.0 && duracionTotal < 20.0) {
                    res.append("Duración mínima");
                } else if (duracionTotal >= 20.0){
                    res.append("Duración máxima");
                } else {
                    throw new RuntimeException("El album con título " + titulo + " no existe");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error de SQL\n" + e.getMessage());
        } finally {
            cerrarConexion(conexion, sentencia, resultado);
        }

        return res.toString();
    }
}
