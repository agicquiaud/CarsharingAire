package fr.eni.carsharingaire.pojo;


public class Parkings {

    private String nhits;

    private Records[] records;

    private Parameters parameters;

    public String getNhits ()
    {
        return nhits;
    }

    public void setNhits (String nhits)
    {
        this.nhits = nhits;
    }

    public Records[] getRecords ()
    {
        return records;
    }

    public void setRecords (Records[] records)
    {
        this.records = records;
    }

    public Parameters getParameters ()
    {
        return parameters;
    }

    public void setParameters (Parameters parameters)
    {
        this.parameters = parameters;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [nhits = "+nhits+", records = "+records+", parameters = "+parameters+"]";
    }
}
