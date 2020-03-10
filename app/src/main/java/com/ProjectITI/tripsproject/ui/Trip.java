package com.ProjectITI.tripsproject.ui;

public class Trip {

    public Trip(String name, String from, String to, String time, String date, String status, String type) {
        Name = name;
        From = from;
        To = to;
        Time = time;
        Date = date;
        this.status = status;
        this.type = type;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }



    private String Name;

    private String From;

    private String To;

    private String Time;

    private String Date;

    private String status;

    private String type;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
