package atelier5;

public class Order {
	private String id;
	private double amount;
	private boolean paid;
	public Order(String id, double amount) {
	this.id = id;
	this.amount = amount;
	this.paid = false;
	}
	public String getId() { return id; }
	public double getAmount() { return amount; }
	public boolean isPaid() { return paid; }
	public void setPaid(boolean paid) { this.paid = paid; }
}
