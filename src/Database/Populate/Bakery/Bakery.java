package Database.Populate.Bakery;

public class Bakery {
    private String UPC;
    private String serving;
    private String expiration_date;

    public Bakery(String[] data) {
        this.UPC = data[0];
        this.serving = data[1];
        this.expiration_date = data[2];
    }

    public String getUPC() {
        return UPC;
    }

    public String getServing() {
        return serving;
    }

    public String getExpiration_date() {
        return expiration_date;
    }
}
