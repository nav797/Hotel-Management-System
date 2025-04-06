package project;

import java.sql.Connection;
import java.sql.Date;
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
            	int rowIndex3 = form.reservationTable.getSelectedRow();
                if (rowIndex3 == -1) {
                    JOptionPane.showMessageDialog(null, "No room selected.");
                    return null;
                }

                int roomId3 = (int) form.reservationTable.getValueAt(rowIndex3, 0);
                
                int guestId = CheckUser.userId;

                java.sql.Date checkInDate1 = form.checkInSpinner.getValue() == null ? null 
                    : new java.sql.Date(((java.util.Date) form.checkInSpinner.getValue()).getTime());

                java.sql.Date checkOutDate1 = form.checkOutSpinner.getValue() == null ? null 
                    : new java.sql.Date(((java.util.Date) form.checkOutSpinner.getValue()).getTime());

                String service1 = form.comboBox.getSelectedItem().toString().equals("None") 
                    ? null : form.comboBox.getSelectedItem().toString();

                PreparedStatement insertStmt = conn.prepareStatement(
                    "INSERT INTO Reservations (guest_id, room_id, check_in_date, check_out_date, payment_status, additional_service) VALUES (?, ?, ?, ?, 'Pending', ?)"
                );

                insertStmt.setInt(1, guestId);
                insertStmt.setInt(2, roomId3);
                insertStmt.setDate(3, checkInDate1);
                insertStmt.setDate(4, checkOutDate1);
                insertStmt.setString(5, service1);
                insertStmt.executeUpdate();

                PreparedStatement updateStmt = conn.prepareStatement(
                    "UPDATE Rooms SET status = 'Occupied' WHERE id = ?"
                );
                updateStmt.setInt(1, roomId3);
                updateStmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Reservation added successfully.");

                break;

            	
            case "UPDATE":
            	int rowIndex = form.reservationTable.getSelectedRow();
                if (rowIndex == -1) {
                    JOptionPane.showMessageDialog(null, "No reservation selected.");
                    return null;
                }
                int reservationId = (int) form.reservationTable.getValueAt(rowIndex, 0);

                

                Date checkInDate = (Date) (form.reservationTable.getValueAt(rowIndex, 4));
                if (form.checkInSpinner.getValue() != null) {
                    checkInDate = new java.sql.Date(((java.util.Date) form.checkInSpinner.getValue()).getTime());
                }

                Date checkOutDate = (Date) (form.reservationTable.getValueAt(rowIndex, 5));
                if (form.checkOutSpinner.getValue() != null) {
                    checkOutDate = new java.sql.Date(((java.util.Date) form.checkOutSpinner.getValue()).getTime());
                }

                String service = form.comboBox.getSelectedItem().toString().equals("None")
                        ? form.reservationTable.getValueAt(rowIndex, 7).toString()
                        : form.comboBox.getSelectedItem().toString();

             
                stmt = conn.prepareStatement(
                        "UPDATE Reservations SET check_in_date = ?, check_out_date = ?, additional_service = ? WHERE id = ?");

                stmt.setDate(1, new java.sql.Date(checkInDate.getTime()));
                stmt.setDate(2, new java.sql.Date(checkOutDate.getTime()));
                stmt.setString(3, service);
                stmt.setInt(4, reservationId);

                stmt.executeUpdate();

            
                break;
            case "DELETE":
            	int rowIndex1 = form.reservationTable.getSelectedRow();
                if (rowIndex1 == -1) {
                    JOptionPane.showMessageDialog(null, "No reservation selected.");
                    return null;
                }

             
                int reservationId1 = (int) form.reservationTable.getValueAt(rowIndex1, 0);
                
                int roomId;  
                stmt = conn.prepareStatement("SELECT room_id FROM Reservations WHERE id = ?");
                stmt.setInt(1, reservationId1);
                ResultSet rs1 = stmt.executeQuery();
                if (rs1.next()) {  
                    roomId = rs1.getInt("room_id"); 
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Could not find the room for the selected reservation.");
                    return null;
                }

                
                
                int confirm = JOptionPane.showConfirmDialog(null, 
                        "Are you sure you want to delete this reservation?", 
                        "Confirm", 
                        JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    
                    stmt = conn.prepareStatement("DELETE FROM Reservations WHERE id = ?");
                    stmt.setInt(1, reservationId1);
                    
                    stmt = conn.prepareStatement("UPDATE Rooms SET status = 'Available' WHERE id = ?");
                    stmt.setInt(1, roomId);
                    stmt.executeUpdate();
                        
                    JOptionPane.showMessageDialog(null, "Reservation deleted successfully.");

                }
                break;



                
            case "ALL":
            	stmt= conn.prepareStatement("SELECT id, room_number, room_type, price_per_night, status FROM Rooms WHERE status = 'Available'");
            	ResultSet rs = stmt.executeQuery();
                
            	String[] columnNames = {"Room ID", "Room Number", "Room Type", "Price", "Status"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0); 
                
                
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getDouble("price_per_night"),
                        rs.getString("status") 
                    });
                }
                form.reservationTable.setModel(model);
                ((DefaultTableModel) form.reservationTable.getModel()).fireTableDataChanged(); 
                break;
             
            case "RESO":
            	int guest_id = CheckUser.userId;
            	stmt= conn.prepareStatement("SELECT res.id, r.room_number, r.room_type, r.price_per_night, res.check_in_date, res.check_out_date, res.payment_status, res.additional_service "
            			+ "FROM Reservations res "
            			+ "JOIN Rooms r ON res.room_id=r.id"
            			+ " WHERE res.guest_id = ?");
            	stmt.setInt(1, guest_id);
            	ResultSet rs2 = stmt.executeQuery();
                
            	String[] columnNames2 = {"Reservation ID", "Room Number", "Room Type", "Price", "Check-in Date","Check-out Date", "Payment Status", "Additional Service"};
                DefaultTableModel model2 = new DefaultTableModel(columnNames2, 0); 
                
                
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
                form.reservationTable.setModel(model2);
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
		if (action.equals("UPDATE")|| action.equals("DELETE") || action.equals("INSERT")) {
	        new GuestWorker("RESO", form).execute();
	    }
    	
    }
	

}
