<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="SQL.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Servlets.*" %>
<%@ page import= "java.lang.*" %>
<%
 	
	HttpSession currentSession = request.getSession();
	
	SQLConnection SQLConn = (SQLConnection) currentSession.getAttribute("SQLConnection");
	String username = (String) currentSession.getAttribute("username");
	String usernameTo = (String) currentSession.getAttribute("usernameTo");
	
	boolean output = SQLConn.sendFriendRequest(username, usernameTo);

	System.out.println(output);
 %>
 <%= output %>

