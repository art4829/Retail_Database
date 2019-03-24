package Database.Populate.Pantry;

public class Pantry {

    private String PanUPC;
    private String type;
    private String container;

    public Pantry(String[] data) {
        this.PanUPC = data[0];
        this.type = data[1];
        this.container = data[2];
    }

    public String getPanUPC() {
        return PanUPC;
    }

    public String getType() {
        return type;
    }

    public String getContainer() {
        return container;
    }
}
