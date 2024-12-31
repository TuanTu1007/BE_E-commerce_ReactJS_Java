package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import Entity.paymentMethodEntity;

public class paymentMethodDAO {
    private DataSource dataSource;

    public paymentMethodDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Thêm phương thức thanh toán
    public boolean addPaymentMethod(paymentMethodEntity paymentMethod) throws SQLException {
        String sql = "INSERT INTO PaymentMethods (user_id, card_number, card_holder_name, expiration_date, cvv) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, paymentMethod.getUserId()); // Đảm bảo phương thức tồn tại
            stmt.setString(2, paymentMethod.getCardNumber());
            stmt.setString(3, paymentMethod.getCardHolderName());
            stmt.setString(4, paymentMethod.getExpirationDate());
            stmt.setString(5, paymentMethod.getCvv());
            return stmt.executeUpdate() > 0;
        }
    }

    // Cập nhật phương thức thanh toán
    public boolean updatePaymentMethod(paymentMethodEntity paymentMethod) throws SQLException {
        String sql = "UPDATE PaymentMethods SET card_number = ?, card_holder_name = ?, expiration_date = ?, cvv = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paymentMethod.getCardNumber());
            stmt.setString(2, paymentMethod.getCardHolderName());
            stmt.setString(3, paymentMethod.getExpirationDate());
            stmt.setString(4, paymentMethod.getCvv());
            stmt.setInt(5, paymentMethod.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    // Xóa phương thức thanh toán
    public boolean deletePaymentMethod(int id) throws SQLException {
        String sql = "DELETE FROM PaymentMethods WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Lấy phương thức thanh toán theo ID
    public paymentMethodEntity getPaymentMethodById(int id) throws SQLException {
        String sql = "SELECT * FROM PaymentMethods WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new paymentMethodEntity(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("card_number"),
                        rs.getString("card_holder_name"),
                        rs.getString("expiration_date"),
                        rs.getString("cvv")
                );
            }
        }
        return null;
    }
}
