package project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class HousekeepingWorker extends SwingWorker<Void,Void>{
	
	private final String action;
	private final HousekeepingPage form;
	String display = "Ini";
	
	
	
	public HousekeepingWorker(String act, HousekeepingPage page) {
		action = act;
		form = page;
	}
	
	protected Void doInBackground() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            PreparedStatement stmt;
            ResultSet rs;
            DefaultTableModel model;
            int rowIndex;
            String item;
            

            switch (action) {
            case "UPDATEROOM":
            	rowIndex = form.reservationTable.getSelectedRow();
                if (rowIndex == -1) {
                    JOptionPane.showMessageDialog(null, "No reservation selected.");
                    return null;
                }
                int taskId = (int) form.reservationTable.getValueAt(rowIndex, 0);
                
                int roomId = (int) form.reservationTable.getValueAt(rowIndex, 1);
                
                String newStatus = (String) form.comboBox.getSelectedItem();
                
                String name = (String) form.itemNameUpdate.getSelectedItem();
                
                int itemUsed = (int) form.spinner.getValue();
                
                
                stmt = conn.prepareStatement("UPDATE Housekeeping SET status = ? WHERE id = ?");
                stmt.setString(1, newStatus);
                stmt.setInt(2, taskId);
                stmt.executeUpdate();
                
                if(itemUsed > 0 ) {
                	stmt = conn.prepareStatement("SELECT quantity FROM Inventory WHERE item_name = ?");
                    stmt.setString(1, name);
                    rs = stmt.executeQuery();
                    
                    if (rs.next()) {
                    int currentQuan = rs.getInt("quantity");
                    
                    
                    int newQuan = currentQuan - itemUsed;
                    
                    stmt = conn.prepareStatement("UPDATE Inventory SET quantity = ? WHERE item_name = ?");
                    stmt.setInt(1, newQuan);
                    stmt.setString(2, name);
                    stmt.executeUpdate();
                    }
                    display = "Inventory";
                }else {
                	display = "Rooms";
                }
                
                if(newStatus.equals("Completed")) {
                	stmt = conn.prepareStatement("UPDATE Rooms SET status = 'Available' WHERE id = ?");
                    stmt.setInt(1, roomId);
                    stmt.executeUpdate();
                	
                }
                break;
            	
            case "ROOMS":
            	stmt= conn.prepareStatement("SELECT h.id, h.room_id, r.room_number, h.status FROM Housekeeping h "
            			+ "JOIN Rooms r ON h.room_id = r.id "
            			+ "WHERE h.status IN ('Pending', 'In Progress')");
            	rs = stmt.executeQuery();
                
            	String[] columnNames1 = {"Taks ID", "Room ID", "Room Number", "Status"};
               model = new DefaultTableModel(columnNames1, 0); 
               
                
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getInt("room_id"),
                        rs.getString("room_number"),
                        rs.getString("status") 
                    });
                }
                form.reservationTable.setModel(model);
                ((DefaultTableModel) form.reservationTable.getModel()).fireTableDataChanged(); 
                break;
            	
            case "UPDATEINV":
            	rowIndex = form.reservationTable.getSelectedRow();
                if (rowIndex == -1) {
                    JOptionPane.showMessageDialog(null, "No reservation selected.");
                    return null;
                }
                item = (String) form.reservationTable.getValueAt(rowIndex, 0);
                
                stmt = conn.prepareStatement("SELECT quantity FROM Inventory WHERE item_name = ?");
                stmt.setString(1, item);
                rs = stmt.executeQuery();
                
                if (rs.next()) {
                int currentQuan = rs.getInt("quantity");
                
                int addedQuan = (int) form.spinner.getValue();
                
                int newQuan = currentQuan + addedQuan;
                
                stmt = conn.prepareStatement("UPDATE Inventory SET quantity = ? WHERE item_name = ?");
                stmt.setInt(1, newQuan);
                stmt.setString(2, item);
                stmt.executeUpdate();
                }
                break;
                
                
                
            	
            case "ALLINV":
            	InventoryNotifier notification = form.getNotification();
            	stmt = conn.prepareStatement("SELECT  item_name,category,quantity ,restock_threshold FROM Inventory");
            	rs = stmt.executeQuery();
            	String[] columnNames = {"Name","Category","Current Stock","Min Stock Needed"};
            	model = new DefaultTableModel(columnNames, 0); 
                form.reservationTable.setModel(model);
                
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
                ((DefaultTableModel) form.reservationTable.getModel()).fireTableDataChanged(); 
                break;
            	
            	
                
            }
            
        }  catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return null;
        
	}
	
	protected void done() {
		if (action.equals("UPDATEINV")|| display.equals("Inventory") || action.equals("INSERT")) {
	        new HousekeepingWorker("ALLINV", form).execute();
	    }
		if (display.equals("Inventory")) {
	        new HousekeepingWorker("ALLINV", form).execute();
	       // uptButton.setVisible(false);
			//uptInvButton.setVisible(true);
			//lblNewLabel.setVisible(true);
	    }
		if ( display.equals("Rooms")) {
	        new HousekeepingWorker("ROOMS", form).execute();
	    }
    	
    }

}
