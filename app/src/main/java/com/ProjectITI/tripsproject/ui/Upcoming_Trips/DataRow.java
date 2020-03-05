package com.ProjectITI.tripsproject.ui.Upcoming_Trips;

public class DataRow {

    public String TripName;
    private String From;
    private String To;
    private String Data;
    private String Time;
    private long img;

    public DataRow(String tripName, String from, String to, String data, String time) {
        TripName = tripName;
        From = from;
        To = to;
        Data = data;
        Time = time;

    }

    public String getTripName() {
        return TripName;
    }

    public String getFrom() {
        return From;
    }

    public String getTo() {
        return To;
    }

    public String getData() {
        return Data;
    }

    public String getTime() {
        return Time;
    }

    public long getImg() {
        return img;
    }
}
