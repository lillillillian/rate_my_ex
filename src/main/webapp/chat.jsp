<%@ page import="java.util.*" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="index.css" rel="stylesheet" type="text/css">
    <link href="chat.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Bangers">
    <title>RATE MY EX</title>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="jquery-3.6.0.min.js"></script>
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
	    String otherUserName = (String) request.getAttribute("otherUserName");
	    Integer otherUserID = (Integer) request.getAttribute("otherUserID");
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
        <div id="navbar-middle">
        	<a href="ContactDispatcher" class="navbar-link">Contacts</a>
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
    <div id="chat-name">
    	<p></p><%=otherUserName%>
    </div>
    <div id="chat-history">
    </div>
    <div id="chat">
    	<form action="ChatDispatcher" method="POST" id="input-box">
    		<div id="chat-text-box">
    			<input type="text" name="text" id="chat-box" placeholder="Type something ...">
    			<input type="hidden" name="otherUserName" id="otherUserName" value=<%=otherUserName%>>
    			<input type="hidden" name="otherUserID" id="otherUserID" value=<%=otherUserID%>>
    		</div>
    	</form>
    </div>
    <script>
		const chatHistory = document.querySelector("#chat-history");
		const form = document.querySelector("#input-box");
		const otherUserName = "<%=otherUserName%>";
		const otherUserID = "<%=otherUserID%>";
		setInterval(() => {
			let xhr = new XMLHttpRequest();
			xhr.open("POST", "get-chat.jsp", true);
			xhr.onload = ()=> {
				if (xhr.readyState === XMLHttpRequest.DONE) {
					if (xhr.status === 200) {
						let data = xhr.response;
						chatHistory.innerHTML = data;
					}
				}
			}
			xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhr.send("otherUserName="+otherUserName+"&otherUserID="+otherUserID);
		}, 1000);
	</script> 
</body>
</html>