package datalayer;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import businesslayer.Book;
import businesslayer.Instructor;
import businesslayer.ProgramChair;
import businesslayer.SectionOrder;

/**
 * Used to test the SectionOrderStorage class.
 */
public class SectionOrderStorageTest {

	/**
	 * The department that created the section order for books.
	 */
	private String department1 = "Department Alpha";
	/**
	 * The course the section order was created for.
	 */
	private String course1 = "Course Golf";
	/**
	 * 
	 */
	private List<Book> bookList1 = new ArrayList<Book>();
	/**
	 * A book associated with this order
	 */
	private Book book1 = new Book("v123abc", 29.99, true, "Test Book Name",
			"Test Author", "Test Publisher", Book.BookUseRequirement.REQUIRED,
			false);
	/**
	 * The course sections that this order will cover.
	 */
	private List<String> sectionNumberList1 = Arrays.asList("Section Charlie",
			"Section Delta", "Section Echo");
	/**
	 * The semester the section order was created for.
	 */
	private String semester1 = "Fall";
	/**
	 * The start date for the semester.
	 */
	private GregorianCalendar semesterStart = new GregorianCalendar(2014, 01,
			01);
	/**
	 * The end date for the semester.
	 */
	private GregorianCalendar semesterEnd = new GregorianCalendar(2014, 05, 05);
	/**
	 * The program chair for the course program.
	 */
	private ProgramChair programChair = new ProgramChair("test", "test",
			"test", "test");
	/**
	 * One of the instructors this order belongs too.
	 */
	private Instructor ins1 = new Instructor("test", "test", "test", "test");
	/**
	 * The instructors of the course section.
	 */
	List<Instructor> instructorList = new ArrayList<Instructor>();
	/**
	 * Anticipated number of students who will enroll in the course section.
	 */
	private int anticipatedEnrollment = 100;
	/**
	 * Section order test object
	 */
	private SectionOrder testSectionOrderObject1;

	/**
	 * Get test data ready for testing.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		instructorList.add(ins1);
		bookList1.add(book1);

		testSectionOrderObject1 = new SectionOrder(department1, course1,
				bookList1, sectionNumberList1, semester1, semesterStart,
				semesterEnd, programChair, instructorList,
				anticipatedEnrollment);
	}

	/**
	 * Tests the static addSectionOrderToDatabase method by trying to pass in a
	 * SectionOrder object and trying to save it in db. If the method returns 7
	 * we know it was successful.
	 */
	@Test
	public void testAddSectionOrderToDatabase() {
		/*
		 * Judging by the inputed info for our SectionOrder object named
		 * 'testSectionOrderObject1' : We expect 7 rows to be added in total. 1
		 * in SECTIONORDER table, 1 in BOOK table, 1 in INSTRUCTOR table, 3 in
		 * SECTION table, and 1 in PROGRAMCHAIR table.
		 */
		int expectedUpdatedRows = 7;

		// 'false' in the isCommit parameter makes it so this isn't actually
		// COMMITED to the database every time the test is run.
		assertEquals(
				expectedUpdatedRows,
				SectionOrderStorage.getSectionOrderStorage()
						.addSectionOrderToDatabase(false,
								testSectionOrderObject1));
	}

	/**
	 * Tests the initializeDatabaseConnection method. If it returns true, then
	 * the connection is working.
	 */
	@Test
	public void testInitializeDatabaseConnection() {
		assertEquals(true, SectionOrderStorage.getSectionOrderStorage()
				.initializeDatabaseConnection());
	}

	/**
	 * Tests the disconnectDatabaseConnection method. If it returns true, then
	 * the connection has been closed.
	 */
	@Test
	public void testDisconnectDatabaseConnection() {
		// first it should be initialized
		SectionOrderStorage.getSectionOrderStorage()
				.initializeDatabaseConnection();
		assertEquals(true, SectionOrderStorage.getSectionOrderStorage()
				.disconnectDatabaseConnection());
	}

}
