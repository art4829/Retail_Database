package Database.Populate;

public class Reorder {
    private String reorder_id;
    private String vendor_id;
    private String UPC;
    private String amount;
    private String store_id;

    public Reorder(String[] data) {
        this.reorder_id=data[0];
        this.vendor_id = data[1];
        this.UPC = data[2];
        this.amount = data[3];
        this.store_id = data[4];
    }

    public String getReorder_id() {
        return reorder_id;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public String getUPC() {
        return UPC;
    }

    public String getAmount() {
        return amount;
    }

    public String getStore_id() {
        return store_id;
    }
}
