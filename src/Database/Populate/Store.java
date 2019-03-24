package Database.Populate;

public class Store {
    private String store_id;
    private String num;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String start_hour;
    private String end_hour;

    public Store(String[] data) {
        this.store_id = data[0];
        this.num = data[1];
        this.street = data[2];
        this.city = data[3];
        this.state = data[4];
        this.zip = data[5];
        this.start_hour = data[6];
        this.end_hour = data[7];
    }

    public String getStore_id() {
        return store_id;
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

    public String getStart_hour() {
        return start_hour;
    }

    public String getEnd_hour() {
        return end_hour;
    }
}
