package project;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class CreateUserPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	JTextField firstNameText;
	JTextField lastNameText;
	JTextField userText;
	JPasswordField passwordText;
	JComboBox<String> comboBox;
	JButton submitBtn;
	
	private AdminPage parent;

	/**
	 * Create the panel.
	 */
	public CreateUserPanel(AdminPage p) {
		
		parent = p;
		
		setLayout(null);
		
		JLabel fnLabel = new JLabel("First Name:");
		fnLabel.setBounds(183, 31, 100, 16);
		add(fnLabel);
		
		firstNameText = new JTextField();
		firstNameText.setBounds(310, 26, 130, 26);
		add(firstNameText);
		firstNameText.setColumns(10);
		
		JLabel lnLabel = new JLabel("Last Name: ");
		lnLabel.setBounds(183, 76, 100, 16);
		add(lnLabel);
		
		lastNameText = new JTextField();
		lastNameText.setBounds(310, 71, 130, 26);
		add(lastNameText);
		lastNameText.setColumns(10);
		
		JLabel usernameLabel = new JLabel("Username: ");
		usernameLabel.setBounds(183, 120, 100, 16);
		add(usernameLabel);
		
		userText = new JTextField();
		userText.setBounds(310, 115, 130, 26);
		add(userText);
		userText.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Password: ");
		passwordLabel.setBounds(183, 166, 100, 16);
		add(passwordLabel);
		
		passwordText = new JPasswordField();
		passwordText.setBounds(310, 161, 130, 26);
		add(passwordText);
		
		JLabel roleLabel = new JLabel("Role: ");
		roleLabel.setBounds(183, 214, 61, 16);
		add(roleLabel);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(310, 210, 140, 27);
		add(comboBox);
		
		comboBox.addItem("Guest");
		comboBox.addItem("Housekeeping");
		comboBox.addItem("Receptionist");
		comboBox.addItem("Admin");
		
		submitBtn = new JButton("Submit");
		submitBtn.setBounds(250, 281, 117, 29);
		add(submitBtn);
		submitBtn.addActionListener(e->{
			
			//new AdminWorker("SUBMIT",parent).execute();
			
		});

	}

}
