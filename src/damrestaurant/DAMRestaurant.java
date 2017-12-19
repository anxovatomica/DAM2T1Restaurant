/*
 * Clase para testear que nuestro programa funciona
 */
package damrestaurant;

import dao.RestaurantDAO;
import excepciones.ExcepcionRestaurante;
import java.sql.SQLException;
import java.util.List;
import modelo.Cocinero;
import modelo.Plato;

/**
 *
 * @author mfontana
 */
public class DAMRestaurant {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Necesitamos un objeto de tipo RestaurantDAO
        RestaurantDAO restaurantDAO = new RestaurantDAO();

        // Datos de test
        Cocinero c1 = new Cocinero("Maria Lapera", "666554433", "Mujer", 33, 6, "Postres");
        Cocinero c2 = new Cocinero("Karlos Arguiñano", "33333333", "Mujer", 23, 0, "Entrantes");
        Cocinero c3 = new Cocinero("Anonimo", "888444568", "Anonimo", 40, 10, "Platos principales");
        Cocinero c4 = new Cocinero("Cocinero Auxiliar", "999999999", "Auxiliar", 10, 10, "Auxiliar");
        Plato p1 = new Plato("Macedonia", "Postre", 4.20, c1);
        Plato p2 = new Plato("Espaguetis carbonara", "Platos principales", 6.20, c2);
        Plato p3 = new Plato("Ensalada de la huerta", "Entrantes", 9.90, c3);

        // TEST:        
        System.out.println("************************************************************");
        System.out.println("Testeando conexión con la base de datos...");
        try {
            restaurantDAO.conectar();
            System.out.println("Establecida la conexión.");

            System.out.println("************************************************************");
            System.out.println("Testeando insert cocinero " + c4.getNombre());
            altaCocinero(restaurantDAO, c4);
            System.out.println("************************************************************");
            System.out.println("Testeando insert cocinero duplicado " + c4.getNombre());
            altaCocinero(restaurantDAO, c4);

            System.out.println("************************************************************");
            System.out.println("Testeando insert plato: " + p1.getNombre());
            altaPlato(restaurantDAO, p1);
            System.out.println("************************************************************");
            System.out.println("Testeando insert plato duplicado " + p1.getNombre());
            altaPlato(restaurantDAO, p1);
            System.out.println("************************************************************");
            System.out.println("Testeando insert plato con cocinero que no existe " + p3.getNombre());
            altaPlato(restaurantDAO, p3);

            System.out.println("************************************************************");
            System.out.println("Testeando listado de todos los cocineros");
            List<Cocinero> cocineros;
            cocineros = restaurantDAO.selectAllCocineros();
            if (cocineros.isEmpty()) {
                System.out.println("No hay cocineros todavía");
            } else {
                System.out.println("Listado de cocineros");
                for (Cocinero x : cocineros) {
                    System.out.println(x);
                }
            }
            
            System.out.println("************************************************************");
            System.out.println("Cerrando conexión con la base de datos");
            restaurantDAO.desconectar();
            System.out.println("Conexión cerrada.");
            System.out.println("************************************************************");

        } catch (SQLException ex) {
            System.out.println("Error SQL: " + ex.getMessage());
        }
    }

    private static void altaPlato(RestaurantDAO restaurantDAO, Plato p1) throws SQLException {
        try {
            restaurantDAO.insertarPlato(p1);
            System.out.println("Plato dado de alta.");
        } catch (ExcepcionRestaurante ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void altaCocinero(RestaurantDAO restaurantDAO, Cocinero c4) throws SQLException {
        try {
            restaurantDAO.insertarCocinero(c4);
            System.out.println("Cocinero dado de alta.");
        } catch (ExcepcionRestaurante ex) {
            System.out.println(ex.getMessage());
        }
    }

}
