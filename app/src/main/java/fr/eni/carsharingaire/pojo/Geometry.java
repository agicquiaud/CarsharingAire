package fr.eni.carsharingaire.pojo;

public class Geometry {

    private int id;

    private String[] coordinates;

    private String type;

    public String[] getCoordinates ()
    {
        return coordinates;
    }

    public void setCoordinates (String[] coordinates)
    {
        this.coordinates = coordinates;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [coordinates = "+coordinates+", type = "+type+"]";
    }

}
