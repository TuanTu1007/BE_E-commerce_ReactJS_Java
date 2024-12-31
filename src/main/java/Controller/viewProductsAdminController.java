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
        try {
            // Get the list of products
            List<productsEntity> productList = productsDAO.getAllProducts();
            request.setAttribute("productList", productList);
            request.getRequestDispatcher("/listItemsAdmin.jsp").forward(request, response);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            request.setAttribute("errorMessage", "Không thể lấy danh sách sản phẩm");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productId = request.getParameter("productId");

        try {
            // Delete the product based on the productId
            productsDAO.deleteProduct(productId);

            // After deletion, redirect back to the product list
            response.sendRedirect(request.getContextPath() + "/viewProductsAdminController");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            request.setAttribute("errorMessage", "Không thể xóa sản phẩm");
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}
