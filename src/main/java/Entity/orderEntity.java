package Entity;

import java.sql.Timestamp;
import java.util.List;

public class orderEntity {
	private int orderId;
    private int userId;
    private double totalAmount;
    private String orderStatus;
    private Timestamp createdAt;
    private List<orderDetailEntity> orderDetails;
	public orderEntity(int orderId, int userId, double totalAmount, String orderStatus, 
			List<orderDetailEntity> orderDetails) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.totalAmount = totalAmount;
		this.orderStatus = orderStatus;
		this.orderDetails = orderDetails;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public List<orderDetailEntity> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<orderDetailEntity> orderDetails) {
		this.orderDetails = orderDetails;
	}
    
    
}
