package project;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class AdminPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JPanel panel;
	CreateUserPanel createUserPanel;
	ReportsPanel reportsPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminPage frame = new AdminPage();
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
	
	public AdminPage() {
		
		createUserPanel = new CreateUserPanel(this);
		reportsPanel = new ReportsPanel(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Admin Console");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblNewLabel.setBounds(37, 35, 140, 16);
		contentPane.add(lblNewLabel);
		
		JButton userButton = new JButton("Create Users");
		userButton.setBounds(28, 64, 117, 29);
		contentPane.add(userButton);
		userButton.addActionListener(e->{
			
			showCard("Users");
			
		});
		
		JButton reportButton = new JButton("Reports");
		reportButton.setBounds(28, 105, 117, 29);
		contentPane.add(reportButton);
		reportButton.addActionListener(e->{
			
			showCard("Reports");
			
		});
		
		panel = new JPanel(new CardLayout());
		panel.setBounds(38, 146, 668, 400);
		contentPane.add(panel);
		
		panel.add(createUserPanel,"Users");
		panel.add(reportsPanel,"Reports");
		
		showCard("Users");
	}
	
	private void showCard(String name) {
		CardLayout cl = (CardLayout) panel.getLayout();
		cl.show(panel, name);
	}

}


