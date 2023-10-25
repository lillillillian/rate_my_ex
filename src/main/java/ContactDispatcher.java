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
@WebServlet("/ContactDispatcher")
public class ContactDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
    	request.setAttribute("contacts", getContacts(userID));
    	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/contacts.jsp");
    	dispatcher.forward(request, response);
    }
    
    public ArrayList<String> getContacts(int userID)
    {
    	ArrayList<String> contacts = new ArrayList<String>();
    	try
    	{
    		Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(Constant.Url, Constant.DBUserName, Constant.DBPassword);
			String sql = "SELECT DISTINCT u.username FROM Username u INNER JOIN Message m ON u.userid = m.senderID AND m.receiverID = ? OR u.userid = m.receiverID AND m.senderID = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, userID);
			ps.setInt(2, userID);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				String username = rs.getString("username");
				contacts.add(username);
			}
			ps.close();
    	}
    	catch (Exception e) {e.printStackTrace();}
    	return contacts;
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}