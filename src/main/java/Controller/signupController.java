package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;

import DAO.signupDAO;
import Entity.usersEntity;


@WebServlet("/signupController")
public class signupController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private signupDAO SignupDAO;
    
	@Resource(name="jdbc/DB_ECOMMERCE_J2EE")
	private DataSource datasource;
	
	@Override
    public void init() {
		SignupDAO = new signupDAO(datasource);
	}
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        usersEntity newUser = new usersEntity(0, username, password, email, null, null);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        
        try {
            boolean success = SignupDAO.CreateAccount(newUser);

            if (success) {
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.print(gson.toJson("Tạo tài khoản thành công!"));
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(gson.toJson("Đăng ký thất bại. Vui lòng thử lại!"));
            }
            out.flush();
        } catch (ClassNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(gson.toJson("Lỗi máy chủ. Vui lòng thử lại sau!"));
            e.printStackTrace();
            out.flush();
        }
    }

}
