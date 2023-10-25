import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Serial;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Util.*; 

@WebServlet("/WriteDispatcher")
public class WriteDispatcher extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WriteDispatcher() {}
	
	public static int count = 4;
	
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		response.setContentType("text/html");
		
		try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
		
		HttpSession session = request.getSession();
		Integer personid = 0;
		String comments = request.getParameter("description");
		String rating = request.getParameter("rate");
		int ratings = Integer.parseInt(rating);
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		Double myRating = (double) ratings;
		
		String c="SELECT COUNT(*) FROM PERSON";
		//get the size of db for personid
				try (Connection conn = DriverManager.getConnection(Util.Constant.Url, Util.Constant.DBUserName, Util.Constant.DBPassword);
		       	       PreparedStatement ps = conn.prepareStatement(c);) {
					ResultSet rs=ps.executeQuery();
					while(rs.next()) {
						personid= rs.getInt(1)+1;
					}
					
				}
				catch (SQLException ex) 
			      {
			      	System.out.println ("SQLException: " + ex.getMessage());
			      }
				
				
				Integer postid=0;
				String c1="SELECT COUNT(*) FROM POST";
				//get the size of db for personid
				try (Connection conn = DriverManager.getConnection(Util.Constant.Url, Util.Constant.DBUserName, Util.Constant.DBPassword);
		       	       PreparedStatement ps = conn.prepareStatement(c);) {
						ResultSet rs=ps.executeQuery();
						while(rs.next()) {
							postid= rs.getInt(1)+1;
						}
						
					}
					catch (SQLException ex) {
				      	System.out.println ("SQLException: " + ex.getMessage());
					}
						
		String userID = "";
		Cookie cookies[] = request.getCookies();
	    if (cookies != null) {
	    	for (Cookie cookie : cookies) {
	    		if (cookie.getName().equals("userid")) userID = cookie.getValue(); 
	    	}
	    }
	    
	    System.out.println(personid);
	    System.out.println(comments);
	    System.out.println(rating);
	    System.out.println(gender);
	    System.out.println(userID);
	    
	    Integer userid = Integer.parseInt(userID);
	    System.out.println("USERID!!!!!!!!!!!!!!!!!!!");
	    System.out.println(userid);
	    
	    String addPerson = "INSERT INTO Person (personid, name, gender, overall_rating, rating_count) VALUES(?,?,?,?,?)";
	    
	    try (Connection conn = DriverManager.getConnection(Util.Constant.Url, Util.Constant.DBUserName, Util.Constant.DBPassword);
	     	       PreparedStatement ps = conn.prepareStatement(addPerson);) 
	        {
				ps.setInt(1,personid);
	    		ps.setString(2, name);
				ps.setString(3, gender);
	        	ps.setInt(4, ratings);
	        	ps.setInt(5, 1);
	        	ps.execute();
	        }
	        catch (SQLException exs) {System.out.println("SQLException: " + exs.getMessage());}
	    
		
		String insert ="INSERT INTO Post (postid, personid,userid, description, rating) VALUES (?,?,?,?,?)";

		
		try (Connection conn = DriverManager.getConnection(Util.Constant.Url, Util.Constant.DBUserName, Util.Constant.DBPassword);
	     	       PreparedStatement ps = conn.prepareStatement(insert);) 
	        {
				ps.setInt(1,postid);
				ps.setInt(2, personid);
				ps.setInt(3,userid);
	        	ps.setString(4, comments);
	        	ps.setDouble(5, myRating);
	        	ps.execute();
	        }
	        catch (SQLException exs) {System.out.println("SQLException: " + exs.getMessage());}
		
		//Helper myHelper = new Helper();
		//myHelper.recalculate(personid, ratings);
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.sendRedirect("SearchServlet?text-box="+name);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 doGet(request, response);
	}
	
}