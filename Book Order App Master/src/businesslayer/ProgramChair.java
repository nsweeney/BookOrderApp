package businesslayer;

/**
 * Used to store information about the program chair associated with a section
 * order.
 */
public class ProgramChair extends Employee {

	/**
	 * ProgramChair class constructor
	 * 
	 * @param firstName
	 *            String - Program chair first name
	 * @param lastName
	 *            String - Program chair last name
	 * @param phoneNumber
	 *            String - Program chair phone number
	 * @param email
	 *            String - Program chair email address
	 */
	public ProgramChair(String firstName, String lastName, String phoneNumber,
			String email) {
		super(firstName, lastName, phoneNumber, email);
	}

}
