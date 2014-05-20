package datalayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import businesslayer.Book;
import businesslayer.Book.BookUseRequirement;
import businesslayer.Instructor;
import businesslayer.ProgramChair;
import businesslayer.SectionOrder;

/**
 * This class will be used for saving, and querying SectionOrder objects.
 */
public class SectionOrderStorage {

	/**
	 * Used in displaying results of SELECT queries.
	 */
	private ResultSet rs;
	/**
	 * Used for connecting to the database.
	 */
	private Connection con;
	/**
	 * Used for SQL queries.
	 */
	private Statement stmt;
	/**
	 * Used for SQL queries.
	 */
	private PreparedStatement preparedSqlStatement;
	/**
	 * Used to hold the value of the PK for the SectionOrder.
	 */
	private int sectionOrderPrimaryKey;

	/**
	 * Reference to single instance of SectionOrderStorage class.
	 */
	// Use of singleton pattern part 1.
	private static SectionOrderStorage sectionOrderStorage = null;

	/**
	 * A private constructor that is only called one time.
	 */
	// Use of singleton pattern part 2.
	private SectionOrderStorage() {
		rs = null;
		con = null;
		stmt = null;
		preparedSqlStatement = null;
	}

	/**
	 * Public method to make sectionOrderStorage object available throughout the
	 * application. The first time the method is called, the Single instance of
	 * sectionOrderStorage is created, each subsequent time, the one data object
	 * created is returned.
	 */
	// Use of singleton pattern part 3.
	public static SectionOrderStorage getSectionOrderStorage() {
		if (sectionOrderStorage == null) {
			sectionOrderStorage = new SectionOrderStorage();
		}

		return sectionOrderStorage;
	}

