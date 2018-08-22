<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="SQL.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
    		<link href="https://fonts.googleapis.com/css?family=Muli|Pacifico" rel="stylesheet"/>
    		<link href="styles/styles-login.css" rel="stylesheet"/>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Login</title>
	</head>
	<body>
	<%

	HttpSession currentSession = request.getSession();
	SQLConnection SQLConn = new SQLConnection();
	currentSession.setAttribute("SQLConnection", SQLConn);
	String error;

	if(currentSession.getAttribute("error") != null) {
		error = (String) currentSession.getAttribute("error");
	} else {
		error = "";
	}

	%>
	
	
	<div class = "title">FaceRank</div>
	  	<div class="form-container">
	  		<form name="myform" method="POST" action="authenticate.jsp">
	        <div class="fields">
	      	  <input type="text" name="username" placeholder="Username" /> <br />
	          <input type="password" name="password" placeholder="Password"/> <br />
	        </div>
			<div class = "error"><%=error%></div>
	        <input type="submit" value="Submit" />
	  		</form>
	  	</div>
    <div class="guest-container">
      <a href="viewall.jsp">Continue as guest </a>
      &nbsp;&nbsp;&nbsp;
      <a href="new.jsp">Create new account</a>
    </div>	
	</body>
</html>
