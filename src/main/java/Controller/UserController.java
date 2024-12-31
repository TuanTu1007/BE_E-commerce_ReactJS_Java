package Controller;

import java.io.IOException;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import DAO.UserDAO;
import Entity.usersEntity;
import com.google.gson.Gson;

@WebServlet("/userController")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    @Resource(name = "jdbc/DB_ECOMMERCE_J2EE")
    private DataSource dataSource;

    @Override
    public void init() {
        userDAO = new UserDAO(dataSource);
    }

    // Lấy thông tin người dùng
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = (Integer) request.getSession().getAttribute("userId"); // Đảm bảo kiểm tra null
        if (userId == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("User ID is not provided in session.");
            return;
        }

        try {
            usersEntity userProfile = userDAO.getUserProfile(userId);
            if (userProfile != null) {
                String jsonResponse = new Gson().toJson(userProfile);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonResponse);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("User not found.");
            }
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }

    // Cập nhật thông tin người dùng
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = (Integer) request.getSession().getAttribute("userId"); // Đảm bảo kiểm tra null
        if (userId == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("User ID is not provided in session.");
            return;
        }

        String username = request.getParameter("username");
        String email = request.getParameter("email");

        if (username == null || email == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Username and email are required.");
            return;
        }

        usersEntity userProfile = new usersEntity(userId, username, null, email, null, null);
        try {
            boolean success = userDAO.updateUserProfile(userProfile); // Đổi thành tên hàm chính xác
            response.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
