package frontend;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;


public class NewProgramChairDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtPhoneNumber;
	private JTextField txtEmail;
	private MaskFormatter formatter = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NewProgramChairDialog dialog = new NewProgramChairDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NewProgramChairDialog() {
		setTitle("Submit New Program Chair");
		setBounds(100, 100, 325, 220);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFirstName.setBounds(6, 12, 100, 16);
		contentPanel.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLastName.setBounds(6, 52, 100, 16);
		contentPanel.add(lblLastName);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number:");
		lblPhoneNumber.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPhoneNumber.setBounds(6, 92, 100, 16);
		contentPanel.add(lblPhoneNumber);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(6, 132, 100, 16);
		contentPanel.add(lblEmail);		
		
		txtFirstName = new JTextField();
		txtFirstName.setColumns(10);
		txtFirstName.setBounds(118, 6, 201, 28);
		contentPanel.add(txtFirstName);
		
		txtLastName = new JTextField();
		txtLastName.setColumns(10);
		txtLastName.setBounds(118, 46, 201, 28);
		contentPanel.add(txtLastName);
		
		try {
			formatter = new MaskFormatter("(###) ###-####");
			formatter.setValidCharacters("0123456789");
			formatter.setPlaceholderCharacter('_');
		} catch (ParseException e1) {
			//TODO Auto-generated catch block
			e1.printStackTrace();
		}
		txtPhoneNumber = new JFormattedTextField(formatter);
		txtPhoneNumber.setColumns(10);
		txtPhoneNumber.setBounds(118, 86, 201, 28);
		contentPanel.add(txtPhoneNumber);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(118, 126, 201, 28);
		contentPanel.add(txtEmail);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				makeObject();
			}
		});
		btnSubmit.setActionCommand("OK");
		buttonPane.add(btnSubmit);
		getRootPane().setDefaultButton(btnSubmit);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				closeDialog();
			}
		});
		btnCancel.setActionCommand("Cancel");
		buttonPane.add(btnCancel);
	}
	
	/**
	 * Collects user input from dialog and creates an Instructor object.
	 */
	private void makeObject() {
		String firstName = txtFirstName.getText();
		String lastName = txtLastName.getText();
		String phoneNumber = txtPhoneNumber.getText();
		System.out.println(firstName + " " + lastName + " " + phoneNumber);
	}
	
	/**
	 * Clears user input from all fields and disposes dialog.
	 */
	private void closeDialog() {
		txtFirstName.setText(null);
		txtLastName.setText(null);
		txtPhoneNumber.setText(null);
		txtEmail.setText(null);
		dispose();
	}
}
