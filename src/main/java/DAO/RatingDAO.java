package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;

public class RatingDAO { // Sửa tên class thành PascalCase theo convention Java
    private DataSource dataSource;

    public RatingDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean addRating(int userId, String productId, int rating) throws SQLException {
        String sql = "INSERT INTO Ratings (user_id, product_id, rating) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, productId);
            stmt.setInt(3, rating);
            return stmt.executeUpdate() > 0;
        }
    }

    public Integer getUserRating(int userId, String productId) throws SQLException { // Sửa tên hàm thành getUserRating
        String sql = "SELECT rating FROM Ratings WHERE user_id = ? AND product_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("rating");
            }
        }
        return null;
    }
}
