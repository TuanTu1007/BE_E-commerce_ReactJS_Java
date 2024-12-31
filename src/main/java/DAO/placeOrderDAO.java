package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import Entity.cartEntity;
import Entity.orderDetailEntity;
import Entity.orderEntity;

public class placeOrderDAO {
	private DataSource dataSource;
	
	public placeOrderDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
	
	// Phương thức tạo đơn hàng
	public boolean createOrder(orderEntity order) {
        Connection conn = null;
        PreparedStatement orderStmt = null;
        PreparedStatement detailStmt = null;

        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);

            // Thêm vào bảng Orders
            String orderSQL = "INSERT INTO Orders (user_id, total_amount) VALUES (?, ?)";
            orderStmt = conn.prepareStatement(orderSQL, PreparedStatement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, order.getUserId());
            orderStmt.setDouble(2, order.getTotalAmount());
            orderStmt.executeUpdate();

            // Lấy order_id vừa tạo
            var rs = orderStmt.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            // Thêm vào bảng Order_Details
            String detailSQL = "INSERT INTO Order_Details (order_id, product_id, quantity, price, size_id) VALUES (?, ?, ?, ?, ?)";
            detailStmt = conn.prepareStatement(detailSQL);
            for (orderDetailEntity detail : order.getOrderDetails()) {
                detailStmt.setInt(1, orderId);
                detailStmt.setString(2, detail.getProductId());
                detailStmt.setInt(3, detail.getQuantity());
                detailStmt.setDouble(4, detail.getPrice());
                detailStmt.setInt(5, detail.getSizeId());
                detailStmt.addBatch();
            }
            detailStmt.executeBatch();

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (orderStmt != null) orderStmt.close();
                if (detailStmt != null) detailStmt.close();
                if (conn != null) conn.close();
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }
}
