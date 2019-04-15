package fr.eni.carsharingaire.pojo;

public class Parameters {

    private String timezone;

    private String format;

    private String rows;

    private String[] dataset;

    public String getTimezone ()
    {
        return timezone;
    }

    public void setTimezone (String timezone)
    {
        this.timezone = timezone;
    }

    public String getFormat ()
    {
        return format;
    }

    public void setFormat (String format)
    {
        this.format = format;
    }

    public String getRows ()
    {
        return rows;
    }

    public void setRows (String rows)
    {
        this.rows = rows;
    }

    public String[] getDataset ()
    {
        return dataset;
    }

    public void setDataset (String[] dataset)
    {
        this.dataset = dataset;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [timezone = "+timezone+", format = "+format+", rows = "+rows+", dataset = "+dataset+"]";
    }

}
