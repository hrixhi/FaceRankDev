<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="SQL.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Servlets.*" %>
<%@ page import= "java.lang.*" %>
<%
 	String input = request.getParameter("inputname");
 	input = input.toLowerCase();	
 	
	HttpSession currentSession = request.getSession();
	
	if(input.equals("")) {
		response.sendRedirect("homepage.jsp");
		return;
	}
	
	ArrayList<String> matches = new ArrayList<String>();
	String output = "";
	
	SQLConnection SQLConn = (SQLConnection) currentSession.getAttribute("SQLConnection");
	matches = SQLConn.getMatches(input);
	
	for(int i = 0; i < matches.size(); i++) {
		if(i != matches.size() - 1) {
			output += matches.get(i) + ",";
		}
		else {
			output += matches.get(i);
		}
	}

	System.out.println(output);
 %>
 <%= output %>

