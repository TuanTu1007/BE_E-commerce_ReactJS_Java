package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Cấu hình CORS
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (PrintWriter out = response.getWriter()) {
            Gson gson = new Gson();
            usersEntity account = LoginDao.Login(email, password);

            if (account == null) {
                // Trả về JSON cho lỗi xác thực
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print(gson.toJson(new ResponseMessage("Sai tài khoản hoặc mật khẩu", false)));
            } else {
                // Trả về JSON cho tài khoản đã xác thực
                response.setStatus(HttpServletResponse.SC_OK);
                out.print(gson.toJson(new AuthResponse(account, "Đăng nhập thành công", true)));
            }

            out.flush();
        } catch (SQLException | ClassNotFoundException e) {
            // Trả về JSON cho lỗi server
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            try (PrintWriter out = response.getWriter()) {
                Gson gson = new Gson();
                out.print(gson.toJson(new ResponseMessage("Lỗi server: " + e.getMessage(), false)));
                out.flush();
            }
            e.printStackTrace();
        }
    }
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
