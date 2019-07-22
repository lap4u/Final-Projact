package Parts;

public class OS {

    private String Manufacture;
    private String Version;
    private int Series;
    private int BitSize;

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
