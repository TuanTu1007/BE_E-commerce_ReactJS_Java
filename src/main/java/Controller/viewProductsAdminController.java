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

import com.google.gson.Gson;

import DAO.viewProductsAdminDAO;
import Entity.productsEntity;

@WebServlet("/viewProductsAdminController")
public class viewProductsAdminController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private viewProductsAdminDAO productsDAO;

    @Resource(name = "jdbc/DB_ECOMMERCE_J2EE")
    private DataSource dataSource;

    @Override
    public void init() {
        productsDAO = new viewProductsAdminDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();

        try {
            // Get the list of products
            List<productsEntity> productList = productsDAO.getAllProducts();
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson(productList));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson("Error fetching product list: " + e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();

        String productId = request.getParameter("productId");

        try {
            // Delete the product based on the productId
            productsDAO.deleteProduct(productId);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(gson.toJson("Product deleted successfully"));
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson("Error deleting product: " + e.getMessage()));
        }
    }
}
