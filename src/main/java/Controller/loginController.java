package Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import DAO.loginDAO;
import Entity.usersEntity;

@WebServlet("/loginController")
public class loginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private loginDAO LoginDao;
    private static final String SECRET_KEY = "mySecretKey"; // Replace with a secure key

    @Override
    public void init() {
        // Initialize DataSource (example: JNDI lookup)
        try {
            DataSource dataSource = (DataSource) new javax.naming.InitialContext()
                    .lookup("java:/comp/env/jdbc/yourDataSource");
            LoginDao = new loginDAO(dataSource);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize DataSource", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            // Call the Login method from loginDAO
            usersEntity user = LoginDao.Login(email, password);
            if (user != null) {
                // Generate JWT token
                String token = Jwts.builder()
                        .setSubject(email)
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Token valid for 1 hour
                        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                        .compact();

                // Send response
                response.setStatus(HttpServletResponse.SC_OK);
                out.print(new Gson().toJson(new AuthResponse("success", token)));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.print(new Gson().toJson(new AuthResponse("error", "Invalid credentials")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print(new Gson().toJson(new AuthResponse("error", "An error occurred")));
        } finally {
            out.flush();
            out.close();
        }
    }

    // Helper class for JSON response
    private class AuthResponse {
        private String status;
        private String token;

        public AuthResponse(String status, String token) {
            this.status = status;
            this.token = token;
        }

        public String getStatus() {
            return status;
        }

        public String getToken() {
            return token;
        }
    }
}
