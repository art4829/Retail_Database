package Database.Populate;

public class Customer {

    private int customer_id;
    private String first_name;
    private String last_name;
    private int num;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String email;
    private String password;

    public Customer(String[] data) {
        this.customer_id = Integer.parseInt(data[0]);
        this.first_name = data[1];
        this.last_name = data[2];
        this.num = Integer.parseInt(data[3]);
        this.street = data[4];
        this.city = data[5];
        this.state = data[6];
        this.zip = data[7];
        this.email = data[8];
        this.password = data[9];
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public int getNum() {
        return num;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword(){ return password;}
}

