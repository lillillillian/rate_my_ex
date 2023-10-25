import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serial;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class GoogleDispatcher
 */
@WebServlet("/GoogleDispatcher")
public class GoogleDispatcher extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	//get user information
        response.setContentType("text/html");
        String username = request.getParameter("name");
        
        
        // register driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        
     // create database
        try {
	        //Getting the connection
	        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", Util.Constant.DBUserName, Util.Constant.DBPassword);
	        //Initialize the script runner
	        ScriptRunner sr = new ScriptRunner(con);
	        //Creating a reader object
	        ServletContext servletContext = getServletContext();
	        InputStream is = servletContext.getResourceAsStream("rate-my-ex.sql");
	        Reader reader = new InputStreamReader(is);
	        //Running the script
	        sr.runScript(reader);
	        con.close();
    	}
    	catch (SQLException e) {System.out.println(e.getMessage());}
        
        
     // insert original data into database of database is empty now
    	String checkdatabase = "SELECT * FROM Person";
    	try (Connection conn = DriverManager.getConnection(Util.Constant.Url, Util.Constant.DBUserName, Util.Constant.DBPassword);
    			Statement st = conn.createStatement();
        		ResultSet rs = st.executeQuery(checkdatabase);) 
        {
    		if (!rs.next()) // database is empty at this moment
    		{
    			try
    			{
    				//Getting the connection
    		        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", Util.Constant.DBUserName, Util.Constant.DBPassword);
    		        //Initialize the script runner
    		        ScriptRunner sr = new ScriptRunner(con);
    		        //Creating a reader object
    		        ServletContext servletContext = getServletContext();
    		        InputStream is = servletContext.getResourceAsStream("origin.sql");
    		        Reader reader = new InputStreamReader(is);
    		        //Running the script
    		        sr.runScript(reader);
    			}
    			catch (SQLException e) {System.out.println(e.getMessage());}
    		}
        }
    	catch (SQLException e) {System.out.println(e.getMessage());}
        
        
        Integer userid = 0;
        // check whether this user is in database
        String check_google_user = "SELECT userid FROM Username WHERE username = ?";
        try (Connection conn = DriverManager.getConnection(Util.Constant.Url, Util.Constant.DBUserName, Util.Constant.DBPassword);
     	       PreparedStatement ps = conn.prepareStatement(check_google_user);)
        {
        	ps.setString(1, username);
        	ResultSet rs = ps.executeQuery();
        	
        	// user not exist
        	if (!rs.next()) {
        		// insert this user into database
        		String insert_google_user = "INSERT INTO Username (username) VALUES (?)";
        		try (Connection c = DriverManager.getConnection(Util.Constant.Url, Util.Constant.DBUserName, Util.Constant.DBPassword);
        	     	       PreparedStatement p = c.prepareStatement(insert_google_user);)
        		{
        			p.setString(1, username);
        			p.executeUpdate();
        		}
        		catch (SQLException ex) {System.out.println("SQLException: " + ex.getMessage());}
        	}
        	
        	// get userid from database, used for chatting
            String getID = "SELECT userid FROM Username WHERE username = ?";
            try (Connection co = DriverManager.getConnection(Util.Constant.Url, Util.Constant.DBUserName, Util.Constant.DBPassword);
         	       PreparedStatement prs = co.prepareStatement(getID);) 
            {
            	prs.setString(1, username);
            	ResultSet r = prs.executeQuery();
            	r.next();
            	userid = r.getInt("userid");
            } 
            catch (SQLException ex) 
            {
            	System.out.println ("SQLException: " + ex.getMessage());
            }
        }
        catch (SQLException ex) {System.out.println("SQLException: " + ex.getMessage());}
        
        
     // send back username and userid by cookies
        Cookie cookie = new Cookie("username", username.replace(" ", "="));
		cookie.setMaxAge(3600);
		response.addCookie(cookie);
		
    	String userID = userid.toString();
    	Cookie mycookie = new Cookie("userid", userID);
    	response.addCookie(mycookie);

		response.sendRedirect("index.jsp");
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