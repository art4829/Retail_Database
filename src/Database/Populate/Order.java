package Database.Populate;

public class Order {

    private String order_id;
    private String num;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String customer_id;

    public Order(String[] data) {
        this.order_id = data[0];
        this.num = data[1];
        this.street = data[2];
        this.city = data[3];
        this.state = data[4];
        this.zip = data[5];
        this.customer_id = data[6];
    }

    public String getOrder_id() {
        return order_id;
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

    public String getCustomer_id() {
        return customer_id;
    }
}
