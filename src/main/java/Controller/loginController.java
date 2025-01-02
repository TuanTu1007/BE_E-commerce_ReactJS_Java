package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;

import DAO.loginDAO;
import Entity.usersEntity;

@WebServlet("/loginController")
public class loginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private loginDAO LoginDao;

    @Resource(name = "jdbc/DB_ECOMMERCE_J2EE")
    private DataSource datasource;

    @Override
    public void init() {
        LoginDao = new loginDAO(datasource);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Cấu hình CORS để frontend có thể truy cập
        configureCORS(response);

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println("Received login request - Email: " + email);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();

            // Gọi loginDAO để xác thực tài khoản
            usersEntity account = LoginDao.Login(email, password);

            if (account == null) {
                // Trả về lỗi xác thực
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print(gson.toJson(new ResponseMessage("Sai tài khoản hoặc mật khẩu", false)));
            } else {
                // Tạo response trả về nếu thành công
                response.setStatus(HttpServletResponse.SC_OK);
                out.print(gson.toJson(new AuthResponse(account, "Đăng nhập thành công", true)));
            }

            out.flush();
        } catch (SQLException | ClassNotFoundException e) {
            // Xử lý lỗi và trả về JSON hợp lệ
            handleServerError(response, e);
        }
    }

    // Phương thức cấu hình CORS
    private void configureCORS(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:5173"); // Frontend URL
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    // Phương thức xử lý lỗi server
    private void handleServerError(HttpServletResponse response, Exception e) throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            out.print(gson.toJson(new ResponseMessage("Lỗi server: " + e.getMessage(), false)));
            out.flush();
        }
        e.printStackTrace(); // Ghi log chi tiết lỗi trên console
    }

    // Lớp ResponseMessage cho các thông báo đơn giản
    public class ResponseMessage {
        private String message;
        private boolean success;

        public ResponseMessage(String message, boolean success) {
            this.message = message;
            this.success = success;
        }

        // Getters and setters
        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }

    // Lớp AuthResponse cho phản hồi khi đăng nhập thành công
    public class AuthResponse {
        private usersEntity user;
        private String message;
        private boolean success;

        public AuthResponse(usersEntity user, String message, boolean success) {
            this.user = user;
            this.message = message;
            this.success = success;
        }

        // Getters and setters
        public usersEntity getUser() {
            return user;
        }

        public void setUser(usersEntity user) {
            this.user = user;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }
}
