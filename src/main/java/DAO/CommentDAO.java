package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import Entity.CommentEntity;

public class CommentDAO {
    private DataSource dataSource;

    public CommentDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean addComment(int userId, String productId, String comment) throws SQLException {
        String sql = "INSERT INTO Comments (user_id, product_id, comment) VALUES (?, ?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, productId);
            stmt.setString(3, comment);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<CommentEntity> getCommentsByProductId(String productId) throws SQLException {
        List<CommentEntity> comments = new ArrayList<>();
        String sql = "SELECT * FROM Comments WHERE product_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CommentEntity comment = new CommentEntity(
                        rs.getInt("comment_id"),
                        rs.getInt("user_id"),
                        rs.getString("product_id"),
                        rs.getString("comment"),
                        rs.getTimestamp("created_at")
                );
                comments.add(comment);
            }
        }
        return comments;
    }
}