package Database.Populate.Grocery;

public class Meat {
    private String UPC;
    private String type;
    private String serving;


    public Meat(String[] data) {
        this.UPC = data[0];
        this.type = data[1];
        this.serving = data[2];
    }

    public String getUPC() {
        return UPC;
    }

    public String getType() {
        return type;
    }

    public String getServing() {
        return serving;
    }
}