	/**
	 * Initializes the connection to bookOrderApp.db.
	 * 
	 * @return boolean -Whether initializing the connection to the database was
	 *         successful or not.
	 */
	public boolean initializeDatabaseConnection() {

		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:bookOrderApp.db");
			stmt = con.createStatement();
			System.out.println("DB connection success.");
			return true;
		} catch (ClassNotFoundException e) {
			// Perhaps these should be JOption pop ups for the user to see what
			// the error is.
			System.out.println("DB connection failure.");
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			System.out.println("DB connection failure.");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Closes the database connection with bookOrderApp.db.
	 * 
	 * @return boolean -Whether closing the connection to the database was
	 *         successful or not.
	 */
	public boolean disconnectDatabaseConnection() {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (con != null)
				con.close();
			System.out.println("DB disconnection success.");
			return true;
		} catch (SQLException ex) {
			System.out.println("DB disconnection failure.");
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * Takes in a SectionOrder object and adds it to the database.
	 * 
	 * @param isCommit
	 *            -Whether you want this permanently added to the database or
	 *            not.
	 * @param sectionOrder
	 *            -SectionOrder object you want to add to database.
	 * @return integer -How many rows were added to SECTIONORDER, INSTRUCTOR,
	 *         PROGRAMCHAIR, SECTION, and BOOK tables.
	 */
	public int addSectionOrderToDatabase(boolean isCommit,
			SectionOrder sectionOrder) {

		int numberOfRowsUpdated = 0;

		try {

			initializeDatabaseConnection();

			/*
			 * With auto commit set to false it runs through the motions of
			 * adding something to the database, but doesn't COMMIT it. Use to
			 * turn AutoCommit off/on as needed.
			 */
			if (!isCommit) {
				con.setAutoCommit(false);
			}

			// Should contain 1 now if sectionOrder was added to SECTIONORDER
			// table
			numberOfRowsUpdated += addToSectionOrderTable(sectionOrder);
			// Should contain 2 now if one instructor was associated with this
			// section order.
			numberOfRowsUpdated += addToInstructorTable(sectionOrder);
			// Should contain 3 now if one section was associated with this
			// section order.
			numberOfRowsUpdated += addToSectionTable(sectionOrder);
			// Should contain 4 now if one book was associated with this section
			// order.
			numberOfRowsUpdated += addToBookTable(sectionOrder);
			// Should contain 5 now with a program chair person added.
			numberOfRowsUpdated += addToProgramChairTable(sectionOrder);

			disconnectDatabaseConnection();

			if (numberOfRowsUpdated != 1) {
				System.out.println(numberOfRowsUpdated
						+ " rows added successfully in bookOrderApp.db");
			} else {
				System.out.println(numberOfRowsUpdated
						+ " row added successfully in bookOrderApp.db");
			}
			/*
			 * If we have a section order with just one instructor, section, and
			 * book associated with it. This will return 5. Because 1 row will
			 * be added in each of these tables: SECTIONORDER, INSTRUCTOR,
			 * PROGRAMCHAIR, SECTION, and BOOK
			 */
			return numberOfRowsUpdated;

		} catch (SQLException e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			return numberOfRowsUpdated;
		}
	}

	/**
	 * Adds the SectionOrder object to the SECTIONORDER table.
	 * 
	 * @param sectionOrder
	 *            -The object we will parse for information we need to store in
	 *            the SECTIONORDER table,
	 * @return integer -The number of rows updated in the SECTIONORDER table.
	 *         Should always be either 1 for when a row was added successfully
	 *         or 0 when no row was added.
	 */
	public int addToSectionOrderTable(SectionOrder sectionOrder) {

		/*
		 * Since SQlite has no Date data type, semesterStart and semesterEnd
		 * objects are converted to integers.
		 */
		int semesterStartYear = sectionOrder.getSemesterStart().get(
				Calendar.YEAR);
		int semesterStartMonth = sectionOrder.getSemesterStart().get(
				Calendar.MONTH);
		int semesterStartDayOfMonth = sectionOrder.getSemesterStart().get(
				Calendar.DAY_OF_MONTH);
		int semesterEndYear = sectionOrder.getSemesterEnd().get(Calendar.YEAR);
		int semesterEndMonth = sectionOrder.getSemesterEnd()
				.get(Calendar.MONTH);
		int semesterEndDayOfMonth = sectionOrder.getSemesterEnd().get(
				Calendar.DAY_OF_MONTH);

		/*
		 * Used to return a value indicating how many rows were added.
		 */
		int preparedStmntUpateCount = 0;

		// Initialize the prepared statement.
		try {
			stmt = con.createStatement();
			String sql = "INSERT INTO SECTIONORDER (DEPARTMENT,  COURSE, SEMESTER, MONTH_SEMESTERSTART, DAY_SEMESTERSTART, YEAR_SEMESTERSTART, MONTH_SEMESTEREND, DAY_SEMESTEREND, YEAR_SEMESTEREND, ANTICIPATEDENROLLMENT) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			preparedSqlStatement = con.prepareStatement(sql);
		} catch (SQLException ex) {
			System.out.println("SQL Exception: " + ex);
			ex.printStackTrace();
		}

		// Sets values in prepared statement.
		try {
			preparedSqlStatement.setString(1, sectionOrder.getDepartment());
			preparedSqlStatement.setString(2, sectionOrder.getCourse());
			preparedSqlStatement.setString(3, sectionOrder.getSemester());
			preparedSqlStatement.setInt(4, semesterStartMonth);
			preparedSqlStatement.setInt(5, semesterStartDayOfMonth);
			preparedSqlStatement.setInt(6, semesterStartYear);
			preparedSqlStatement.setInt(7, semesterEndMonth);
			preparedSqlStatement.setInt(8, semesterEndDayOfMonth);
			preparedSqlStatement.setInt(9, semesterEndYear);
			preparedSqlStatement.setInt(10,
					sectionOrder.getAnticipatedEnrollment());
		} catch (SQLException ex) {
			System.out.println("SQL Exception: " + ex);
			ex.printStackTrace();
		}

		// Execute prepared statement.
		try {
			preparedSqlStatement.executeUpdate();
			// Save the PK for this section order for later use with the other
			// tables that depend on it.
			sectionOrderPrimaryKey = findSectionOrderPK();
			try {
				preparedStmntUpateCount = preparedSqlStatement.getUpdateCount();
				if (preparedStmntUpateCount == 1) {
					System.out.println(preparedStmntUpateCount
							+ " row successfully updated!");
				} else {
					System.out.println(preparedStmntUpateCount
							+ " rows successfully updated!");
				}

			} catch (SQLException sqle) {
				System.out.println("SQL Exception: " + sqle);
				return preparedStmntUpateCount;
			}
		} catch (SQLIntegrityConstraintViolationException sqlicve) {
			System.out.println("SQL Integrity Constraint Exception: "
					+ sqlicve.getErrorCode());
			sqlicve.printStackTrace();
			// should return 0
			return preparedStmntUpateCount;
			// System.exit(-1);
		} catch (SQLException ex) {
			System.out.println("SQL Exception: " + ex);
			ex.printStackTrace();
			// should return 0
			return preparedStmntUpateCount;
		}

		return preparedStmntUpateCount;
	}

	/**
	 * Adds the SectionOrder object to the INSTRUCTOR table.
	 * 
	 * @param sectionOrder
	 *            -The object we will parse for information we need to store in
	 *            the INSTRUCTOR table,
	 * @return integer -The number of rows updated in the INSTRUCTOR table. If 1
	 *         instructor is associated with the order then this number is 1. If
	 *         there are two, then two rows will have been added, and this
	 *         number is 2, etc.
	 */
	public int addToInstructorTable(SectionOrder sectionOrder) {

		/*
		 * Used to return a value indicating how many rows were added.
		 */
		int preparedStmntUpateCount = 0;

		/*
		 * Go through instructor list and add each instructor to the INSTRUCTOR
		 * table.
		 */
		for (Instructor instructor : sectionOrder.getInstructorList()) {

			// Initialize the prepared statement.
			try {
				stmt = con.createStatement();
				String sql = "INSERT INTO INSTRUCTOR (FIRSTNAME,  LASTNAME, PHONE, EMAIL, SECTIONORDER_ID) "
						+ "VALUES (?, ?, ?, ?, ?)";
				preparedSqlStatement = con.prepareStatement(sql);
			} catch (SQLException ex) {
				System.out.println("SQL Exception: " + ex);
				ex.printStackTrace();
			}

			// Sets values in prepared statement.
			try {
				preparedSqlStatement.setString(1, instructor.getFirstName());
				preparedSqlStatement.setString(2, instructor.getLastName());
				preparedSqlStatement.setString(3, instructor.getPhoneNumber());
				preparedSqlStatement.setString(4, instructor.getEmail());
				// Saves current PK from SECTIONORDER table as FK in this table
				preparedSqlStatement.setInt(5, sectionOrderPrimaryKey);
			} catch (SQLException ex) {
				System.out.println("SQL Exception: " + ex);
				ex.printStackTrace();
			}

			// Execute prepared statement.
			try {
				preparedSqlStatement.executeUpdate();

				try {
					preparedStmntUpateCount += preparedSqlStatement
							.getUpdateCount();

				} catch (SQLException sqle) {
					System.out.println("SQL Exception: " + sqle);
					return preparedStmntUpateCount;
				}
			} catch (SQLIntegrityConstraintViolationException sqlicve) {
				System.out.println("SQL Integrity Constraint Exception: "
						+ sqlicve.getErrorCode());
				sqlicve.printStackTrace();
				// should return 0
				return preparedStmntUpateCount;
				// System.exit(-1);
			} catch (SQLException ex) {
				System.out.println("SQL Exception: " + ex);
				ex.printStackTrace();
				// should return 0
				return preparedStmntUpateCount;
			}
		}

		if (preparedStmntUpateCount == 1) {
			System.out.println(preparedStmntUpateCount
					+ " row successfully updated!");
		} else {
			System.out.println(preparedStmntUpateCount
					+ " rows successfully updated!");
		}

		return preparedStmntUpateCount;
	}

	/**
	 * Adds the SectionOrder object to the PROGRAMCHAIR table.
	 * 
	 * @param sectionOrder
	 *            -The object we will parse for information we need to store in
	 *            the PROGRAMCHAIR table,
	 * @return integer -The number of rows updated in the PROGRAMCHAIR table.
	 *         Should always be either 1 for when a row was added successfully
	 *         or 0 when no row was added.
	 */
	public int addToProgramChairTable(SectionOrder sectionOrder) {

		/*
		 * Used to return a value indicating how many rows were added.
		 */
		int preparedStmntUpateCount = 0;

		// Initialize the prepared statement.
		try {
			stmt = con.createStatement();
			String sql = "INSERT INTO PROGRAMCHAIR (FIRSTNAME,  LASTNAME, PHONE, EMAIL, SECTIONORDER_ID) "
					+ "VALUES (?, ?, ?, ?, ?)";
			preparedSqlStatement = con.prepareStatement(sql);
		} catch (SQLException ex) {
			System.out.println("SQL Exception: " + ex);
			ex.printStackTrace();
		}

		// Sets values in prepared statement.
		try {
			preparedSqlStatement.setString(1, sectionOrder.getProgramChair()
					.getFirstName());
			preparedSqlStatement.setString(2, sectionOrder.getProgramChair()
					.getLastName());
			preparedSqlStatement.setString(3, sectionOrder.getProgramChair()
					.getPhoneNumber());
			preparedSqlStatement.setString(4, sectionOrder.getProgramChair()
					.getEmail());
			// Saves current PK from SECTIONORDER as FK in this table
			preparedSqlStatement.setInt(5, sectionOrderPrimaryKey);
		} catch (SQLException ex) {
			System.out.println("SQL Exception: " + ex);
			ex.printStackTrace();
		}

		// Execute prepared statement.
		try {
			preparedSqlStatement.executeUpdate();

			try {
				preparedStmntUpateCount = preparedSqlStatement.getUpdateCount();
				if (preparedStmntUpateCount == 1) {
					System.out.println(preparedStmntUpateCount
							+ " row successfully updated!");
				} else {
					System.out.println(preparedStmntUpateCount
							+ " rows successfully updated!");
				}

			} catch (SQLException sqle) {
				System.out.println("SQL Exception: " + sqle);
				return preparedStmntUpateCount;
			}
		} catch (SQLIntegrityConstraintViolationException sqlicve) {
			System.out.println("SQL Integrity Constraint Exception: "
					+ sqlicve.getErrorCode());
			sqlicve.printStackTrace();
			// should return 0
			return preparedStmntUpateCount;
			// System.exit(-1);
		} catch (SQLException ex) {
			System.out.println("SQL Exception: " + ex);
			ex.printStackTrace();
			// should return 0
			return preparedStmntUpateCount;
		}

		return preparedStmntUpateCount;
	}

	/**
	 * Adds the SectionOrder object to the SECTION table.
	 * 
	 * @param sectionOrder
	 *            -The object we will parse for information we need to store in
	 *            the SECTION table,
	 * @return integer -The number of rows updated in the SECTION table. If 1
	 *         section is associated with the order then this number is 1. If
	 *         there are two, then two rows will have been added, and this
	 *         number is 2, etc.
	 */
	public int addToSectionTable(SectionOrder sectionOrder) {

		/*
		 * Used to return a value indicating how many rows were added.
		 */
		int preparedStmntUpateCount = 0;

		/*
		 * Go through sectionNumberList and add each section to the SECTION
		 * table.
		 */
		for (String sectionString : sectionOrder.getSectionNumberList()) {

			// Initialize the prepared statement.
			try {
				stmt = con.createStatement();
				String sql = "INSERT INTO SECTION (SECTIONNUMBER, SECTIONORDER_ID) "
						+ "VALUES (?, ?)";
				preparedSqlStatement = con.prepareStatement(sql);
			} catch (SQLException ex) {
				System.out.println("SQL Exception: " + ex);
				ex.printStackTrace();
			}

			// Sets values in prepared statement.
			try {
				preparedSqlStatement.setString(1, sectionString);
				// Saves current PK from SECTIONORDER as FK in this table
				preparedSqlStatement.setInt(2, sectionOrderPrimaryKey);
			} catch (SQLException ex) {
				System.out.println("SQL Exception: " + ex);
				ex.printStackTrace();
			}

			// Execute prepared statement.
			try {
				preparedSqlStatement.executeUpdate();

				try {
					preparedStmntUpateCount += preparedSqlStatement
							.getUpdateCount();

				} catch (SQLException sqle) {
					System.out.println("SQL Exception: " + sqle);
					return preparedStmntUpateCount;
				}
			} catch (SQLIntegrityConstraintViolationException sqlicve) {
				System.out.println("SQL Integrity Constraint Exception: "
						+ sqlicve.getErrorCode());
				sqlicve.printStackTrace();
				// should return 0
				return preparedStmntUpateCount;
				// System.exit(-1);
			} catch (SQLException ex) {
				System.out.println("SQL Exception: " + ex);
				ex.printStackTrace();
				// should return 0
				return preparedStmntUpateCount;
			}
		}

		if (preparedStmntUpateCount == 1) {
			System.out.println(preparedStmntUpateCount
					+ " row successfully updated!");
		} else {
			System.out.println(preparedStmntUpateCount
					+ " rows successfully updated!");
		}

		return preparedStmntUpateCount;
	}

	/**
	 * Adds the SectionOrder object to the BOOK table.
	 * 
	 * @param sectionOrder
	 *            -The object we will parse for information we need to store in
	 *            the BOOK table,
	 * @return integer -The number of rows updated in the BOOK table. If 1 book
	 *         is associated with the order then this number is 1. If there are
	 *         two, then two rows will have been added, and this number is 2,
	 *         etc.
	 */
	public int addToBookTable(SectionOrder sectionOrder) {

		/*
		 * Used to return a value indicating how many rows were added.
		 */
		int preparedStmntUpateCount = 0;

		/*
		 * Go through bookList and add each book to the BOOK table
		 */
		for (Book book : sectionOrder.getBookList()) {

			int resellable;
			int newestEditionOnly;
			/*
			 * Stores false as 0 and true as 1 since SQlite has no boolean data
			 * type.
			 */
			if (book.isResellable()) {
				resellable = 1;
			} else {
				resellable = 0;
			}
			/*
			 * Stores false as 0 and true as 1 since SQlite has no boolean data
			 * type.
			 */
			if (book.isNewestBookEditionUseOnly()) {
				newestEditionOnly = 1;
			} else {
				newestEditionOnly = 0;
			}

			// Initialize the prepared statement.
			try {
				stmt = con.createStatement();
				String sql = "INSERT INTO BOOK (BOOKUSEREQUIREMENT,  ISBN, STUDENTCOST, RESELLABLE, TITLE, AUTHOR, PUBLISHER, SECTIONORDER_ID, NEWESTBOOKEDITIONUSEONLY) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				preparedSqlStatement = con.prepareStatement(sql);
			} catch (SQLException ex) {
				System.out.println("SQL Exception: " + ex);
				ex.printStackTrace();
			}

			// Sets values in prepared statement.
			try {
				preparedSqlStatement.setString(1, book.getBookUseRequirement()
						.toString());
				preparedSqlStatement.setString(2,
						book.getInternationalStandardBookNumber());
				preparedSqlStatement.setDouble(3, book.getStudentCost());
				// 0 for false, 1 for true
				preparedSqlStatement.setInt(4, resellable);
				preparedSqlStatement.setString(5, book.getTitle());
				preparedSqlStatement.setString(6, book.getAuthor());
				preparedSqlStatement.setString(7, book.getPublisher());
				// Saves current PK from SECTIONORDER as FK in this table
				preparedSqlStatement.setInt(8, sectionOrderPrimaryKey);
				// 0 for false, 1 for true
				preparedSqlStatement.setInt(9, newestEditionOnly);
			} catch (SQLException ex) {
				System.out.println("SQL Exception: " + ex);
				ex.printStackTrace();
			}

			// Execute prepared statement.
			try {
				preparedSqlStatement.executeUpdate();

				try {
					preparedStmntUpateCount += preparedSqlStatement
							.getUpdateCount();

				} catch (SQLException sqle) {
					System.out.println("SQL Exception: " + sqle);
					return preparedStmntUpateCount;
				}
			} catch (SQLIntegrityConstraintViolationException sqlicve) {
				System.out.println("SQL Integrity Constraint Exception: "
						+ sqlicve.getErrorCode());
				sqlicve.printStackTrace();
				// should return 0
				return preparedStmntUpateCount;
				// System.exit(-1);
			} catch (SQLException ex) {
				System.out.println("SQL Exception: " + ex);
				ex.printStackTrace();
				// should return 0
				return preparedStmntUpateCount;
			}
		}

		if (preparedStmntUpateCount == 1) {
			System.out.println(preparedStmntUpateCount
					+ " row successfully updated!");
		} else {
			System.out.println(preparedStmntUpateCount
					+ " rows successfully updated!");
		}

		return preparedStmntUpateCount;
	}

	/**
	 * Used to get the PK of the current SectionOrder object from the
	 * SECTIONORDER table. This is used after the successful addition of a row
	 * into the SECTIONORDER table.
	 */
	public int findSectionOrderPK() {
		int result = 0;
		String queryString = "SELECT last_insert_rowid()";
		int primaryKey = 0;

		try {

			stmt = con.createStatement();
			rs = stmt.executeQuery(queryString);
			// finds PK
			while (rs.next()) {
				primaryKey = rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e);
			e.printStackTrace();
		}

		result = primaryKey;

		return result;
	}

	/**
	 * Creates the database and populates it with the tables needed for this
	 * project.
	 */
	public static void createSectionOrderStorageDatabaseAndTables() {
		/*
		 * Used for database connection.
		 */
		Connection c = null;
		/*
		 * Used for SQL statements.
		 */
		Statement stmt = null;
		/*
		 * Used for storing the text of a SQL statement.
		 */
		String sql = "";

		try {
			/*
			 * Makes sure the class that implements the JDBC driver for SQLite
			 * is loaded and ready for use.
			 */
			Class.forName("org.sqlite.JDBC");
			/*
			 * Sets up connection to bookOrderApp.db. The .db file is created if
			 * it is not already present.
			 */
			c = DriverManager.getConnection("jdbc:sqlite:bookOrderApp.db");
			System.out.println("Opened database successfully");

			// Create SECTIONORDER Table
			stmt = c.createStatement();
			sql = "CREATE TABLE SECTIONORDER "
					+ "(SECTIONORDER_ID INTEGER PRIMARY KEY   AUTOINCREMENT, "
					+ " DEPARTMENT           		TEXT    , "
					+ " COURSE           			TEXT    , "
					+ " SEMESTER           			TEXT    , "
					+ " MONTH_SEMESTERSTART         INT    , "
					+ " DAY_SEMESTERSTART           INT    , "
					+ " YEAR_SEMESTERSTART          INT    , "
					+ " MONTH_SEMESTEREND           INT    , "
					+ " DAY_SEMESTEREND          	INT    , "
					+ " YEAR_SEMESTEREND           	INT    , "
					+ " ANTICIPATEDENROLLMENT       INT)";

			stmt.executeUpdate(sql);
			System.out.println("Create SECTIONORDER Table success!");

			// Create PROGRAMCHAIR Table
			stmt = c.createStatement();
			sql = "CREATE TABLE PROGRAMCHAIR "
					+ "(PROGRAMCHAIR_ID INTEGER PRIMARY KEY   AUTOINCREMENT, "
					+ " FIRSTNAME           TEXT    , "
					+ " LASTNAME           	TEXT    , "
					+ " PHONE           	TEXT    , "
					+ " EMAIL           	TEXT    , "
					+ " SECTIONORDER_ID     INT     NOT NULL, "
					+ "FOREIGN KEY(SECTIONORDER_ID) REFERENCES SECTIONORDER(SECTIONORDER_ID))";
			stmt.executeUpdate(sql);
			System.out.println("Create PROGRAMCHAIR Table success!");

			// Create INSTRUCTOR Table
			stmt = c.createStatement();
			sql = "CREATE TABLE INSTRUCTOR "
					+ "(INSTRUCTOR_ID INTEGER PRIMARY KEY   AUTOINCREMENT, "
					+ " FIRSTNAME           TEXT    , "
					+ " LASTNAME           	TEXT    , "
					+ " PHONE           	TEXT    , "
					+ " EMAIL           	TEXT    , "
					+ " SECTIONORDER_ID     INT     NOT NULL, "
					+ "FOREIGN KEY(SECTIONORDER_ID) REFERENCES SECTIONORDER(SECTIONORDER_ID))";
			stmt.executeUpdate(sql);
			System.out.println("Create INSTRUCTOR Table success!");

			// Create BOOK table
			stmt = c.createStatement();
			sql = "CREATE TABLE BOOK "
					+ "(BOOK_ID INTEGER PRIMARY KEY   AUTOINCREMENT, "
					+ " BOOKUSEREQUIREMENT           TEXT    NOT NULL, "
					+ " ISBN           				TEXT    , "
					+ " STUDENTCOST         		NUMERIC , "
					+ " RESELLABLE     				INT      NOT NULL, "
					+ " TITLE           			TEXT    , "
					+ " AUTHOR           			TEXT    , "
					+ " PUBLISHER           		TEXT    , "
					+ " SECTIONORDER_ID     INT     NOT NULL, "
					+ " NEWESTBOOKEDITIONUSEONLY    INT, "
					+ " FOREIGN KEY(SECTIONORDER_ID) REFERENCES SECTIONORDER(SECTIONORDER_ID))";
			stmt.executeUpdate(sql);
			System.out.println("Create BOOK table success!");

			// Create SECTION table
			stmt = c.createStatement();
			sql = "CREATE TABLE SECTION "
					+ "(SECTION_ID 			INTEGER PRIMARY KEY   AUTOINCREMENT, "
					+ " SECTIONNUMBER       TEXT    	NOT NULL, "
					+ " SECTIONORDER_ID     INTEGER     	NOT NULL, "
					+ "FOREIGN KEY(SECTIONORDER_ID) REFERENCES SECTIONORDER(SECTIONORDER_ID))";
			stmt.executeUpdate(sql);
			System.out.println("Create SECTION table success!");
			stmt.close();
			c.close();

		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out
				.println("All tables created successfully and connections closed.");

	}

	/**
	 * Find all resalable books.
	 * 
	 * @return List<Book> -that are resalable
	 */
	public List<Book> findResellableBooks() {
		List<Book> bookList = new ArrayList<>();
		List<SectionOrder> sectionOrderList = findAllSectionOrders();

		// First well cycle through every section order
		for (SectionOrder sectionOrder : sectionOrderList) {
			// now well exam every book in each section orders book list
			for (Book book : sectionOrder.getBookList()) {
				// If that book is resalable well add it to the list
				if (book.isResellable()) {
					bookList.add(book);
				}
			}
		}

		return bookList;

	}

	/**
	 * Find all non-resalable books.
	 * 
	 * @return List<Book> -that are not resalable
	 */
	public List<Book> findNonResellableBooks() {
		List<Book> bookList = new ArrayList<>();
		List<SectionOrder> sectionOrderList = findAllSectionOrders();

		// First well cycle through every section order
		for (SectionOrder sectionOrder : sectionOrderList) {
			// now well exam every book in each section orders book list
			for (Book book : sectionOrder.getBookList()) {
				// If that book is NOT resalable well add it to the list
				if (!book.isResellable()) {
					bookList.add(book);
				}
			}
		}

		return bookList;
	}

	/**
	 * Find all resalable books in a given year.
	 * 
	 * @return List<Book> -that are resalable in the given year
	 */
	public List<Book> findResellableBooksByYear(int year) {
		List<Book> bookList = new ArrayList<>();
		List<SectionOrder> sectionOrderList = findAllSectionOrders();

		// First well cycle through every section order
		for (SectionOrder sectionOrder : sectionOrderList) {
			// now well check if the section order is from the given year
			int sectionOrderYear = sectionOrder.getSemesterStart().get(
					Calendar.YEAR);
			if (sectionOrderYear == year) {
				// now well exam every book in each section orders book list
				for (Book book : sectionOrder.getBookList()) {
					// If that book is resalable well add it to the list
					if (book.isResellable()) {
						bookList.add(book);
					}
				}
			}
		}

		return bookList;

	}

	/**
	 * Find all non-resalable books in a given year.
	 * 
	 * @return List<Book> -that are not resalable in the given year
	 */
	public List<Book> findNonResellableBooksBooksByYear(int year) {
		List<Book> bookList = new ArrayList<>();
		List<SectionOrder> sectionOrderList = findAllSectionOrders();

		// First well cycle through every section order
		for (SectionOrder sectionOrder : sectionOrderList) {
			// now well check if the section order is from the given year
			int sectionOrderYear = sectionOrder.getSemesterStart().get(
					Calendar.YEAR);
			if (sectionOrderYear == year) {
				// now well exam every book in each section orders book list
				for (Book book : sectionOrder.getBookList()) {
					// If that book is NOT resalable well add it to the list
					if (!book.isResellable()) {
						bookList.add(book);
					}
				}
			}
		}

		return bookList;
	}

	/**
	 * Returns a list of all the section orders in the database.
	 * 
	 * @return ArrayList<SectionOrder> -representing all the section orders in
	 *         the database.
	 */
	public ArrayList<SectionOrder> findAllSectionOrders() {
		ArrayList<SectionOrder> sectionOrderList = new ArrayList<>();
		String queryString = "SELECT * FROM SECTIONORDER";

		try {
			initializeDatabaseConnection();

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(queryString);

			while (rs.next()) {
				// First we get all the info we can from the SECTIONORDER table
				// We need this sectionOrderID to get the related info from the
				// other tables
				int sectionOrderID = rs.getInt("sectionorder_id");
				String department = rs.getString("department");
				String course = rs.getString("course");
				String semester = rs.getString("semester");
				int monthSemesterStart = rs.getInt("month_semesterstart");
				int daySemesterStart = rs.getInt("day_semesterstart");
				int yearSemesterStart = rs.getInt("year_semesterstart");
				int monthSemesterEnd = rs.getInt("month_semesterend");
				int daySemesterEnd = rs.getInt("day_semesterend");
				int yearSemesterEnd = rs.getInt("year_semesterend");
				int anticipatedEnrollment = rs.getInt("ANTICIPATEDENROLLMENT");
				// Create GregorianCalendat semesterStart object needed to
				// create a SectionOrder
				GregorianCalendar semesterStart = new GregorianCalendar(
						yearSemesterStart, monthSemesterStart, daySemesterStart);
				// Create GregorianCalendat semesterEnd object needed to create
				// a SectionOrder
				GregorianCalendar semesterEnd = new GregorianCalendar(
						yearSemesterEnd, monthSemesterEnd, daySemesterEnd);
				// Create ProgramChair related to this SectionOrder
				ProgramChair programChair = findProgramChairBySectionID(sectionOrderID);
				// Create list of Instructors related to this SectionOrder
				ArrayList<Instructor> instructorList = findInstructorListBySectionID(sectionOrderID);
				// Create list of Books related to this SectionOrder
				ArrayList<Book> bookList = findBookListBySectionID(sectionOrderID);
				// Create list of Strings that represent the section numbers
				// related to this SectionOrder
				ArrayList<String> sectionList = findSectionListBySectionID(sectionOrderID);
				// Create the SectionOrder object from all the queried info
				SectionOrder tempSectionOrder = new SectionOrder(department,
						course, bookList, sectionList, semester, semesterStart,
						semesterEnd, programChair, instructorList,
						anticipatedEnrollment);

				sectionOrderList.add(tempSectionOrder);
			}

			disconnectDatabaseConnection();
			return sectionOrderList;

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e);
			e.printStackTrace();
			return sectionOrderList;
		}

	}

	/**
	 * Used to find a ProgramChair object by its sectionOrderID.
	 * 
	 * @param sectionOrderID
	 *            -the sectionOrderID you want to use to find the ProgramChair
	 *            associated with it.
	 * @return ProgramChair -the ProgramChair that is associated with the given
	 *         sectionOrderID
	 */
	public ProgramChair findProgramChairBySectionID(int sectionOrderID) {
		ProgramChair programChair = null;
		String queryString = "SELECT * FROM PROGRAMCHAIR WHERE SECTIONORDER_ID = "
				+ sectionOrderID + "";

		try {
			// initializeDatabaseConnection();

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(queryString);

			while (rs.next()) {
				String firstName = rs.getString("FIRSTNAME");
				String lastName = rs.getString("LASTNAME");
				String phone = rs.getString("PHONE");
				String email = rs.getString("EMAIL");
				programChair = new ProgramChair(firstName, lastName, phone,
						email);
			}

			// disconnectDatabaseConnection();
			return programChair;

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e);
			e.printStackTrace();
			return programChair;
		}
	}

	/**
	 * Returns a list of all the Instructor objects associated with a given
	 * sectionOrderID.
	 * 
	 * @param sectionOrderID
	 *            -the sectionOrderID you want to use to find the Instructor
	 *            associated with it.
	 * @return ArrayList<Instructor> -the Instructor that is associated with the
	 *         given sectionOrderID
	 */
	public ArrayList<Instructor> findInstructorListBySectionID(
			int sectionOrderID) {
		ArrayList<Instructor> instructorList = new ArrayList<>();
		String queryString = "SELECT * FROM INSTRUCTOR WHERE SECTIONORDER_ID = "
				+ sectionOrderID + "";

		try {
			// initializeDatabaseConnection();

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(queryString);

			while (rs.next()) {
				String firstName = rs.getString("FIRSTNAME");
				String lastName = rs.getString("LASTNAME");
				String phone = rs.getString("PHONE");
				String email = rs.getString("EMAIL");
				Instructor instructor = new Instructor(firstName, lastName,
						phone, email);
				instructorList.add(instructor);
			}

			// disconnectDatabaseConnection();
			return instructorList;

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e);
			e.printStackTrace();
			return instructorList;
		}
	}

