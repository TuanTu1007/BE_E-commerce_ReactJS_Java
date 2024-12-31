package Controller;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import DAO.RatingDAO;

@WebServlet("/ratingController")
public class RatingController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private RatingDAO ratingDAO;

    @Resource(name = "jdbc/DB_ECOMMERCE_J2EE")
    private DataSource dataSource;

    @Override
    public void init() {
        ratingDAO = new RatingDAO(dataSource);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (Integer) request.getSession().getAttribute("userId");
        String productId = request.getParameter("productId");
        int rating = Integer.parseInt(request.getParameter("rating"));

        try {
            boolean success = ratingDAO.addRating(userId, productId, rating);
            response.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}