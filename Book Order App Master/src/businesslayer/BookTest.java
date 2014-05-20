package businesslayer;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit class created for testing the Book class.
 */
public class BookTest {
	// Test Book objects.
	private Book testBookSame1;
	private Book testBookSame2;
	private Book testBookDifferent;

	/**
	 * Sets up Book objects for testing.
	 */
	private void setUp() {
		/**
		 * This book object is the same as testBookSame2
		 */
		testBookSame1 = new Book("v123abc", 29.99, true, "Test Book Name",
				"Test Author", "Test Publisher",
				Book.BookUseRequirement.REQUIRED, false);
		/**
		 * This book object is the same as testBookSame1
		 */
		testBookSame2 = new Book("v123abc", 29.99, true, "Test Book Name",
				"Test Author", "Test Publisher",
				Book.BookUseRequirement.REQUIRED, false);
		/**
		 * This book object is different compared to testBookSame1 and
		 * testBookSame2
		 */
		testBookDifferent = new Book("Different", 29.99, true,
				"Different Name", "Different Author", "Different Publisher",
				Book.BookUseRequirement.REQUIRED, true);
	}

	/**
	 * Tests that the Book objects that we expect to be equal are indeed equal
	 * and that the two objects we dont expect to be equal are not equal.
	 */
	@Test
	public void testBook() {
		setUp();
		// These 2 books should be equal.
		assertEquals(testBookSame1, testBookSame2);
		// This should return false because these 2 books are not equal.
		assertFalse(testBookSame1.equals(testBookDifferent));
	}

}
