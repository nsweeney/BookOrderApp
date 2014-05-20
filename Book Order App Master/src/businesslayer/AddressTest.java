package businesslayer;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * Used to test an Address object.
 */
public class AddressTest {

	/**
	 * Street of the address
	 */
	private String street1 = "1234 Main St.";
	private String street2 = "4321 First Ave.";
	/**
	 * City of the address
	 */
	private String city1 = "Anytown";
	private String city2 = "Thistown";
	/**
	 * State of the address
	 */
	private String state1 = "FL";
	private String state2 = "TX";
	/**
	 * Postal Code of the address
	 */
	private String postalCode1 = "32901";
	private String postalCode2 = "78586";
	/**
	 * Country of the address
	 */
	private String country1 = "USA";
	private String country2 = "Mexico";

	private Address TestAddressObject1; // Should match #2
	private Address TestAddressObject2; // Should match #1
	private Address TestAddressObject3; // Should not match any
	private Address TestAddressObject4; // Should not match any
	private Address TestAddressObject5; // Should not match any
	private Address TestAddressObject6; // Should not match any
	private Address TestAddressObject7; // Should not match any

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		TestAddressObject1 = new Address(street1, city1, state1, postalCode1,
				country1);
		TestAddressObject2 = new Address(street1, city1, state1, postalCode1,
				country1);
		TestAddressObject3 = new Address(street2, city1, state1, postalCode1,
				country1);
		TestAddressObject4 = new Address(street1, city2, state1, postalCode1,
				country1);
		TestAddressObject5 = new Address(street1, city1, state2, postalCode1,
				country1);
		TestAddressObject6 = new Address(street1, city1, state1, postalCode2,
				country1);
		TestAddressObject7 = new Address(street1, city1, state1, postalCode1,
				country2);

	}

	@After
	public void tearDown() throws Exception {

		street1 = null;
		street2 = null;
		city1 = null;
		city2 = null;
		state1 = null;
		state2 = null;
		postalCode1 = null;
		postalCode2 = null;
		country1 = null;
		country2 = null;

		TestAddressObject1 = null;
		TestAddressObject2 = null;
		TestAddressObject3 = null;
		TestAddressObject4 = null;
		TestAddressObject5 = null;
		TestAddressObject6 = null;
		TestAddressObject7 = null;
	}

	@Test
	public void testEqualsObject() {

		assertTrue("Test Address Order 1 and 2 should equal each other",
				TestAddressObject1.equals(TestAddressObject2));
		assertFalse(
				"Test Address Order 1 and 3 should not equal each other - Street",
				TestAddressObject1.equals(TestAddressObject3));
		assertFalse(
				"Test Address Order 1 and 4 should not equal each other - City",
				TestAddressObject1.equals(TestAddressObject4));
		assertFalse(
				"Test Address Order 1 and 5 should not equal each other - State",
				TestAddressObject1.equals(TestAddressObject5));
		assertFalse(
				"Test Address Order 1 and 6 should not equal each other - Postal Code",
				TestAddressObject1.equals(TestAddressObject6));
		assertFalse(
				"Test Address Order 1 and 7 should not equal each other - Country",
				TestAddressObject1.equals(TestAddressObject7));
	}

}
