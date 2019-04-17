package fr.eni.carsharingaire.pojo;


public class Records {

    private String recordid;

    private Fields fields;

    public String getRecordid ()
    {
        return recordid;
    }

    public void setRecordid (String recordid)
    {
        this.recordid = recordid;
    }

    public Fields getFields ()
    {
        return fields;
    }

    public void setFields (Fields fields)
    {
        this.fields = fields;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [recordid = "+recordid+", fields = "+fields+"]";
    }

}
