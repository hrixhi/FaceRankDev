package Servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    		HttpSession currentSession = request.getSession();
    		int id = (Integer) currentSession.getAttribute("iterator");
    		String username1 = (String) currentSession.getAttribute("username");
    		String username2 = (String) currentSession.getAttribute("usernameTo");

    		String username;
    		if(username1.equals("username2")) {
    			username = (String) currentSession.getAttribute("username");
    		} else {
    			username = (String) currentSession.getAttribute("usernameTo");
    		}
    		
        String imageID = request.getPathInfo().substring(1); // Returns "foo.png".
        System.out.println("imageName: " + imageID);
        String SQL_FIND = "SELECT * FROM ImageURL WHERE username=? AND userImageNum=?";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/SelfieUserDatabase?user=root&password=woofy&useSSL=false");
        		PreparedStatement statement = conn.prepareStatement(SQL_FIND)) {
      
        	String[] split = imageID.split("\\.");
          	System.out.println("id: " + split[0]);
        	statement.setInt(2, Integer.parseInt(split[0]));
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    byte[] content = resultSet.getBytes("bImage");
                    response.setContentType(getServletContext().getMimeType(imageID));
                    response.setContentLength(content.length);
                    response.getOutputStream().write(content);
                    id++;
                    currentSession.setAttribute("iterator", 1);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Something failed at SQL/DB level.", e);
        }
    }

}