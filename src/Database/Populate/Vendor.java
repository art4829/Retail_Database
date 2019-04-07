package Database.Populate;

public class Vendor {
    private String vendor_id;
    private String vendor_name;
    private String num;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String account_number;
    private String password;

    public Vendor(String[] data) {
        this.vendor_id = data[0];
        this.vendor_name = data[1];
        this.num = data[2];
        this.street = data[3];
        this.city = data[4];
        this.state = data[5];
        this.zip = data[6];
        this.account_number = data[7];
        this.password = data[8];
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public String getNum() {
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

    public String getAccount_number() {
        return account_number;
    }

    public String getPassword(){
        return password;
    }
}
