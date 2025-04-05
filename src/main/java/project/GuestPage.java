package project;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JButton;

public class GuestPage extends JFrame {

	private static final long serialVersionUID = 1L;
	JPanel contentPane;
	public JTextField textField;
	public JTextField textField_1;
	public JTable reservationTable;
	public JSpinner checkInSpinner;
	public JSpinner checkOutSpinner;
	public JComboBox<String> comboBox;
	public String currentTableMode = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuestPage frame = new GuestPage();
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
	public GuestPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(125, 23, 130, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel firstNameLabel = new JLabel("First Name:");
		firstNameLabel.setBounds(21, 28, 100, 16);
		contentPane.add(firstNameLabel);
		
		JLabel lastNameLabel = new JLabel("Last Name:");
		lastNameLabel.setBounds(21, 74, 100, 16);
		contentPane.add(lastNameLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(125, 69, 130, 26);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel checkInLabel = new JLabel("Check In:");
		checkInLabel.setBounds(313, 28, 80, 16);
		contentPane.add(checkInLabel);
		
		checkInSpinner = new JSpinner(new SpinnerDateModel());
		checkInSpinner.setBounds(400, 23, 120, 26);
		checkInSpinner.setEditor(new JSpinner.DateEditor(checkInSpinner, "yyyy-MM-dd"));
		contentPane.add(checkInSpinner);
		
		JLabel checkOutLabel = new JLabel("Check Out:");
		checkOutLabel.setBounds(313, 74, 80, 16);
		contentPane.add(checkOutLabel);
		
		checkOutSpinner = new JSpinner(new SpinnerDateModel());
		checkOutSpinner.setBounds(400, 69, 120, 26);
		checkOutSpinner.setEditor(new JSpinner.DateEditor(checkInSpinner, "yyyy-MM-dd"));
		contentPane.add(checkOutSpinner);
		
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
		bookButton.setBounds(21, 518, 117, 29);
		contentPane.add(bookButton);
		bookButton.addActionListener(e->{
			
			new GuestWorker("INSERT",this).execute();
			
		});
		
		JButton modifyButton = new JButton("Modify");
		modifyButton.setBounds(150, 518, 117, 29);
		contentPane.add(modifyButton);
		modifyButton.addActionListener(e->{
			new GuestWorker("UPDATE",this).execute();
			
		});
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(279, 518, 117, 29);
		contentPane.add(deleteButton);
		deleteButton.addActionListener(e->{
			
			new GuestWorker("DELETE",this).execute();
			
		});
		
		JButton roomsButton = new JButton("Available Rooms");
		roomsButton.setBounds(408, 518, 140, 29);
		contentPane.add(roomsButton);
		roomsButton.addActionListener(e->{
			currentTableMode = "ALL";
			new GuestWorker("ALL",this).execute();
			
		});
		
		JButton userResButton = new JButton("My Reservations");
		userResButton.setBounds(572, 518, 130, 29);
		contentPane.add(userResButton);
		userResButton.addActionListener(e->{
			currentTableMode = "RESO";
			new GuestWorker("RESO",this).execute();
			
		});
		
		
	}
}
