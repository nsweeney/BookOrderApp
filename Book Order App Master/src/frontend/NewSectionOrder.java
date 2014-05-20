package frontend;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import businesslayer.Book;
import businesslayer.Book.BookUseRequirement;
import businesslayer.Instructor;
import businesslayer.ProgramChair;
import businesslayer.SectionOrder;

import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import datalayer.SectionOrderStorage;

/**
 * Used to create new SectionOrder objects and store them in the database.
 */
public class NewSectionOrder extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Main content panel that contains the General, Sections, Instructors, and Books tabbed panes.
	 */
	private JPanel contentPanel = new JPanel();
	/**
	 * SectionOrder object
	 */
	private SectionOrder sectionOrder;
	/**
	 * ProgramChair object
	 */
	private ProgramChair programChair;
	/**
	 * Instructor object
	 */
	private Instructor instructor;
	/**
	 * Book object
	 */
	private Book book;
	/**
	 * List of instructors that have been added to the section order.
	 */
	private ArrayList<Instructor> instructors = new ArrayList<Instructor>();
	/**
	 * List of books that have been added to the section order.
	 */
	private ArrayList<Book> books = new ArrayList<Book>();
	/**
	 * List of sections that have been added to the section order.
	 */
	private ArrayList<String> sections = new ArrayList<String>();
	/**
	 * Sections pane table model used to model the table within the sections pane.
	 */
	private DefaultTableModel tableModelSections;
	/**
	 * Instructors pane table model used to model the table within the instructors pane.
	 */
	private DefaultTableModel tableModelInstructors;
	/**
	 * Books pane table model used to model the table within the books pane.
	 */
	private DefaultTableModel tableModelBooks;
	/**
	 * Sections pane table used to display the sections that have been added to the section order.
	 */
	private JTable tblSections;
	/**
	 * Instructors pane table used to display the instructors that have been added to the section order.
	 */
	private JTable tblInstructors;
	/**
	 * Books pane table used to display the books that have been added to the section order.
	 */
	private JTable tblBooks;
	/**
	 * MaskFormatter object for use with JFormattedTextField objects.
	 */
	private MaskFormatter formatter = null;
	private Component frame = null;
	/**
	 * The following JFormattedTextField is used in place of a normal text field in order to constrain user input
	 * to conform with a specific phone number format.
	 */
	private JFormattedTextField txtPhone;
	/**
	 * The following JFormattedTextField is used in place of a normal text field in order to constrain user input
	 * to conform with a specific phone number format.
	 */
	private JFormattedTextField txtChairPhone;
	/**
	 * The following JFormattedTextField is used in place of a normal text field in order to constrain user input
	 * to conform with a specific date format.
	 */
	private JFormattedTextField txtSemesterStartDate;
	/**
	 * The following JFormattedTextField is used in place of a normal text field in order to constrain user input
	 * to conform with a specific date format.
	 */
	private JFormattedTextField txtSemesterEndDate;
	/**
	 * General tab department text field.
	 */
	private JTextField txtDepartment;
	/**
	 * General tab course text field.
	 */
	private JTextField txtCourse;
	/**
	 * General tab anticipated enrollment text field.
	 */
	private JTextField txtEnrollment;
	/**
	 * Books tab ISBN text field.
	 */
	private JTextField txtISBN;
	/**
	 * Books tab title text field.
	 */
	private JTextField txtTitle;
	/**
	 * Books tab author text field.
	 */
	private JTextField txtAuthor;
	/**
	 * Books tab publisher text field.
	 */
	private JTextField txtPublisher;
	/**
	 * Books tab cost text field.
	 */
	private JTextField txtCost;
	/**
	 * Instructors tab first name text field.
	 */
	private JTextField txtFirstName;
	/**
	 * Instructors tab last name text field.
	 */
	private JTextField txtLastName;
	/**
	 * Instructors tab email text field.
	 */
	private JTextField txtEmail;
	/**
	 * Sections tab section text field.
	 */
	private JTextField txtSection;
	/**
	 * General tab program chair first name text field.
	 */
	private JTextField txtChairFirstName;
	/**
	 * General tab program chair last name text field.
	 */
	private JTextField txtChairLastName;
	/**
	 * General tab program chair email text field.
	 */
	private JTextField txtChairEmail;
	/**
	 * Genral tab semester combo box field.
	 */
	private JComboBox<String> comboSemester;
	/**
	 * Books tab requirement combo box field.
	 */
	private JComboBox<String> comboRequirement;
	/**
	 * Books tab edition combo box field
	 */
	private JComboBox<String> comboEdition;
	/**
	 * Books tab resellable combo box field.
	 */
	private JComboBox<String> comboResellable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			NewSectionOrder dialog = new NewSectionOrder();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NewSectionOrder() {
		setTitle("Save New Section Order");
		setBounds(100, 100, 350, 560);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			// Creates the JTabbedPane container for the various JPanels
			JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
			tabbedPane.setBounds(6, 6, 338, 487);
			contentPanel.add(tabbedPane);
			{
				// General Tab GUI Components
				JPanel panelGeneral = new JPanel();
				tabbedPane.addTab("General", null, panelGeneral, null);
				panelGeneral.setLayout(null);
				{
					JLabel lblNewLabel = new JLabel("Department:");
					lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
					lblNewLabel.setBounds(6, 12, 103, 16);
					panelGeneral.add(lblNewLabel);

					JLabel lblCourse = new JLabel("Course:");
					lblCourse.setHorizontalAlignment(SwingConstants.RIGHT);
					lblCourse.setBounds(6, 52, 103, 16);
					panelGeneral.add(lblCourse);

					JLabel lblSemester = new JLabel("Semester:");
					lblSemester.setHorizontalAlignment(SwingConstants.RIGHT);
					lblSemester.setBounds(6, 90, 103, 16);
					panelGeneral.add(lblSemester);

					JLabel lblSemesterStarts = new JLabel("Semester Starts:");
					lblSemesterStarts
							.setHorizontalAlignment(SwingConstants.RIGHT);
					lblSemesterStarts.setBounds(6, 131, 103, 16);
					panelGeneral.add(lblSemesterStarts);

					JLabel lblSemesterEnds = new JLabel("Semester Ends:");
					lblSemesterEnds
							.setHorizontalAlignment(SwingConstants.RIGHT);
					lblSemesterEnds.setBounds(6, 171, 103, 16);
					panelGeneral.add(lblSemesterEnds);

					JLabel lblAnticipatedEnrollment = new JLabel("Enrollment:");
					lblAnticipatedEnrollment
							.setHorizontalAlignment(SwingConstants.RIGHT);
					lblAnticipatedEnrollment.setBounds(6, 211, 103, 16);
					panelGeneral.add(lblAnticipatedEnrollment);

					txtDepartment = new JTextField();
					txtDepartment.setBounds(121, 6, 190, 28);
					panelGeneral.add(txtDepartment);
					txtDepartment.setColumns(10);

					txtCourse = new JTextField();
					txtCourse.setBounds(121, 46, 190, 28);
					panelGeneral.add(txtCourse);
					txtCourse.setColumns(10);

					comboSemester = new JComboBox<String>();
					comboSemester.setBounds(121, 86, 190, 27);
					comboSemester.addItem("Fall");
					comboSemester.addItem("Spring");
					comboSemester.addItem("Summer");
					panelGeneral.add(comboSemester);

					try {
						formatter = new MaskFormatter("##/##/####");
						formatter.setValidCharacters("0123456789");
						formatter.setPlaceholderCharacter('_');
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					txtSemesterStartDate = new JFormattedTextField(formatter);
					txtSemesterStartDate.setBounds(121, 125, 190, 28);
					panelGeneral.add(txtSemesterStartDate);
					txtSemesterStartDate.setColumns(10);

					txtSemesterEndDate = new JFormattedTextField(formatter);
					txtSemesterEndDate.setBounds(121, 165, 190, 28);
					panelGeneral.add(txtSemesterEndDate);
					txtSemesterEndDate.setColumns(10);

					txtEnrollment = new JTextField();
					txtEnrollment.setBounds(121, 205, 190, 28);
					panelGeneral.add(txtEnrollment);
					txtEnrollment.setColumns(10);
				}

				JPanel panelProgramChair = new JPanel();
				panelProgramChair.setBorder(new TitledBorder(new EtchedBorder(
						EtchedBorder.LOWERED, null, null), "Program Chair ",
						TitledBorder.CENTER, TitledBorder.TOP, null, null));
				panelProgramChair.setBounds(6, 245, 305, 190);
				panelGeneral.add(panelProgramChair);
				panelProgramChair.setLayout(null);

				txtChairFirstName = new JTextField();
				txtChairFirstName.setText("");
				txtChairFirstName.setBounds(115, 20, 184, 28);
				panelProgramChair.add(txtChairFirstName);
				txtChairFirstName.setColumns(10);

				txtChairLastName = new JTextField();
				txtChairLastName.setBounds(115, 60, 184, 28);
				panelProgramChair.add(txtChairLastName);
				txtChairLastName.setColumns(10);

				try {
					formatter = new MaskFormatter("(###) ###-####");
					formatter.setValidCharacters("0123456789");
					formatter.setPlaceholderCharacter('_');
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				txtChairPhone = new JFormattedTextField(formatter);
				txtChairPhone.setBounds(115, 100, 184, 28);
				panelProgramChair.add(txtChairPhone);
				txtChairPhone.setColumns(10);

				txtChairEmail = new JTextField();
				txtChairEmail.setBounds(115, 140, 184, 28);
				panelProgramChair.add(txtChairEmail);
				txtChairEmail.setColumns(10);

				JLabel lblChairFirstName = new JLabel("First Name:");
				lblChairFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
				lblChairFirstName.setBounds(6, 26, 97, 16);
				panelProgramChair.add(lblChairFirstName);

				JLabel lblChairLastName = new JLabel("Last Name:");
				lblChairLastName.setHorizontalAlignment(SwingConstants.RIGHT);
				lblChairLastName.setBounds(6, 66, 97, 16);
				panelProgramChair.add(lblChairLastName);

				JLabel lblChairPhone = new JLabel("Phone:");
				lblChairPhone.setHorizontalAlignment(SwingConstants.RIGHT);
				lblChairPhone.setBounds(6, 106, 97, 16);
				panelProgramChair.add(lblChairPhone);

				JLabel lblChairEmail = new JLabel("Email:");
				lblChairEmail.setHorizontalAlignment(SwingConstants.RIGHT);
				lblChairEmail.setBounds(6, 146, 97, 16);
				panelProgramChair.add(lblChairEmail);

				// Sections Tab GUI Components
				JPanel panelSections = new JPanel();
				tabbedPane.addTab("Sections", null, panelSections, null);
				panelSections.setLayout(null);
				{
					JLabel lblSection = new JLabel("Section:");
					lblSection.setHorizontalAlignment(SwingConstants.RIGHT);
					lblSection.setBounds(6, 12, 92, 16);
					panelSections.add(lblSection);

					txtSection = new JTextField();
					txtSection.setBounds(110, 6, 201, 28);
					panelSections.add(txtSection);
					txtSection.setColumns(10);

					tableModelSections = new DefaultTableModel(
							new Object[] { "Section" }, 0);
					tblSections = new JTable(tableModelSections);
					tblSections.getColumnModel().getColumn(0)
							.setPreferredWidth(100);
					tblSections.setEnabled(false);
					JScrollPane scrollPaneSections = new JScrollPane(
							tblSections);
					scrollPaneSections.setBounds(6, 87, 305, 348);
					panelSections.add(scrollPaneSections);

					JButton btnAddSection = new JButton("Add Section");
					btnAddSection.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							addSection();
						}
					});
					btnAddSection.setBounds(194, 46, 117, 29);
					panelSections.add(btnAddSection);
				}

				// Instructors Tab GUI Components
				JPanel panelInstructors = new JPanel();
				tabbedPane.addTab("Instructors", null, panelInstructors, null);
				panelInstructors.setLayout(null);
				{
					JLabel lblFirstName = new JLabel("First Name:");
					lblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
					lblFirstName.setBounds(-2, 12, 100, 16);
					panelInstructors.add(lblFirstName);

					JLabel lblLastName = new JLabel("Last Name:");
					lblLastName.setHorizontalAlignment(SwingConstants.RIGHT);
					lblLastName.setBounds(-2, 52, 100, 16);
					panelInstructors.add(lblLastName);

					JLabel lblPhone = new JLabel("Phone:");
					lblPhone.setHorizontalAlignment(SwingConstants.RIGHT);
					lblPhone.setBounds(-2, 92, 100, 16);
					panelInstructors.add(lblPhone);

					JLabel lblEmail = new JLabel("Email:");
					lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
					lblEmail.setBounds(-2, 132, 100, 16);
					panelInstructors.add(lblEmail);

					txtFirstName = new JTextField();
					txtFirstName.setColumns(10);
					txtFirstName.setBounds(110, 6, 201, 28);
					panelInstructors.add(txtFirstName);

					txtLastName = new JTextField();
					txtLastName.setColumns(10);
					txtLastName.setBounds(110, 46, 201, 28);
					panelInstructors.add(txtLastName);

					try {
						formatter = new MaskFormatter("(###) ###-####");
						formatter.setValidCharacters("0123456789");
						formatter.setPlaceholderCharacter('_');
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					txtPhone = new JFormattedTextField(formatter);
					txtPhone.setColumns(10);
					txtPhone.setBounds(110, 86, 201, 28);
					panelInstructors.add(txtPhone);

					txtEmail = new JTextField();
					txtEmail.setColumns(10);
					txtEmail.setBounds(110, 126, 201, 28);
					panelInstructors.add(txtEmail);

					tableModelInstructors = new DefaultTableModel(new Object[] {
							"First Name", "Last Name", "Phone" }, 0);
					tblInstructors = new JTable(tableModelInstructors);
					tblInstructors.getColumnModel().getColumn(0)
							.setPreferredWidth(30);
					tblInstructors.getColumnModel().getColumn(1)
							.setPreferredWidth(30);
					tblInstructors.getColumnModel().getColumn(2)
							.setPreferredWidth(40);
					tblInstructors.setEnabled(false);
					JScrollPane scrollPaneInstructors = new JScrollPane(
							tblInstructors);
					scrollPaneInstructors.setBounds(6, 207, 305, 228);
					panelInstructors.add(scrollPaneInstructors);

					JButton btnAddInstructor = new JButton("Add Instructor");
					btnAddInstructor.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							addInstructor();
						}
					});
					btnAddInstructor.setBounds(176, 166, 135, 29);
					panelInstructors.add(btnAddInstructor);
				}

				// Books Tab GUI Components
				JPanel panelBooks = new JPanel();
				tabbedPane.addTab("Books", null, panelBooks, null);
				panelBooks.setLayout(null);
				{
					JLabel lblISBN = new JLabel("ISBN:");
					lblISBN.setHorizontalAlignment(SwingConstants.RIGHT);
					lblISBN.setBounds(8, 12, 90, 16);
					panelBooks.add(lblISBN);

					JLabel lblTitle = new JLabel("Title:");
					lblTitle.setHorizontalAlignment(SwingConstants.RIGHT);
					lblTitle.setBounds(6, 52, 90, 16);
					panelBooks.add(lblTitle);

					JLabel lblAuthor = new JLabel("Author:");
					lblAuthor.setHorizontalAlignment(SwingConstants.RIGHT);
					lblAuthor.setBounds(6, 92, 90, 16);
					panelBooks.add(lblAuthor);

					JLabel lblPublisher = new JLabel("Publisher:");
					lblPublisher.setHorizontalAlignment(SwingConstants.RIGHT);
					lblPublisher.setBounds(6, 132, 90, 16);
					panelBooks.add(lblPublisher);

					JLabel lblStudentCost = new JLabel("Student Cost:");
					lblStudentCost.setHorizontalAlignment(SwingConstants.RIGHT);
					lblStudentCost.setBounds(6, 172, 90, 16);
					panelBooks.add(lblStudentCost);

					JLabel lblRequirement = new JLabel("Requirement:");
					lblRequirement.setHorizontalAlignment(SwingConstants.RIGHT);
					lblRequirement.setBounds(-36, 210, 134, 16);
					panelBooks.add(lblRequirement);

					JLabel lblResellable = new JLabel("Resellable:");
					lblResellable.setHorizontalAlignment(SwingConstants.RIGHT);
					lblResellable.setBounds(-36, 288, 134, 16);
					panelBooks.add(lblResellable);

					JLabel lblEdition = new JLabel("Edition:");
					lblEdition.setHorizontalAlignment(SwingConstants.RIGHT);
					lblEdition.setBounds(-5, 249, 103, 16);
					panelBooks.add(lblEdition);

					txtISBN = new JTextField();
					txtISBN.setColumns(10);
					txtISBN.setBounds(110, 6, 201, 28);
					panelBooks.add(txtISBN);

					txtTitle = new JTextField();
					txtTitle.setColumns(10);
					txtTitle.setBounds(110, 46, 201, 28);
					panelBooks.add(txtTitle);

					txtAuthor = new JTextField();
					txtAuthor.setColumns(10);
					txtAuthor.setBounds(108, 86, 201, 28);
					panelBooks.add(txtAuthor);

					txtPublisher = new JTextField();
					txtPublisher.setColumns(10);
					txtPublisher.setBounds(108, 126, 201, 28);
					panelBooks.add(txtPublisher);

					txtCost = new JTextField();
					txtCost.setColumns(10);
					txtCost.setBounds(108, 166, 201, 28);
					panelBooks.add(txtCost);

					comboRequirement = new JComboBox<String>();
					comboRequirement.setBounds(110, 206, 199, 27);
					comboRequirement.addItem("Required");
					comboRequirement.addItem("Optional");
					comboRequirement.addItem("Recommended");
					panelBooks.add(comboRequirement);

					comboResellable = new JComboBox<String>();
					comboResellable.setBounds(110, 284, 201, 27);
					comboResellable.addItem("Yes");
					comboResellable.addItem("No");
					panelBooks.add(comboResellable);

					comboEdition = new JComboBox<String>();
					comboEdition.setBounds(110, 245, 201, 27);
					comboEdition.addItem("Newest edition");
					comboEdition.addItem("Any edition");
					panelBooks.add(comboEdition);

					tableModelBooks = new DefaultTableModel(new Object[] {
							"ISBN", "Title" }, 0);
					tblBooks = new JTable(tableModelBooks);
					tblBooks.getColumnModel().getColumn(0)
							.setPreferredWidth(90);
					tblBooks.getColumnModel().getColumn(1)
							.setPreferredWidth(210);
					JScrollPane scrollPaneBooks = new JScrollPane(tblBooks);
					scrollPaneBooks.setBounds(6, 364, 305, 71);
					panelBooks.add(scrollPaneBooks);

					JButton btnAddBook = new JButton("Add Book");
					btnAddBook.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							addBook();
						}
					});
					btnAddBook.setBounds(221, 323, 90, 29);
					panelBooks.add(btnAddBook);
				}
			}
		}

		// Panel for the primary dialog Submit and Cancel buttons.
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			JButton btnSubmit = new JButton("  Save  ");
			btnSubmit.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					addOrder();
				}
			});
			buttonPane.add(btnSubmit);

			JButton btnCancel = new JButton("Cancel");
			btnCancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					NewSectionOrder.this.dispose();
				}
			});
			buttonPane.add(btnCancel);
		}
	}

	/**
	 * Adds a section to the sections list.
	 */
	private void addSection() {
		try {
			// Create locals variables from form field data.
			String section = txtSection.getText();
			// Check local variables to make sure each contains data before
			// committing to list.
			if (section.equals("")) {
				// Notify user that all form fields must be completed.
				JOptionPane.showMessageDialog(frame,
						"Please complete all fields!");
			} else {
				sections.add(section);
				// Update section summary table.
				String[] rowData = { section };
				tableModelSections.addRow(rowData);
				// Clear section form fields.
				clearSectionFields();
			}
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new Instructor object and adds the object to the instructors
	 * list.
	 */
	private void addInstructor() {
		try {
			// Create locals variables from form field data.
			String firstName = txtFirstName.getText();
			String lastName = txtLastName.getText();
			String phone = (String) txtPhone.getValue();
			String email = txtEmail.getText();
			// Check local variables to make sure each contains data before
			// committing to object.
			if (firstName.equals("") || lastName.equals("") || phone.equals("")
					|| email.equals("")) {
				// Notify user that all form fields must be completed.
				JOptionPane.showMessageDialog(frame,
						"Please complete all fields!");
			} else {
				instructor = new Instructor(firstName, lastName, phone, email);
				instructors.add(instructor);
				System.out.println("Contents: " + instructors);
				// Update instructor summary table.
				String[] rowData = { firstName, lastName, phone };
				tableModelInstructors.addRow(rowData);
				// Clear instructor form fields.
				clearInstructorFields();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Creates a new Book object and adds the object to the books list.
	 */
	private void addBook() {
		try {
			// Create locals variables from form field data.
			String isbn = txtISBN.getText();
			String title = txtTitle.getText();
			String author = txtAuthor.getText();
			String publisher = txtPublisher.getText();
			Double cost = Double.parseDouble(txtCost.getText());
			// Translate data from the requirement combo box into
			// BookUseRequirement enum values.
			BookUseRequirement requirement = null;
			if (comboRequirement.getSelectedItem() == "Required") {
				requirement = BookUseRequirement.REQUIRED;
			} else if (comboRequirement.getSelectedItem() == "Optional") {
				requirement = BookUseRequirement.OPTIONAL;
			} else {
				requirement = BookUseRequirement.RECOMMENDED;
			}
			// Translate data from the edition combo box into Boolean.
			Boolean edition = null;
			if (comboEdition.getSelectedItem() == "Newest edition") {
				edition = true;
			} else {
				edition = false;
			}
			// Translate data from the resellable combo box into Boolean.
			Boolean resellable = null;
			if (comboResellable.getSelectedItem() == "Yes") {
				resellable = true;
			} else {
				resellable = false;
			}
			// Check local variables to make sure each contains data before
			// committing to object.
			if (isbn.equals("") || title.equals("") || author.equals("")
					|| publisher.equals("") || cost.equals("")) {
				// Notify user that all form fields must be completed.
				JOptionPane.showMessageDialog(frame,
						"Please complete all fields!");
			} else {
				book = new Book(isbn, cost, resellable, title, author,
						publisher, requirement, edition);
				books.add(book);
				System.out.println("Contents: " + books);
				// Update book summary table.
				String[] rowData = { isbn, title };
				tableModelBooks.addRow(rowData);
				// Clear book form fields.
				clearBookFields();
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addOrder() {
		// Create string variables from the semester start and end formatted
		// text fields.
		System.out.println((String) txtChairPhone.getValue());
		String semesterStartDate = (String) txtSemesterStartDate.getValue();
		String semesterEndDate = (String) txtSemesterEndDate.getValue();
		System.out.println("Start: " + semesterStartDate + " and End: "
				+ semesterEndDate);
		// Check to make sure variables are not null.
		if (semesterStartDate == null || semesterEndDate == null) {
			// Notify user to complete the semester start and end form fields.
			JOptionPane.showMessageDialog(frame,
					"Please complete the Semester Start and End form fields!");
		} else {
			// Convert semester start and end date variables to
			// GregorianCalendar type.
			GregorianCalendar startDate = convertStringToDate(semesterStartDate);
			GregorianCalendar endDate = convertStringToDate(semesterEndDate);
			// Check remaining form fields for missing user input.
			if (txtDepartment.getText().equals("")
					|| txtCourse.getText().equals("")
					|| txtEnrollment.getText().equals("")
					|| txtChairFirstName.getText().equals("")
					|| txtChairLastName.getText().equals("")
					|| (String) txtChairPhone.getValue() == null
					|| txtChairEmail.getText().equals("")) {
				// Notify user that all form fields must be completed.
				JOptionPane.showMessageDialog(frame,
						"Please complete all fields!");
			} else {
				// Populate local variables from form field data.
				String department = txtDepartment.getText();
				String course = txtCourse.getText();
				String semester = (String) comboSemester.getSelectedItem();
				Integer enrollment = Integer.valueOf(txtEnrollment.getText());
				String chairFirstName = txtChairFirstName.getText();
				String chairLastName = txtChairLastName.getText();
				String chairPhone = (String) txtChairPhone.getValue();
				String chairEmail = txtChairEmail.getText();
				// Create ProgramChair object
				programChair = new ProgramChair(chairFirstName, chairLastName,
						chairPhone, chairEmail);
				// Check to make sure that user has created at least one
				// Section, Instructor, and Book object.
				if (sections.size() == 0) {
					// Notify user that a section order must contain at least
					// one section.
					JOptionPane
							.showMessageDialog(frame,
									"Please add at least one section to your section order!");
				} else if (instructors.size() == 0) {
					// Notify user that a section order must contain at least
					// one instructor.
					JOptionPane
							.showMessageDialog(frame,
									"Please add at least one instructor to your section order!");
				} else if (books.size() == 0) {
					// Notify user that a section order must contain at least
					// one book.
					JOptionPane
							.showMessageDialog(frame,
									"Please add at least one book to your section order!");
				} else {
					// Create SectionOrder object.
					sectionOrder = new SectionOrder(department, course, books,
							sections, semester, startDate, endDate,
							programChair, instructors, enrollment);
					// Adds sectionOrder object to the database
					sectionOrder.addToDatabase();
					clearGeneralFields();
					clearTables();
					JOptionPane.showMessageDialog(frame,
							"Section Order Created!");
				}
			}
		}
	}

	/**
	 * @param value
	 * @return
	 */
	private GregorianCalendar convertStringToDate(String value) {
		DateFormat df = new SimpleDateFormat("mm/dd/yyyy");
		Date date = null;
		try {
			date = df.parse(value);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		return cal;
	}

	/**
	 * Clears all form fields on the General tab.
	 */
	private void clearGeneralFields() {
		// Set all form fields to null.
		try {
			txtDepartment.setText(null);
			txtCourse.setText(null);
			txtEnrollment.setText(null);
			txtSemesterStartDate.setText(null);
			txtSemesterEndDate.setText(null);
			txtChairFirstName.setText(null);
			txtChairLastName.setText(null);
			txtChairPhone.setText(null);
			txtChairEmail.setText(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Clears all form fields on the Section tab.
	 */
	private void clearSectionFields() {
		// Set all form fields to null.
		try {
			txtSection.setText(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Clears all form fields on the Instructor tab.
	 */
	private void clearInstructorFields() {
		// Set all form fields to null.
		try {
			txtFirstName.setText(null);
			txtLastName.setText(null);
			txtPhone.setValue(null);
			txtEmail.setText(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Clears all form fields on the Book tab.
	 */
	private void clearBookFields() {
		// Set all form fields to null.
		try {
			txtISBN.setText(null);
			txtTitle.setText(null);
			txtAuthor.setText(null);
			txtPublisher.setText(null);
			txtCost.setText(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Clears the Sections, Instructors, and Books tables by setting their row
	 * count to zero.
	 */
	private void clearTables() {
		// Set the row count for each table to zero.
		tableModelSections.setRowCount(0);
		tableModelInstructors.setRowCount(0);
		tableModelBooks.setRowCount(0);
	}
}
