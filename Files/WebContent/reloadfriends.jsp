<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="SQL.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Servlets.*" %>
<%@ page import= "java.lang.*" %>
<%
 	
HttpSession currentSession = request.getSession();
SQLConnection SQLConn = (SQLConnection) currentSession.getAttribute("SQLConnection");


String status;
String username = (String) currentSession.getAttribute("username");


if(currentSession.getAttribute("status") != null) {
	status = (String) currentSession.getAttribute("status");
	currentSession.setAttribute("status", null);
} else {
	status = "";
}

int imageCount = SQLConn.getImageNum( (String) username);
int friendCount = SQLConn.getFriendNum( (String) username);
currentSession.setAttribute("iterator", 1);
currentSession.setAttribute("addStatus", "");

ArrayList<String> requestUsernames = SQLConn.getFriendRequests(username);
currentSession.setAttribute("requestsArray", requestUsernames);	

ArrayList<String> scores = SQLConn.getScores(username);

 %>
<br/>
	<%
	if (friendCount == 0) {
		out.print("You have no friends :(");
	}
	else{
	for(int i = 1; i<=friendCount; i++) {
		String friend = SQLConn.getFriend(username, i);
		String friendName = SQLConn.getName(friend);
	%>
	<a href="redirect?to=<%=friend%>" ><%=friendName%></a><br/>
	<% } }%>
	<br/>