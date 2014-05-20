package businesslayer;

/**
 * Used in the creation of a new person.
 */
public class Person {

	/**
	 * persons first name
	 */
	private String firstName;
	/**
	 * persons last name
	 */
	private String lastName;
	/**
	 * persons phone number
	 */
	private String phoneNumber;

	/**
	 * Person class constructor, takes in detailed person info.
	 * 
	 * @param firstName
	 *            String -person first name
	 * @param lastName
	 *            String -persons last name
	 * @param phoneNumber
	 *            String -persons phone number
	 */
	public Person(String firstName, String lastName, String phoneNumber) {
		setFirstName(firstName);
		setLastName(lastName);
		setPhoneNumber(phoneNumber);
	}

	/**
	 * Returns a String representing a persons first name
	 * 
	 * @return String -persons first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets a persons first name
	 * 
	 * @param firstName
	 *            String -the name you want as the first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns a String representing a persons last name
	 * 
	 * @return String -persons last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets a persons last name
	 * 
	 * @param lastName
	 *            String -the name you want as the last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns a String representing a persons phone number
	 * 
	 * @return String -persons phone number
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets a persons phone number
	 * 
	 * @param phoneNumber
	 *            String -number you want as the phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Returns Person objects attribute information. Overrides default
	 * toString() to include output of all attributes of a Person object.
	 * 
	 * @return String -Person objects attribute information
	 */
	@Override
	public String toString() {
		return "Person [firstName=" + firstName + ", lastName=" + lastName
				+ ", phoneNumber=" + phoneNumber + "]";
	}

	/**
	 * Returns a Boolean value that represents if two Person objects are equal.
	 * Overrides default equals() and makes objects equal only if they are both
	 * Person objects with that same firstName, lastName, and phoneNumber.
	 * 
	 * @return boolean -value that represents if two Person objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;

		if (!(obj instanceof Person)) {
			return result;
		}
		// Temp. Person object created from argument
		Person paramPerson = ((Person) (obj));

		if (firstName == paramPerson.getFirstName()
				&& lastName == paramPerson.getLastName()
				&& phoneNumber == paramPerson.getPhoneNumber()) {
			result = true;
		}
		return result;
	}

}
