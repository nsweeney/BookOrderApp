package frontend;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import businesslayer.Book;
import datalayer.SectionOrderStorage;

/**
 * Used to search for various info about SectionOrder objects stored in the
 * database.
 */
public class SectionOrderSearchGUI {

	/**
	 * The main frame for the GUI
	 */
	private JFrame frmSearchSectionOrders;
	/**
	 * The panel that will be used to search for and display info regarding
	 * resalable and non-resalable books.
	 */
	private JPanel panelResalableBooks;
	/**
	 * The panel that will be used to display info regarding books cost over
	 * time.
	 */
	private JPanel panelBooksCostOverTime;
	/**
	 * Panel used to display table info pertaining to resalable and
	 * non-resalable books.
	 */
	private JPanel panelResalableResult;
	/**
	 * Panel used to display table info about books costs over time.
	 */
	private JPanel panelBookCostsResult;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SectionOrderSearchGUI window = new SectionOrderSearchGUI();
					window.frmSearchSectionOrders
							.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					window.frmSearchSectionOrders.setResizable(false);
					window.frmSearchSectionOrders.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SectionOrderSearchGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSearchSectionOrders = new JFrame();
		frmSearchSectionOrders.setTitle("Search Section Orders");
		frmSearchSectionOrders.setBounds(100, 100, 930, 440);
		frmSearchSectionOrders.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSearchSectionOrders.getContentPane().setLayout(
				new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmSearchSectionOrders.getContentPane().add(tabbedPane,
				BorderLayout.CENTER);

		panelResalableBooks = new JPanel();
		tabbedPane.addTab("Find resalable and non-resalable books.", null,
				panelResalableBooks, null);
		panelResalableBooks.setLayout(null);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// System.exit(-1);
				frmSearchSectionOrders.dispose();
			}
		});
		btnCancel.setBounds(723, 346, 181, 23);
		panelResalableBooks.add(btnCancel);

		JButton btnSearchResalableBooks = new JButton("Find Resalable Books");
		btnSearchResalableBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				int year = -1;
				String yearString = "";
				boolean isInYearDialog = true;

				List<Book> resalableBookList;

				// Ask user to enter a year. Make sure year is valid or that
				// they did not enter anything.
				while (isInYearDialog) {

					yearString = JOptionPane
							.showInputDialog("Enter Year (leave blank to search all years):");

					if (yearString.equals("")) {
						isInYearDialog = false;
					} else {
						try {
							year = Integer.parseInt(yearString);
							if (year < 0) {
								throw new NumberFormatException();
							}
							isInYearDialog = false;
						} catch (NumberFormatException nfe) {
							JOptionPane
									.showMessageDialog(
											panelResalableBooks,
											"Please enter a valid year or leave the field blank to search all years.",
											"Year search error.",
											JOptionPane.ERROR_MESSAGE);
						}
					}

				}

				// If user entered a valid year show all the resalable books
				// from that year.
				if (year >= 1) {
					// Find all resalable books in a given year
					resalableBookList = SectionOrderStorage
							.getSectionOrderStorage()
							.findResellableBooksByYear(year);
					// If user didnt enter anything in the year text box then
					// they want to see all the resalable books in the database.
				} else {
					// Find all resalable books
					resalableBookList = SectionOrderStorage
							.getSectionOrderStorage().findResellableBooks();
				}
				// Convert book list to an object array so we can pass it in to
				// a JTable
				Object[][] bookArray = new Object[resalableBookList.size()][8];

				for (int i = 0; i < resalableBookList.size(); i++) {
					bookArray[i][0] = resalableBookList.get(i)
							.getInternationalStandardBookNumber();
					bookArray[i][1] = resalableBookList.get(i).getStudentCost();
					bookArray[i][2] = resalableBookList.get(i).isResellable();
					bookArray[i][3] = resalableBookList.get(i).getTitle();
					bookArray[i][4] = resalableBookList.get(i).getAuthor();
					bookArray[i][5] = resalableBookList.get(i).getPublisher();
					bookArray[i][6] = resalableBookList.get(i)
							.getBookUseRequirement();
					bookArray[i][7] = resalableBookList.get(i)
							.isNewestBookEditionUseOnly();
				}
				// adds book info to panel
				createBookInfoDisplayArea(bookArray);
				panelResalableResult.validate();
			}
		});
		btnSearchResalableBooks.setBounds(341, 346, 181, 23);
		panelResalableBooks.add(btnSearchResalableBooks);

		JButton btnSearchByGiven = new JButton("Find Non-Resalable Books");
		btnSearchByGiven.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = -1;
				String yearString = "";
				boolean isInYearDialog = true;

				List<Book> nonResalableBookList;

				// Ask user to enter a year. Make sure year is valid or that
				// they did not enter anything.
				while (isInYearDialog) {

					yearString = JOptionPane
							.showInputDialog("Enter Year (leave blank to search all years):");

					if (yearString.equals("")) {
						isInYearDialog = false;
					} else {
						try {
							year = Integer.parseInt(yearString);
							if (year < 0) {
								throw new NumberFormatException();
							}
							isInYearDialog = false;
						} catch (NumberFormatException nfe) {
							JOptionPane
									.showMessageDialog(
											panelResalableBooks,
											"Please enter a valid year or leave the field blank to search all years.",
											"Year search error.",
											JOptionPane.ERROR_MESSAGE);
						}
					}

				}

				// If user entered a valid year show all the resalable books
				// from that year.
				if (year >= 1) {
					// Find all resalable books in a given year
					nonResalableBookList = SectionOrderStorage
							.getSectionOrderStorage()
							.findNonResellableBooksBooksByYear(year);
					// If user didnt enter anything in the year text box then
					// they want to see all the resalable books in the database.
				} else {
					// Find all resalable books
					nonResalableBookList = SectionOrderStorage
							.getSectionOrderStorage().findNonResellableBooks();
				}
				// Convert book list to an object array so we can pass it in to
				// a JTable
				Object[][] bookArray = new Object[nonResalableBookList.size()][8];

				for (int i = 0; i < nonResalableBookList.size(); i++) {
					bookArray[i][0] = nonResalableBookList.get(i)
							.getInternationalStandardBookNumber();
					bookArray[i][1] = nonResalableBookList.get(i)
							.getStudentCost();
					bookArray[i][2] = nonResalableBookList.get(i)
							.isResellable();
					bookArray[i][3] = nonResalableBookList.get(i).getTitle();
					bookArray[i][4] = nonResalableBookList.get(i).getAuthor();
					bookArray[i][5] = nonResalableBookList.get(i)
							.getPublisher();
					bookArray[i][6] = nonResalableBookList.get(i)
							.getBookUseRequirement();
					bookArray[i][7] = nonResalableBookList.get(i)
							.isNewestBookEditionUseOnly();
				}
				// adds book info to panel
				createBookInfoDisplayArea(bookArray);
				panelResalableResult.validate();
			}
		});
		btnSearchByGiven.setBounds(532, 346, 181, 23);
		panelResalableBooks.add(btnSearchByGiven);

		panelResalableResult = new JPanel();
		panelResalableResult.setBounds(15, 16, 889, 313);
		panelResalableBooks.add(panelResalableResult);

		panelBooksCostOverTime = new JPanel();
		tabbedPane.addTab("Find cost of books over time.", null,
				panelBooksCostOverTime, null);
		panelBooksCostOverTime.setLayout(null);

		panelBookCostsResult = new JPanel();
		panelBookCostsResult.setBounds(15, 16, 889, 313);
		panelBooksCostOverTime.add(panelBookCostsResult);

		JButton buttonSearchBookCostOverTime = new JButton("Search");
		buttonSearchBookCostOverTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				// Test outputting total cost of required books by year
				List<StringBuilder> requiredBookCostsOverTheYears2 = SectionOrderStorage
						.getSectionOrderStorage().findCostOfBooksOverTimeV2();

				// Convert StringBuilder list to an object array so we can pass
				// it in to
				// a JTable
				Object[][] bookArray = new Object[requiredBookCostsOverTheYears2
						.size()][8];
				for (int i = 0; i < requiredBookCostsOverTheYears2.size(); i++) {
					String[] lines = requiredBookCostsOverTheYears2.get(i)
							.toString().split("\\n");
					// Year will be one lines[0]
					bookArray[i][0] = lines[0];
					// Cost will be on lines[1]
					bookArray[i][1] = lines[1];
				}
				// adds book info to panel
				createBookCostsDisplayArea(bookArray);
				panelBookCostsResult.validate();
			}
		});
		buttonSearchBookCostOverTime.setBounds(716, 346, 89, 23);
		panelBooksCostOverTime.add(buttonSearchBookCostOverTime);

		JButton buttonCancelBookCostOverTime = new JButton("Cancel");
		buttonCancelBookCostOverTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmSearchSectionOrders.dispose();
			}
		});
		buttonCancelBookCostOverTime.setBounds(815, 346, 89, 23);
		panelBooksCostOverTime.add(buttonCancelBookCostOverTime);

		Object[][] bookArray = new Object[1][8];
		createBookInfoDisplayArea(bookArray);

		Object[][] bookArray2 = new Object[1][8];
		createBookCostsDisplayArea(bookArray2);
	}

	/**
	 * Used for displaying info about books in a table.
	 * 
	 * @param book
	 *            -Info used to populate the rows in the table
	 */
	public void createBookInfoDisplayArea(Object[][] book) {
		// Used to create column names in the JTable
		String[] columnNames = { "ISBN", "Student Cost", "Resalable", "Title",
				"Author", "Publisher", "Use Requirement", "Newest Edition" };
		JTable table = new JTable(book, columnNames);
		table.setFillsViewportHeight(true);
		panelResalableResult.setLayout(new BorderLayout());
		panelResalableResult.add(table.getTableHeader(),
				BorderLayout.PAGE_START);
		panelResalableResult.add(table, BorderLayout.CENTER);

	}

	/**
	 * Used for displaying info about books in a table.
	 * 
	 * @param book
	 *            -Info used to populate the rows in the table
	 */
	public void createBookCostsDisplayArea(Object[][] book) {
		// Used to create column names in the JTable
		String[] columnNames = { "YEAR", "STUDENT REQUIRED BOOK COST TOTAL" };
		JTable table = new JTable(book, columnNames);
		table.setFillsViewportHeight(true);
		panelBookCostsResult.setLayout(new BorderLayout());
		panelBookCostsResult.add(table.getTableHeader(),
				BorderLayout.PAGE_START);
		panelBookCostsResult.add(table, BorderLayout.CENTER);

	}
}
