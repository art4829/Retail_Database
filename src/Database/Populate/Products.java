package Database.Populate;

import java.util.function.DoubleBinaryOperator;

public class Products {
    private String UPC;
    private String name;
    private String size;
    private String price;
    private String vendor_id;
    private String brand_id;

    public String getUPC() {
        return UPC;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getPrice() {
        return price;
    }

    public String getVendor_id() {
        return vendor_id;
    }

    public String getBrand_id() {
        return brand_id;
    }


    public Products(String[] data) {
        this.UPC =  data[0];
        this.name = data[1];
        this.size = data[2];
        this.price =  data[3];
        this.vendor_id =  data[4];
        this.brand_id =  data[5];

    }
}
