package edu.ung.hughs.jobscheduler;

import java.sql.Date;
import java.util.ArrayList;

public class Board {
    public int getBoardID() {
        return boardID;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public ArrayList<String> getStatusList() { return statusList; }

    @Override
    public String toString()
    {
        return name;
    }

    private int boardID, createdBy;
    private String name,description, timeCreated;
    private Date dateCreated;
    private ArrayList<String> statusList;
    //private Time timeCreated;

    public Board (int boardID, int createdBy, String name, String description, Date dateCreated, String timeCreated, ArrayList<String> statusList)
    {
        this.boardID = boardID;
        this.createdBy = createdBy;
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
        this.timeCreated = timeCreated;
        this.statusList = statusList;
    }

}
