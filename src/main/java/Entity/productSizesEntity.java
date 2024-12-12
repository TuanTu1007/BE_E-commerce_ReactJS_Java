package Entity;

public class productSizesEntity {
	private String productId;
    private int sizeId;

    public productSizesEntity(String productId, int sizeId) {
        this.productId = productId;
        this.sizeId = sizeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }
}
