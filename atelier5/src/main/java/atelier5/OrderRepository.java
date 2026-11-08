package atelier5;

public interface OrderRepository {
    Order findById(String id);
    void save(Order order);
}
