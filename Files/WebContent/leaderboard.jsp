<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="SQL.SQLConnection" import="Servlets.*" import="Other.*" import="java.util.PriorityQueue"%>
<%@ page import="WebSocket.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.lang.Math" %>
<%@ page import="java.util.Collection" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <link href="https://fonts.googleapis.com/css?family=Muli|Pacifico" rel="stylesheet"/>
<link href="styles/styles-leader.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Facerank Leaderboard</title>
</head>
<body>
<%


HttpSession currentSession = request.getSession();
SQLConnection SQLConn = (SQLConnection) currentSession.getAttribute("SQLConnection");
String username = (String) currentSession.getAttribute("username");

PriorityQueue<Pair> LBPQ = SQLConn.getLeaderboard();
ArrayList<Pair> leaderboardx = new ArrayList<Pair>();
ArrayList<Pair> leaderboard = new ArrayList<Pair>();

while(!LBPQ.isEmpty()) {
	leaderboardx.add(LBPQ.poll());
}

int num = leaderboardx.size();

for(int i =0; i<num; i++) {
	leaderboard.add(leaderboardx.get(num-1-i));
}

System.out.println("size: " + num);

if(num>10) {
	num = 10;
}
%>
<div class="header" id = "title">FaceRank</div>



<div class="leader-contain">
	<div class = "leaderboard">
	    <div class = "leaderboard-header">
	      Leaderboard
	    </div>
	<% for(int i = 0; i<num; i++) {
		Pair top = leaderboard.get(i);
		%>
		<div class= "leaderboard-entry">
	      <span class= "entry-place"><%=i+1%></span>
	      <span class="entry-data">
	        <div class= "entry-name"><%= top.getKey()%></div>
	        <div class="entry-score">has image with score <%= top.getValue()%></div>
	      </span>
	    </div>
	<% } %>
	</div>
</div>
<% if(username!=null && !username.equals("guest")) { %>
<div class="gohome">
<form name="myform" method="POST" action="${pageContext.request.contextPath}/redirecthome">
<input type="submit" value="Go Home" />
</form>
</div>
<br/>
<% } else { %>
<div class="gohome">
<form name="myform" method="POST" action="${pageContext.request.contextPath}/viewall.jsp">
<input type="submit" value="Go to view all users" />
</form>
</div>

<% } %>

</body>
</html>