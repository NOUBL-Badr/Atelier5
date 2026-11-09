package atelier5;

public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentProcessor paymentProcessor;

    public OrderService(PaymentProcessor paymentProcessor, OrderRepository orderRepository) {
        this.paymentProcessor = paymentProcessor;
        this.orderRepository = orderRepository;
    }

    public boolean processOrder(String orderId) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Commande introuvable : " + orderId);
        }
        if (order.isPaid()) {
            return true;
        }
        if (!paymentProcessor.processPayment(order.getId(), order.getAmount())) {
            throw new PaymentFailedException("Échec du paiement pour la commande " + orderId);
        }
        order.setPaid(true);
        orderRepository.save(order);
        return true;
    }
}
