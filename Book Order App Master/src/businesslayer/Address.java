package businesslayer;

/**
 * Stores detailed address information. Used in the constructor of the Person
 * class.
 * 
 */
public class Address {

	/**
	 * Street of the address
	 */
	private String street;
	/**
	 * City of the address
	 */
	private String city;
	/**
	 * State of the address
	 */
	private String state;
	/**
	 * Postal Code of the address
	 */
	private String postalCode;
	/**
	 * Country of the address
	 */
	private String country;

	/**
	 * Address class constructor, takes in detailed address info.
	 * 
	 * @param street
	 *            String -represents the street of the address
	 * @param city
	 *            String -represents the city of the address
	 * @param state
	 *            String -represents the state of the address
	 * @param postalCode
	 *            String -represents the postal code of the address
	 * @param country
	 *            String -represents the country of the address
	 */
	public Address(String street, String city, String state, String postalCode,
			String country) {
		setStreet(street);
		setCity(city);
		setState(state);
		setPostalCode(postalCode);
		setCountry(country);
	}

	/**
	 * Returns a String that represents the addresses street
	 * 
	 * @return String -representing the addresses street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Sets the street part of the address.
	 * 
	 * @param street
	 *            String -addresses street name
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Returns a String that represents the addresses city
	 * 
	 * @return String -addresses city name
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city name of the address.
	 * 
	 * @param city
	 *            String -addresses city name
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Returns a String that represents the addresses state
	 * 
	 * @return String -addresses state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state part of the address.
	 * 
	 * @param state
	 *            String -addresses state name
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Returns the String that represents the addresses postal code
	 * 
	 * @return String -addresses postal code
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * Sets the postal code part of the address.
	 * 
	 * @param postalCode
	 *            String -addresses postal code
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * Returns the String that represents the addresses country
	 * 
	 * @return String -addresses country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country part of the address.
	 * 
	 * @param country
	 *            String -addresses country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Returns Address objects attribute information. Overrides default
	 * toString() to include output of all attributes in Address class.
	 * 
	 * @return String -Address objects attribute information
	 */
	@Override
	public String toString() {
		return "Address [street=" + street + ", city=" + city + ", state="
				+ state + ", postalCode=" + postalCode + ", country=" + country
				+ "]";
	}

	/**
	 * Returns a Boolean value that represents if two Address objects are equal.
	 * Overrides default equals() and makes objects equal only if they are both
	 * Address objects that have the same street, city, state, postalCode and
	 * country.
	 * 
	 * @return boolean -represents if two Address objects are equal or not
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;

		if (!(obj instanceof Address)) {
			return result;
		}
		// Temp. Address object created from argument
		Address paramAddress = ((Address) (obj));

		if (street == paramAddress.getStreet()
				&& city == paramAddress.getCity()
				&& state == paramAddress.getState()
				&& postalCode == paramAddress.getPostalCode()
				&& country == paramAddress.getCountry()) {
			result = true;
		}
		return result;
	}

}
