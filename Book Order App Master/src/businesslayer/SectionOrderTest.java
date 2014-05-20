package businesslayer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Used to test a SectionOrder object/
 */
public class SectionOrderTest {

	/**
	 * The department that created the section order for books.
	 */
	private String department1 = "Department Alpha";
	private String department2 = "Department Beta";
	/**
	 * The course the section order was created for.
	 */
	private String course1 = "Course Golf";
	private String course2 = "Course Hotel";
	/**
	 * List of books used in the section order.
	 */
	private List<Book> bookList = new ArrayList<Book>();

	/**
	 * The course sections that this order will cover.
	 */
	private List<String> sectionNumberList1 = Arrays.asList("Section Charlie",
			"Section Delta", "Section Echo");
	private List<String> sectionNumberList2 = Arrays.asList("Section Foxtrot");
	/**
	 * The semester the section order was created for.
	 */
	private String semester1 = "Fall";
	private String semester2 = "Spring";
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
	private ProgramChair programChair;
	/**
	 * The instructors of the course section.
	 */
	private List<Instructor> instructorList;
	/**
	 * Anticipated number of students who will enroll in the course section.
	 */
	private int anticipatedEnrollment = 100;

	private SectionOrder TestSectionOrderObject1; // Should match #2
	private SectionOrder TestSectionOrderObject2; // Should match #1
	private SectionOrder TestSectionOrderObject3; // Should not match any
	private SectionOrder TestSectionOrderObject4; // Should not match any
	private SectionOrder TestSectionOrderObject5; // Should not match any
	private SectionOrder TestSectionOrderObject6; // Should not match any

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		TestSectionOrderObject1 = new SectionOrder(department1, course1,
				bookList, sectionNumberList1, semester1, semesterStart,
				semesterEnd, programChair, instructorList,
				anticipatedEnrollment);

		TestSectionOrderObject2 = new SectionOrder(department1, course1,
				bookList, sectionNumberList1, semester1, semesterStart,
				semesterEnd, programChair, instructorList,
				anticipatedEnrollment);

		TestSectionOrderObject3 = new SectionOrder(department2, course1,
				bookList, sectionNumberList1, semester1, semesterStart,
				semesterEnd, programChair, instructorList,
				anticipatedEnrollment);

		TestSectionOrderObject4 = new SectionOrder(department1, course2,
				bookList, sectionNumberList1, semester1, semesterStart,
				semesterEnd, programChair, instructorList,
				anticipatedEnrollment);

		TestSectionOrderObject5 = new SectionOrder(department1, course1,
				bookList, sectionNumberList2, semester1, semesterStart,
				semesterEnd, programChair, instructorList,
				anticipatedEnrollment);

		TestSectionOrderObject6 = new SectionOrder(department1, course1,
				bookList, sectionNumberList1, semester2, semesterStart,
				semesterEnd, programChair, instructorList,
				anticipatedEnrollment);
	}

	@After
	public void tearDown() throws Exception {

		TestSectionOrderObject1 = null;
		TestSectionOrderObject2 = null;
		TestSectionOrderObject3 = null;
		TestSectionOrderObject4 = null;
		TestSectionOrderObject5 = null;
		TestSectionOrderObject6 = null;

		department1 = null;
		course1 = null;
		sectionNumberList1 = null;
		semester1 = null;
		department2 = null;
		course2 = null;
		sectionNumberList2 = null;
		semester2 = null;
		semesterStart = null;
		semesterEnd = null;
		programChair = null;
		instructorList = null;
		anticipatedEnrollment = -1;
	}

	@Test
	public void testEqualsObject() {

		assertTrue("Test Section Order 1 and 2 should equal each other",
				TestSectionOrderObject1.equals(TestSectionOrderObject2));
		assertFalse(
				"Test Section Order 1 and 3 should not equal each other - Department",
				TestSectionOrderObject1.equals(TestSectionOrderObject3));
		assertFalse(
				"Test Section Order 1 and 4 should not equal each other - Course",
				TestSectionOrderObject1.equals(TestSectionOrderObject4));
		assertFalse(
				"Test Section Order 1 and 5 should not equal each other - Section Number List",
				TestSectionOrderObject1.equals(TestSectionOrderObject5));
		assertFalse(
				"Test Section Order 1 and 6 should not equal each other - Semester",
				TestSectionOrderObject1.equals(TestSectionOrderObject6));

	}

}
