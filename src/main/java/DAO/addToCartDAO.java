package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import Entity.cartEntity;
import Entity.productsEntity;

public class addToCartDAO {

    private DataSource dataSource;

    public addToCartDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Phương thức thêm sản phẩm vào giỏ hàng
    public void addToCart(int userId, String productId, int sizeId, int quantity) {
        String checkSql = "SELECT quantity FROM Cart WHERE user_id = ? AND product_id = ? AND size_id = ?";
        String updateSql = "UPDATE Cart SET quantity = quantity + ? WHERE user_id = ? AND product_id = ? AND size_id = ?";
        String insertSql = "INSERT INTO Cart (user_id, product_id, quantity, size_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql);
             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

            // Kiểm tra nếu đã tồn tại
            checkStmt.setInt(1, userId);
            checkStmt.setString(2, productId);
            checkStmt.setInt(3, sizeId);

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Nếu tồn tại, cập nhật quantity
                updateStmt.setInt(1, quantity);
                updateStmt.setInt(2, userId);
                updateStmt.setString(3, productId);
                updateStmt.setInt(4, sizeId);
                updateStmt.executeUpdate();
            } else {
                // Nếu không tồn tại, thêm mới
                insertStmt.setInt(1, userId);
                insertStmt.setString(2, productId);
                insertStmt.setInt(3, quantity);
                insertStmt.setInt(4, sizeId);
                insertStmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    public List<cartEntity> getCartItems(int userId) {
//        List<cartEntity> cartItems = new ArrayList<>();
//        String sql = "SELECT c.cart_id, c.user_id, c.product_id, c.size_id, c.quantity, c.added_at, p.product_name, "
//                   + "p.product_image, p.price, s.size_name FROM Cart c "
//                   + "JOIN Products p ON c.product_id = p.product_id "
//                   + "JOIN Sizes s ON c.size_id = s.size_id "
//                   + "WHERE c.user_id = ?";
//
//        try (Connection conn = dataSource.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1, userId);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                cartEntity cart = new cartEntity(
//                        rs.getInt("cart_id"),
//                        rs.getInt("user_id"),
//                        rs.getString("product_id"),
//                        new productsEntity(
//                                rs.getString("product_id"),
//                                rs.getString("product_name"),
//                                rs.getString("product_image"),
//                                rs.getBigDecimal("price")
//                        ),
//                        rs.getInt("quantity"),
//                        rs.getInt("size_id"),
//                        rs.getTimestamp("added_at")
//                );
//                cartItems.add(cart);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return cartItems;
//    }
//
//    // Cập nhật số lượng của sản phẩm trong giỏ hàng
//    public void updateQuantity(int userId, String productId, int sizeId, int quantity) {
//        String sql = "UPDATE Cart SET quantity = ? WHERE user_id = ? AND product_id = ? AND size_id = ?";
//        try (Connection conn = dataSource.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1, quantity);
//            stmt.setInt(2, userId);
//            stmt.setString(3, productId);
//            stmt.setInt(4, sizeId);
//            stmt.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Xóa sản phẩm khỏi giỏ hàng
//    public void removeItem(int userId, String productId, int sizeId) {
//        String sql = "DELETE FROM Cart WHERE user_id = ? AND product_id = ? AND size_id = ?";
//        try (Connection conn = dataSource.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//            stmt.setInt(1, userId);
//            stmt.setString(2, productId);
//            stmt.setInt(3, sizeId);
//            stmt.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}