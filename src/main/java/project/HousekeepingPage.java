package project;

import java.awt.EventQueue;

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
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

public class HousekeepingPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTextField textField;
	public JTable reservationTable;
	public JComboBox<String> comboBox;
	public JSpinner spinner;
	private InventoryNotifier IN = new InventoryNotifier();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HousekeepingPage frame = new HousekeepingPage();
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
	public HousekeepingPage() {
		InvNotification invNotification = new InvNotification();
		IN.registerObserver(invNotification);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(525, 74, 130, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel itemLabel = new JLabel("Inventory Item: ");
		itemLabel.setBounds(421, 79, 100, 16);
		contentPane.add(itemLabel);
		
		JLabel itemQanLabel = new JLabel("Quantity:");
		itemQanLabel.setBounds(421, 125, 100, 16);
		contentPane.add(itemQanLabel);
		
		
		JLabel servicesLabel = new JLabel("Room Cleaning Status:");
		servicesLabel.setBounds(75, 74, 150, 16);
		contentPane.add(servicesLabel);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(75, 123, 150, 27);
		contentPane.add(comboBox);
		
		comboBox.addItem("In Progress");
		comboBox.addItem("Completed");
		
		spinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
		spinner.setBounds(525, 120, 120, 26);
		contentPane.add(spinner);
		
		
		reservationTable = new JTable();
		
		JScrollPane scrollPane = new JScrollPane(reservationTable);
		scrollPane.setBounds(21, 185, 700, 300);
		contentPane.add(scrollPane);
		
		JButton uptButton = new JButton("Update Task");
		uptButton.setBounds(21, 518, 117, 29);
		contentPane.add(uptButton);
		uptButton.addActionListener(e->{
			
			
			
		});
		
		JButton uptInvButton = new JButton("Update Inventory");
		uptInvButton.setBounds(279, 518, 140, 29);
		contentPane.add(uptInvButton);
		uptInvButton.addActionListener(e->{
			new HousekeepingWorker("UPDATEINV",this).execute();
			
			
		});
		
		JButton invButton = new JButton("All Inventory");
		invButton.setBounds(428, 518, 140, 29);
		contentPane.add(invButton);
		invButton.addActionListener(e->{
			
			new HousekeepingWorker("ALLINV",this).execute();
		});
		
		JButton roomsButton = new JButton("Rooms");
		roomsButton.setBounds(572, 518, 130, 29);
		contentPane.add(roomsButton);
		
	
		roomsButton.addActionListener(e->{
			
			
		});
		

	}
	
	public InventoryNotifier getNotification() {
		return IN;
	}

}
