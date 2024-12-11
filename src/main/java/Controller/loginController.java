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
	
	@Resource(name="jdbc/DB_ECOMMERCE_J2EE")
	private DataSource datasource;
	
	@Override
    public void init() {
		LoginDao = new loginDAO(datasource);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
		
        usersEntity account;
		try {
			account = LoginDao.Login(email, password);
			PrintWriter out = response.getWriter();
            Gson gson = new Gson();

            if (account == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print(gson.toJson("Sai tài khoản hoặc mật khẩu"));
            } else {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print(gson.toJson(account));
            }
            out.flush();
        } catch (SQLException | ClassNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
	}
}
