<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="SQL.SQLConnection" import="Servlets.*"%>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <link href="https://fonts.googleapis.com/css?family=Muli|Pacifico" rel="stylesheet"/>
<link href="styles/styles-homepage.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RateMySelfie</title>
<script>

document.addEventListener("DOMContentLoaded", function() {
	document.getElementById("addButton").addEventListener("click", function() {

		var xhttp = new XMLHttpRequest();
		xhttp.open("GET", "${pageContext.request.contextPath}/sendrequest.jsp", false);
		xhttp.send();
			
		var responseText = xhttp.responseText.trim();
		console.log(responseText);
		
		if(responseText == "true") {
			document.getElementById("buttonHere").innerHTML = "";
			document.getElementById("addFriendStatus").innerHTML = "Friend Request Sent! <br/>";
		} else {
			document.getElementById("addFriendStatus").innerHTML = "request failed!<br/>";
		}

	});
});

</script>
</head>
<body>
<div class="header" id = "title">FaceRank</div>
<br/>
<%
	HttpSession currentSession = request.getSession();
	SQLConnection SQLConn = (SQLConnection) currentSession.getAttribute("SQLConnection");
	
	
	String status;
	String username = (String) currentSession.getAttribute("username");
	String usernameTo = (String) currentSession.getAttribute("usernameTo");
	
	if(currentSession.getAttribute("status") != null) {
		status = (String) currentSession.getAttribute("status");
		currentSession.setAttribute("status", null);
	} else {
		status = "";
	}
	
	if(username.equals(usernameTo)) {
		 RequestDispatcher dispatcher = request.getRequestDispatcher("/homepage.jsp");  
			if (dispatcher != null) {  
			   dispatcher.forward(request, response);  
			}
	}
	
	int imageCount = SQLConn.getImageNum( (String) usernameTo);
	int friendCount = SQLConn.getFriendNum( (String) usernameTo);
	currentSession.setAttribute("iterator", 1);
	
	boolean isFriend = SQLConn.isFriend(username, usernameTo);
	boolean friendRequestSent = SQLConn.friendRequestSent(username, usernameTo);
	ArrayList<String> scores = SQLConn.getScores(usernameTo);
%>
<div id="currUser"><%=username%>, Welcome to <%=usernameTo%>'s page!</div>
<br/><hr/>
<div class="gohome">
<% if(!username.equals("guest")){ if(!isFriend) { 
	if(!friendRequestSent) {%>
<div id = "buttonHere"><button id="addButton">Send Friend Request</button><br/>
</div>
<% } else { %>
<div id = "buttonHere">Friend Request Sent</button></div><br/>
<%}}}%>
<div id="addFriendStatus"></div>
</div>
<br/>

<div class="span-container">
<span class="image-span visiting">
<div class="subtitle">Images</div><hr/>
<% 
if (imageCount==0){
	out.print("No images to show");
}

for(int i = 1; i<=imageCount; i++) { 
	///currentSession.setAttribute("iterator", i);
%>
		<div class="image-col-entry">
		<img width= "256" height="256" src="${pageContext.request.contextPath}/images/<%=i%>.png">
		<div class="image-col-entry-text">Score: <%=scores.get(i-1)%></div>
		</div>
		
<%}%>
</span>
<span class="friends-span visiting">
<div id="list"></div>
<div class="subtitle">Friends</div><hr/>


<% for(int i = 1; i<=friendCount; i++) {
	String friend = SQLConn.getFriend(usernameTo, i);
	String friendName = SQLConn.getName(friend);
%>
<a href="redirect?to=<%=friend%>"><%=friendName%></a><br/>
<% } %>
</span>
</div>
<br/>
<% if(!username.equals("guest")) { %>
<div class="gohome">
<form name="myform" method="POST" action="${pageContext.request.contextPath}/redirecthome">
<input type="submit" value="Go Back" />
</form>
</div>
<br/>
<% } else { %>
<div class="gohome">
<form name="myform" method="POST" action="${pageContext.request.contextPath}/viewall.jsp">
<input type="submit" value="Go back to login" />
</form>
</div>

<% } %>
</body>
</html>