package app.model;

/**
 * @author Eldair F. Dornelles
 */
public class Customer {

    private int id;
    private String name;
    private String cpf;
    private String email;
    private String homePhone;
    private String cellPhone;
    private Address address;

    public Customer() {
    }

    public Customer(String name, String cpf, String email, String homePhone, String cellPhone, Address address) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.homePhone = homePhone;
        this.cellPhone = cellPhone;
        this.address = address;
    }

    
    public Customer(int id, String name, String cpf, String email, String homePhone, String cellPhone, Address address) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.homePhone = homePhone;
        this.cellPhone = cellPhone;
        this.address = address;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name=" + name + ", cpf=" + cpf + ", email=" + email + ", homePhone=" + homePhone + ", cellPhone=" + cellPhone + ", address=" + address + '}';
    }

}
