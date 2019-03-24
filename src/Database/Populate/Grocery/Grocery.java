package Database.Populate.Grocery;

public class Grocery {

    private String UPC;
    private String storage;
    private String readiness;

    public Grocery(String[] data) {
        this.UPC = data[0];
        this.storage = data[1];
        this.readiness = data[2];
    }

    public String getUPC() {
        return UPC;
    }

    public String getStorage() {
        return storage;
    }

    public String getReadiness() {
        return readiness;
    }
}
