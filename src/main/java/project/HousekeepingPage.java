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
import javax.swing.SwingConstants;

public class HousekeepingPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public JTable reservationTable;
	public JComboBox<String> comboBox;
	public JComboBox <String>itemNameUpdate;
	public JSpinner spinner;
	private InventoryNotifier IN = new InventoryNotifier();
	
	public JButton uptButton;
	public JButton uptInvButton;
	public JLabel lblNewLabel;

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
		
		lblNewLabel = new JLabel("Select an Item and enter number restocked");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(450, 29, 300, 16);
		contentPane.add(lblNewLabel);
		lblNewLabel.setVisible(false);
		
		JLabel itemLabel = new JLabel("Inventory Item: ");
		itemLabel.setBounds(320, 74, 100, 16);
		contentPane.add(itemLabel);
		
		itemNameUpdate = new JComboBox<String>();
		itemNameUpdate.setBounds(300, 123, 150, 27);
		contentPane.add(itemNameUpdate);
		
		itemNameUpdate.addItem("Toothpaste");
		itemNameUpdate.addItem("Towels");
		itemNameUpdate.addItem("Shampoo");
		itemNameUpdate.addItem("Mr. Clean Spray");
		itemNameUpdate.addItem("Chips");
		itemNameUpdate.addItem("Blankets");
		
		
		JLabel itemQanLabel = new JLabel("Quantity:");
		itemQanLabel.setBounds(525, 74, 100, 16);
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
		
		uptButton = new JButton("Update Room");
		uptButton.setBounds(21, 518, 117, 29);
		contentPane.add(uptButton);
		uptButton.setVisible(false);
		uptButton.addActionListener(e->{
			
			new HousekeepingWorker("UPDATEROOM",this).execute();
			
		});
		
		 uptInvButton = new JButton("Update Inventory");
		uptInvButton.setBounds(440, 518, 140, 29);
		contentPane.add(uptInvButton);
		uptInvButton.setVisible(false);
		uptInvButton.addActionListener(e->{
			new HousekeepingWorker("UPDATEINV",this).execute();
			
			
		});
		
		JButton invButton = new JButton("All Inventory");
		invButton.setBounds(575, 518, 140, 29);
		contentPane.add(invButton);
		invButton.addActionListener(e->{
			uptButton.setVisible(false);
			uptInvButton.setVisible(true);
			lblNewLabel.setVisible(true);
			new HousekeepingWorker("ALLINV",this).execute();
		});
		
		JButton roomsButton = new JButton("Rooms");
		roomsButton.setBounds(140, 518, 130, 29);
		contentPane.add(roomsButton);

		roomsButton.addActionListener(e->{
			uptButton.setVisible(true);
			uptInvButton.setVisible(false);
			lblNewLabel.setVisible(false);
			new HousekeepingWorker("ROOMS",this).execute();
			
			
		});
		

	}
	
	public InventoryNotifier getNotification() {
		return IN;
	}

}
