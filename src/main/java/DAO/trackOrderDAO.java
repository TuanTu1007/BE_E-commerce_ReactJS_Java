package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import Entity.orderDetailEntity;

public class trackOrderDAO {
	private DataSource dataSource;

    public trackOrderDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public List<orderDetailEntity> getAllOrderDetails() throws SQLException {
        List<orderDetailEntity> orderDetails = new ArrayList<>();
        String sql = """
                SELECT od.order_detail_id, od.order_id, o.user_id, od.product_id, od.quantity, od.price,
                       o.total_amount, o.order_status, o.created_at AS order_date,
                       p.name AS product_name, p.image_url AS product_image
                FROM Order_Details od
                JOIN Orders o ON od.order_id = o.order_id
                JOIN Products p ON od.product_id = p.product_id
            """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             ResultSet rs = stmt.executeQuery();
           while (rs.next()) {
               orderDetailEntity orderDetail = new orderDetailEntity();
               orderDetail.setOrderDetailId(rs.getInt("order_detail_id"));
               orderDetail.setOrderId(rs.getInt("order_id"));
               orderDetail.setUserId(rs.getString("user_id"));
               orderDetail.setProductId(rs.getString("product_id"));
               orderDetail.setQuantity(rs.getInt("quantity"));
               orderDetail.setPrice(rs.getDouble("price"));
               orderDetail.setTotalAmount(rs.getDouble("total_amount"));
               orderDetail.setOrderStatus(rs.getString("order_status"));
               orderDetail.setOrderDate(rs.getTimestamp("order_date"));
               orderDetail.setProductName(rs.getString("product_name"));
               orderDetail.setProductImage(rs.getString("product_image"));
               orderDetails.add(orderDetail);
           }
        }
           return orderDetails;
    }
}
