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

    @Test // Scénario C : échec du paiement
    void processOrder_echecPaiement() {
        Order order = new Order("2", 50.0);
        when(orderRepository.findById("2")).thenReturn(order);
        when(paymentProcessor.processPayment(order)).thenReturn(false);

        assertThrows(PaymentFailedExeption.class, () -> orderService.processOrder("2"));
        assertFalse(order.isPaid());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test // Scénario D : commande déjà payée
    void processOrder_dejaPayee() {
        Order order = new Order("3", 75.0);
        order.setPaid(true);
        when(orderRepository.findById("3")).thenReturn(order);

        assertTrue(orderService.processOrder("3"));
        verifyNoInteractions(paymentProcessor);
        verify(orderRepository, never()).save(any(Order.class));
    }
}