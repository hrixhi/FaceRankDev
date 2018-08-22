<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="SQL.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    		<link href="https://fonts.googleapis.com/css?family=Muli|Pacifico" rel="stylesheet"/>
    		<link href="styles/styles-login.css" rel="stylesheet"/>
<title>Create New Account</title>
</head>
<body>
<%

HttpSession currentSession = request.getSession();
SQLConnection SQLConn = new SQLConnection();
currentSession.setAttribute("SQLConnection", SQLConn);
String errorx;

if(currentSession.getAttribute("errorx") != null) {
	errorx = (String) currentSession.getAttribute("errorx");
} else {
	errorx = "";
}

%>
	<div class = "title">FaceRank</div>

<div class="form-container">
<form action="authenticatenew.jsp">
  <input type="text" name="username" value="" placeholder="Username">
  <br>
  <input type="password" name="password" value="" placeholder="Password">
  <br>
  <br>
  <input type="text" name="firstname" value="" placeholder="First Name">
  <br>
  <input type="text" name="lastname" value="" placeholder="Last Name">
  <br><br>
    <div class = "errorx"><%=errorx%></div>

  <input type="submit" value="Submit">
  
</form> 
<br/>
</div>
<div class="form-container">

<form name="myform" method="POST" action="${pageContext.request.contextPath}/goback">
<input type="submit" value="Return to Login" />
</form>
</div>
</body>
</html>