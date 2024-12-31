package DAO;

import java.sql.*;
import java.util.*;

import javax.sql.DataSource;

import Entity.categoriesEntity;
import Entity.subcategoriesEntity;

public class categoriesDAO {
    private DataSource dataSource;

    public categoriesDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Lấy danh sách tất cả các categories
    public List<categoriesEntity> getAllCategories() throws SQLException {
        List<categoriesEntity> categories = new ArrayList<>();
        String sql = "SELECT * FROM Categories";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
            	categoriesEntity category = new categoriesEntity(
                    rs.getInt("category_id"),
                    rs.getString("category_name")
                );
                categories.add(category);
            }
        }
        return categories;
    }

    // Lấy tất cả subcategories của một category
    public List<subcategoriesEntity> getSubcategoriesByCategoryId(int categoryId) throws SQLException {
        List<subcategoriesEntity> subcategories = new ArrayList<>();
        String sql = "SELECT * FROM Subcategories WHERE category_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                	subcategoriesEntity subcategory = new subcategoriesEntity(
                        rs.getInt("subcategory_id"),
                        rs.getString("subcategory_name"),
                        categoryId
                    );
                    subcategories.add(subcategory);
                }
            }
        }
        return subcategories;
    }
    // Thêm danh mục mới
    public boolean addCategory(categoriesEntity category) throws SQLException {
        String sql = "INSERT INTO Categories (category_name) VALUES (?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getCategoryName());
            return stmt.executeUpdate() > 0;
        }
    }

    // Cập nhật danh mục
    public boolean updateCategory(categoriesEntity category) throws SQLException {
        String sql = "UPDATE Categories SET category_name = ? WHERE category_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category.getCategoryName());
            stmt.setInt(2, category.getCategoryId());
            return stmt.executeUpdate() > 0;
        }
    }

    // Xóa danh mục
    public boolean deleteCategory(int categoryId) throws SQLException {
        String sql = "DELETE FROM Categories WHERE category_id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, categoryId);
            return stmt.executeUpdate() > 0;
        }
    }
}
