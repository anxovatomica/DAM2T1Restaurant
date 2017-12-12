/*
 * Clase para testear que nuestro programa funciona
 */
package damrestaurant;

import dao.RestaurantDAO;
import excepciones.ExcepcionRestaurante;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        System.out.println("Conectando con la base de datos...");
        RestaurantDAO restaurantDAO = new RestaurantDAO();
        try {
            restaurantDAO.conectar();
            Cocinero c = new Cocinero("Pepe", "123456789", "Hombre", 30, 10, "Postres");
            System.out.println("Insertando cocinero " + c.getNombre());
            try {
                restaurantDAO.insertarCocinero(c);
                System.out.println("Cocinero dado de alta.");
            } catch (ExcepcionRestaurante ex) {
                System.out.println(ex.getMessage());
            }
            Cocinero a = new Cocinero("Tiramisu", "888888888", "Mujer", 20, 2, "Entrantes");
            System.out.println("Insertando cocinero " + a.getNombre());
            try {
                restaurantDAO.insertarCocinero(a);
                System.out.println("Cocinero dado de alta.");
            } catch (ExcepcionRestaurante ex) {
                System.out.println(ex.getMessage());
            }
            Plato p = new Plato("Espaguetis carbonara", "Platos principales", 4.50, c);
            System.out.println("Insertando plato "+p.getNombre());
            try {
                restaurantDAO.insertarPlato(p);
                System.out.println("Plato dado de alta.");
            } catch (ExcepcionRestaurante ex) {
                System.out.println(ex.getMessage());
            }
            
            System.out.println("Testeando listado de cocineros");
            List<Cocinero> cocineros;
            try {
                cocineros = restaurantDAO.selectAllCocineros();
                if (cocineros.isEmpty()) {
                    System.out.println("No hay cocineros todavía");
                } else {
                    System.out.println("Listado de cocineros");
                    for (Cocinero x : cocineros) {
                        System.out.println(x);
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error al consultar: "+ex.getMessage());
            }

            System.out.println("Desconectando de la base de datos...");
            restaurantDAO.desconectar();

        } catch (SQLException ex) {
            System.out.println("Error al conectar " + ex.getMessage());
        }
    }

}
