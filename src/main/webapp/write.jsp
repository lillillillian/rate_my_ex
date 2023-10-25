<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="index.css" rel="stylesheet" type="text/css">
    <link href="detail.css" rel="stylesheet" type="text/css">
    <link href="write.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Bangers">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Rajdhani">
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
            		out.print("<span id=\"welcome\">Hi, "+ username + "!</span>");
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
    <div id="add-person">
    	<h1 class="header">Add a New Person</h1>
    	<form action="WriteDispatcher" method="GET">
    	<span class="element">
    		<label style="font-family: 'Montserrat', sans-serif;">Enter a Name: </label>
    		<span class="input">
    			<input style="margin-left:6rem"type="text" name="name" class="user-inf" required>
    		</span>
    	</span>
    	<div class="element" style="position:relative; top:2rem;">
    		<label style="font-family: 'Montserrat', sans-serif;padding-bottom:10px">Gender: </label>
    		<span style="margin-left:7rem;" class="input">
    			<label style="font-family: 'Montserrat', sans-serif;"><input type="radio" name="gender" value="male" required/>Male</label>
    			<label style="font-family: 'Montserrat', sans-serif;"><input type="radio" name="gender" value="female" />Female</label>
    			<label style="font-family: 'Montserrat', sans-serif;"><input type="radio" name="gender" value="other" />Other</label>
    		</span>
    	</div>
    	<div class="element" style="position:relative; top:4rem;">
    		<label style="font-family: 'Montserrat', sans-serif;position:relative; top:1rem;" >Choose a rating: </label>
    		<span class="rate">
			    <input type="radio" id="star5" name="rate" value="5" required/>
			    <label style="font-family: 'Montserrat', sans-serif;" for="star5" title="text">5</label>
			    <input type="radio" id="star4" name="rate" value="4" />
			    <label style="font-family: 'Montserrat', sans-serif;"for="star4" title="text">4</label>
			    <input type="radio" id="star3" name="rate" value="3" />
			    <label style="font-family: 'Montserrat', sans-serif;"for="star3" title="text">3</label>
			    <input type="radio" id="star2" name="rate" value="2" />
			    <label style="font-family: 'Montserrat', sans-serif;"for="star2" title="text">2</label>
			    <input type="radio" id="star1" name="rate" value="1" />
			    <label style="font-family: 'Montserrat', sans-serif;"for="star1" title="text">1</label>
			 </span>
    	</div>
    	<div class="element" style="position:relative; top:8rem;">
    	<label style="font-family: 'Montserrat', sans-serif;">Comment: </label>
    		<span class="input"style="position:relative; top:1rem; left: 7rem" >
    			<textarea id="comment" name="description" row="20" column="100" placeholder = "Description..." required></textarea>
    		</span>
    		<br><br>
    		<button style="font-family: 'Montserrat', sans-serif; margin-top: 30px; position:relative; background-color: #FFFFCC; font-weight: bold; border-radius: 20px;padding: 5px 20px 5px 20px;"type="submit" name="submit" class="regbutton"><i class="fa fa-sign-in"></i>    Submit</button>
    	</div>
    	</form>
    </div>
</body>
</html> 