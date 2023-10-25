import java.io.IOException;
import java.io.Serial;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CreateDispatcher")
public class CreateDispatcher extends HttpServlet{
	public CreateDispatcher(){}
	@Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		response.setContentType("text/html");
		String name= request.getParameter("name");
		String birth=  request.getParameter("birth");
		String rating =request.getParameter("rating");
		String description=request.getParameter("description");
		int personID=0;
		
		
		String error = "";
		if (name == null || name.equals("")) error += "<p>Missing name. ";
        else if (birth == null || birth.equals("")) error += "<p>Missing birth. ";
        else if (rating == null || rating.equals("")) error += "<p>Missing rating. ";
        else if (description == null || description.equals("")) error += "<p>Missing description. ";
		
		if (!error.equals("")) {
	        error += "Please enter again.</p>";
	       	request.setAttribute("error", error);
			request.getRequestDispatcher("write.jsp").include(request, response);
			return;
		}
		
		//person id is the primary key determined by the order been added
		String count="SELECT COUNT(*) AS nums FROM BIAO1";
		String insert="INSERT INTO biao1 VALUES (?, ?, ?, ?)";
		
		try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		
		//get the size of db for personid
		try (Connection conn = DriverManager.getConnection(Util.Constant.Url, Util.Constant.DBUserName, Util.Constant.DBPassword);
       	       PreparedStatement ps = conn.prepareStatement(count);) {
			ResultSet rs=ps.executeQuery();
			personID=rs.getInt("count(*)");
			
		}
		catch (SQLException ex) 
	      {
	      	System.out.println ("SQLException: " + ex.getMessage());
	      } 
		
		
		//add this new person
		 try (Connection conn = DriverManager.getConnection(Util.Constant.Url, Util.Constant.DBUserName, Util.Constant.DBPassword);
      	       PreparedStatement ps = conn.prepareStatement(insert);) {
			 ps.setInt(1, personID);
			 ps.setString(2, name);
			 ps.setString(3, birth);
			 ps.setString(4, rating);
			 ps.executeUpdate();
		 } 
		 catch (SQLException ex) 
		 {
			 System.out.println ("SQLException: " + ex.getMessage());
		 } 
		 
        
	 }
	 
	 @Override
	 protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		 doGet(request, response);
	 }
}