	/**
	 * Returns a list of all the Book objects associated with a given
	 * sectionOrderID.
	 * 
	 * @param sectionOrderID
	 *            -the sectionOrderID you want to use to find the Book
	 *            associated with it.
	 * @return ArrayList<Book> -the Book that is associated with the given
	 *         sectionOrderID
	 */
	public ArrayList<Book> findBookListBySectionID(int sectionOrderID) {
		ArrayList<Book> bookList = new ArrayList<>();
		String queryString = "SELECT * FROM BOOK WHERE SECTIONORDER_ID = "
				+ sectionOrderID + "";

		try {
			// initializeDatabaseConnection();

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(queryString);

			while (rs.next()) {
				String internationalStandardBookNumber = rs.getString("ISBN");
				double studentCost = rs.getDouble("STUDENTCOST");
				String title = rs.getString("TITLE");
				String author = rs.getString("AUTHOR");
				String publisher = rs.getString("PUBLISHER");
				boolean resalable;
				boolean newestBookEditionOnly;
				Book.BookUseRequirement bookUseRequirement;

				// Convert 1 or 0 to true or false for resalable variable.
				if (rs.getInt("RESELLABLE") == 1) {
					resalable = true;
				} else {
					resalable = false;
				}
				// Convert 1 or 0 to true or false for newestBookEditionOnly
				// variable.
				if (rs.getInt("NEWESTBOOKEDITIONUSEONLY") == 1) {
					newestBookEditionOnly = true;
				} else {
					newestBookEditionOnly = false;
				}
				// Convert String representation of book use requirement to a
				// BookUseRequirement enum.
				if (rs.getString("BOOKUSEREQUIREMENT").equals("OPTIONAL")) {
					bookUseRequirement = BookUseRequirement.OPTIONAL;
				} else if (rs.getString("BOOKUSEREQUIREMENT").equals(
						"RECOMMENDED")) {
					bookUseRequirement = BookUseRequirement.RECOMMENDED;
				} else {
					bookUseRequirement = BookUseRequirement.REQUIRED;
				}

				Book book = new Book(internationalStandardBookNumber,
						studentCost, resalable, title, author, publisher,
						bookUseRequirement, newestBookEditionOnly);
				bookList.add(book);
			}

			// disconnectDatabaseConnection();
			return bookList;

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e);
			e.printStackTrace();
			return bookList;
		}
	}

	/**
	 * Returns a list of all the String objects representing sections associated
	 * with a given sectionOrderID.
	 * 
	 * @param sectionOrderID
	 *            -the sectionOrderID you want to use to find the sections
	 *            associated with it.
	 * @return ArrayList<String> -the String objects representing sections
	 *         associated with a given sectionOrderID.
	 */
	public ArrayList<String> findSectionListBySectionID(int sectionOrderID) {
		ArrayList<String> sectionList = new ArrayList<>();
		String queryString = "SELECT * FROM SECTION WHERE SECTIONORDER_ID = "
				+ sectionOrderID + "";

		try {
			// initializeDatabaseConnection();

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(queryString);

			while (rs.next()) {
				String section = rs.getString("SECTIONNUMBER");
				sectionList.add(section);
			}

			// disconnectDatabaseConnection();
			return sectionList;

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e);
			e.printStackTrace();
			return sectionList;
		}
	}

	/**
	 * Find total cost of required books over the years.
	 * 
	 * @return List<String> -representing Year and Cost info about books.
	 */
	/*
	 * **The following query achieves this!
	 * 
	 * SELECT YEAR_SEMESTERSTART, SUM(STUDENTCOST) AS "TOTAL" FROM SECTIONORDER
	 * JOIN BOOK USING (SECTIONORDER_ID) WHERE BOOKUSEREQUIREMENT = 'REQUIRED'
	 * GROUP BY YEAR_SEMESTERSTART ORDER BY YEAR_SEMESTERSTART;
	 */
	public ArrayList<String> findCostOfBooksOverTime() {
		ArrayList<String> bookList = new ArrayList<>();
		String queryString = "SELECT YEAR_SEMESTERSTART, SUM(STUDENTCOST) AS \"TOTAL\""
				+ "FROM SECTIONORDER JOIN BOOK USING (SECTIONORDER_ID) "
				+ "WHERE BOOKUSEREQUIREMENT = 'REQUIRED' "
				+ "GROUP BY YEAR_SEMESTERSTART "
				+ "ORDER BY YEAR_SEMESTERSTART";

		try {
			initializeDatabaseConnection();

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(queryString);

			while (rs.next()) {
				int year = rs.getInt("YEAR_SEMESTERSTART");
				double studentCost = rs.getDouble("TOTAL");
				bookList.add(year + "\t" + studentCost);
			}

			disconnectDatabaseConnection();
			return bookList;

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e);
			e.printStackTrace();
			return bookList;
		}
	}

	/**
	 * A more useful way to find total cost of required books over the years.
	 * 
	 * @return List<StringBuilder> -representing Year and Cost info about books.
	 */
	/*
	 * **The following query achieves this!
	 * 
	 * SELECT YEAR_SEMESTERSTART, SUM(STUDENTCOST) AS "TOTAL" FROM SECTIONORDER
	 * JOIN BOOK USING (SECTIONORDER_ID) WHERE BOOKUSEREQUIREMENT = 'REQUIRED'
	 * GROUP BY YEAR_SEMESTERSTART ORDER BY YEAR_SEMESTERSTART;
	 */
	public List<StringBuilder> findCostOfBooksOverTimeV2() {
		ArrayList<StringBuilder> bookList = new ArrayList<>();
		String queryString = "SELECT YEAR_SEMESTERSTART, SUM(STUDENTCOST) AS \"TOTAL\""
				+ "FROM SECTIONORDER JOIN BOOK USING (SECTIONORDER_ID) "
				+ "WHERE BOOKUSEREQUIREMENT = 'REQUIRED' "
				+ "GROUP BY YEAR_SEMESTERSTART "
				+ "ORDER BY YEAR_SEMESTERSTART";

		try {
			initializeDatabaseConnection();

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(queryString);

			while (rs.next()) {
				StringBuilder tempBookInfo = new StringBuilder();
				int year = rs.getInt("YEAR_SEMESTERSTART");
				tempBookInfo.append(year + "\n");
				double studentCost = rs.getDouble("TOTAL");
				tempBookInfo.append(studentCost + "\n");
				bookList.add(tempBookInfo);
			}

			disconnectDatabaseConnection();
			return bookList;

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e);
			e.printStackTrace();
			return bookList;
		}
	}

}
