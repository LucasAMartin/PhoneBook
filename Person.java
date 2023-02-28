import java.io.*;

public class Person implements Serializable, Comparable<Person> {

	// fields for contact information
	private String name;
	private String address;
	private String phoneNum;

	// getter methods
	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	// setter methods
	public void setName(String nameI) {
		name = nameI;
	}

	public void setAddress(String addressI) {
		address = addressI;
	}

	public void setPhoneNum(String phoneNumI) {
		phoneNum = phoneNumI;
	}

	// method for checking if a person has an address
	public boolean hasAddress() {
		if (address.isEmpty()) {
			return false;
		}
		return true;
	}

	// method for checking if a person has a name
	public boolean hasName() {
		if (name.isEmpty()) {
			return false;
		}
		return true;
	}

	// method for checking if a person has a phone num
	public boolean hasPhoneNum() {
		if (phoneNum.isEmpty()) {
			return false;
		}
		return true;
	}

	// tostring method displays relevant information
	public String toString() {
		return name + "\n" + phoneNum + "\n" + address + "\n\n";
	}

	// first constructor which takes all 3 fields
	public Person(String nameI, String addressI, String phoneNumI) {
		name = nameI;
		address = addressI;
		phoneNum = phoneNumI;

	}

	// second constructor which takes only name
	public Person(String nameI) {
		name = nameI;
	}

	// method from comparable interface, compares the names of two people
	public int compareTo(Person p) {
		if (name.equals(p.name))
			return 0;
		else if (name.charAt(0) > p.name.charAt(0))
			return 1;
		else
			return -1;
	}
}
