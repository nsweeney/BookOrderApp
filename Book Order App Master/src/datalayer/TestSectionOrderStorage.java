package datalayer;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import businesslayer.Book;
import businesslayer.Instructor;
import businesslayer.ProgramChair;
import businesslayer.SectionOrder;

public class TestSectionOrderStorage {

	public static void main(String[] args) {

		/**
		 * The list of books associated with the section order.
		 */
		List<Book> bookList1 = new ArrayList<Book>();
		/**
		 * The first book you want to be stored in bookList1
		 */
		Book book1 = new Book("v123abc", 59.99, false, "Tigers Way",
				"Test Author", "Test Publisher",
				Book.BookUseRequirement.REQUIRED, false);
		/**
		 * The second book you want to be stored in bookList1
		 */
		Book book2 = new Book("v456456456", 29.99, true, "Zen Golf",
				"Test2 Author2", "Test2 Publisher2",
				Book.BookUseRequirement.RECOMMENDED, true);
		// add books to bookList1
		bookList1.add(book1);
		bookList1.add(book2);
		/**
		 * The department the section order was created for.
		 */
		String department1 = "Department Alpha";
		/**
		 * The course the section order was created for.
		 */
		String course1 = "Course Golf";
		/**
		 * The course sections that this order will cover.
		 */
		List<String> sectionNumberList1 = new ArrayList<>();
		// add sections to sectionNumberList1
		sectionNumberList1.add("101a4");
		sectionNumberList1.add("300vv6");
		/**
		 * The semester the section order was created for.
		 */
		String semester1 = "Spring";
		/**
		 * The start date for the semester. 01/01/1999
		 */
		GregorianCalendar semesterStart = new GregorianCalendar(2008, 01, 01);
		/**
		 * The end date for the semester. 05/05/1999
		 */
		GregorianCalendar semesterEnd = new GregorianCalendar(2008, 05, 05);
		/**
		 * The program chair for the course program.
		 */
		ProgramChair programChair = new ProgramChair("Golden", "Bear",
				"407-123-1231", "jack@nicklaus.com");
		/**
		 * The instructors of the course section.
		 */
		List<Instructor> instructorList = new ArrayList<Instructor>();
		/**
		 * An instructor associated with this section order.
		 */
		Instructor ins1 = new Instructor("Jordan", "Spieth", "321-321-3211",
				"emailAddy@golf.com");
		/**
		 * The second instructor associated with this section order.
		 */
		Instructor ins2 = new Instructor("Bubba", "Watson", "123-123-1233",
				"testEmail.com");
		// add instructors to instructorList
		instructorList.add(ins1);
		instructorList.add(ins2);
		/**
		 * Anticipated number of students who will enroll in the course section.
		 */
		int anticipatedEnrollment = 100;

		/**
		 * A section order made up of all the info above.
		 */
		SectionOrder testSectionOrderObject1 = new SectionOrder(department1,
				course1, bookList1, sectionNumberList1, semester1,
				semesterStart, semesterEnd, programChair, instructorList,
				anticipatedEnrollment);

		// create the database
		// SectionOrderStorage.createSectionOrderStorageDatabaseAndTables();

		// This will add testSectionOrderObject1 to bookOrderApp.db
		testSectionOrderObject1.addToDatabase();

		// test finding all the section orders in db
		// ****I added toString method to SectionOrder
		// here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		List<SectionOrder> sectionOrderList = SectionOrderStorage
				.getSectionOrderStorage().findAllSectionOrders();
		System.out.println("All section orders:");
		for (SectionOrder sectionOrder : sectionOrderList) {
			System.out.println(sectionOrder);
		}

		// test finding all resalable books
		List<Book> resalableBookList = SectionOrderStorage
				.getSectionOrderStorage().findResellableBooks();
		// Show the resalable books
		System.out.println("All resalable books:");
		for (Book book : resalableBookList) {
			System.out.println(book);
		}

		// test finding all non-resalable books
		List<Book> nonResalableBookList = SectionOrderStorage
				.getSectionOrderStorage().findNonResellableBooks();
		System.out.println("All non-resalable books:");
		// Show the non-resalable books
		for (Book book : nonResalableBookList) {
			System.out.println(book);
		}

		// test finding all resalable books by year
		List<Book> resalableBookListByYear = SectionOrderStorage
				.getSectionOrderStorage().findResellableBooksByYear(2009);
		System.out.println("All resalable books from year 2009:");
		// Show the non-resalable books
		for (Book book : resalableBookListByYear) {
			System.out.println(book);
		}

		// Test outputting total cost of required books by year
		List<String> requiredBookCostsOverTheYears = SectionOrderStorage
				.getSectionOrderStorage().findCostOfBooksOverTime();
		// Show required books total cost over the years
		System.out.println("YEAR\tTOTAL REQUIRED BOOK COST");
		for (String bookInfo : requiredBookCostsOverTheYears) {
			System.out.println(bookInfo);

		}

		// Test another way of outputting total cost of required books by year
		List<StringBuilder> requiredBookCostsOverTheYears2 = SectionOrderStorage
				.getSectionOrderStorage().findCostOfBooksOverTimeV2();
		// Show required books total cost over the years
		System.out.println("YEAR\tTOTAL REQUIRED BOOK COST");
		for (StringBuilder bookInfo : requiredBookCostsOverTheYears2) {
			String[] lines = bookInfo.toString().split("\\n");
			System.out.println("Year = " + lines[0]);
			System.out.println("Cost = " + lines[1]);

		}

	}
}
