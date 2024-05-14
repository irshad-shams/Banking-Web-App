import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";

        try {

            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, "IRSHAD", "123");

            String query = "SELECT * FROM register WHERE username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
            	out.println("<h1 style='color:red'>Login Successfully</h1>");
            	RequestDispatcher rd = request.getRequestDispatcher("Success.html");
                rd.include(request, response);
                
            } else {
                out.println("<h4 style='color:red'>Login Failed - Try Again</h4>");
                RequestDispatcher rd = request.getRequestDispatcher("login.html");
                rd.include(request, response);
            }

        } catch (Exception e) {
            out.print("<h4 style='color:red'>Error: " + e.getMessage() + "</h4>");
        }
    }
}
