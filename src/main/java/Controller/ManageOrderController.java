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
import DAO.OrderDAO;
import Entity.orderEntity;
import com.google.gson.Gson;

@WebServlet("/manageOrderController")
public class ManageOrderController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private OrderDAO orderDAO;

    @Resource(name = "jdbc/DB_ECOMMERCE_J2EE")
    private DataSource dataSource;

    // Khởi tạo OrderDAO
    @Override
    public void init() throws ServletException {
        super.init();  // Gọi phương thức init() của lớp cha
        orderDAO = new OrderDAO(dataSource);  // Khởi tạo OrderDAO
    }

    // Lấy danh sách tất cả đơn hàng
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<orderEntity> orders = orderDAO.getAllOrders();
            String jsonResponse = new Gson().toJson(orders);
            response.getWriter().write(jsonResponse);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    // Cập nhật trạng thái đơn hàng
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        String newStatus = request.getParameter("orderStatus");

        try {
            boolean success = orderDAO.updateOrderStatus(orderId, newStatus);
            response.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    // Xóa đơn hàng
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try {
            boolean success = orderDAO.deleteOrder(orderId);
            response.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
