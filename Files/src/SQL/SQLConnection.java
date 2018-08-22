package SQL;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.PriorityQueue;

import AI.LabelImage;
import Other.Pair;

public class SQLConnection {
	private static Connection conn;
	private static PreparedStatement ps = null;
	private static ResultSet rs;

	public static void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/SelfieUserDatabase?user=root&password=woofy&useSSL=false");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void close(){
		try{
			if (rs != null){
				rs.close();
				rs = null;
			}
			if(conn != null){
				conn.close();
				conn = null;
			}
			if(ps != null ){
				ps = null;
			}
		}catch(SQLException sqle){
			System.out.println("connection close error");
			sqle.printStackTrace();
		}
	}
	
	public static boolean validate(String username, String password) {
		connect();
		try {
			ps = conn.prepareStatement("SELECT * FROM User WHERE username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if(rs.next()) {
				if(password.equals(rs.getString("password"))) {
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException in function \"validate\"");
			e.printStackTrace();
		} finally{
			close();
		}
		return false;
	}
	
	public PriorityQueue<Pair> getLeaderboard() {
		connect();
		System.out.println("1");
		PriorityQueue<Pair> toReturn = new PriorityQueue<Pair>();
		System.out.println("2");
		
		try {
			System.out.println("3");
			ps = conn.prepareStatement("SELECT * FROM ImageURL");
			rs = ps.executeQuery();
			System.out.println("4");
			while(true) {
				System.out.println("5");
				if(rs.next()) {
					System.out.println("6");
					String x = rs.getString("score");
					System.out.println("IN THE SS: "+ x);
					x = x.substring(0, 3);
					System.out.println("IN THE SS: "+ x);
					
					toReturn.add(new Pair(rs.getString("username"), Double.parseDouble(x)));
				} else {
					break;
				}
			}
		} catch(Exception e) {
			
		}
		
		return toReturn;
		
	}
	
	public String insertImageAndGetScore(byte[] bytes, String username) {
		connect();
		
		InputStream inputStream1 = new ByteArrayInputStream(bytes); 
		InputStream inputStream2 = new ByteArrayInputStream(bytes); 
		
		System.out.println("hrishi");
		String toReturn = null;
		LabelImage testingLabel = new LabelImage();
		System.out.println("hrishi");
		
		try {
			toReturn = testingLabel.readInImage(inputStream1);
			String[] splitTerm = toReturn.split("\\|");
			toReturn = splitTerm[0] + "\n";
		
			for(int i = 1; i<splitTerm.length; i++) {
				toReturn += splitTerm[i] + " ";
			}
			
			
		} catch (Exception e) {
			System.out.println("Error! LabelImage has failed.");
		}
		
	   	try {
	   		
	   	  ps = conn.prepareStatement("SELECT * FROM User WHERE username=?");
	        ps.setString(1, username);
	        rs = ps.executeQuery();
	        int num = 0;
	        if(rs.next()) {
	        	
	        		num = rs.getInt("numberOfImages");
	        		 num++;
	        		 System.out.println(num);
	        }
	       
	        
	   		ps = conn.prepareStatement("INSERT INTO ImageURL(username, bImage, userImageNum, score) values(?, ?, ?, ?)");
	   		ps.setBlob(2, inputStream2);
	        ps.setString(1, username);
	        ps.setInt(3, num);
	        ps.setString(4, toReturn);
	        ps.executeUpdate();
	        
	        ps = conn.prepareStatement("UPDATE User " +
	                   "SET numberOfImages = ? WHERE username = ?");
	        ps.setInt(1, num);
	        ps.setString(2, username);
	        ps.executeUpdate();
	        
	     
	   	}
	   	catch(Exception e)
	   	{
	   		e.printStackTrace();
	   		return "false";
	   		
	   	}  
	   	
	   
		close();
		return toReturn;
	   	
	}
	
	public int getImageNum(String username) {
		connect();
		try {
			ps = conn.prepareStatement("SELECT * FROM User WHERE username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt("numberOfImages");
			}
		} catch (SQLException e) {
			System.out.println("SQLException in function \"validate\"");
			e.printStackTrace();
		} finally{
			close();
		}
		return 0;
	}
	
	public int getFriendNum(String username) {
		connect();
		try {
			ps = conn.prepareStatement("SELECT * FROM User WHERE username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt("numberOfFriends");
			}
		} catch (SQLException e) {
			System.out.println("SQLException in function \"validate\"");
			e.printStackTrace();
		} finally{
			close();
		}
		return 0;
	}
	
	public ArrayList<String> getMatches(String username) {
		ArrayList<String> matches = new ArrayList<String>();
		
		connect();
		try {
			
			// processing here 
			String[] splitedName = username.split("\\s+");
			
			if(splitedName.length!=1) {
				ps = conn.prepareStatement("SELECT * FROM User WHERE fname=?");
				ps.setString(1, splitedName[0]);
				rs = ps.executeQuery();
				while(rs.next()) {
					String fname = rs.getString("fname");
					String lname = rs.getString("lname");
					String user = rs.getString("username");
					if(fname.equals(splitedName[0]) || lname.equals(splitedName[1])) {
						if(!matches.contains(fname + " " + lname)) {
							matches.add(user);
							matches.add(fname + " " + lname);
						}
					}
				}
			} else {
				ps = conn.prepareStatement("SELECT * FROM User WHERE fname=?");
				ps.setString(1, username);
				rs = ps.executeQuery();
				while(rs.next()) {
					String fname = rs.getString("fname");
					String lname = rs.getString("lname");
					String user = rs.getString("username");
					if(fname.equals(username) || lname.equals(username)) {
						if(!matches.contains(fname + " " + lname)) {
							matches.add(user);
							matches.add(fname + " " + lname);
						}
					}
				}
				
				ps = conn.prepareStatement("SELECT * FROM User WHERE lname=?");
				ps.setString(1, username);
				rs = ps.executeQuery();
				while(rs.next()) {
					String fname = rs.getString("fname");
					String lname = rs.getString("lname");
					String user = rs.getString("username");
					if(fname.equals(username) || lname.equals(username)) {
						if(!matches.contains(fname + " " + lname)) {
							matches.add(user);
							matches.add(fname + " " + lname);
						}
					}
				}
			}
			
		} catch (SQLException e) {
			System.out.println("SQLException in function \"validate\"");
			e.printStackTrace();
		} finally{
			close();
		}
		
		
		return matches;
	}
	
	public String getFriend(String username, int iterator) {
		connect();
		try {
			ps = conn.prepareStatement("SELECT * FROM FriendList WHERE username1=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			for(int i = 1; i<iterator; i++) {
				rs.next();
			}
			
			if(rs.next() ) {
				return rs.getString("username2");
			}
			
		} catch (SQLException e) {
			System.out.println("SQLException in function \"validate\"");
			e.printStackTrace();
		} finally{
			close();
		}
		
		return "";
		
	}
	
	public String getName(String username) {

		connect();
		String toReturn = "";
		try {
			ps = conn.prepareStatement("SELECT * FROM User WHERE username=?");
			ps.setString(1, username);
			rs = ps.executeQuery();
			
			if(rs.next() ) {
				toReturn = rs.getString("fname") + " " + rs.getString("lname");
				return toReturn;
			}

		} catch (SQLException e) {
			System.out.println("SQLException in function \"validate\"");
			e.printStackTrace();
		} finally{
			close();
		}

		return "";

	}
	
	public boolean isFriend(String username1, String username2) {
		connect();
		try {
			ps = conn.prepareStatement("SELECT * FROM FriendList WHERE username1=? AND username2=?");
			ps.setString(1, username1);
			ps.setString(2, username2);
			rs = ps.executeQuery();
			
			if(rs.next() ) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			System.out.println("SQLException in function \"validate\"");
			e.printStackTrace();
		} finally{
			close();
		}

		return false;
	}
	
	public boolean friendRequestSent(String username1, String username2) {
		connect();
		try {
			ps = conn.prepareStatement("SELECT * FROM FriendRequests WHERE username1=? AND username2=?");
			ps.setString(1, username1);
			ps.setString(2, username2);
			rs = ps.executeQuery();
			
			if(rs.next() ) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			System.out.println("SQLException in function \"validate\"");
			e.printStackTrace();
		} finally{
			close();
		}

		return false;
	}
	
	public boolean addFriend(String username1, String username2) {
		connect();
		try {
	   			//  String sql = "DELETE FROM warehouses WHERE id = ?";
				ps = conn.prepareStatement("DELETE FROM FriendRequests WHERE username1=? AND username2=?");
				ps.setString(2, username1);
				ps.setString(1, username2);
				ps.executeUpdate();
			
		   	  	ps = conn.prepareStatement("SELECT * FROM User WHERE username=?");
		        ps.setString(1, username1);
		        rs = ps.executeQuery();
		        int num1 = 0;
		        if(rs.next()) {
		        	
		        		num1 = rs.getInt("numberOfFriends");
		        		 num1++;
		        		 System.out.println(num1);
		        }
		        
		        ps = conn.prepareStatement("SELECT * FROM User WHERE username=?");
		        ps.setString(1, username2);
		        rs = ps.executeQuery();
		        int num2 = 0;
		        if(rs.next()) {
		        	
		        		num2 = rs.getInt("numberOfFriends");
		        		 num2++;
		        		 System.out.println(num2);
		        }
		       
		        
		   		ps = conn.prepareStatement("INSERT INTO FriendList(username1, username2) values(?, ?)");
		        ps.setString(1, username1);
		        ps.setString(2, username2);
		        ps.executeUpdate();
		        
		        //System.out.println("YO YO YO : " + username1);
		        //System.out.println("YO YO YO : " + username2);
		        
		        
		        ps = conn.prepareStatement("INSERT INTO FriendList(username1, username2) values(?, ?)");
		        ps.setString(2, username1);
		        ps.setString(1, username2);
		        ps.executeUpdate();
		        
		        ps = conn.prepareStatement("UPDATE User " +
		                   "SET numberOfFriends = ? WHERE username = ?");
		        ps.setInt(1, num1);
		        ps.setString(2, username1);
		        ps.executeUpdate();
		        
		        ps = conn.prepareStatement("UPDATE User " +
		                   "SET numberOfFriends = ? WHERE username = ?");
		        ps.setInt(1, num2);
		        ps.setString(2, username2);
		        ps.executeUpdate();
		        
		        return true;
		   	}
		   	catch(Exception e)
		   	{
		   		e.printStackTrace();
		   		return false;
		   		
		   	}  finally{
				close();
			}
	}
	
	public String add(String user, String pass, String fname, String lname) {
		connect();
	
		try {
			
			
			ps = conn.prepareStatement("SELECT * FROM User WHERE username=?");
	        ps.setString(1, user);
	        rs = ps.executeQuery();
	        
	        if(rs.next()) {
	        		return "Username exists";
	        }
	        
		
			
			ps = conn.prepareStatement("INSERT INTO User(username, password, fname, lname, numberOfImages, numberOfFriends) values(?, ?, ?, ?, ?, ?)");
	        ps.setString(1, user);
	        ps.setString(2, pass);
	        ps.setString(3, fname);
	        ps.setString(4, lname);
	        ps.setInt(5, 0);
	        ps.setInt(6, 0);
	        ps.executeUpdate();
	        
	        return "pass";
		   	  
		   	}
		   	catch(Exception e)
		   	{
		   		e.printStackTrace();
		   
		   		
		   	}  finally{
				close();
			}
			
			return "pass";
		
	}
	
	public boolean sendFriendRequest(String username1, String username2) {
		connect();
		try {
			ps = conn.prepareStatement("INSERT INTO FriendRequests(username1, username2) values(?, ?)");
	        ps.setString(1, username1);
	        ps.setString(2, username2);
	        ps.executeUpdate();
		   	  	return true;
		   	}
		   	catch(Exception e)
		   	{
		   		e.printStackTrace();
		   		return false;
		   		
		   	}  finally{
				close();
			}
	}
	
	public ArrayList<String> getFriendRequests(String username) {
		ArrayList<String> toReturn = new ArrayList<String>();
		connect();
		try {
			ps = conn.prepareStatement("SELECT * FROM FriendRequests WHERE username2=?");
	        ps.setString(1, username);
	        rs = ps.executeQuery();
	    
	        while(true ) {
	        		if(rs.next()) {
	        			toReturn.add(rs.getString("username1"));
	        		} else {
	        			break;
	        		}
	        }
	        
	
	        
		} catch(Exception e)
		   	{
		   		e.printStackTrace();
		   		
		   	}  finally {
				close();
			}
        return toReturn;
		
	}
	
	public ArrayList<String> getAllUsers() {
		ArrayList<String> toReturn = new ArrayList<String>();
		connect();
		try {
			ps = conn.prepareStatement("SELECT * FROM User");
	        rs = ps.executeQuery();
	    
	        while(true ) {
	        		if(rs.next()) {
	        			toReturn.add(rs.getString("username"));
	        		} else {
	        			break;
	        		}
	        }
	        
	
	        
		} catch(Exception e)
		   	{
		   		e.printStackTrace();
		   		
		   	}  finally {
				close();
			}
        return toReturn;
		
	}
	
	public ArrayList<String> getScores(String username) {
		ArrayList<String> toReturn = new ArrayList<String>();
		connect();
		try {
			ps = conn.prepareStatement("SELECT * FROM ImageURL WHERE username=?");
			ps.setString(1, username);
	        rs = ps.executeQuery();
	    
	        while(true) {
	        		if(rs.next()) {
	        			toReturn.add(rs.getString("score"));
	        		} else {
	        			break;
	        		}
	        }
	   
	        
		} catch(Exception e)
		   	{
		   		e.printStackTrace();
		   		
		   	}  finally {
				close();
			}
        return toReturn;
		
	}
}
