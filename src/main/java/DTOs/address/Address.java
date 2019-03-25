package DTOs.address;

/**
 * @author Rasmus Sander Larsen
 */
public class Address implements IAddress {

    /*
    -------------------------- Fields --------------------------
     */
    
    private String country;
    private String city;
    private String street;
    private int zip;
    private int houseNo;
    
    /*
    ----------------------- Constructor -------------------------
     */
    
    public Address() {}
    
    /*
    ------------------------ Properties -------------------------
     */

    // <editor-folder desc="Properties"

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public int getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(int houseNo) {
        this.houseNo = houseNo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    // </editor-folder>
    
    /*
    ---------------------- Public Methods -----------------------
     */
    
    
    
    /*
    ---------------------- Support Methods ----------------------
     */


}
