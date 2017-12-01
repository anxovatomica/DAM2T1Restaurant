/*
 * Clase para testear que nuestro programa funciona
 */
package damrestaurant;

import dao.RestaurantDAO;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            System.out.println("Desconectando de la base de datos...");
            restaurantDAO.desconectar();
        } catch (SQLException ex) {
            System.out.println("Error al conectar "+ ex.getMessage());
        }
    }
    
}
