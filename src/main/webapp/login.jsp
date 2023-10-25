<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta content="560279862037-cacflq7fptae4gu2etrjo7igv9djmp8q.apps.googleusercontent.com" name="google-signin-client_id">
    <link href="index.css" rel="stylesheet" type="text/css">
    <link href="login.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Bangers">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Rajdhani">
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@100;300&display=swap" rel="stylesheet">
    <script src="https://apis.google.com/js/api:client.js"></script>
    <script src="https://apis.google.com/js/platform.js?onload=onLoad" async defer></script>
    <title>RATE MY EX</title>
    <script>
    var googleUser = {};
    var startApp = function() {
      gapi.load('auth2', function(){
        // Retrieve the singleton for the GoogleAuth library and set up the client.
        auth2 = gapi.auth2.init({
          client_id: '560279862037-cacflq7fptae4gu2etrjo7igv9djmp8q.apps.googleusercontent.com',
          cookiepolicy: 'single_host_origin',
          scope:'https://www.googleapis.com/auth/userinfo.email'
        });
        attachSignin(document.getElementById('customBtn'));
      });
    };

    function attachSignin(element) {
      auth2.attachClickHandler(element, {},
          function(googleUser) {
  			window.location.href="GoogleDispatcher?name="+googleUser.getBasicProfile().getName();
          }, function(error) {
            alert(JSON.stringify(error, undefined, 2));
          });
    }
    </script>
</head>
<body>
    <div id="navbar">
        <div id="navbar-left">
            <a href="index.jsp" id="logo">Rate my ex</a>
        </div>
        <div id="navbar-right">
            <a href="index.jsp" class="navbar-link">Home</a>
			<a href="login.jsp" class="navbar-link">Login/Register</a>
        </div>
    </div>
    <% 
		String error = (String) request.getAttribute("error");
		if (error != null) out.println("<div style=\"background-color:#ffd6d6;height:3rem;text-align:center;border-top:1px solid #d5d4d4;\">"+error+"</div>");
	%>
	<div id="login">
		<form action="LoginDispatcher" method="GET">
    		<h1 class="header">Login</h1>
    		<p>Email</p>
    		<input type="email" name="email" class="userinf">
    		<br/><br/>
    		<p>Password</p>
    		<input type="password" name="password" class="userinf">
    		<br/><br/>
    		<button type="submit" name="signIn" class="regbutton"><i class="fa fa-sign-in"></i>    Sign In</button>
    	</form>
    	<div id="gSignInWrapper">
   			<button type="button" value="Sign in with Google" id="customBtn"><i class="fa fa-google"></i> Sign In with Google</button>
			<script>startApp();</script>
		</div>
	</div>
	<div id="register">
		<form action="RegisterDispatcher" method="GET">
    		<h1 class="header">Sign Up</h1>
    		<p>Email</p>
    		<input type="email" name="email" class="userinf">
    		<br/><br/>
    		<p>Name</p>
    		<input type="text" name="name" class="userinf">
    		<br/><br/>
    		<p>Password</p>
    		<input type="password" name="password" class="userinf">
    		<br/><br/>
    		<p>Confirm Password</p>
    		<input type="password" name="confirmed_password" class="userinf">
    		<br/><br/>
    		<input type="checkbox" name="terms" value="term"> I agree to the Terms of Use and our Privacy Policy.
    		<br/><br/>
    		<button type="submit" name="Create Account" class="regbutton"><i class="fa fa-user-plus"></i>    Create Account</button>
    	</form>
	</div>
</body>
</html> 