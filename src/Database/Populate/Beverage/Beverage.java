package Database.Populate.Beverage;

public class Beverage {
    private String UPC;
    private String flavor;
    private String serving;
    private String storage;

    public Beverage(String[] data) {
        this.UPC = data[0];
        this.flavor = data[1];
        this.serving = data[2];
        this.storage = data[3];
    }

    public String getUPC() {
        return UPC;
    }

    public String getFlavor() {
        return flavor;
    }

    public String getServing() {
        return serving;
    }

    public String getStorage() {
        return storage;
    }
}
