package businesslayer;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DepartmentTest {

	/**
	 * Unique id of the department for data storage purposes.
	 */
	private String id1 = "ID1";
	private String id2 = "ID2";
	/**
	 * The name of the department.
	 */
	private String name1 = "Alpha";
	private String name2 = "Beta";
	/**
	 * The chair of the department.
	 */
	private Person chair1 = new Person("Nicholas", " Sweeney", "321-555-2121");
	private Person chair2 = new Person("Joseph", "Tomarazzo", "321-555-1212");

	private Department TestDepartmentObject1; // Should match #2
	private Department TestDepartmentObject2; // Should match #1
	private Department TestDepartmentObject3; // Should not match any
	private Department TestDepartmentObject4; // Should not match any
	private Department TestDepartmentObject5; // Should not match any

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		TestDepartmentObject1 = new Department(id1, name1, chair1);
		TestDepartmentObject2 = new Department(id1, name1, chair1);
		TestDepartmentObject3 = new Department(id2, name1, chair1);
		TestDepartmentObject4 = new Department(id1, name2, chair1);
		TestDepartmentObject5 = new Department(id1, name1, chair2);

	}

	@After
	public void tearDown() throws Exception {

		id1 = null;
		id2 = null;
		name1 = null;
		name2 = null;
		chair1 = null;
		chair2 = null;

		TestDepartmentObject1 = null;
		TestDepartmentObject2 = null;
		TestDepartmentObject3 = null;
		TestDepartmentObject4 = null;
		TestDepartmentObject5 = null;

	}

	@Test
	public void testEqualsObject() {

		assertTrue("Test Address Order 1 and 2 should equal each other",
				TestDepartmentObject1.equals(TestDepartmentObject2));
		assertFalse(
				"Test Address Order 1 and 3 should not equal each other - Id",
				TestDepartmentObject1.equals(TestDepartmentObject3));
		assertFalse(
				"Test Address Order 1 and 4 should not equal each other - Name",
				TestDepartmentObject1.equals(TestDepartmentObject4));
		assertFalse(
				"Test Address Order 1 and 5 should not equal each other - Chair",
				TestDepartmentObject1.equals(TestDepartmentObject5));

	}

}
