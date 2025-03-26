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
             
            case "RESO":
            	int guest_id = CheckUser.userId;
            	stmt= conn.prepareStatement("SELECT r.id, r.room_number, r.room_type, r.price_per_night, res.check_in_date, res.check_out_date, res.payment_status, res.additional_service "
            			+ "FROM Reservations res "
            			+ "JOIN Rooms r ON res.room_id=r.id"
            			+ " WHERE res.guest_id = ?");
            	stmt.setInt(1, guest_id);
            	ResultSet rs2 = stmt.executeQuery();
                
            	String[] columnNames2 = {"Reservation ID", "Room Number", "Room Type", "Price", "Check-in Date","Check-out Date", "Payment Status", "Additional Service"};
                DefaultTableModel model2 = new DefaultTableModel(columnNames2, 0); 
                form.reservationTable.setModel(model2);
                
                while (rs2.next()) {
                    model2.addRow(new Object[]{
                        rs2.getInt("id"),
                        rs2.getString("room_number"),
                        rs2.getString("room_type"),
                        rs2.getDouble("price_per_night"),
                        rs2.getDate("check_in_date"),
                        rs2.getDate("check_out_date"),
                        rs2.getString("payment_status"),
                        rs2.getString("additional_service")
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
    	
    }
	

}
