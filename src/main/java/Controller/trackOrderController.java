package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import DAO.trackOrderDAO;
import Entity.orderDetailEntity;

@WebServlet("/trackOrderController")
public class trackOrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource(name = "jdbc/DB_ECOMMERCE_J2EE") 
    private DataSource dataSource;
	
	trackOrderDAO dao = new trackOrderDAO(dataSource);
	
	@Override
    public void init() throws ServletException {
        super.init();
        dao = new trackOrderDAO(dataSource);
    }
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<orderDetailEntity> orderDetailsList = dao.getAllOrderDetails();
            request.setAttribute("orderDetailsList", orderDetailsList);
            RequestDispatcher dispatcher = request.getRequestDispatcher("trackOrder.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Cannot retrieve order details", e);
        }
    }

}
