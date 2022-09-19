import java.io.Serializable;

public class Vendor  implements Serializable {
	//need this field for the serialization
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Address address;
	
	public Vendor(String name,Address address) {
		this.name = name;
		this.address = address;
	}
	@Override
	public String toString() {
		return this.name;
		
	}
	public String getName() {
		return name;
	}
	public String getStreetAddress() {
		return address.getStreet();
	}
	public USState getState() {
		return address.getState();
	}
	public String city() {
		return address.getCity();
	}
	public String zip() {
		return address.getZipcode();
	}
}
