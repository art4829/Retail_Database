package Database.Populate.Grocery;

public class Produce {
    private String UPC;
    private String type;
    private String container;

    public Produce(String[] data) {
        this.UPC = data[0];
        this.type = data[1];
        this.container = data[2];
    }

    public String getUPC() {
        return UPC;
    }

    public String getType() {
        return type;
    }

    public String getContainer() {
        return container;
    }
}

