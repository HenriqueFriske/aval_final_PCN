package app.model;

/**
 *
 * @author Eldair
 */
public class Address {
    private String street;
    private String addressNumber;
    private String neighborhood;
    private String city;
    private String zipCode;
    private String uf;

    public Address() {
    }

    
    public Address(String street, String addressNumber, String neighborhood, String city, String zipCode, String uf) {
        this.zipCode = zipCode;
        this.street = street;
        this.addressNumber = addressNumber;
        this.neighborhood = neighborhood;
        this.city = city;
        this.uf = uf;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public String toString() {
        return "Address{"  + " address=" + street + ", addressNumber=" + addressNumber + ", neighborhood=" + neighborhood + ", city=" + city + ", zipCode=" + zipCode + ", uf=" + uf + '}';
    }
        
}
