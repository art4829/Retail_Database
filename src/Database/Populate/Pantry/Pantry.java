package Database.Populate.Pantry;

public class Pantry {

    private String UPC;
    private String type;
    private String container;

    public Pantry(String[] data) {
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
