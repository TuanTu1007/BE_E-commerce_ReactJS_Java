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

import DAO.cartDAO;
import Entity.cartEntity;

@WebServlet("/cartController")
public class cartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Resource(name = "jdbc/DB_ECOMMERCE_J2EE") 
    private DataSource dataSource;
    
    cartDAO cartDao = new cartDAO(dataSource);

    @Override
    public void init() throws ServletException {
        super.init();
        cartDao = new cartDAO(dataSource);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = 1; // (Integer) request.getSession().getAttribute("userId");

        try {
            List<cartEntity> cartItems = cartDao.getCartItems(userId);
            request.setAttribute("cartItems", cartItems);
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("remove".equals(action)) {
            try {
                int cartId = Integer.parseInt(request.getParameter("cartId")); 
                cartDao.removeCartItem(cartId);  // Thực hiện xóa giỏ hàng

                // Chuyển hướng lại về trang giỏ hàng
                response.sendRedirect(request.getContextPath() + "/cartController");
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
        } else {
            // Nếu không phải hành động xóa, chuyển tiếp tới checkout
            response.sendRedirect("proceedToCheckout.jsp");
        }
    }
}


