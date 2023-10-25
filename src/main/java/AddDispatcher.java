import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Util.*;

@WebServlet("/AddDispatcher")
public class AddDispatcher extends HttpServlet{
	public AddDispatcher() {}
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		response.setContentType("text/html");
		String des=request.getParameter("description");
		
//		String error = "";
//		if (des == null || des.equals("")) error += "<p>Missing description. ";
//		
//		if (!error.equals("")) 
//        {
//        	error += "Please enter again.</p>";
//        	request.setAttribute("error", error);
//			request.getRequestDispatcher("detail.jsp").include(request, response);
//			return;
//        }
		
		try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		
		HttpSession session = request.getSession();
		Integer personid = (Integer)session.getAttribute("personid");
		String comments = request.getParameter("comment");
		String rating = request.getParameter("rating");
		int ratings = Integer.parseInt(rating);
		Double myRating = (double) ratings;
		
		String userID = "";
		Cookie cookies[] = request.getCookies();
	    if (cookies != null) {
	    	for (Cookie cookie : cookies) {
	    		if (cookie.getName().equals("userid")) userID = cookie.getValue(); 
	    	}
	    }
	    
	    Integer userid = Integer.parseInt(userID);
		
		String insert ="INSERT INTO Post (personid, userid, description, rating) VALUES (?,?,?,?)";

		
		try (Connection conn = DriverManager.getConnection(Util.Constant.Url, Util.Constant.DBUserName, Util.Constant.DBPassword);
	     	       PreparedStatement ps = conn.prepareStatement(insert);) 
	        {
				ps.setInt(1, personid);
				ps.setInt(2, userid);
	        	ps.setString(3, comments);
	        	ps.setDouble(4, myRating);
	        	ps.execute();
	        }
	        catch (SQLException ex) {System.out.println("SQLException: " + ex.getMessage());}
		
		Helper myHelper = new Helper();
		myHelper.recalculate(personid, ratings);
		
		response.sendRedirect("detail.jsp?personId="+personid.toString());
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 doGet(request, response);
	}
	
}