package frontend;

import businesslayer.Address;
import businesslayer.Book;
import businesslayer.Department;
import businesslayer.Employee;
import businesslayer.Person;

public class TestOne {

	public static void main(String[] args) {

		Address address1 = new Address("111 E Cove St", "Aurora", "IL",
				"32817", "US");
		Person person1 = new Person("Wayne", "Campbell", "321-321-3211");
		Department department1 = new Department("1", "IT", person1);

		Book book1 = new Book("v123abc", 29.99, true, "Test Book Name",
				"Test Author", "Test Publisher",
				Book.BookUseRequirement.REQUIRED, false);
		Employee emp1 = new Employee("empFName", "empLName", "321-312-3211", "emp@emp.org");

		System.out.println(person1);
		System.out.println(department1);
		System.out.println(book1);
		System.out.println(emp1);

	}

}
