package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import DAO.cartDAO;
import Entity.cartEntity;
import Entity.orderDetailEntity;
import Entity.orderEntity;
import DAO.placeOrderDAO;

@WebServlet("/placeOrderController")
public class placeOrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Resource(name = "jdbc/DB_ECOMMERCE_J2EE") 
    private DataSource dataSource;

    private cartDAO CartDao;
    private placeOrderDAO orderDao;

    @Override
    public void init() throws ServletException {
        super.init();
        CartDao = new cartDAO(dataSource);
        orderDao = new placeOrderDAO(dataSource);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ form checkout
        int userId = 1;// Integer.parseInt(request.getParameter("userId"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String street = request.getParameter("street");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String zipcode = request.getParameter("zipcode");
        String country = request.getParameter("country");
        String phone = request.getParameter("phone");

        // Lấy giỏ hàng từ database thông qua cartDAO
        List<cartEntity> cartItems = CartDao.getCartItemsByUserId(userId);

        if (cartItems == null || cartItems.isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        // Tính tổng tiền
        double totalAmount = 0.0;
        List<orderDetailEntity> orderDetails = new ArrayList<>();
        for (cartEntity item : cartItems) {
            totalAmount += item.getProduct().getPrice() * item.getQuantity();

            orderDetailEntity detail = new orderDetailEntity();
            detail.setProductId(item.getProduct().getProductId());
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getProduct().getPrice());
            detail.setSizeId(item.getSizeId());
            orderDetails.add(detail);
        }

        // Tạo đơn hàng
        orderEntity order = new orderEntity(0, userId, totalAmount, null, orderDetails);

        // Lưu đơn hàng vào database
        boolean success = orderDao.createOrder(order);

        if (success) {
            try {
                // Xóa giỏ hàng sau khi đặt hàng thành công
                CartDao.clearCart(userId);
                response.sendRedirect(request.getContextPath() + "/trackOrderController");
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("cart.jsp");
            }
        } else {
            response.sendRedirect("cart.jsp");
        }
    }

}
