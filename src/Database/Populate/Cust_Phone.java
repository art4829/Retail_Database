package Database.Populate;

public class Cust_Phone {
    private String customer_id;
    private String phone;

    public Cust_Phone(String[] data) {
        this.customer_id = data[0];
        this.phone = data[1];
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getPhone() {
        return phone;
    }
}
