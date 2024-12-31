package Controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;

import DAO.trackOrderDAO;
import Entity.orderDetailEntity;

@WebServlet("/trackOrderController")
public class trackOrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Resource(name = "jdbc/DB_ECOMMERCE_J2EE") 
    private DataSource dataSource;

    private trackOrderDAO dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new trackOrderDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();

        try {
            List<orderDetailEntity> orderDetailsList = dao.getAllOrderDetails();
            String jsonResponse = gson.toJson(orderDetailsList);
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(gson.toJson("Error: " + e.getMessage()));
        }
    }
}
