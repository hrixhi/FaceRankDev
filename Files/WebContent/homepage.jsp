<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="SQL.SQLConnection" import="Servlets.*"%>
<%@ page import="WebSocket.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.lang.Math" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <link href="https://fonts.googleapis.com/css?family=Muli|Pacifico" rel="stylesheet"/>
<link href="styles/styles-homepage.css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RateMySelfie</title>
<script>

	var socket;
	
function connectToServer() {
	socket = new WebSocket("ws://localhost:8080/RateMySelfie/ws");
	socket.onopen = function(event) {
		//document.getElementById("newsfeed").innerHTML += "Connected!";
	}
	socket.onmessage = function(event) {
		//location.reload();
		document.getElementById("newsfeed").innerHTML += event.data + "<br/>";
		
	}
	socket.onclose = function(event) {
		//document.getElementById("mychat").innerHTML += "Disconnected!";
	}
}

function sendMessage(name) {
	console.log(name + " Poked!");
	socket.send(name + " Poked!");
}


document.addEventListener("DOMContentLoaded", function() {
	document.getElementById("searchButton").addEventListener("click", function() {

		var xhttp = new XMLHttpRequest();
		
		if(document.myform.inputname.value!=null && document.myform.inputname.value!="") {
		
		xhttp.open("GET", "${pageContext.request.contextPath}/getFriends.jsp?inputname=" + document.myform.inputname.value, false);
		xhttp.send();
			
		var responseText = xhttp.responseText.trim();
		console.log(responseText);
		
		var textArray = responseText.split(",");
	
		console.log(textArray.length);
		document.getElementById("list").innerHTML = "";
		
		for(var i = 0; i<textArray.length-1; i=i+2) {
			document.getElementById("list").innerHTML += "<a href=\"redirect?to=" +textArray[i] + "\" >" + textArray[i+1] + "</a>"
		}
		document.getElementById("list").innerHTML += "<br/>";
		
		} else {
			document.myform.inputname.value = "";
		}
			
	});
	
	document.getElementById("reload").addEventListener("click", function() {

		var xhttp = new XMLHttpRequest();
		xhttp.open("GET", "${pageContext.request.contextPath}/reload.jsp", false);
		xhttp.send();
			
		var responseText = xhttp.responseText.trim(); 
		document.getElementById("requests").innerHTML = responseText;
		
	});
	
	document.getElementById("reloadPage").addEventListener("click", function() {

		var xhttp = new XMLHttpRequest();
		xhttp.open("GET", "${pageContext.request.contextPath}/reloadfriends.jsp", false);
		xhttp.send();
			
		var responseText = xhttp.responseText.trim(); 
		document.getElementById("friends").innerHTML = responseText;
		
	});
	
	document.getElementById("clearPokes").addEventListener("click", function() {

		document.getElementById("newsfeed").innerHTML = "";
		
	});
	
});
</script>
</head>
<body onload="connectToServer()">

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
<div class="header" id = "title">FaceRank</div>
<br/>
<div id="currUser">Welcome, <%=username%>!<br/><a href="leaderboard.jsp">View Leaderboard</a></div><br/>

<hr/>
<div class = "span-container">
<span class = "image-span">
	<div class="subtitle">Upload</div>
	<hr/>
	<div class = "upload-text">Upload a selfie!</div>
	<form class = "upload-form" name="frm" action="uploadServlet" enctype="multipart/form-data" method="post">
	 <input type="file" name="photo" /> <br>
	 <input type="submit" name="goUpload">
	</form>
	<p><%= status %></p>
	<div class = "image-col">
	<% for(int i = 1; i<=imageCount; i++) { 
		//currentSession.setAttribute("iterator", i);
	%>
		<div class="image-col-entry">
		<img width= "256" height="256" src="${pageContext.request.contextPath}/images/<%=i%>.png">
		<div class="image-col-entry-text">Score: <%=scores.get(i-1)%></div>
		</div>
		
	<%if (i!=imageCount)%>
		<hr/>
	<%}%>
	</div>
	<br/><br/>
</span>
<span class = "friends-span">
	<div class="subtitle">Friends</div>
	<hr/>
	Add Friends:
	<br/>
	<form name="myform" action="uploadServlet" enctype="multipart/form-data" method="post">
	 <input type="text" name="inputname" /> <br>
	</form>
	<button id="searchButton">Search</button><br/><br/>
	<div id="list"></div> 
	<hr/><br/>
	Your Friends:
	<div id = "friends"> 
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
	</div>
	<button id="reloadPage">Reload Friends</button><br/>
	Friend Requests<br/><br/>
	
	<div id="requests">
	<% for(int i = 0; i<requestUsernames.size(); i++) { 
		String User = requestUsernames.get(i);
		System.out.println("xxx: " + User);%>
	<%= SQLConn.getName(requestUsernames.get(i))%>
	<a href="addFriend?from=<%=User%>">Accept Request</a>
	<% } %>
	
	<% String message = (String) currentSession.getAttribute("addStatus");
	if(!message.equals("")) {
		currentSession.setAttribute("addStatus", "");
	%>
	<%=message%>
	<% }%></div>
	<button id="reload">Reload Requests</button>
	<br/><hr/><br/>
	
	<form class="logout-form" name="myformx" method="POST" action="${pageContext.request.contextPath}/goback">
		<input type="submit" value="Log out" />
	</form>
</span>
<span class ="newsfeed-span">
	<div class="subtitle">Pokes</div> <hr/>
	<button value="<%=username%>" onclick="sendMessage(this.value);">Poke</button>
	<button id = "clearPokes">Clear Pokes</button>
	<hr/>
	<div id="newsfeed"></div>
	---------
</span>
</div>

</body>
</html>