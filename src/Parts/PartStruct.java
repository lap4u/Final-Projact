package Parts;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity

public class
PartStruct implements Serializable {

private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue

    private long id;

 @JsonProperty("manufactor")
    private String Manufacture;
	
	@JsonProperty("model")
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
