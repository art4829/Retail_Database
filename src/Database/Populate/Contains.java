package Database.Populate;

public class Contains {
    private String store_id;
    private String UPC;
    private String amount;

    public Contains(String[] data) {
        this.store_id = data[0];
        this.UPC = data[1];
        this.amount = data[2];
    }

    public String getStore_id() {
        return store_id;
    }

    public String getUPC() {
        return UPC;
    }

    public String getAmount() {
        return amount;
    }
}
