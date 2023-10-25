<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="index.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Bangers">
    <link href="https://fonts.googleapis.com/css2?family=Tapestry&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;300&display=swap" rel="stylesheet">
    <title>RATE MY EX</title>
</head>
<body>
	<%
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
            		out.print("<span id=\"welcome\" style=\" font-size=15px; font-weight: bold; font-family: 'Montserrat', sans-serif;\">Hi, "+ username + "!</span>");
            	}
            %>
        </div>
        <%
        	if (!username.equals("")) {
        		%>	<div id="navbar-middle">
        			<a href="ContactDispatcher" class="navbar-link">Contacts</a>
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
    	<div id="search-description">
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
    </div>
</body>
</html> 