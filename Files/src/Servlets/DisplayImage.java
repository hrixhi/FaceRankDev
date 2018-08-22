package Servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import SQL.SQLConnection;

public class DisplayImage extends HttpServlet { 
    public void doGet(HttpServletRequest request,HttpServletResponse response)  
             throws IOException  
    { 
    	
    	HttpSession currentSession = request.getSession();
    	SQLConnection SQLConn = (SQLConnection) currentSession.getAttribute("SQLConnection");
    	String username = (String) currentSession.getAttribute("username");
    	System.out.println("Hrishi");
    //	SQLConn.getImage(request, response, username);
    
    }  
}  