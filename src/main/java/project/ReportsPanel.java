package project;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReportsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	JTable table;
	private AdminPage parent;

	/**
	 * Create the panel.
	 */
	public ReportsPanel(AdminPage p) {
		setLayout(null);
		parent = p;
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 90, 621, 300);
		add(scrollPane);
		
		JButton resButton = new JButton("Reservations");
		resButton.setBounds(21, 34, 117, 29);
		add(resButton);
		
		table = new JTable();
		table.setBounds(0, 0, 617, 0);
		add(table);
		
		JButton roomsButton = new JButton("Rooms");
		roomsButton.setBounds(150, 34, 117, 29);
		add(roomsButton);
		
		JButton houseButton = new JButton("Housekeeping");
		houseButton.setBounds(279, 34, 117, 29);
		add(houseButton);
		
		JButton invoiceButton = new JButton("Invoices");
		invoiceButton.setBounds(408, 34, 117, 29);
		add(invoiceButton);
		
		JButton btnNewButton = new JButton("Inventory");
		btnNewButton.setBounds(525, 34, 117, 29);
		add(btnNewButton);

	}

}
