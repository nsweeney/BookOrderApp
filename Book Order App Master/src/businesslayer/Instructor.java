package businesslayer;

/**
 * Used to store information about instructors associated with a section order.
 */
public class Instructor extends Employee {

	/**
	 * Instructor class constructor
	 * 
	 * @param firstName
	 *            String - Instructor first name
	 * @param lastName
	 *            String - Instructor last name
	 * @param phoneNumber
	 *            String - Instructor phone number
	 * @param email           
	 *            String - Instructor email address
	 */
	public Instructor(String firstName, String lastName, String phoneNumber, String email) {
		super(firstName, lastName, phoneNumber, email);
	}

}
