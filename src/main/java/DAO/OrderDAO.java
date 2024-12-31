package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import Entity.orderEntity;
import Entity.orderDetailEntity;

public class OrderDAO {
    private DataSource dataSource;

    public OrderDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Lấy danh sách tất cả đơn hàng
    public List<orderEntity> getAllOrders() throws SQLException {
        List<orderEntity> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders"; // Giả sử có bảng Orders

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                int userId = rs.getInt("user_id");
                double totalAmount = rs.getDouble("total_amount");
                String orderStatus = rs.getString("order_status");
                Timestamp createdAt = rs.getTimestamp("created_at");

                // Lấy chi tiết đơn hàng
                List<orderDetailEntity> orderDetails = getOrderDetails(orderId);

                orders.add(new orderEntity(orderId, userId, totalAmount, orderStatus, orderDetails));
            }
        }
        return orders;
    }

    // Lấy chi tiết đơn hàng theo orderId
    private List<orderDetailEntity> getOrderDetails(int orderId) throws SQLException {
        List<orderDetailEntity> orderDetails = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetails WHERE order_id = ?"; // Giả sử có bảng OrderDetails

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Khởi tạo đối tượng orderDetailEntity với constructor mới có các tham số bổ sung
                orderDetailEntity detail = new orderDetailEntity(
                        rs.getInt("order_detail_id"),
                        rs.getInt("order_id"),
                        rs.getString("product_id"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getInt("size_id"),
                        rs.getDouble("total_amount"),
                        rs.getString("order_status"),
                        rs.getTimestamp("order_date"),
                        rs.getString("product_name"),
                        rs.getString("product_image")
                );
                orderDetails.add(detail);
            }
        }
        return orderDetails;
    }

    // Cập nhật trạng thái đơn hàng
    public boolean updateOrderStatus(int orderId, String newStatus) throws SQLException {
        String sql = "UPDATE Orders SET order_status = ? WHERE order_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, orderId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Xóa đơn hàng
    public boolean deleteOrder(int orderId) throws SQLException {
        String sql = "DELETE FROM Orders WHERE order_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            return stmt.executeUpdate() > 0;
        }
    }
}
