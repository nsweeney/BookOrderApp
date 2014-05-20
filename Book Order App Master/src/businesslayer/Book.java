package businesslayer;

/**
 * Used to store info about a students book used in a Valencia college course.
 */
public class Book {

	/**
	 * Used for setting the 3 various book use requirements.
	 */
	public enum BookUseRequirement {
		REQUIRED, OPTIONAL, RECOMMENDED
	};

	/**
	 * The books ISBN.
	 */
	private String internationalStandardBookNumber;
	/**
	 * Cost of book for student.
	 */
	private double studentCost;
	/**
	 * Signifies whether a student can resell this this.
	 */
	private boolean resellable;
	/**
	 * The books title.
	 */
	private String title;
	/**
	 * The books author.
	 */
	private String author;
	/**
	 * The books publisher.
	 */
	private String publisher;
	/**
	 * Signifies whether the book is required, optional, or recommended.
	 */
	private BookUseRequirement bookUseRequirement;
	/**
	 * Indicates if the books in the section order can be new or used.
	 */
	private boolean newestBookEditionUseOnly;

	/**
	 * Book use constructor. Takes in all the values associated with a Book
	 * object.
	 * 
	 * @param internationalStandardBookNumber
	 *            -books ISBN
	 * @param studentCost
	 *            -cost of book to student
	 * @param resellable
	 *            -whether student can sell book back or not
	 * @param title
	 *            -books title
	 * @param author
	 *            -books author
	 * @param publisher
	 *            -books publisher
	 * @param bookUseRequirement
	 *            -books use requirement
	 */
	public Book(String internationalStandardBookNumber, double studentCost,
			boolean resellable, String title, String author, String publisher,
			BookUseRequirement bookUseRequirement, boolean newestBookEditionOnly) {
		super();
		setInternationalStandardBookNumber(internationalStandardBookNumber);
		setStudentCost(studentCost);
		setResellable(resellable);
		setTitle(title);
		setAuthor(author);
		setPublisher(publisher);
		setBookUseRequirement(bookUseRequirement);
		setNewestBookEditionUseOnly(newestBookEditionOnly);
	}

	public String getInternationalStandardBookNumber() {
		return internationalStandardBookNumber;
	}

	public void setInternationalStandardBookNumber(
			String internationalStandardBookNumber) {
		this.internationalStandardBookNumber = internationalStandardBookNumber;
	}

	public double getStudentCost() {
		return studentCost;
	}

	public void setStudentCost(double studentCost) {
		if (studentCost >= 0) {
			this.studentCost = studentCost;
		} else {
			System.out.println("Please enter a numeric value >= zero.");
		}
	}

	public boolean isResellable() {
		return resellable;
	}

	public void setResellable(boolean resellable) {
		this.resellable = resellable;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public BookUseRequirement getBookUseRequirement() {
		return bookUseRequirement;
	}

	public void setBookUseRequirement(BookUseRequirement bookUseRequirement) {
		this.bookUseRequirement = bookUseRequirement;
	}

	public boolean isNewestBookEditionUseOnly() {
		return newestBookEditionUseOnly;
	}

	public void setNewestBookEditionUseOnly(boolean newestBookEditionUseOnly) {
		this.newestBookEditionUseOnly = newestBookEditionUseOnly;
	}

	/**
	 * Overridden toString method used to display info about a Book object.
	 */
	@Override
	public String toString() {
		return "Book [internationalStandardBookNumber="
				+ internationalStandardBookNumber + ", studentCost="
				+ studentCost + ", resellable=" + resellable + ", title="
				+ title + ", author=" + author + ", publisher=" + publisher
				+ ", bookUseRequirement=" + bookUseRequirement
				+ ", newestBookEditionUseOnly=" + newestBookEditionUseOnly
				+ "]";
	}

	/**
	 * Overridden equals method used to check for equality between two Book
	 * objects.
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;

		if (!(obj instanceof Book)) {
			return result;
		}
		// Temp. Book object created from argument
		Book paramBook = ((Book) (obj));

		if (getInternationalStandardBookNumber().equals(
				paramBook.getInternationalStandardBookNumber())
				&& getStudentCost() == (paramBook.getStudentCost())
				&& isResellable() == paramBook.isResellable()
				&& getTitle().equals(paramBook.getTitle())
				&& getAuthor().equals(paramBook.getAuthor())
				&& getPublisher().equals(paramBook.getPublisher())
				&& getBookUseRequirement().equals(
						paramBook.getBookUseRequirement())
				&& isNewestBookEditionUseOnly() == paramBook
						.isNewestBookEditionUseOnly()) {
			result = true;
		}

		return result;
	}

}
