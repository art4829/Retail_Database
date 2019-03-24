package Database.Populate.Bakery;

public class Bread {
    private String UPC;
    private String type;
    private String form;

    public Bread(String[] data) {
        this.UPC = data[0];
        this.type = data[1];
        this.form = data[2];
    }

    public String getUPC() {
        return UPC;
    }

    public String getType() {
        return type;
    }

    public String getForm() {
        return form;
    }
}
