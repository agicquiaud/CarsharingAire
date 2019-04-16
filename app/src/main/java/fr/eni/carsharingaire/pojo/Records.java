package fr.eni.carsharingaire.pojo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

@Entity(tableName = "parking")
public class Records {

    @PrimaryKey()
    private String recordid;

    private String datasetid;
    private Geometry geometry;
    private Fields fields;

    private String record_timestamp;

    public String getRecordid ()
    {
        return recordid;
    }

    public void setRecordid (String recordid)
    {
        this.recordid = recordid;
    }

    public String getDatasetid ()
    {
        return datasetid;
    }

    public void setDatasetid (String datasetid)
    {
        this.datasetid = datasetid;
    }

    public Geometry getGeometry ()
    {
        return geometry;
    }

    public void setGeometry (Geometry geometry)
    {
        this.geometry = geometry;
    }

    public Fields getFields ()
    {
        return fields;
    }

    public void setFields (Fields fields)
    {
        this.fields = fields;
    }

    public String getRecord_timestamp ()
    {
        return record_timestamp;
    }

    public void setRecord_timestamp (String record_timestamp)
    {
        this.record_timestamp = record_timestamp;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [recordid = "+recordid+", datasetid = "+datasetid+", geometry = "+geometry+", fields = "+fields+", record_timestamp = "+record_timestamp+"]";
    }

}
