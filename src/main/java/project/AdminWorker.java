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
	DefaultTableModel model;
	ResultSet rs;
	
	
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
                        "INSERT INTO Users (first_name, last_name, username, password, role) VALUES (?, ?, ?, ?, ?)"
                    );
            	stmt.setString(1, user.getFirstName());
            	stmt.setString(2, user.getLastName());
            	stmt.setString(3, user.getUsername());
            	stmt.setString(4, user.getPassword());
            	stmt.setString(5, user.getRole());
            	
            	stmt.executeUpdate();
            	
            	JOptionPane.showMessageDialog(null, user.info());
            	break;
            	
            case"RESO":
            	
            	stmt= conn.prepareStatement("SELECT id, guest_id, room_id, check_in_date, check_out_date, payment_status, additional_service "
            			+ "FROM Reservations");
            	
            	rs = stmt.executeQuery();
                
            	String[] columnNames = {"ID", "Guest ID", "Rooms iD", "Check In", "Check Out","Payment Status", "Additional Service"};
            	model = new DefaultTableModel(columnNames, 0); 
                
                
                while (rs.next()) {
                	
                	model.addRow(new Object[]{
                    		rs.getInt("id"),
                    		rs.getInt("guest_id"),
                    		rs.getInt("room_id"),
                    		rs.getDate("check_in_date"),
                    		rs.getDate("check_out_date"),
                    		rs.getString("payment_status"),
                    		rs.getString("additional_service")
                    });
                }
                form.reportsPanel.table.setModel(model);
                ((DefaultTableModel) form.reportsPanel.table.getModel()).fireTableDataChanged(); 
                break;
                
            case"ROOMS":
            	stmt= conn.prepareStatement("SELECT id, room_number, room_type, price_per_night, status FROM Rooms  ");
            	rs = stmt.executeQuery();
                
            	String[] columnNames2 = {"ID", "Room Number", "Room Type", "Price Per Night", "Status"};
               model = new DefaultTableModel(columnNames2, 0); 
               
                
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getDouble("price_per_night"),
                        rs.getString("status") 
                    });
                }
                form.reportsPanel.table.setModel(model);
                ((DefaultTableModel) form.reportsPanel.table.getModel()).fireTableDataChanged(); 
                break;
                
            case"HOUSE":
            	stmt= conn.prepareStatement("SELECT id, room_id, status FROM Housekeeping  ");
            	rs = stmt.executeQuery();
                
            	String[] columnNames3 = {"ID", "Room ID", "Status"};
               model = new DefaultTableModel(columnNames3, 0); 
               
                
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getInt("room_id"),
                        rs.getString("status") 
                    });
                }
                form.reportsPanel.table.setModel(model);
                ((DefaultTableModel) form.reportsPanel.table.getModel()).fireTableDataChanged(); 
                break;
                
            case"INVOICE":
            	stmt= conn.prepareStatement("SELECT id, reservation_id, total_amount, payment_method, payment_status FROM Invoice  ");
            	rs = stmt.executeQuery();
                
            	String[] columnNames4 = {"ID", "Room ID","Total","Payment Method", "Status"};
               model = new DefaultTableModel(columnNames4, 0); 
               
                
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getInt("reservation_id"),
                        rs.getDouble("total_amount"),
                        rs.getString("payment_method"),
                        rs.getString("payment_status") 
                    });
                }
                form.reportsPanel.table.setModel(model);
                ((DefaultTableModel) form.reportsPanel.table.getModel()).fireTableDataChanged(); 
                break;
                
            case "ALLINV":
            	InventoryNotifier notification = InventoryNotifier.getInstance();
            	stmt = conn.prepareStatement("SELECT  item_name,category,quantity ,restock_threshold FROM Inventory");
            	rs = stmt.executeQuery();
            	String[] columnNames5 = {"Name","Category","Current Stock","Min Stock Needed"};
            	model = new DefaultTableModel(columnNames5, 0); 
                form.reportsPanel.table.setModel(model);
                
                while (rs.next()) {
                	String itemName = rs.getString("item_name");
                	String cat = rs.getString("category");
                	int quantity = rs.getInt("quantity");
                	int threshold = rs.getInt("restock_threshold");
                    model.addRow(new Object[]{
                    		itemName,
                    		cat,
                    		quantity,
                    		threshold,
                        
                    });
                    
                    if (quantity < threshold) {
                    	notification.notifyObservers(itemName, quantity,threshold);
                    }
                }
                ((DefaultTableModel) form.reportsPanel.table.getModel()).fireTableDataChanged(); 
                break;
                    
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
