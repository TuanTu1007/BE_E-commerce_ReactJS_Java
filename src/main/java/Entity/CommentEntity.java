package Entity;

import java.sql.Timestamp;

public class CommentEntity {
    private int commentId;
    private int userId;
    private String productId;
    private String comment;
    private Timestamp createdAt;

    // Constructor
    public CommentEntity(int commentId, int userId, String productId, String comment, Timestamp createdAt) {
        this.commentId = commentId;
        this.userId = userId;
        this.productId = productId;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
