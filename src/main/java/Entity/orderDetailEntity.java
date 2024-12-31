package Entity;

import java.sql.Timestamp;

public class orderDetailEntity {
    private int orderDetailId;
    private int orderId;
    private String productId;
//    private productsEntity product;
    private int quantity;
    private double price;
    private int sizeId;
    private double totalAmount;
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

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	private String orderStatus;
    private Timestamp orderDate;
    private String productName;
    private String productImage;

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

//    public productsEntity getProduct() {
//        return product;
//    }
//
//    public void setProduct(productsEntity product) {
//        this.product = product;
//    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public orderDetailEntity(int orderDetailId, int orderId, String productId, productsEntity product, int quantity,
                             double price, int sizeId) {
        super();
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.productId = productId;
//        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.sizeId = sizeId;
    }

    public orderDetailEntity() {
        super();
    }

	public orderDetailEntity(int orderDetailId, int orderId, String productId, int quantity, double price, int sizeId,
			double totalAmount, String orderStatus, Timestamp orderDate, String productName, String productImage) {
		super();
		this.orderDetailId = orderDetailId;
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.price = price;
		this.sizeId = sizeId;
		this.totalAmount = totalAmount;
		this.orderStatus = orderStatus;
		this.orderDate = orderDate;
		this.productName = productName;
		this.productImage = productImage;
	}
    
}
