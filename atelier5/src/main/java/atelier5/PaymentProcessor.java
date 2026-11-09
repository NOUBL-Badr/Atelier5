package atelier5;

public interface PaymentProcessor {
    boolean processPayment(String orderId, double amount);
}
