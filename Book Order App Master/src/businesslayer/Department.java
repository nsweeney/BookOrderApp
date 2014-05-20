package businesslayer;

/**
 * Used in the creation of a department.
 */
public class Department {
	/**
	 * Unique id of the department for data storage purposes.
	 */
	private String id;
	/**
	 * The name of the department.
	 */
	private String name;
	/**
	 * The chair of the department.
	 */
	private Person chair;

	/**
	 * Department class constructor.
	 * 
	 * @param name
	 *            (String) - Name of the department.
	 * @param chair
	 *            (Person) - Chair of the department.
	 * @param id
	 *            (int) - Department identification number.
	 */
	public Department(String id, String name, Person chair) {
		setId(id);
		setName(name);
		setChair(chair);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Person getChair() {
		return chair;
	}

	public void setChair(Person chair) {
		this.chair = chair;
	}

	/**
	 * Overridden toString method used to display info about a Department
	 * object.
	 */
	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", chair=" + chair
				+ "]";
	}

	/**
	 * Overridden equals method used to check for equality between two
	 * Department objects.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Department)) {
			return false;
		}
		Department other = (Department) obj;
		if (chair == null) {
			if (other.chair != null) {
				return false;
			}
		} else if (!chair.equals(other.chair)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
}