package project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;



public class AdminWorker extends SwingWorker<Void,Void> {
	private final String action;
	private final AdminPage form;
	
	
	
	public AdminWorker(String act, AdminPage page) {
		action = act;
		form = page;
	}
	
	protected Void doInBackground() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            PreparedStatement stmt;
            
            switch (action) {
            case"SUBMIT":
            	
            	String firstName = form.createUserPanel.firstNameText.getText();
            	
            	String lastName = form.createUserPanel.lastNameText.getText();
            	
            	String username = form.createUserPanel.userText.getText();
            	
            	String password = new String(form.createUserPanel.passwordText.getPassword());
            	
            	String role = (String) form.createUserPanel.comboBox.getSelectedItem();
            	
            	User user = UserFactory.createUser(firstName, lastName, username, password, role);
            	stmt = conn.prepareStatement(
                        "INSERT INTO Users (first_name, last_name, username, password, role) VALUES (?, ?, ?, ?, ?, ?)"
                    );
            	stmt.setString(1, user.getFirstName());
            	stmt.setString(2, user.getLastName());
            	stmt.setString(3, user.getUsername());
            	stmt.setString(4, user.getPassword());
            	stmt.setString(5, user.getRole());
            	
            	JOptionPane.showMessageDialog(null, user.info());
                    
            }
            
            
        }
	   catch (Exception e) {
         e.printStackTrace();
         JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
     }
     return null;
}
	
	protected void done() {
		if (action.equals("UPDATE")|| action.equals("DELETE") || action.equals("INSERT") || action.equals("CHECKOUT")) {
	       
	    }
    	
    }
}
