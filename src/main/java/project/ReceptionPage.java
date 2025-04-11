package project;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;

public class ReceptionPage extends JFrame {

	private static final long serialVersionUID = 1L;
	JPanel contentPane;
	public JTable reservationTable;
	public JSpinner checkInSpinner;
	public JSpinner checkOutSpinner;
	public JComboBox<String> comboBox;
	public JComboBox<String> guestList;
	public Map<String, Integer> guestMap = new HashMap<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReceptionPage frame = new ReceptionPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ReceptionPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel userLabel = new JLabel("Guest:");
		userLabel.setBounds(21, 28, 100, 16);
		contentPane.add(userLabel);
		
		JLabel checkInLabel = new JLabel("Check In:");
		checkInLabel.setBounds(413, 28, 80, 16);
		contentPane.add(checkInLabel);
		
		checkInSpinner = new JSpinner(new SpinnerDateModel());
		checkInSpinner.setBounds(500, 23, 120, 26);
		checkInSpinner.setEditor(new JSpinner.DateEditor(checkInSpinner, "yyyy-MM-dd"));
		contentPane.add(checkInSpinner);
		
		JLabel checkOutLabel = new JLabel("Check Out:");
		checkOutLabel.setBounds(413, 74, 80, 16);
		contentPane.add(checkOutLabel);
		
		checkOutSpinner = new JSpinner(new SpinnerDateModel());
		checkOutSpinner.setBounds(500, 69, 120, 26);
		checkOutSpinner.setEditor(new JSpinner.DateEditor(checkOutSpinner, "yyyy-MM-dd"));
		contentPane.add(checkOutSpinner);
		
		guestList = new JComboBox<String>();
		guestList.setBounds(95, 24, 150, 27);
		contentPane.add(guestList);
		
		try {
		    Connection conn = DatabaseConnection.getInstance().getConnection();
		    PreparedStatement stmt = conn.prepareStatement("SELECT id, first_name FROM Users WHERE role = 'Guest'");
		    ResultSet rs = stmt.executeQuery();

		    while (rs.next()) {
		    	int id = rs.getInt("id");
		        String username = rs.getString("first_name");
		        guestList.addItem(username);
		        guestMap.put(username, id);
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		JLabel servicesLabel = new JLabel("Additional Services:");
		servicesLabel.setBounds(21, 127, 130, 16);
		contentPane.add(servicesLabel);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(163, 123, 150, 27);
		contentPane.add(comboBox);
		
		comboBox.addItem("None");
		comboBox.addItem("Dining");
		comboBox.addItem("Spa");
		comboBox.addItem("Room Service");
		comboBox.addItem("Laundry");
		
		reservationTable = new JTable();
		
		JScrollPane scrollPane = new JScrollPane(reservationTable);
		scrollPane.setBounds(21, 185, 700, 300);
		contentPane.add(scrollPane);
		
		JButton bookButton = new JButton("Book");
		bookButton.setBounds(100, 518, 117, 29);
		contentPane.add(bookButton);
		bookButton.addActionListener(e->{
			
			new ReceptionWorker("INSERT",this).execute();
			
		});
		
		JButton modifyButton = new JButton("Modify");
		modifyButton.setBounds(228, 518, 117, 29);
		contentPane.add(modifyButton);
		modifyButton.addActionListener(e->{
			new ReceptionWorker("UPDATE",this).execute();
			
		});
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(358, 518, 117, 29);
		contentPane.add(deleteButton);
		deleteButton.addActionListener(e->{
			
			new ReceptionWorker("DELETE",this).execute();
			
		});
		
		JButton roomsButton = new JButton("Available Rooms");
		roomsButton.setBounds(208, 570, 140, 29);
		contentPane.add(roomsButton);
		roomsButton.addActionListener(e->{
			
			new ReceptionWorker("ALL",this).execute();
			
		});
		
		JButton userResButton = new JButton("Reservations");
		userResButton.setBounds(358, 570, 130, 29);
		contentPane.add(userResButton);
		userResButton.addActionListener(e->{
			
			new ReceptionWorker("RESO",this).execute();
			
		});
		
		JButton checkOutButton = new JButton("Check Out");
		checkOutButton.setBounds(487, 518, 117, 29);
		contentPane.add(checkOutButton);
		checkOutButton.addActionListener(e->{
			
			new ReceptionWorker("CHECKOUT",this).execute();
			
		});
	}

}
