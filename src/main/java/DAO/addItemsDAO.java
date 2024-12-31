package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import Entity.productsEntity;
import Entity.sizesEntity;
import Entity.productSizesEntity;

public class addItemsDAO {
	
	private DataSource dataSource;

    public addItemsDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
	
	private static final String INSERT_PRODUCT = "INSERT INTO Products (product_id, name, image_url, description, price, category_id, subcategory_id, bestseller) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_PRODUCT_SIZE = "INSERT INTO Product_Sizes (product_id, size_id) VALUES (?, ?)";

    public boolean addItems(productsEntity product, List<Integer> sizeIds) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement psProduct = conn.prepareStatement(INSERT_PRODUCT);
             PreparedStatement psSize = conn.prepareStatement(INSERT_PRODUCT_SIZE)) {

            conn.setAutoCommit(false);

            psProduct.setString(1, product.getProductId());
            psProduct.setString(2, product.getName());
            psProduct.setString(3, product.getImageUrl());
            psProduct.setString(4, product.getDescription());
            psProduct.setDouble(5, product.getPrice());
            psProduct.setInt(6, product.getCategory().getCategoryId());
            psProduct.setInt(7, product.getSubCategory().getSubcategoryId());
            psProduct.setBoolean(8, product.isBestseller());
            psProduct.executeUpdate();

            for (int sizeId : sizeIds) {
                psSize.setString(1, product.getProductId());
                psSize.setInt(2, sizeId);
                psSize.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
