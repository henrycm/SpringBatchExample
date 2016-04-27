package com.jhcm.batch.jobs.summaryreport.model;

import java.util.Date;

public class SummaryMediaPlaceHolder
{
    private String username;
    private Date date;
    private int numFiles;

    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

    public int getNumFiles()
    {
        return numFiles;
    }

    public void setNumFiles( int numFiles )
    {
        this.numFiles = numFiles;
    }

}
