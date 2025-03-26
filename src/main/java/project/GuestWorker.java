package project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class GuestWorker extends SwingWorker<Void,Void> {
	private final String action;
	private final GuestPage form;
	
	
	
	public GuestWorker(String act, GuestPage page) {
		action = act;
		form = page;
	}
	
	protected Void doInBackground() {
        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            PreparedStatement stmt;

            switch (action) {
            case "INSERT":
            	JOptionPane.showMessageDialog(null,"Need to work on this one - Add Pay no or later featture");
            	
            case "UPDATE":
            	int rowIndex = form.reservationTable.getSelectedRow();
                if (rowIndex == -1) {
                    JOptionPane.showMessageDialog(null, "No reservation selected.");
                    return null;
                }
                
            case "ALL":
            	stmt= conn.prepareStatement("SELECT id, room_number, room_type, price_per_night, status FROM Rooms WHERE status = 'Available'");
            	ResultSet rs = stmt.executeQuery();
                
            	String[] columnNames = {"Room ID", "Room Number", "Room Type", "Price", "Status"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0); 
                form.reservationTable.setModel(model);
                
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getDouble("price_per_night"),
                        rs.getString("status") 
                    });
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
    	if (!action.equals("ALL")) { // Only refresh if not a search operation
    		new GuestWorker("ALL", form).execute(); // Refresh table after operation
    	}
    }
	

}
