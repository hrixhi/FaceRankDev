<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="SQL.SQLConnection"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		System.out.println(username + " " + password);
		
		HttpSession currentSession = request.getSession();
		SQLConnection SQLConn = (SQLConnection) currentSession.getAttribute("SQLConnection");
		
		if(SQLConn.validate(username, password)) {
			currentSession.setAttribute("username", username);
			currentSession.setAttribute("usernameTo", username);
			currentSession.setAttribute("authenticated", true);
			currentSession.setAttribute("imageCount", 0);
			response.sendRedirect("homepage.jsp");
		}
		else{
			currentSession.setAttribute("error", "Invalid username and password.");
			response.sendRedirect("login.jsp");
		}
	%>

</body>
</html>