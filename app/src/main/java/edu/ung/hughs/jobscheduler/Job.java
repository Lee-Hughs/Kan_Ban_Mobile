package edu.ung.hughs.jobscheduler;

import java.sql.Date;

public class Job {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getBoardID() {
        return boardID;
    }

    public void setBoardID(int boardID) {
        this.boardID = boardID;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    @Override
    public String toString(){   return name;    }

    private String name, desc, timeCreated, status, createdBy;
    private int jobID, boardID;
    private Date dateCreated;

    public Job(int jobID, String name, String desc, String createdBy, int boardID, Date dateCreated, String timeCreated, String status)
    {
        this.jobID = jobID;
        this.name = name;
        this.desc = desc;
        this.createdBy = createdBy;
        this.boardID = boardID;
        this.dateCreated = dateCreated;
        this.timeCreated = timeCreated;
        this.status = status;
    }
}
