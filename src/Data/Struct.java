package Data;

public class Struct implements Comparable<Struct>{
    private String name;
    private int place;

    Struct(String i_name,int i_place)
    {
        this.name = i_name;
        this.place = i_place;
    }

    public int getPlace() {
        return place;
    }

    public String getName() {
        return name;
    }


    @Override
    public int compareTo(Struct part) {
        if (getPlace() == 0 || part.getPlace() == 0) {
            return 0;
        }
        if(getPlace()>part.getPlace())
            return 1;
        else if(getPlace()<part.getPlace())
            return -1;
        else
            return 0;
        //return getPlace().compareTo(part.getPlace());
    }

}
