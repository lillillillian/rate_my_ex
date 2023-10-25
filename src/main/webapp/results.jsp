<%@ page import="Util.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="results.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Bangers">
    <link href="https://fonts.googleapis.com/css2?family=Tapestry&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;300&display=swap" rel="stylesheet">
    <title>Search</title>
    
</head>
<body>
	<!-- TODO -->
	
	<%
        //TODO perform search
        // search through the database and find the matching restaurant
        // search through data base and find according to category its corresponding name
        
		
	    String searchText = request.getParameter("text-box");
    	searchText = (String)session.getAttribute("searchText");
    	
    	System.out.println(searchText);
		
		String username = "";
		Cookie cookies[] = request.getCookies();
	    if (cookies != null) {
	    	for (Cookie cookie : cookies) {
	    		if (cookie.getName().equals("username")) username = cookie.getValue().replace("=", " "); 
	    	}
	    }
		
    %>
	
	<div id="navbar">
        <div id="navbar-left">
            <a href="index.jsp" id="logo">Rate my ex</a>
            <%
            	if (!username.equals("")) {
            		out.print("<span id=\"welcome\">Hi, "+ username + "!</span>");
            	}
            %>
        </div>
        <%
        if (!username.equals("")) {
        	%>
	        <div id="navbar-middle">
	        	<a href="ContactDispatcher" class="navbar-link" style="font-family: 'Montserrat', sans-serif;">Contacts</a>
	        </div>
	        <%
        }
        %>
        <div id="navbar-right">
            <a href="index.jsp" class="navbar-link">Home</a>
            <%
            	if (!username.equals("")) {
            		%><a href="LogoutDispatcher" class="navbar-link">Logout</a><%
            	}
            	else {
            		%><a href="login.jsp" class="navbar-link">Login/Register</a><%
            	}
            %>
        </div>
    </div>

	    <div id="search">
        <div id="search-text">
            <p>Enter a name to see the comments</p>
        </div>
        <div id="search-box">
            <form action="SearchServlet" method="GET">
                <div id="search-text-box">
                    <input type="text" name="text-box" id="text-box" placeholder="Name">
                </div>
            </form>
        </div>
    </div>
	
	<div id="search-main">
	    <h1><%= Helper.myList.size() %> people with "<%= searchText %>" in their name</h1>
	    <hr>
	    <!--  loop through all of the search results  -->
	    <%    
	    for(int num = 0; num < Helper.myList.size(); num++) {
		      Person temp = Helper.myList.get(num);
		      Integer id = temp.personid;
		%>
		      <div class = "section" style="font-family: 'Montserrat', sans-serif; margin-left: 3rem;">
		         <div class="name">
		             <h3><%=temp.name%></h3>
		         </div>
		         <div class="texts">
		             
		             <p>Gender: <%= temp.gender %></p >
		             <p>Count: <%= temp.rating_count %></p >
		             <p>Rating: <%  double rating = temp.overall_rating;
		             for (int i = 0; i < (int)rating; i++) { %>
		             	<span class="fa fa-star checked"></span>
		             <%}%>
		             <%if(rating - (int)rating > 0){%>
		             	<i class="fa fa-star-half-o"></i>
		             <%} %></p >
		             <p><a style="position:relative; top:1rem;	background-color: #FFFFCC; font-weight: bold; border-radius: 20px;padding: 5px 20px 5px 20px;" href="detail.jsp?personId=<%= id %>">
		             View Details >>
		             </a></p>
		          <br>
		      
		         </div>
		         
		     </div>
		     
		     <hr>
	     <% } %>
	     
	</div>
	<br>
	<div style="padding-left: 20%;">
	<%
		if (!username.equals("")) {
			%><a href="write.jsp" style="margin-left: 3rem; font-family: 'Tapestry', cursive;background-color: black; color: white; font-weight: bold; border-radius: 20px;padding: 5px 20px 5px 20px;">Didn't find the one you are looking for?</a><%
		}
		else {
			%><a href="login.jsp" style="margin-left: 3rem; font-family: 'Tapestry', cursive;background-color: black; color: white; font-weight: bold; border-radius: 20px;padding: 5px 20px 5px 20px;">Didn't find the one you are looking for? Log in to create a new person!</a><%
		}
	%>
	</div>
	<br><br>
</html>