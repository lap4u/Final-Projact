package Parts;


import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity


public class OS  implements Serializable{

private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

 @JsonProperty("manufactor")
    private String Manufacture;
	 @JsonProperty("version")
    private String Version;
	@JsonProperty("series")
    private int Series;
	@JsonProperty("bit")
    private int BitSize;


    public OS()
    {



    }

    public OS(String i_manufacture, String i_version, int i_series, int i_bitSize) {
        Manufacture = i_manufacture;
        Version = i_version;
        Series = i_series;
        BitSize = i_bitSize;
    }

    public String getManufacture() {
        return Manufacture;
    }

    public int getBitSize() {
        return BitSize;
    }

    public int getSeries() {
        return Series;
    }

    public String getVersion() {
        return Version;
    }

    public void printOs()
    {
        System.out.println("Manufacture: " + Manufacture);
        System.out.println("Version: " + Version);
        System.out.println("Serie: " + Series);
        System.out.println("Bit: " + BitSize + "\n");
    }
}
