/*
 *  Clase que se encarga de la persistencia en la BBDD
 *  Recibe y devuelve objetos
 */
package dao;

import excepciones.ExcepcionRestaurante;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Cocinero;
import modelo.Plato;

/**
 *
 * @author mfontana
 */
public class RestaurantDAO {

    Connection conexion;

    // ********************* Selects ****************************
    public List<Cocinero> selectAllCocineros() throws SQLException {
        String query = "select * from cocinero";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query);
        List<Cocinero> cocineros = new ArrayList<>();
        while (rs.next()) {
            Cocinero c = new Cocinero();
            c.setNombre(rs.getString("nombre"));
            c.setEdad(rs.getInt("edad"));
            c.setEspecialidad(rs.getString("especialidad"));
            c.setExperiencia(rs.getInt("experiencia"));
            c.setSexo(rs.getString("sexo"));
            c.setTelefono(rs.getString("telefono"));
            cocineros.add(c);
        }
        rs.close();
        st.close();
        return cocineros;
    }
    
     // Función que devuelve un cocinero a partir del nombre
    public Cocinero getCocineroByNombre(String nombre) throws  SQLException, ExcepcionRestaurante {
        Cocinero aux = new Cocinero(nombre);
        if (!existeCocinero(aux)) {
            throw new ExcepcionRestaurante("ERROR: No existe ningún cocinero con ese nombre");
        }
        String select = "select * from cocinero where nombre='" + nombre + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        Cocinero c = new Cocinero();
        if (rs.next()) {
            c.setNombre(rs.getString("nombre"));
            c.setEdad(rs.getInt("edad"));
            c.setEspecialidad(rs.getString("especialidad"));
            c.setExperiencia(rs.getInt("experiencia"));
            c.setSexo(rs.getString("sexo"));
            c.setTelefono(rs.getString("telefono"));
        }
        rs.close();
        st.close();
        return c;
    }
    
    
    // ********************* Updates ****************************
    public void modificarExperienciaCocinero(Cocinero c) throws SQLException, ExcepcionRestaurante {
        if (!existeCocinero(c)) {
            throw new ExcepcionRestaurante("ERROR: No existe un cocinero con ese nombre");
        }
        String update = "update cocinero set experiencia=? where nombre=?";
        PreparedStatement ps = conexion.prepareStatement(update);
        ps.setInt(1, c.getExperiencia());
        ps.setString(2, c.getNombre());
        ps.executeUpdate();
        ps.close();
    }
    

    // ********************* Inserts ****************************
    public void insertarPlato(Plato p) throws ExcepcionRestaurante, SQLException {
        if (existePlato(p)) {
            throw new ExcepcionRestaurante("ERROR: Ya existe un plato con ese nombre");
        }
        if (!existeCocinero(p.getCocinero())) {
            throw new ExcepcionRestaurante("ERROR: No existe el cocinero del plato");

        }
        String insert = "insert into plato values (?, ?, ?, ?)";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setString(1, p.getNombre());
        ps.setString(2, p.getTipo());
        ps.setDouble(3, p.getPrecio());
        ps.setString(4, p.getCocinero().getNombre());
        ps.executeUpdate();
        ps.close();
    }

    public void insertarCocinero(Cocinero c) throws SQLException, ExcepcionRestaurante {
        if (existeCocinero(c)) {
            throw new ExcepcionRestaurante("ERROR: Ya existe un cocinero con ese nombre");
        }
        String insert = "insert into cocinero values (?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = conexion.prepareStatement(insert);
        ps.setString(1, c.getNombre());
        ps.setString(2, c.getTelefono());
        ps.setString(3, c.getSexo());
        ps.setInt(4, c.getEdad());
        ps.setInt(5, c.getExperiencia());
        ps.setString(6, c.getEspecialidad());
        ps.executeUpdate();
        ps.close();
    }

    // ********************* Funciones auxiliares ****************************
    private boolean existePlato(Plato p) throws SQLException {
        String select = "select * from plato where nombre='" + p.getNombre() + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        boolean existe = false;
        if (rs.next()) {
            existe = true;
        }
        rs.close();
        st.close();
        return existe;
    }

    private boolean existeCocinero(Cocinero c) throws SQLException {
        String select = "select * from cocinero where nombre='" + c.getNombre() + "'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(select);
        boolean existe = false;
        if (rs.next()) {
            existe = true;
        }
        rs.close();
        st.close();
        return existe;
    }

    // ********************* Conectar / Desconectar ****************************
    public void conectar() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/restaurant";
        String user = "root";
        String pass = "root";
        conexion = DriverManager.getConnection(url, user, pass);
    }

    public void desconectar() throws SQLException {
        if (conexion != null) {
            conexion.close();
        }
    }

}
