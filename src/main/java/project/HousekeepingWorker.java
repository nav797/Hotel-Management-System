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
            case "INSERT":
            	JOptionPane.showMessageDialog(null, "jfkhbalwfji ");
            	
            case "ROOMS":
            	JOptionPane.showMessageDialog(null, "jfkhbalwfji ");
            	
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
                
                int addedQuan = (Integer) form.spinner.getValue();
                
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
		if (action.equals("UPDATEINV")|| action.equals("DELETE") || action.equals("INSERT")) {
	        new HousekeepingWorker("ALLINV", form).execute();
	    }
    	
    }

}
