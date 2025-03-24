package project;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class LoginPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameTextField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage frame = new LoginPage();
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
	public LoginPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel hotelName = new JLabel("SkyLines Hotel");
		hotelName.setBounds(350, 110, 100, 50);
		contentPane.add(hotelName);
		
		JLabel instructions = new JLabel("Please Login into your account");
		instructions.setBounds(315, 190, 200, 16);
		contentPane.add(instructions);
		
		usernameTextField = new JTextField();
		usernameTextField.setBounds(385, 297, 130, 26);
		contentPane.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(249, 302, 100, 16);
		contentPane.add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(249, 385, 100, 16);
		contentPane.add(passwordLabel);
		passwordField = new JPasswordField();
		passwordField.setBounds(385, 380, 130, 26);
		contentPane.add(passwordField);
		
		JButton submitBtn = new JButton("Submit");
		submitBtn.setBounds(333, 465, 117, 29);
		contentPane.add(submitBtn);
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openPage(CheckUser.checkUser(usernameTextField.getText(),new String(passwordField.getPassword())));
			}
		});
		
		
	}
	
	public void openPage(String role) {
		switch(role) {
		case "Guest":
			this.dispose();
			new GuestPage().setVisible(true);
			break;
		default:
			JOptionPane.showMessageDialog(this,"Unable to open page. Please try again later");
		
		}
	}
	
}
