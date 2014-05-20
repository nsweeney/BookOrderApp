package frontend;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;


public class NewBookDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private final ButtonGroup bgUseRequirement = new ButtonGroup();
	private final ButtonGroup bgResellable = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NewBookDialog dialog = new NewBookDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NewBookDialog() {
		setTitle("Submit New Textbook");
		setBounds(100, 100, 325, 405);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(null);
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(118, 6, 201, 28);
		contentPanel.add(textField);
		
		JLabel label = new JLabel("ISBN:");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(16, 12, 90, 16);
		contentPanel.add(label);
		
		JLabel label_1 = new JLabel("Title:");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(16, 52, 90, 16);
		contentPanel.add(label_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(118, 46, 201, 28);
		contentPanel.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(118, 86, 201, 28);
		contentPanel.add(textField_2);
		
		JLabel label_2 = new JLabel("Author:");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(16, 92, 90, 16);
		contentPanel.add(label_2);
		
		JLabel label_3 = new JLabel("Publisher:");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(16, 132, 90, 16);
		contentPanel.add(label_3);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(118, 126, 201, 28);
		contentPanel.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(118, 166, 201, 28);
		contentPanel.add(textField_4);
		
		JLabel label_4 = new JLabel("Student Cost:");
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setBounds(16, 172, 90, 16);
		contentPanel.add(label_4);
		
		JLabel label_5 = new JLabel("Use Requirement:");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(6, 210, 134, 16);
		contentPanel.add(label_5);
		
		JRadioButton rdbtnRequired = new JRadioButton("Required");
		bgUseRequirement.add(rdbtnRequired);
		rdbtnRequired.setSelected(true);
		rdbtnRequired.setBounds(145, 206, 141, 23);
		contentPanel.add(rdbtnRequired);
		
		JRadioButton rdbtnOptional = new JRadioButton("Optional");
		bgUseRequirement.add(rdbtnOptional);
		rdbtnOptional.setBounds(145, 231, 141, 23);
		contentPanel.add(rdbtnOptional);
		
		JRadioButton rdbtnRecommended = new JRadioButton("Recommended");
		bgUseRequirement.add(rdbtnRecommended);
		rdbtnRecommended.setBounds(145, 256, 141, 23);
		contentPanel.add(rdbtnRecommended);
		
		JRadioButton radioButton_3 = new JRadioButton("Yes");
		bgResellable.add(radioButton_3);
		radioButton_3.setSelected(true);
		radioButton_3.setBounds(145, 291, 141, 23);
		contentPanel.add(radioButton_3);
		
		JRadioButton radioButton_4 = new JRadioButton("No");
		bgResellable.add(radioButton_4);
		radioButton_4.setBounds(145, 316, 141, 23);
		contentPanel.add(radioButton_4);
		
		JLabel label_6 = new JLabel("Resellable:");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setBounds(6, 295, 134, 16);
		contentPanel.add(label_6);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Submit");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
