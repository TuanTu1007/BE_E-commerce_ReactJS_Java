package Entity;

import java.sql.Timestamp;

public class productsEntity {
    private String productId;
    private String name;
    private String imageUrl;
    private String description;
    private double price;
    private categoriesEntity category;
    private subcategoriesEntity subCategory;  
    private String[] sizes;           
    private long date;
    private boolean bestseller;

    // Constructor cập nhật
    public productsEntity(String productId, String name, String imageUrl, String description, double price,
    		categoriesEntity category, subcategoriesEntity subCategory, String[] sizes, long date, boolean bestseller) {
        this.productId = productId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.price = price;
        this.category = category;
        this.subCategory = subCategory;  // Gán đối tượng Subcategory
        this.sizes = sizes;              // Gán mảng sizes
        this.date = date;
        this.bestseller = bestseller;
    }
    
    public productsEntity(String productId, String name, String imageUrl, double price) {
        this.productId = productId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.price = price;
        this.category = category;
        this.subCategory = subCategory;  // Gán đối tượng Subcategory
        this.sizes = sizes;              // Gán mảng sizes
        this.date = date;
        this.bestseller = bestseller;
    }

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public categoriesEntity getCategory() {
		return category;
	}

	public void setCategory(categoriesEntity category) {
		this.category = category;
	}

	public subcategoriesEntity getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(subcategoriesEntity subCategory) {
		this.subCategory = subCategory;
	}

	public String[] getSizes() {
		return sizes;
	}

	public void setSizes(String[] sizes) {
		this.sizes = sizes;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public boolean isBestseller() {
		return bestseller;
	}

	public void setBestseller(boolean bestseller) {
		this.bestseller = bestseller;
	}

}
