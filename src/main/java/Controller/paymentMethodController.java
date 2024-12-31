package Controller;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import DAO.paymentMethodDAO;
import Entity.paymentMethodEntity;
import com.google.gson.Gson;

@WebServlet("/paymentMethodController")
public class paymentMethodController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private paymentMethodDAO paymentMethodDAO;

    @Resource(name = "jdbc/DB_ECOMMERCE_J2EE")
    private DataSource dataSource;

    @Override
    public void init() {
        paymentMethodDAO = new paymentMethodDAO(dataSource);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Gson gson = new Gson();

        try {
            if ("add".equals(action)) {
                // Thêm phương thức thanh toán
                int userId = Integer.parseInt(request.getParameter("userId"));
                String cardNumber = request.getParameter("cardNumber");
                String cardHolderName = request.getParameter("cardHolderName");
                String expirationDate = request.getParameter("expirationDate");
                String cvv = request.getParameter("cvv");

                paymentMethodEntity newPaymentMethod = new paymentMethodEntity(0, userId, cardNumber, cardHolderName, expirationDate, cvv);
                boolean success = paymentMethodDAO.addPaymentMethod(newPaymentMethod);
                response.getWriter().write(gson.toJson(success ? "Payment method added successfully." : "Failed to add payment method."));
            } else if ("update".equals(action)) {
                // Cập nhật phương thức thanh toán
                int id = Integer.parseInt(request.getParameter("id"));
                String cardNumber = request.getParameter("cardNumber");
                String cardHolderName = request.getParameter("cardHolderName");
                String expirationDate = request.getParameter("expirationDate");
                String cvv = request.getParameter("cvv");

                paymentMethodEntity updatedPaymentMethod = new paymentMethodEntity(id, 0, cardNumber, cardHolderName, expirationDate, cvv);
                boolean success = paymentMethodDAO.updatePaymentMethod(updatedPaymentMethod);
                response.getWriter().write(gson.toJson(success ? "Payment method updated successfully." : "Failed to update payment method."));
            } else if ("delete".equals(action)) {
                // Xóa phương thức thanh toán
                int id = Integer.parseInt(request.getParameter("id"));
                boolean success = paymentMethodDAO.deletePaymentMethod(id);
                response.getWriter().write(gson.toJson(success ? "Payment method deleted successfully." : "Failed to delete payment method."));
            } else if ("get".equals(action)) {
                // Lấy thông tin phương thức thanh toán
                int id = Integer.parseInt(request.getParameter("id"));
                paymentMethodEntity paymentMethod = paymentMethodDAO.getPaymentMethodById(id);
                response.getWriter().write(gson.toJson(paymentMethod != null ? paymentMethod : "Payment method not found."));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            response.getWriter().write(gson.toJson("An error occurred: " + e.getMessage()));
        }
    }
}