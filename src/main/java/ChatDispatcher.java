import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import Util.Constant;
import Util.Message;

import javax.servlet.http.Cookie;

/**
 * Servlet implementation class LoginDispatcher
 */
@WebServlet("/ChatDispatcher")
public class ChatDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    
    public String getUsername(int userID)
    {
    	String username ="";
    	try
    	{
    		Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(Constant.Url, Constant.DBUserName, Constant.DBPassword);
			String sql = "SELECT username FROM Username WHERE userid = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
			{
				username = rs.getString("username");
			}
			ps.close();
    	}
    	catch (Exception e) {e.printStackTrace();}
    	return username;
    }
    
    public int getUserID(String username)
    {
    	int userID = -1;
    	try
    	{
    		Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(Constant.Url, Constant.DBUserName, Constant.DBPassword);
			String sql = "SELECT userid FROM Username WHERE username = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
			{
				userID = rs.getInt("userid");
			}
			ps.close();
    	}
    	catch (Exception e) {e.printStackTrace();}
    	return userID;
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	int userID = -1;
    	Cookie[] cookies = request.getCookies();
    	if (cookies != null)
    	{
    		for (Cookie cookie: cookies)
    		{
    			if (cookie.getName().equals("userid")) userID = Integer.valueOf(cookie.getValue());
    		}
    	}
    	response.setContentType("text/html");
    	String text = request.getParameter("text");
    	String otherUserName = request.getParameter("otherUserName");
    	String otherUserIDString = request.getParameter("otherUserID");
    	Integer otherUserID = otherUserIDString == null ? getUserID(otherUserName) : Integer.valueOf(otherUserIDString);
    	otherUserName = otherUserName == null ? getUsername(otherUserID) : otherUserName;
    	Timestamp createdTime = new Timestamp(System.currentTimeMillis());
    	if (text != null && !text.isEmpty())
    	{
	    	Message message = new Message(text, userID, otherUserID, createdTime);
	    	message.insertIntoDatabase();
    	}
    	request.setAttribute("otherUserName", otherUserName);
    	request.setAttribute("otherUserID", otherUserID);
    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/chat.jsp");
    	dispatcher.forward(request, response);
    }
}