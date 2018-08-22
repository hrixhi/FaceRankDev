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
	ArrayList<String> requestUsernames = SQLConn.getFriendRequests(username);

 %>
<% for(int i = 0; i<requestUsernames.size(); i++) { %>
	<%= SQLConn.getName(requestUsernames.get(i))%><a href = "addFriend?from=<%=requestUsernames.get(i)%>"> Accept Request</a> <br/>
<% } %>