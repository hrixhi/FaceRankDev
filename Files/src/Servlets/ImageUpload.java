package Servlets;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import SQL.SQLConnection;

/**
 * Servlet implementation class ImageUpload
 */

@WebServlet("/uploadServlet")
@MultipartConfig(maxFileSize = 16177215)
public class ImageUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public ImageUpload() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 InputStream inputStream = null; // input stream of the upload file
         byte[] bytes = null;
	        // obtains the upload file part in this multipart request
	        Part filePart = request.getPart("photo");
	        if (filePart != null) {
	            // prints out some information for debugging
	            System.out.println(filePart.getName());
	            System.out.println(filePart.getSize());
	            System.out.println(filePart.getContentType());
	             
	            // obtains input stream of the upload file
	            inputStream = filePart.getInputStream();
	            bytes = IOUtils.toByteArray(inputStream);
	        }
	         
	    HttpSession currentSession = request.getSession();
	    	SQLConnection SQLConn = (SQLConnection) currentSession.getAttribute("SQLConnection");
	    	String username = (String) currentSession.getAttribute("username");
	    	int imageCount = (Integer) currentSession.getAttribute("imageCount");
	    	String output = (String)SQLConn.insertImageAndGetScore(bytes, username);
	    	
	    	if(!output.equals("false")) {
	    		currentSession.setAttribute("status", "Image uploaded!");
	    		imageCount++;
	    		currentSession.setAttribute("imageCount", imageCount);
	    		currentSession.setAttribute("score" + imageCount, output);
	    	} else {
	    		currentSession.setAttribute("status", "Upload failed!");
	    	}

	    	response.sendRedirect("homepage.jsp");
	}
	public static void main(String [] args) {
		
	}

}
