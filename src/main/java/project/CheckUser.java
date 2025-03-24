package project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class CheckUser {

	
	public static String checkUser(String username, String password) {
		String role = "none";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
	             PreparedStatement ps = conn.prepareStatement("SELECT role FROM Users WHERE username = ? AND password = ?")) {

	            ps.setString(1, username);
	            ps.setString(2, password); 

	            ResultSet rs = ps.executeQuery();
	            if (rs.next()) {
	                role = rs.getString("role"); 
	            } else {
	            	JOptionPane.showMessageDialog(null, "User Not Found");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
	        }

	        return role;

		
	}
}
