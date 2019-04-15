package Parts;

public class PartStruct {

    private String Manufacture;
    private String Model;

    public PartStruct(){}

    public PartStruct(String i_manufacture, String i_model) {
        Manufacture = i_manufacture;
        Model = i_model;
    }

    public String getManufacture() {
        return Manufacture;
    }

    public String getModel() {
        return Model;
    }

}
