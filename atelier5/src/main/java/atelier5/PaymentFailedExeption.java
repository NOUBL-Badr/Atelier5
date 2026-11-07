package atelier5;

public class PaymentFailedExeption extends RuntimeException {
    public PaymentFailedExeption(String message) {
        super(message);
    }
}
