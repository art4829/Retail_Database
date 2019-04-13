package Database.Populate;

public class Includes {
    private String order_id;
    private String UPC;
    private String amountToBuy;



    public Includes(String[] data) {
        this.order_id = data[0];
        this.UPC = data[1];
        this.amountToBuy=data[2];
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getUPC() {
        return UPC;
    }

    public String getAmountToBuy() {
        return amountToBuy;
    }
}
