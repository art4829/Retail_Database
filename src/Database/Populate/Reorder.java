package Database.Populate;

public class Reorder {
    private String vendor_id;
    private String UPC;
    private String amount;
    private String store_id;

    public Reorder(String vendor_id, String UPC, String amount, String store_id) {
        this.vendor_id = vendor_id;
        this.UPC = UPC;
        this.amount = amount;
        this.store_id = store_id;
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
