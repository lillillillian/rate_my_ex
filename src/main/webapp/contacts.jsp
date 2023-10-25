<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="index.css" rel="stylesheet" type="text/css">
    <link href="contacts.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Bangers">
    <title>Contacts</title>
</head>
<body>
<%@ page import ="java.util.ArrayList"%>
	<%
		String username = "";
		Cookie cookies[] = request.getCookies();
	    if (cookies != null) {
	    	for (Cookie cookie : cookies) {
	    		if (cookie.getName().equals("username")) username = cookie.getValue().replace("=", " "); 
	    	}
	    }
	    ArrayList<String> contacts = (ArrayList<String>) request.getAttribute("contacts");
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

    <div id="contacts">
    	<%
    		if(contacts != null){
    			for (int i=0; i<contacts.size(); i++){
        			%>
        			<div id="contact" style="text-align: center;">
        			<form action="ChatDispatcher" method="POST">
        				<input type="submit" name="otherUserName" style=" font-family: 'Bangers'; font-size: 40px; width: 30%; padding: 20px; margin: 30px;" value=<%=contacts.get(i)%>>
        			</form>
        			</div>
        			<%
        		}
    		}
    	%>
    </div>
</body>
</html> 