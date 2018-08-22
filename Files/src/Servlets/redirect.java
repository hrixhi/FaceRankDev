package Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import SQL.SQLConnection;

/**
 * Servlet implementation class redirect
 */
@WebServlet("/redirect")
public class redirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public redirect() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String usernameTo = request.getParameter("to");
		HttpSession currentSession = request.getSession();
		SQLConnection SQLConn = (SQLConnection) currentSession.getAttribute("SQLConnection");
	    	String username = (String) currentSession.getAttribute("username");
		 
		 if(username.equals(usernameTo)) {
			 currentSession.setAttribute("usernameTo", username);
			 RequestDispatcher dispatcher = request.getRequestDispatcher("/homepage.jsp");  
				if (dispatcher != null) {  
				   dispatcher.forward(request, response);  
				}
				
			 
		 } else {
		 
		 System.out.println("LEts: " + usernameTo);
		 currentSession.setAttribute("usernameTo", usernameTo);
		
		 RequestDispatcher dispatcher1 = request.getRequestDispatcher("/homepage1.jsp");  
			if (dispatcher1 != null) {  
			   dispatcher1.forward(request, response);  
			}
			
		 }
	}

}
