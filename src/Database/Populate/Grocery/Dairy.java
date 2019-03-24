package Database.Populate.Grocery;

public class Dairy {
    private String UPC;
    private String form;
    private String fat_level;

    public Dairy(String[] data) {
        this.UPC = data[0];
        this.form = data[1];
        this.fat_level = data[2];
    }

    public String getUPC() {
        return UPC;
    }

    public String getForm() {
        return form;
    }

    public String getFat_level() {
        return fat_level;
    }
}
