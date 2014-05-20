package businesslayer;

import java.util.GregorianCalendar;
import java.util.List;

import datalayer.SectionOrderStorage;

/**
 * Used to store info about a complete order of books for a course section.
 */
public class SectionOrder {
	/**
	 * The department that created the section order for books.
	 */
	private String department;
	/**
	 * The course the section order was created for.
	 */
	private String course;
	/**
	 * The books needed for this order
	 */
	private List<Book> bookList;
	/**
	 * The course sections that this order will cover.
	 */
	private List<String> sectionNumberList;
	/**
	 * The semester the section order was created for.
	 */
	private String semester;
	/**
	 * The start date for the semester.
	 */
	private GregorianCalendar semesterStart;
	/**
	 * The end date for the semester.
	 */
	private GregorianCalendar semesterEnd;
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
	private int anticipatedEnrollment;

	/**
	 * SectionOrder class constructor.
	 * 
	 * @param department
	 *            (String) - The department that created the section order for
	 *            books.
	 * @param course
	 *            (String) - The course the section order was created for.
	 * @param bookList
	 *            (List<Book>) - The books included in this order.
	 * @param sectionNumberList
	 *            (List<String>) - The course sections that this order will
	 *            cover.
	 * @param semester
	 *            (String) - The semester the section order was created for.
	 * @param semesterStart
	 *            (Date) - The start date for the semester.
	 * @param semesterEnd
	 *            (Date) - The end date for the semester.
	 * @param programChair
	 *            (ProgramChair) - The program chair for the course program.
	 * @param instructorList
	 *            (List<Instructor>) - The instructors of the course section.
	 * @param anticipatedEnrollment
	 *            (int) - Anticipated number of students who will enroll in the
	 *            course section.
	 */
	public SectionOrder(String department, String course, List<Book> bookList,
			List<String> sectionNumberList, String semester,
			GregorianCalendar semesterStart, GregorianCalendar semesterEnd,
			ProgramChair programChair, List<Instructor> instructorList,
			int anticipatedEnrollment) {
		setDepartment(department);
		setCourse(course);
		setBookList(bookList);
		setSectionNumberList(sectionNumberList);
		setSemester(semester);
		setSemesterStart(semesterStart);
		setSemesterEnd(semesterEnd);
		setProgramChair(programChair);
		setInstructorList(instructorList);
		setAnticipatedEnrollment(anticipatedEnrollment);
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	public List<String> getSectionNumberList() {
		return sectionNumberList;
	}

	public void setSectionNumberList(List<String> sectionNumberList) {
		this.sectionNumberList = sectionNumberList;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public GregorianCalendar getSemesterStart() {
		return semesterStart;
	}

	public void setSemesterStart(GregorianCalendar semesterStart) {
		this.semesterStart = semesterStart;
	}

	public GregorianCalendar getSemesterEnd() {
		return semesterEnd;
	}

	public void setSemesterEnd(GregorianCalendar semesterEnd) {
		this.semesterEnd = semesterEnd;
	}

	public ProgramChair getProgramChair() {
		return programChair;
	}

	public void setProgramChair(ProgramChair programChair) {
		this.programChair = programChair;
	}

	public List<Instructor> getInstructorList() {
		return instructorList;
	}

	public void setInstructorList(List<Instructor> instructorList) {
		this.instructorList = instructorList;
	}

	public int getAnticipatedEnrollment() {
		return anticipatedEnrollment;
	}

	public void setAnticipatedEnrollment(int anticipatedEnrollment) {
		this.anticipatedEnrollment = anticipatedEnrollment;
	}

	/**
	 * Adds a SectionOrder object to the database.
	 */
	public void addToDatabase() {
		/*
		 * 'true' makes it COMMIT to the database 'this' passes instance of the
		 * SectionOrder object in to be saved in the db.
		 */
		SectionOrderStorage.getSectionOrderStorage().addSectionOrderToDatabase(
				true, this);
	}

	/**
	 * Returns information regarding a SectionOrder object.
	 */
	@Override
	public String toString() {
		return "SectionOrder [department=" + getDepartment() + ", course="
				+ getCourse() + ", bookList=" + getBookList()
				+ ", sectionList=" + getSectionNumberList() + ", semester="
				+ getSemester() + ", semesterStart=" + getSemesterStart()
				+ ", semesterEnd=" + getSemesterEnd() + ", programChar="
				+ getProgramChair() + ", instructorList=" + getInstructorList()
				+ ", anticipatedEnrollment=" + getAnticipatedEnrollment() + "]";
	}

	/**
	 * Returns a Boolean value that represents if two SectionOrder objects are
	 * equal. Overrides default equals() and makes objects equal only if they
	 * are both Person objects with that same department, course,
	 * sectionNumberList, and semester
	 * 
	 * @return boolean -value that represents if two Person objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;

		if (!(obj instanceof SectionOrder)) {
			return result;
		}
		// Temporary SectionOrder object created from argument
		SectionOrder paramSectionOrder = ((SectionOrder) (obj));

		if (department == paramSectionOrder.getDepartment()
				&& course == paramSectionOrder.getCourse()
				&& sectionNumberList == paramSectionOrder
						.getSectionNumberList()
				&& semester == paramSectionOrder.getSemester()) {
			result = true;
		}
		return result;
	}

}
