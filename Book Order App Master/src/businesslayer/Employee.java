package businesslayer;

/**
 * Used to store info about a Person who is-a Employee. Adds to the Person class
 * by adding an email address.
 */
public class Employee extends Person {

	/**
	 * Employees email address.
	 */
	private String email;

	/**
	 * Employee class constructor
	 * 
	 * @param firstName
	 *            String - employee first name
	 * @param lastName
	 *            String - employee last name
	 * @param phoneNumber
	 *            String - employee phone number
	 * @param email
	 *            String - employee email address
	 */
	public Employee(String firstName, String lastName, String phoneNumber,
			String email) {
		super(firstName, lastName, phoneNumber);
		setEmail(email);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns all the valid instance variable info for an employee object.
	 * 
	 * @return String -all the valid instance variable info for an Employee
	 *         object.
	 */
	@Override
	public String toString() {
		return "Employee [firstName=" + getFirstName() + ", lastName="
				+ getLastName() + ", phoneNumber=" + getPhoneNumber()
				+ ", email=" + getEmail() + "]";
	}

	/**
	 * Returns true is the objects have matching first and last names, phone
	 * numbers, and email addresses.
	 * 
	 * @param obj
	 *            -The object you want to check for equality too.
	 * @return boolean -Whether the objects are equal or not.
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;

		if (!(obj instanceof Employee)) {
			return result;
		}
		// Temp. Employee object created from argument
		Employee paramEmployee = ((Employee) (obj));

		if (getFirstName().equals(paramEmployee.getFirstName())
				&& getLastName().equals(paramEmployee.getLastName())
				&& getPhoneNumber().equals(paramEmployee.getPhoneNumber())
				&& getEmail().equals(paramEmployee.getEmail())) {
			result = true;
		}

		return result;
	}

}
