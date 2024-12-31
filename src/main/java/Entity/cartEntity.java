package Entity;

import java.security.Timestamp;

public class cartEntity {
	private int cartId;
    private int userId;
    private String productId;
    private productsEntity product;
    private int quantity;
    private int sizeId;
    private Timestamp addedAt;
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public productsEntity getProduct() {
		return product;
	}
	public void setProduct(productsEntity product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getSizeId() {
		return sizeId;
	}
	public void setSizeId(int sizeId) {
		this.sizeId = sizeId;
	}
	public Timestamp getAddedAt() {
		return addedAt;
	}
	public void setAddedAt(Timestamp addedAt) {
		this.addedAt = addedAt;
	}
	public cartEntity(int cartId, int userId, String productId, productsEntity product, int quantity, int sizeId,
			Timestamp addedAt) {
		super();
		this.cartId = cartId;
		this.userId = userId;
		this.productId = productId;
		this.product = product;
		this.quantity = quantity;
		this.sizeId = sizeId;
		this.addedAt = addedAt;
	}
	public cartEntity() {
		
	}
    
}
