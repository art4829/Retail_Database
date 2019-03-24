package Database.Populate.Bakery;

public class Pastry {
    private String UPC;
    private String filling;
    private String readiness;

    public Pastry(String[] data) {
        this.UPC = data[0];
        this.filling = data[1];
        this.readiness = data[2];
    }

    public String getUPC() {
        return UPC;
    }

    public String getFilling() {
        return filling;
    }

    public String getReadiness() {
        return readiness;
    }
}
