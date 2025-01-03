package Controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import DAO.categoriesDAO;
import Entity.categoriesEntity;
import com.google.gson.Gson;

@WebServlet("/manageCategoriesController")
public class ManageCategoriesController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private categoriesDAO categoryDAO;

    @Resource(name = "jdbc/DB_ECOMMERCE_J2EE")
    private DataSource dataSource;

    @Override
    public void init() {
        categoryDAO = new categoriesDAO(dataSource);
    }

    // Lấy danh sách tất cả các danh mục
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<categoriesEntity> categories = categoryDAO.getAllCategories();
            String jsonResponse = new Gson().toJson(categories);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    // Thêm danh mục mới
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoryName = request.getParameter("categoryName");
        categoriesEntity newCategory = new categoriesEntity(0, categoryName); // ID sẽ tự động tăng

        try {
            boolean success = categoryDAO.addCategory(newCategory);
            response.setStatus(success ? HttpServletResponse.SC_CREATED : HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    // Cập nhật danh mục
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String body = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        String[] params = body.split("&");
        String categoryIdStr = null;
        String categoryName = null;

        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                if (keyValue[0].equals("categoryId")) {
                    categoryIdStr = keyValue[1];
                } else if (keyValue[0].equals("categoryName")) {
                    categoryName = keyValue[1];
                }
            }
        }

        if (categoryIdStr == null || categoryName == null || categoryName.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Invalid input. categoryId and categoryName are required.\"}");
            return;
        }

        try {
            int categoryId = Integer.parseInt(categoryIdStr);
            categoriesEntity updatedCategory = new categoriesEntity(categoryId, categoryName);

            boolean success = categoryDAO.updateCategory(updatedCategory);
            response.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"" + (success ? "Category updated successfully." : "Failed to update category.") + "\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Invalid categoryId format. Must be an integer.\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }



    // Xóa danh mục
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        try {
            boolean success = categoryDAO.deleteCategory(categoryId);
            response.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
