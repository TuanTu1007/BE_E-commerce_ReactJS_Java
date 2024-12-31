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
    	Integer userId = 1; // (Integer) request.getSession().getAttribute("userId");
        
     // Check if the userId is null (i.e., the user is not logged in)
        if (userId == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.getWriter().write("{\"message\":\"User not logged in\"}");
            return;
        }

        try {
            List<cartEntity> cartItems = cartDao.getCartItems(userId);
            
            // Convert the list of cart items to JSON
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(cartItems);

            // Set response content type to JSON
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching cart items");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("remove".equals(action)) {
            try {
                int cartId = Integer.parseInt(request.getParameter("cartId")); 
                cartDao.removeCartItem(cartId);  // Remove cart item

                // Respond with a success message in JSON format
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(new ResponseMessage("Item removed successfully"));

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonResponse);
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error removing cart item");
            }
        } else {
            // If the action is not 'remove', respond with a message for checkout
            String jsonResponse = new Gson().toJson(new ResponseMessage("Proceed to checkout"));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
        }
    }

    // Simple class to represent response messages
    private static class ResponseMessage {
        private String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
