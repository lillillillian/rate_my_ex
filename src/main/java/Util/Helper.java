package Util;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Helper {
	public static List<Person> myList = new ArrayList<Person>();
	public static boolean flag = true;
	
	public Helper() {
		
	}
	
	public void recalculate(Integer personID, Integer rating) {
    	try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    	
    	String check = "SELECT rating_count FROM Person WHERE personid = ?";
    	Integer count = 0;
    	
    	try (Connection conn = DriverManager.getConnection(Util.Constant.Url, Util.Constant.DBUserName, Util.Constant.DBPassword);
       	       PreparedStatement ps = conn.prepareStatement(check);) 
         {
          	ps.setInt(1, personID);
          	ResultSet rs = ps.executeQuery();
          	rs.next();
          	count = rs.getInt(1);
         }
    	 catch (SQLException ex) {System.out.println("SQLException: " + ex.getMessage());}
    	
    	String search = "SELECT * FROM Person WHERE personid = ?";
    	Double overRating = 0.;
    	try (Connection conn = DriverManager.getConnection(Util.Constant.Url, Util.Constant.DBUserName, Util.Constant.DBPassword);
        	       PreparedStatement ps = conn.prepareStatement(search);) 
          {
           	ps.setInt(1, personID);
           	ResultSet rs = ps.executeQuery();
           	rs.next();
           	overRating = rs.getDouble("overall_rating");
          }
     	 catch (SQLException ex) {System.out.println("SQLException: " + ex.getMessage());}
    	
    	Double newRating = (overRating * count + rating)/(count+1);
    	System.out.println(count);
    	//System.out.println(overRating);
    	//System.out.println(rating);
    	//System.out.println(newRating);
    	//System.out.println(personID);
    	
    	String update = "UPDATE Person SET overall_rating=?, rating_count=? WHERE personid=?";
    	try (Connection conn = DriverManager.getConnection(Util.Constant.Url, Util.Constant.DBUserName, Util.Constant.DBPassword);
     	       PreparedStatement ps = conn.prepareStatement(update);) 
        {
        	ps.setDouble(1, newRating);
        	ps.setInt(2, count+1);
        	ps.setInt(3, personID);
        	ps.execute();
        }
        catch (SQLException ex) {System.out.println("SQLException: " + ex.getMessage());}
	}
	
}
