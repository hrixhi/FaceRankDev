package Servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import SQL.SQLConnection;


@WebServlet("/addFriend")
public class addFriend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		HttpSession currentSession = request.getSession();
		
		SQLConnection SQLConn = (SQLConnection) currentSession.getAttribute("SQLConnection");
		String username = (String) currentSession.getAttribute("username");
		String usernameTo = (String) request.getParameter("from");
		
		System.out.println("BBB: " + usernameTo);
		
		boolean output = SQLConn.addFriend(username, usernameTo);
		if(output) {
			currentSession.setAttribute("addStatus", "Added!");
			response.sendRedirect("homepage.jsp");
		} else {
			currentSession.setAttribute("addStatus", "Error!");
			response.sendRedirect("homepage.jsp");
		}
		
	}

	

}
