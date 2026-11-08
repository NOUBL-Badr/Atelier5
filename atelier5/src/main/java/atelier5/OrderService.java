package atelier5;

public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentProcessor paymentProcessor;

    public OrderService(OrderRepository orderRepository, PaymentProcessor paymentProcessor) {
        this.orderRepository = orderRepository;
        this.paymentProcessor = paymentProcessor;
    }

    public boolean processOrder(String orderId) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Commande introuvable : " + orderId);
        }
        if (order.isPaid()) {
            return true;
        }
        if (!paymentProcessor.processPayment(order)) {
            throw new PaymentFailedExeption("Échec du paiement pour la commande " + orderId);
        }
        order.setPaid(true);
        orderRepository.save(order);
        return true;
    }
}
