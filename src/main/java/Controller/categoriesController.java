package Controller;

import java.io.IOException;
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
import Entity.subcategoriesEntity;
import com.google.gson.Gson;

@WebServlet("/categoriesController")
public class categoriesController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private categoriesDAO categoryDAO;
    @Resource(name = "jdbc/DB_ECOMMERCE_J2EE")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        this.categoryDAO = new categoriesDAO(dataSource);
    }

    // Lấy danh sách categories dưới dạng JSON
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<categoriesEntity> categories = categoryDAO.getAllCategories();
            
            // Chuyển đổi danh sách categories sang JSON
            Gson gson = new Gson();
            String categoriesJson = gson.toJson(categories);
            
            // Đặt kiểu trả về là JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(categoriesJson);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred");
        }
    }

    // Lấy danh sách subcategories theo categoryId dưới dạng JSON
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        try {
            List<subcategoriesEntity> subcategories = categoryDAO.getSubcategoriesByCategoryId(categoryId);
            
            // Chuyển đổi danh sách subcategories sang JSON
            Gson gson = new Gson();
            String subcategoriesJson = gson.toJson(subcategories);
            
            // Đặt kiểu trả về là JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(subcategoriesJson);
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred");
        }
    }
}
