package frontend;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainGUIWithButtons {

	private JFrame frmBookOrders;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUIWithButtons window = new MainGUIWithButtons();
					window.frmBookOrders.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainGUIWithButtons() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBookOrders = new JFrame();
		frmBookOrders.setTitle("Section Orders");
		frmBookOrders.setBounds(100, 100, 226, 270);
		frmBookOrders.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBookOrders.getContentPane().setLayout(null);

		JButton btnCreate = new JButton("Create");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Opens NewSectionOrder GUI
				try {
					NewSectionOrder sectionOrderGUI = new NewSectionOrder();
					sectionOrderGUI
							.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					sectionOrderGUI.setVisible(true);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnCreate.setBounds(10, 11, 190, 23);
		frmBookOrders.getContentPane().add(btnCreate);

		JButton btnEdit = new JButton("Edit");
		btnEdit.setBounds(10, 45, 190, 23);
		frmBookOrders.getContentPane().add(btnEdit);

		JButton btnSearch = new JButton("Search ");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Opens SectionOrderSearchGUI
				try {
					SectionOrderSearchGUI
							.main(new String[] { "Search Section Order" });
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnSearch.setBounds(10, 79, 190, 23);
		frmBookOrders.getContentPane().add(btnSearch);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(10, 113, 190, 23);
		frmBookOrders.getContentPane().add(btnSubmit);

		JButton btnCancel = new JButton("Close");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(-1);
			}
		});
		btnCancel.setBounds(111, 198, 89, 23);
		frmBookOrders.getContentPane().add(btnCancel);
	}
}
