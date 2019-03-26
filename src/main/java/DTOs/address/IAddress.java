package DTOs.address;

public interface IAddress {
	
	// Methods
	String getStreet();
	void setStreet(String street);
	
	String getCity();
	void setCity(String city);
	
	int getZip();
	void setZip(int zip);
	
	int getHouseNo();
	void setHouseNo(int houseNo);
	
	String getCountry();
	void setCountry(String country);
}
