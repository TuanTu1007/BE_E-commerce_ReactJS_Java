package Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import com.google.gson.Gson;

import DAO.addToCartDAO;

@WebServlet("/addToCartController")
public class addToCartController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private addToCartDAO cartDAO;

    @Resource(name = "jdbc/DB_ECOMMERCE_J2EE")
    private DataSource datasource;

    @Override
    public void init() {
        cartDAO = new addToCartDAO(datasource);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Lấy thông tin từ form
            String productId = request.getParameter("id");
            String sizeName = request.getParameter("sizeName"); 
            String quantityParam = "1";

            if (productId == null || sizeName == null || quantityParam == null) {
                throw new IllegalArgumentException("Missing required parameters.");
            }

            // Chuyển đổi sizeName thành sizeId
            int sizeId = convertSizeNameToId(sizeName);

            // Chuyển đổi quantity
            int quantity = Integer.parseInt(quantityParam);

            // Lấy userId từ session
            Integer userId = 1;//(Integer) request.getSession().getAttribute("userId");
            if (userId == null) {
                throw new IllegalArgumentException("User is not logged in.");
            }

            // Thêm sản phẩm vào giỏ hàng
            cartDAO.addToCart(userId, productId, sizeId, quantity);

            ResponseData responseData = new ResponseData("success", "Product added to cart successfully.");
            String jsonResponse = new Gson().toJson(responseData);
            response.getWriter().write(jsonResponse);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            ResponseData responseData = new ResponseData("error", "Invalid number format for sizeId or quantity.");
            String jsonResponse = new Gson().toJson(responseData);
            response.getWriter().write(jsonResponse);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            ResponseData responseData = new ResponseData("error", e.getMessage());
            String jsonResponse = new Gson().toJson(responseData);
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseData responseData = new ResponseData("error", "An unexpected error occurred.");
            String jsonResponse = new Gson().toJson(responseData);
            response.getWriter().write(jsonResponse);
        }
    }

    // Hàm chuyển đổi sizeName thành sizeId
    private int convertSizeNameToId(String sizeName) throws IllegalArgumentException {
        Map<String, Integer> sizeMap = new HashMap<>();
        sizeMap.put("S", 1);
        sizeMap.put("M", 2);
        sizeMap.put("L", 3);

        Integer sizeId = sizeMap.get(sizeName.toUpperCase());
        if (sizeId == null) {
            throw new IllegalArgumentException("Invalid size name: " + sizeName);
        }
        return sizeId;
    }

    // Lớp để định nghĩa cấu trúc phản hồi JSON
    private class ResponseData {
        private String status;
        private String message;

        public ResponseData(String status, String message) {
            this.status = status;
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }
}
