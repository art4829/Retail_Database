package Database.Populate;

public class Brand {
    private String brand_id;
    private String brand_name;

    public Brand(String[] data) {
        this.brand_id = data[0];
        this.brand_name = data[1];
    }

    public String getBrand_id() {
        return brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }
}
