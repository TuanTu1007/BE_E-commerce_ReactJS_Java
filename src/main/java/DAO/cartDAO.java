package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import Entity.cartEntity;
import Entity.productsEntity;

public class cartDAO {
    private DataSource dataSource;

    public cartDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Phương thức lấy thông tin giỏ hàng
    public List<cartEntity> getCartItems(int userId) throws SQLException {
        List<cartEntity> cartItems = new ArrayList<>();

        String sql = "SELECT " +
                     "    c.cart_id, " +
                     "    c.user_id, " +
                     "    c.product_id, " +
                     "    c.size_id, " +
                     "    c.quantity, " +
                     "    c.added_at, " +
                     "    p.name AS product_name, " +
                     "    p.image_url, " +
                     "    p.price, " +
                     "    s.size_name " +
                     "FROM " +
                     "    Cart c " +
                     "JOIN " +
                     "    Products p ON c.product_id = p.product_id " +
                     "JOIN " +
                     "    Sizes s ON c.size_id = s.size_id " +
                     "WHERE " +
                     "    c.user_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int cartId = rs.getInt("cart_id");
                    String productId = rs.getString("product_id");
                    int sizeId = rs.getInt("size_id");
                    int quantity = rs.getInt("quantity");

                    // Tạo đối tượng productsEntity
                    String productName = rs.getString("product_name");
                    String imageUrl = rs.getString("image_url");
                    double price = rs.getDouble("price");
                    productsEntity product = new productsEntity(productId, productName, imageUrl, price);

                    // Tạo đối tượng cartEntity và thêm vào danh sách
                    cartEntity cartItem = new cartEntity(cartId, userId, productId, product, quantity, sizeId, null);
                    cartItems.add(cartItem);
                }
            }
        }

        return cartItems;
    }
    public void clearCart(int userId) throws SQLException {
        String sql = "DELETE FROM Cart WHERE user_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        }
    }
    
    public void removeCartItem(int cartId) throws SQLException {
        String sql = "DELETE FROM Cart WHERE cart_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cartId);
            stmt.executeUpdate();
        }
    }
    
    // Phương thức lấy giỏ hàng từ userId
    public List<cartEntity> getCartItemsByUserId(int userId) {
        List<cartEntity> cartItems = new ArrayList<>();
        String sql = "SELECT c.product_id, c.quantity, c.size_id, p.name, p.price " +
                     "FROM Cart c JOIN Products p ON c.product_id = p.product_id " +
                     "WHERE c.user_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cartEntity cartItem = new cartEntity();
                cartItem.setProductId(rs.getString("product_id"));
                cartItem.setQuantity(rs.getInt("quantity"));
                cartItem.setSizeId(rs.getInt("size_id"));

                // Tạo productEntity để lưu thông tin sản phẩm
                productsEntity product = new productsEntity();
                product.setProductId(rs.getString("product_id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));

                cartItem.setProduct(product);
                cartItems.add(cartItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartItems;
    }
}
