
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int num = Integer.parseInt(request.getParameter("num"));
		String name = request.getParameter("name");
		int balance = Integer.parseInt(request.getParameter("balance"));
		
		
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		
		
		try {
			String driver = "oracle.jdbc.driver.OracleDriver";
			Class.forName(driver);
			
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String uname = "IRSHAD";
			String pwd = "123";
			Connection con = DriverManager.getConnection(url,uname,pwd);
			
			String query = "insert into LoginApp values(?,?,?)";
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setInt(1, num);
			ps.setString(2, name);
			ps.setInt(3, balance);
			
			int count = ps.executeUpdate();
			if(count>0) {
				out.println("<h1>Account Created</h1>");
				RequestDispatcher rd = request.getRequestDispatcher("Success.html");
				rd.include(request, response);
			}else {
				out.print("Operation Failed");
				RequestDispatcher rd = request.getRequestDispatcher("Create.html");
				rd.include(request, response);
			}
			con.close();
			
		}catch(Exception e) {
			out.println("<h1>Exception :" + e.getMessage()+ "</h1>");
		}

				
		
	}

}
