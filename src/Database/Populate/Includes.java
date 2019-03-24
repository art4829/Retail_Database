package Database.Populate;

public class Includes {
    private String order_id;
    private String UPC;

    public Includes(String[] data) {
        this.order_id = data[0];
        this.UPC = data[1];
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getUPC() {
        return UPC;
    }
}
