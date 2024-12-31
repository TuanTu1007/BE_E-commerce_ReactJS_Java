package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import Entity.categoriesEntity;
import Entity.productsEntity;
import Entity.subcategoriesEntity;

public class viewProductsAdminDAO {
	private DataSource dataSource;

    public viewProductsAdminDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
 // Phương thức lấy danh sách tất cả sản phẩm
    public List<productsEntity> getAllProducts() throws SQLException {
        List<productsEntity> productList = new ArrayList<>();
        String sql = "SELECT p.product_id, p.name, p.image_url, p.description, p.price, p.bestseller, " +
                     "c.category_id, c.category_name, s.subcategory_id, s.subcategory_name, " +
                     "GROUP_CONCAT(sz.size_name ORDER BY sz.size_name) AS sizes " +
                     "FROM Products p " +
                     "JOIN Categories c ON p.category_id = c.category_id " +
                     "JOIN Subcategories s ON p.subcategory_id = s.subcategory_id " +
                     "LEFT JOIN Product_Sizes ps ON p.product_id = ps.product_id " +
                     "LEFT JOIN Sizes sz ON ps.size_id = sz.size_id " +
                     "GROUP BY p.product_id, p.name, p.image_url, p.description, p.price, p.bestseller, c.category_id, c.category_name, s.subcategory_id, s.subcategory_name";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Lấy thông tin Category
            	categoriesEntity category = new categoriesEntity(rs.getInt("category_id"), rs.getString("category_name"));
                
                // Lấy thông tin Subcategory
            	subcategoriesEntity subcategory = new subcategoriesEntity(rs.getInt("subcategory_id"), rs.getString("subcategory_name"), rs.getInt("category_id"));

                // Lấy thông tin Sizes 
                String sizesString = rs.getString("sizes");
                String[] sizesArray = (sizesString != null && !sizesString.isEmpty()) ? sizesString.split(",") : new String[0];

                // Tạo đối tượng product và thêm vào danh sách
                productsEntity product = new productsEntity(
                    rs.getString("product_id"),
                    rs.getString("name"),
                    rs.getString("image_url"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    category,  // Sử dụng đối tượng Category
                    subcategory,  // Sử dụng đối tượng Subcategory
                    sizesArray,  // Dùng mảng sizes
                    System.currentTimeMillis(), 
                    rs.getBoolean("bestseller")
                );
                productList.add(product);
            }
        }
        return productList;
    }
    
    public void deleteProduct(String productId) throws SQLException {
        String sql = "DELETE FROM Products WHERE product_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, productId);
            stmt.executeUpdate();
        }
    }
}
