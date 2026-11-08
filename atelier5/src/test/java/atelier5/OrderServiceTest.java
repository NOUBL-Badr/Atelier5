package atelier5;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock private OrderRepository orderRepository;
    @Mock private PaymentProcessor paymentProcessor;
    @InjectMocks private OrderService orderService;

    @Test // Scénario A : paiement réussi
    void processOrder_paiementReussi() {
        Order order = new Order("1", 100.0);
        when(orderRepository.findById("1")).thenReturn(order);
        when(paymentProcessor.processPayment(order)).thenReturn(true);

        assertTrue(orderService.processOrder("1"));
        assertTrue(order.isPaid());
        verify(orderRepository, times(1)).save(order);
    }

    @Test // Scénario B : commande introuvable
    void processOrder_commandeIntrouvable() {
        when(orderRepository.findById("999")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> orderService.processOrder("999"));
        verifyNoInteractions(paymentProcessor);
    }
}