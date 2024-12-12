package Controller;

import java.io.IOException;
import java.io.PrintWriter;
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

import DAO.viewProductsDAO;
import Entity.productsEntity;

@WebServlet("/viewProductsController")
public class viewProductsController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private viewProductsDAO productsDAO;

    @Resource(name = "jdbc/DB_ECOMMERCE_J2EE")
    private DataSource dataSource;

    @Override
    public void init() {
        productsDAO = new viewProductsDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<productsEntity> productList = productsDAO.getAllProducts();

            Gson gson = new Gson();
            String json = gson.toJson(productList);

            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            response.getWriter().write("{\"error\": \"Không thể lấy danh sách sản phẩm\"}");
        }
    }
}

