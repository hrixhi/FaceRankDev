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
		String fname = request.getParameter("firstname");
		String lname = request.getParameter("lastname");
		boolean error = false;
		String test = null;
		
	HttpSession currentSession = request.getSession();
	SQLConnection SQLConn = (SQLConnection) currentSession.getAttribute("SQLConnection");

	if (username.equals("") || username == null) {
		test = "Please enter username!";
		error = true;
	} else if (password.equals("") || password == null) {
		test = "Please enter password!";
		error = true;
	} else if (fname.equals("") || fname == null) {
		test = "Please enter firstname!";
		error = true;
	} else if (lname.equals("") || lname == null) {
		test = "Please enter lastname!";
		error = true;
	} else {

	}

	if (!error) {
		test = SQLConn.add(username, password, fname, lname);
	}
	
	if (!error && test.equals("pass")) {
		currentSession.setAttribute("username", username);
		currentSession.setAttribute("usernameTo", username);
		currentSession.setAttribute("authenticated", true);
		currentSession.setAttribute("imageCount", 0);
		response.sendRedirect("homepage.jsp");
	} else {
		currentSession.setAttribute("errorx", test);
		response.sendRedirect("new.jsp");
	}
%>

</body>
</html>