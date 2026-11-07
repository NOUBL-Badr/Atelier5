package atelier5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testOrderInitialization() {
        // Arrange & Act
        Order order = new Order("123", 150.0);

        // Assert : Vérifier l'initialisation correcte (ID, montant, et paid = false par défaut)
        assertEquals("123", order.getId());
        assertEquals(150.0, order.getAmount());
        assertFalse(order.isPaid(), "Une nouvelle commande ne doit pas être payée par défaut");
    }

    @Test
    void testSetPaid() {
        // Arrange
        Order order = new Order("123", 150.0);

        // Act
        order.setPaid(true);

        // Assert : Vérifier le bon fonctionnement du setter setPaid(true)
        assertTrue(order.isPaid(), "La commande devrait maintenant être marquée comme payée");
    }
}
