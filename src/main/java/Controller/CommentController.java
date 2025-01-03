package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import DAO.CommentDAO;
import Entity.CommentEntity;
import com.google.gson.Gson;

@WebServlet("/CommentController")
public class CommentController extends HttpServlet {
    @Resource(name = "jdbc/DB_ECOMMERCE_J2EE")
    private DataSource dataSource;

    private CommentDAO commentDAO;

    @Override
    public void init() {
        commentDAO = new CommentDAO(dataSource);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        try {
            int userId = (Integer) request.getSession().getAttribute("userId");

            String productId = request.getParameter("productId");
            String comment = request.getParameter("comment");

            if (productId == null || comment == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Invalid input.\"}");
                return;
            }

            boolean success = commentDAO.addComment(userId, productId, comment);
            response.setStatus(success ? HttpServletResponse.SC_OK : HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"" + (success ? "Comment added successfully." : "Failed to add comment.") + "\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"An error occurred: " + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        try {
            String productId = request.getParameter("productId");

            if (productId == null || productId.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"message\": \"Product ID is required.\"}");
                return;
            }

            // Lấy danh sách comment từ DAO
            List<CommentEntity> comments = commentDAO.getCommentsByProductId(productId);
            if (comments == null || comments.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"message\": \"No comments found for this product.\"}");
                return;
            }

            // Trả về danh sách comment dưới dạng JSON
            response.getWriter().write(new Gson().toJson(comments));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"message\": \"An error occurred: " + e.getMessage() + "\"}");
            e.printStackTrace();
        }
    }
}


