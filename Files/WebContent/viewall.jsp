<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="SQL.SQLConnection" import="Servlets.*"%>
<%@ page import="WebSocket.*" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <link href="https://fonts.googleapis.com/css?family=Muli|Pacifico" rel="stylesheet"/>
<link href="styles/styles-homepage.css" rel="stylesheet">
<title>Guest experience</title>
</head>
<body>
<div class="header" id = "title">FaceRank</div>
<br/>
<%
	HttpSession currentSession = request.getSession();
	SQLConnection SQLConn = (SQLConnection) currentSession.getAttribute("SQLConnection");
	currentSession.setAttribute("username", "guest");
	
	ArrayList<String> users = SQLConn.getAllUsers();
%>
<br/>
<div class = "span-container">
<div class = "viewall">
<div class ="subtitle">Active Users</div><hr/><br/>
<% for(int i = 0; i<users.size(); i++) {
	String friend = users.get(i);
	String friendName = SQLConn.getName(friend);
%>
<a href="redirect?to=<%=friend%>"><%=friendName%></a><br/><br/>
<% } %>
<br/>
<br/>
<br/>
<form name="myform" method="POST" action="${pageContext.request.contextPath}/goback">
<input type="submit" value="Return to Login" />
</form>
	<a href="leaderboard.jsp">View Leaderboard</a>

</div>
</div>
</body>

</html>